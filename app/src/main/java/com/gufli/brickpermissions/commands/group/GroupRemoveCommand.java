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

public class GroupRemoveCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public GroupRemoveCommand(BrickPermissionManager permissionManager) {
        super("remove");
        this.permissionManager = permissionManager;

        // conditions
        setCondition((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.group.remove") ||
                (sender instanceof Player p && p.getPermissionLevel() == 4));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp group remove <group>");
        });

        // arguments
        ArgumentWord group = ArgumentType.Word("group");

        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        Optional<Group> group = permissionManager.group(groupName);
        if (group.isEmpty()) {
            sender.sendMessage(Component.text("The group " + groupName + " does not exist.")); // TODO
            return;
        }

        permissionManager.removeGroup(group.get());
        sender.sendMessage("Removed the group " + group.get().name() + ".");
    }

}
