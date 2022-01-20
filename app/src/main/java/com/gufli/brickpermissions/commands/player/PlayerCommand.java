package com.gufli.brickpermissions.commands.player;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.player.group.PlayerGroupCommand;
import com.gufli.brickpermissions.commands.player.permission.PlayerPermissionCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class PlayerCommand extends BrickCommand {

    public PlayerCommand(BrickPermissionManager permissionManager) {
        super("player", "players");

        setupCommandGroupDefaults();

        addSubcommand(new PlayerPermissionCommand(permissionManager));
        addSubcommand(new PlayerGroupCommand(permissionManager));

        addSubcommand(new PlayerInfoCommand(permissionManager));
    }

}
