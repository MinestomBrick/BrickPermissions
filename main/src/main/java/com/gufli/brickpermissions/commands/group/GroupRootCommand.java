package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.RootCommand;
import com.gufli.brickpermissions.commands.group.permission.GroupPermissionRootCommand;

public class GroupRootCommand extends RootCommand {

    public GroupRootCommand(BrickPermissionManager permissionManager) {
        super("group", "groups");

        addSubcommand(new GroupListCommand(permissionManager));
        addSubcommand(new GroupInfoCommand(permissionManager));

        addSubcommand(new GroupAddCommand(permissionManager));
        addSubcommand(new GroupRemoveCommand(permissionManager));

        addSubcommand(new GroupPermissionRootCommand(permissionManager));
    }

}
