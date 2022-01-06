package com.gufli.brickpermissions.commands.player;

import com.gufli.brickpermissions.BrickPermissionManager;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
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

public class PlayerInfoCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public PlayerInfoCommand(BrickPermissionManager permissionManager) {
        super("info");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.player.info")));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp player info <player>");
        });

        // arguments
        ArgumentWord player = ArgumentType.Word("player");

        addSyntax(this::execute, player);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String targetName = context.get("player");
        Optional<Player> target = MinecraftServer.getConnectionManager().getOnlinePlayers().stream()
                .filter(p -> p.getUsername().equalsIgnoreCase(targetName)).findFirst();

        if (target.isEmpty()) {
            sender.sendMessage(Component.text(targetName + " is not online.")); // TODO
            return;
        }

        sender.sendMessage("Permissions of " + target.get().getUsername() + ": " +
                target.get().getAllPermissions().stream()
                        .map(Permission::getPermissionName)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
