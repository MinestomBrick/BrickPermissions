package com.gufli.brickpermissions.commands;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.commands.group.GroupRootCommand;
import com.gufli.brickpermissions.commands.player.PlayerRootCommand;

public class BrickPermissionRootCommand extends RootCommand {

    public BrickPermissionRootCommand(BrickPermissionManager permissionManager) {
        super("brickpermissions", "bp");

        addSubcommand(new PlayerRootCommand(permissionManager));
        addSubcommand(new GroupRootCommand(permissionManager));
    }

}
