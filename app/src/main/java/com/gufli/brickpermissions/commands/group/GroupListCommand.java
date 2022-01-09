package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.Optional;
import java.util.stream.Collectors;

public class GroupListCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public GroupListCommand(BrickPermissionManager permissionManager) {
        super("list");
        this.permissionManager = permissionManager;

        // conditions
        setCondition((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.group.list") ||
                (sender instanceof Player p && p.getPermissionLevel() == 4));

        // usage
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        sender.sendMessage("List of groups: " + permissionManager.groups().stream()
                        .map(Group::name)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
