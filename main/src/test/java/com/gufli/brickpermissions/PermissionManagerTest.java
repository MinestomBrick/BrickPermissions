package com.gufli.brickpermissions;

import com.gufli.brickpermissions.data.DatabaseContext;
import com.gufli.brickpermissions.data.Group;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PermissionManagerTest {

    private Player player;
    private BrickPermissionManager permissionManager;

    @BeforeEach
    public void init() {
        MinecraftServer.init(); // for entity manager

        // init test player
        player = new TestPlayer(UUID.randomUUID(), "TestPlayer");

        // init memory database
        try {
            DatabaseContext databaseContext = new DatabaseContext();
            databaseContext.init("jdbc:h2:mem:migrationdb;", "dbuser", "");
            permissionManager = new BrickPermissionManager(databaseContext);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        permissionManager.load(player);
    }

    @Test
    public void addAndRemoveGroup() throws ExecutionException, InterruptedException {
        final String GROUP_NAME = "TestGroup1";
        final Permission permission = new Permission("group1.permission");

        // add & check local
        permissionManager.addGroup(GROUP_NAME).get();
        Optional<Group> group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isPresent());

        // reload cache
        permissionManager.reload();

        // check persist
        group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isPresent());

        // remove & check local
        permissionManager.removeGroup(group.get()).get();
        group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isEmpty());

        // reload cache
        permissionManager.reload();

        // check persist
        group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isEmpty());
    }

    @Test
    public void addAndRemoveGroupPermission() throws ExecutionException, InterruptedException {
        final String GROUP_NAME = "TestGroup2";
        final Permission permission = new Permission("group2.permission");

        // create group
        Optional<Group> group = Optional.of(permissionManager.addGroup(GROUP_NAME).get());
        permissionManager.addGroup(player, group.get());

        // add & check local
        permissionManager.addPermission(group.get(), permission).get();
        assertTrue(group.get().permissions().contains(permission));
        assertTrue(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isPresent());
        assertTrue(group.get().permissions().contains(permission));
        assertTrue(player.hasPermission(permission));

        // remove & check local
        permissionManager.removePermission(group.get(), permission).get();
        assertFalse(group.get().permissions().contains(permission));
        assertFalse(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        group = permissionManager.group(GROUP_NAME);
        assertTrue(group.isPresent());
        assertFalse(group.get().permissions().contains(permission));
        assertFalse(player.hasPermission(permission));
    }

    @Test
    public void addAndRemovePlayerPermission() throws ExecutionException, InterruptedException {
        final Permission permission = new Permission("player.permission");

        // add & check local
        permissionManager.addPermission(player, permission).get();
        assertTrue(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        assertTrue(player.hasPermission(permission));

        // remove & check local
        permissionManager.removePermission(player, permission).get();
        assertFalse(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persit
        assertFalse(player.hasPermission(permission));
    }

    @Test
    public void addAndRemovePlayerGroup() throws ExecutionException, InterruptedException {
        final String GROUP_NAME = "TestGroup3";
        final Permission permission = new Permission("player.group3.permission");

        Optional<Group> group = Optional.of(permissionManager.addGroup(GROUP_NAME).get());
        permissionManager.addPermission(group.get(), permission).get();

        // add & check local
        permissionManager.addGroup(player, group.get()).get();
        assertTrue(permissionManager.groups(player).contains(group.get()));
        assertTrue(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        group = permissionManager.group(group.get().name());
        assertTrue(group.isPresent());
        assertTrue(permissionManager.groups(player).contains(group.get()));
        assertTrue(player.hasPermission(permission));

        // remove & check local
        permissionManager.removeGroup(player, group.get()).get();
        assertFalse(permissionManager.groups(player).contains(group.get()));
        assertFalse(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        group = permissionManager.group(group.get().name());
        assertTrue(group.isPresent());
        assertFalse(permissionManager.groups(player).contains(group.get()));
        assertFalse(player.hasPermission(permission));
    }

    @Test
    public void removeGroupWithPlayer() throws ExecutionException, InterruptedException {
        final String GROUP_NAME = "TestGroup4";
        final Permission permission = new Permission("player.group4.permission");

        Optional<Group> group = Optional.of(permissionManager.addGroup(GROUP_NAME).get());
        permissionManager.addPermission(group.get(), permission).get();
        permissionManager.addGroup(player, group.get()).get();
        assertTrue(player.hasPermission(permission));

        // remove & check local
        permissionManager.removeGroup(group.get()).get();
        assertFalse(player.hasPermission(permission));

        // reload cache
        permissionManager.reload();
        permissionManager.load(player);

        // check persist
        assertFalse(player.hasPermission(permission));
    }

}
