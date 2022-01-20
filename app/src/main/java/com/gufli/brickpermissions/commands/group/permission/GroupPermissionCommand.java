package com.gufli.brickpermissions.commands.group.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickutils.commands.BrickCommand;

public class GroupPermissionCommand extends BrickCommand {

    public GroupPermissionCommand(BrickPermissionManager permissionManager) {
        super("permission", "permissions");

        setupCommandGroupDefaults();

        addSubcommand(new GroupPermissionAddCommand(permissionManager));
        addSubcommand(new GroupPermissionRemoveCommand(permissionManager));
    }

}
