package org.minestombrick.permissions.api;

import org.minestombrick.permissions.api.data.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface PermissionManager {

    CompletableFuture<Void> addPermission(Player player, Permission permission);

    CompletableFuture<Void> addPermission(Player player, String permission);

    CompletableFuture<Void> removePermission(Player player, Permission permission);

    CompletableFuture<Void> removePermission(Player player, String permission);

    //

    Set<Group> groups();

    Set<Group> groups(Player player);

    Optional<Group> group(String name);

    CompletableFuture<Group> addGroup(String name);

    CompletableFuture<Void> removeGroup(Group group);

    CompletableFuture<Void> addGroup(Player player, Group group);

    CompletableFuture<Void> removeGroup(Player player, Group group);

    CompletableFuture<Void> addPermission(Group group, Permission permission);

    CompletableFuture<Void> addPermission(Group group, String permission);

    CompletableFuture<Void> removePermission(Group group, Permission permission);

    CompletableFuture<Void> removePermission(Group group, String permission);

}
