package com.gufli.brickpermissions.commands.group.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;

import java.util.Optional;

public class GroupPermissionRemoveCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public GroupPermissionRemoveCommand(BrickPermissionManager permissionManager) {
        super("remove");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.group.permission.remove")));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp group permission remove <group> <permission>");
        });

        // arguments
        ArgumentWord group = ArgumentType.Word("group");
        ArgumentWord permission = ArgumentType.Word("permission");

        addSyntax(this::execute, group, permission);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        Optional<Group> group = permissionManager.group(groupName);
        if (group.isEmpty()) {
            sender.sendMessage(Component.text("The group " + groupName + " does not exist.")); // TODO
            return;
        }

        String permission = context.get("permission");
        permissionManager.removePermission(group.get(), permission);

        sender.sendMessage("Removed permission " + permission + " from " + group.get().name() + ". ");
    }

}
