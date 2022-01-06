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
import net.minestom.server.permission.Permission;

import java.util.Optional;
import java.util.stream.Collectors;

public class GroupAddCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public GroupAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.group.add")));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp group add <name>");
        });

        // arguments
        ArgumentWord group = ArgumentType.Word("group");

        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        Optional<Group> group = permissionManager.group(groupName);
        if ( group.isPresent() ) {
            sender.sendMessage(Component.text("The group " + group.get().name() + " already exists.")); // TODO
            return;
        }

        permissionManager.addGroup(groupName);
        sender.sendMessage("Added the group " + groupName + ".");
    }

}
