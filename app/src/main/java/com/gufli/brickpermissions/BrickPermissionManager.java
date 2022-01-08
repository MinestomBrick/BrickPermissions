package com.gufli.brickpermissions;

import com.gufli.brickpermissions.data.DatabaseContext;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickpermissions.data.beans.BGroup;
import com.gufli.brickpermissions.data.beans.BGroupPermission;
import com.gufli.brickpermissions.data.beans.BPlayerGroup;
import com.gufli.brickpermissions.data.beans.BPlayerPermission;
import com.gufli.brickpermissions.data.beans.query.QBGroup;
import com.gufli.brickpermissions.data.beans.query.QBPlayerGroup;
import com.gufli.brickpermissions.data.beans.query.QBPlayerPermission;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

public class BrickPermissionManager implements PermissionManager {

    private final DatabaseContext databaseContext;

    private final Set<BGroup> groups = new CopyOnWriteArraySet<>();

    private final Map<Player, Set<BPlayerPermission>> players = new ConcurrentHashMap<>();
    private final Map<Player, Set<BPlayerGroup>> playerGroups = new ConcurrentHashMap<>();

    public BrickPermissionManager(DatabaseContext databaseContext) {
        this.databaseContext = databaseContext;
        reload();
    }

    void shutdown() {
        groups.clear();
        players.clear();
        playerGroups.clear();
    }

    void reload() {
        shutdown();

        // load groups
        groups.addAll(new QBGroup().findSet());

        // load players
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(this::load);
    }

    public void load(@NotNull Player player) {

        // load individual permissions
        Set<BPlayerPermission> playerPerms = new CopyOnWriteArraySet<>();
        players.put(player, playerPerms);
        playerPerms.addAll(new QBPlayerPermission().where().playerId.eq(player.getUuid()).findSet());

        // load group permissions
        Set<BPlayerGroup> playerGroups = new CopyOnWriteArraySet<>();
        this.playerGroups.put(player, playerGroups);
        playerGroups.addAll(new QBPlayerGroup().where().playerId.eq(player.getUuid()).findSet());
        playerGroups.forEach(pg -> {
            // replace group in playergroups with reference to global groups
            pg.group = groups.stream().filter(g -> g.name().equals(pg.group.name())).findFirst().orElse(pg.group);
        });

        // give player permissions
        playerPerms.forEach(pp -> player.addPermission(new Permission(pp.permission(), pp.data())));

        // give group permissions
        playerGroups.stream().flatMap(pg -> pg.group.groupPermissons().stream()).forEach(gp ->
                player.addPermission(new Permission(gp.permission(), gp.data())));

        // refresh command conditions for new permissions
        player.refreshCommands();
    }

    public void unload(@NotNull Player player) {
        players.remove(player);
        playerGroups.remove(player);
    }

    // INTERNAL API

    private Optional<BPlayerPermission> permissionByName(@NotNull Player player, @NotNull String permission) {
        return players.get(player).stream()
                .filter(perm -> perm.permission().equalsIgnoreCase(permission))
                .findFirst();
    }

    // PUBLIC API

    @Override
    public CompletableFuture<Void> addPermission(@NotNull Player player, @NotNull Permission permission) {
        // add to local player
        player.addPermission(permission);
        player.refreshCommands();

        // add to/update database
        Optional<BPlayerPermission> bpp = permissionByName(player, permission.getPermissionName());
        if ( bpp.isPresent() ) {
            bpp.get().setData(permission.getNBTData());
            return databaseContext.saveAsync(bpp.get());
        }

        BPlayerPermission perm = new BPlayerPermission(player.getUuid(),
                permission.getPermissionName(), permission.getNBTData());
        players.get(player).add(perm);
        return databaseContext.saveAsync(perm);
    }

    @Override
    public CompletableFuture<Void> addPermission(Player player, String permission) {
        return addPermission(player, new Permission(permission));
    }

    @Override
    public CompletableFuture<Void> removePermission(Player player, Permission permission) {
        // remove from local player
        player.removePermission(permission);
        player.refreshCommands();

        // remove from database
        Optional<BPlayerPermission> bpp = permissionByName(player, permission.getPermissionName());
        if ( bpp.isEmpty() ) {
            return CompletableFuture.completedFuture(null);
        }

        players.get(player).remove(bpp.get());
        return databaseContext.deleteAsync(bpp.get());
    }

    @Override
    public CompletableFuture<Void> removePermission(Player player, String permission) {
        return removePermission(player, new Permission(permission));
    }

    @Override
    public Set<Group> groups() {
        return Collections.unmodifiableSet(groups);
    }

    @Override
    public Set<Group> groups(Player player) {
        return playerGroups.get(player).stream().map(pg -> pg.group)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Optional<Group> group(String name) {
        return groups.stream()
                .filter(group -> group.name().equalsIgnoreCase(name))
                .map(group -> (Group) group)
                .findFirst();
    }

    @Override
    public CompletableFuture<Group> addGroup(String name) {
        if ( group(name).isPresent() )
            throw new IllegalArgumentException("A group with that name already exists.");

        // add to local cache
        BGroup group = new BGroup(name);
        groups.add(group);

        // add to database
        return databaseContext.saveAsync(group).thenApply((v) -> group);
    }

    @Override
    public CompletableFuture<Void> removeGroup(Group group) {
        // remove from local cache
        BGroup bgroup = (BGroup) group;
        groups.remove(bgroup);

        playerGroups.forEach((key, value) -> {
            // remove player groups linked to this group
            Optional<BPlayerGroup> bpg = value.stream()
                    .filter(pg -> pg.group.name().equals(group.name()))
                    .findFirst();

            if ( bpg.isPresent() ) {
                value.remove(bpg.get());
                bpg.get().group.permissions().forEach(key::removePermission);
                key.refreshCommands();
            }
        });

        // remove from database
        // player group should also be removed from database by constraints
        return databaseContext.deleteAsync(bgroup);
    }

    @Override
    public CompletableFuture<Void> addGroup(Player player, Group group) {
        Optional<BPlayerGroup> bpg = playerGroups.get(player).stream()
                .filter(pg -> pg.group.name().equals(group.name())).findFirst();

        if ( bpg.isPresent() ) {
            return CompletableFuture.completedFuture(null);
        }

        // add permissions to local player
        group.permissions().forEach(player::addPermission);
        player.refreshCommands();

        // add to cache
        BPlayerGroup pg = new BPlayerGroup(player.getUuid(), (BGroup) group);
        playerGroups.get(player).add(pg);

        // add to database
        return databaseContext.saveAsync(pg);
    }

    @Override
    public CompletableFuture<Void> removeGroup(Player player, Group group) {
        Optional<BPlayerGroup> bpg = playerGroups.get(player).stream()
                .filter(pg -> pg.group.name().equals(group.name())).findFirst();

        if (bpg.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        // remove permissions from local player
        bpg.get().group.groupPermissons().forEach(perm ->
                player.removePermission(perm.permission()));
        player.refreshCommands();

        // remove from cache
        playerGroups.get(player).remove(bpg.get());

        // remove from database
        return databaseContext.deleteAsync(bpg.get());
    }

    @Override
    public CompletableFuture<Void> addPermission(Group group, Permission permission) {
        BGroup bgroup = (BGroup) group;
        Optional<BGroupPermission> bgp = bgroup.permissionByName(permission.getPermissionName());

        // add permission to local players
        playerGroups.entrySet().stream().filter(entry -> entry.getValue().stream()
                        .anyMatch(pg -> pg.group.name().equals(group.name())))
                .findAny().map(Map.Entry::getKey)
                .ifPresent(p -> {
                    p.addPermission(permission);
                    p.refreshCommands();
                });

        // update database
        if ( bgp.isPresent() ) {
            bgp.get().setData(permission.getNBTData());
            return databaseContext.saveAsync(bgp.get());
        }

        // add to database
        BGroupPermission gp = bgroup.addPermission(permission);
        return databaseContext.saveAsync(gp);
    }

    @Override
    public CompletableFuture<Void> addPermission(Group group, String permission) {
        return addPermission(group, new Permission(permission));
    }

    @Override
    public CompletableFuture<Void> removePermission(Group group, Permission permission) {
        BGroup bgroup = (BGroup) group;
        Optional<BGroupPermission> bgp = bgroup.removePermission(permission);
        if (bgp.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        // remove permission from local players
        playerGroups.entrySet().stream().filter(entry -> entry.getValue().stream()
                        .anyMatch(pg -> pg.group.name().equals(group.name())))
                .findAny().map(Map.Entry::getKey)
                .ifPresent(p -> {
                    p.removePermission(permission.getPermissionName());
                    p.refreshCommands();
                });

        // remove from database
        return databaseContext.deleteAsync(bgp.get());
    }

    @Override
    public CompletableFuture<Void> removePermission(Group group, String permission) {
        return removePermission(group, new Permission(permission));
    }
}
