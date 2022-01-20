package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.group.permission.GroupPermissionCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class GroupCommand extends BrickCommand {

    public GroupCommand(BrickPermissionManager permissionManager) {
        super("group", "groups");

        setupCommandGroupDefaults();

        addSubcommand(new GroupListCommand(permissionManager));
        addSubcommand(new GroupInfoCommand(permissionManager));

        addSubcommand(new GroupAddCommand(permissionManager));
        addSubcommand(new GroupRemoveCommand(permissionManager));

        addSubcommand(new GroupPermissionCommand(permissionManager));
    }

}
