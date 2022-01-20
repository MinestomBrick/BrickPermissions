package com.gufli.brickpermissions.commands.player.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickutils.commands.BrickCommand;

public class PlayerGroupCommand extends BrickCommand {

    public PlayerGroupCommand(BrickPermissionManager permissionManager) {
        super("group", "groups");

        setupCommandGroupDefaults();

        addSubcommand(new PlayerGroupAddCommand(permissionManager));
        addSubcommand(new PlayerGroupRemoveCommand(permissionManager));
        addSubcommand(new PlayerGroupListCommand(permissionManager));
    }

}
