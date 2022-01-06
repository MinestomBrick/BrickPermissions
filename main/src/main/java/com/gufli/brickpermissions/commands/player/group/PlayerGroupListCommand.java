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
import net.minestom.server.entity.Player;

import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerGroupListCommand extends Command {

    private final BrickPermissionManager permissionManager;

    public PlayerGroupListCommand(BrickPermissionManager permissionManager) {
        super("list");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(((sender, commandString) -> sender instanceof ConsoleSender ||
                sender.hasPermission("brickpermissions.player.group.list")));

        // usage
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Usage: /bp player group list <player>");
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

        sender.sendMessage("Groups of " + target.get().getUsername() + ": " +
                permissionManager.groups(target.get()).stream()
                        .map(Group::name)
                        .collect(Collectors.joining(", "))
        );
    }

}
