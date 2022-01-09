package com.gufli.brickpermissions.commands.player.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Optional;

public class PlayerGroupAddCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public PlayerGroupAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.player.group.add") ||
                (sender instanceof Player p && p.getPermissionLevel() == 4)
        );

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp player group add <player> <group>");
        });

        // arguments
        ArgumentWord player = ArgumentType.Word("player");
        ArgumentWord group = ArgumentType.Word("group");

        addSyntax(this::execute, player, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String targetName = context.get("player");
        Optional<Player> target = MinecraftServer.getConnectionManager().getOnlinePlayers().stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(targetName)).findFirst();

        if (target.isEmpty()) {
            sender.sendMessage(Component.text(targetName + " is not online.")); // TODO
            return;
        }

        String groupName = context.get("group");
        Optional<Group> group = permissionManager.group(groupName);
        if (group.isEmpty()) {
            sender.sendMessage(Component.text("The group " + groupName + " does not exist.")); // TODO
            return;
        }

        permissionManager.addGroup(target.get(), group.get());
        sender.sendMessage("Added " + target.get().getUsername() + " to " + group.get().name() + ". ");
    }

}
