package com.gufli.brickpermissions.commands;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.group.GroupCommand;
import com.gufli.brickpermissions.commands.player.PlayerCommand;
import com.gufli.brickutils.commands.BrickCommand;

public class BrickPermissionsCommand extends BrickCommand {

    public BrickPermissionsCommand(BrickPermissionManager permissionManager) {
        super("brickpermissions", "bp");

        setupCommandGroupDefaults();

        addSubcommand(new PlayerCommand(permissionManager));
        addSubcommand(new GroupCommand(permissionManager));
    }

}
