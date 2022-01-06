package com.gufli.brickpermissions.commands.player.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.RootCommand;

public class PlayerPermissionRootCommand extends RootCommand {

    public PlayerPermissionRootCommand(BrickPermissionManager permissionManager) {
        super("permission", "permissions");

        addSubcommand(new PlayerPermissionAddCommand(permissionManager));
        addSubcommand(new PlayerPermissionRemoveCommand(permissionManager));
    }

}
