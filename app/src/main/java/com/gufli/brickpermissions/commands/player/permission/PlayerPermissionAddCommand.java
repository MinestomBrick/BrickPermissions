package com.gufli.brickpermissions.commands.player.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
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
import java.util.stream.Collectors;

public class PlayerPermissionAddCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public PlayerPermissionAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.player.permission.add") ||
                (sender instanceof Player p && p.getPermissionLevel() == 4)
        );

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp player permission add <player> <permission>");
        });

        // arguments
        ArgumentWord player = ArgumentType.Word("player");
        ArgumentWord permission = ArgumentType.Word("permission");
        ArgumentNbtCompoundTag data = ArgumentType.NbtCompound("data");

        addSyntax(this::execute, player, permission);
        addSyntax(this::execute, player, permission, data);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String targetName = context.get("player");
        Optional<Player> target = MinecraftServer.getConnectionManager().getOnlinePlayers().stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(targetName)).findFirst();

        if (target.isEmpty()) {
            sender.sendMessage(Component.text(targetName + " is not online.")); // TODO
            return;
        }

        String permission = context.get("permission");
        NBTCompound data = context.getOrDefault("data", null);

        permissionManager.addPermission(target.get(), new Permission(permission, data));
        sender.sendMessage("Added permission " + permission + " to " + target.get().getUsername() + ". ");
    }

}
