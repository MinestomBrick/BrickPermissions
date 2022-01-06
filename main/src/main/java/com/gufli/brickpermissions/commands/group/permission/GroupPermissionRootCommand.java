package com.gufli.brickpermissions.commands.group.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.RootCommand;

public class GroupPermissionRootCommand extends RootCommand {

    public GroupPermissionRootCommand(BrickPermissionManager permissionManager) {
        super("permission", "permissions");

        addSubcommand(new GroupPermissionAddCommand(permissionManager));
        addSubcommand(new GroupPermissionRemoveCommand(permissionManager));
    }

}
