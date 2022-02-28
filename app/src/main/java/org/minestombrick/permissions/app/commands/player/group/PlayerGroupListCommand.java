package org.minestombrick.permissions.app.commands.player.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.minestombrick.commandtools.api.arguments.ArgumentPlayer;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.api.data.Group;

import java.util.stream.Collectors;

public class PlayerGroupListCommand extends BrickCommand {

    public PlayerGroupListCommand() {
        super("list");

        // conditions
        setCondition(b -> b.permission("brick.permissions.player.group.list"));

        // usage
        setInvalidUsageMessage("cmd.player.group.list.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");

        addSyntax(this::execute, player);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("target");

        I18nAPI.get(this).send(sender, "cmd.player.group.list", target.getName(),
                PermissionAPI.get().groups(target).stream()
                        .map(Group::name)
                        .collect(Collectors.joining(", "))
        );
    }

}
