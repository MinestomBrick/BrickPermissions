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
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Optional;

public class GroupPermissionAddCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public GroupPermissionAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.group.permission.add")));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp group permission add <group> <permission>");
        });

        // arguments
        ArgumentWord group = ArgumentType.Word("group");
        ArgumentWord permission = ArgumentType.Word("permission");
        ArgumentNbtCompoundTag data = ArgumentType.NbtCompound("data");

        addSyntax(this::execute, group, permission);
        addSyntax(this::execute, group, permission, data);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        Optional<Group> group = permissionManager.group(groupName);
        if (group.isEmpty()) {
            sender.sendMessage(Component.text("The group " + groupName + " does not exist.")); // TODO
            return;
        }

        String permission = context.get("permission");
        NBTCompound data = context.getOrDefault("data", null);

        permissionManager.addPermission(group.get(), new Permission(permission, data));
        sender.sendMessage("Added permission " + permission + " to " + group.get().name() + ". ");
    }

}
