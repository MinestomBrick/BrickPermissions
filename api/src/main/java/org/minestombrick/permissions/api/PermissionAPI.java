package org.minestombrick.permissions.api;

public class PermissionAPI {

    private PermissionAPI() {}

    private static PermissionManager permissionManager;

    public static void setPermissionManager(PermissionManager manager) {
        permissionManager = manager;
    }

    //

    public static PermissionManager get() {
        return permissionManager;
    }

}
