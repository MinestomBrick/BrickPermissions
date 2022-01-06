package com.gufli.brickpermissions.commands.player;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.RootCommand;
import com.gufli.brickpermissions.commands.player.group.PlayerGroupRootCommand;
import com.gufli.brickpermissions.commands.player.permission.PlayerPermissionRootCommand;

public class PlayerRootCommand extends RootCommand {

    public PlayerRootCommand(BrickPermissionManager permissionManager) {
        super("player", "players");

        addSubcommand(new PlayerPermissionRootCommand(permissionManager));
        addSubcommand(new PlayerGroupRootCommand(permissionManager));

        addSubcommand(new PlayerInfoCommand(permissionManager));
    }

}
