package com.gufli.brickpermissions.commands.player.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.RootCommand;
import com.gufli.brickpermissions.commands.player.permission.PlayerPermissionAddCommand;
import com.gufli.brickpermissions.commands.player.permission.PlayerPermissionRemoveCommand;

public class PlayerGroupRootCommand extends RootCommand {

    public PlayerGroupRootCommand(BrickPermissionManager permissionManager) {
        super("group", "groups");

        addSubcommand(new PlayerGroupAddCommand(permissionManager));
        addSubcommand(new PlayerGroupRemoveCommand(permissionManager));
        addSubcommand(new PlayerGroupListCommand(permissionManager));
    }

}
