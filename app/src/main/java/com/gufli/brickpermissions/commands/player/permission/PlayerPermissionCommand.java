package com.gufli.brickpermissions.commands.player.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickutils.commands.BrickCommand;

public class PlayerPermissionCommand extends BrickCommand {

    public PlayerPermissionCommand(BrickPermissionManager permissionManager) {
        super("permission", "permissions");

        setupCommandGroupDefaults();

        addSubcommand(new PlayerPermissionAddCommand(permissionManager));
        addSubcommand(new PlayerPermissionRemoveCommand(permissionManager));
    }

}
