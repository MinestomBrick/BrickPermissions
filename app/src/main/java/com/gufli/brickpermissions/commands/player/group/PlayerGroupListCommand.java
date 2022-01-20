package com.gufli.brickpermissions.commands.player.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.ArgumentPlayer;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;

import java.util.stream.Collectors;

public class PlayerGroupListCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public PlayerGroupListCommand(BrickPermissionManager permissionManager) {
        super("list");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.player.group.list"));

        // usage
        setInvalidUsageMessage("cmd.player.group.list.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");

        addSyntax(this::execute, player);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("target");

        TranslationManager.get().send(sender, "cmd.player.group.list", target.getName(),
                permissionManager.groups(target).stream()
                        .map(Group::name)
                        .collect(Collectors.joining(", "))
        );
    }

}
