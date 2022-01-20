package com.gufli.brickpermissions;

import com.gufli.brickpermissions.data.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PermissionAPI {

    private PermissionAPI() {}

    private static PermissionManager permissionManager;

    public static void setPermissionManager(PermissionManager manager) {
        permissionManager = manager;
    }

    public static CompletableFuture<Void> addPermission(Player player, Permission permission) {
        return permissionManager.addPermission(player, permission);
    }

    public static CompletableFuture<Void> addPermission(Player player, String permission) {
        return permissionManager.addPermission(player, permission);
    }

    public static CompletableFuture<Void> removePermission(Player player, Permission permission) {
        return permissionManager.addPermission(player, permission);
    }

    public static CompletableFuture<Void> removePermission(Player player, String permission) {
        return permissionManager.addPermission(player, permission);
    }

    //

    public static Set<Group> groups() {
        return permissionManager.groups();
    }

    public static Set<Group> groups(Player player) {
        return permissionManager.groups(player);
    }

    public static Optional<Group> group(String name) {
        return permissionManager.group(name);
    }

    public static CompletableFuture<Group> addGroup(String name) {
        return permissionManager.addGroup(name);
    }

    public static CompletableFuture<Void> removeGroup(Group group) {
        return permissionManager.removeGroup(group);
    }

    public static CompletableFuture<Void> addGroup(Player player, Group group) {
        return permissionManager.addGroup(player, group);
    }

    public static CompletableFuture<Void> removeGroup(Player player, Group group) {
        return permissionManager.removeGroup(player, group);
    }

    public static CompletableFuture<Void> addPermission(Group group, Permission permission) {
        return permissionManager.addPermission(group, permission);
    }

    public static CompletableFuture<Void> addPermission(Group group, String permission) {
        return permissionManager.addPermission(group, permission);
    }

    public static CompletableFuture<Void> removePermission(Group group, Permission permission) {
        return permissionManager.removePermission(group, permission);
    }

    public static CompletableFuture<Void> removePermission(Group group, String permission) {
        return permissionManager.removePermission(group, permission);
    }

}
