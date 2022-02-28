package org.minestombrick.permissions.app.commands.player;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.minestombrick.commandtools.api.arguments.ArgumentPlayer;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;

import java.util.stream.Collectors;

public class PlayerInfoCommand extends BrickCommand {

    public PlayerInfoCommand() {
        super("info");
        // conditions
        setCondition(b -> b.permission("brick.permissions.player.info"));

        // usage
        setInvalidUsageMessage("cmd.player.info.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        setInvalidArgumentMessage(player, "cmd.error.args.player");

        addSyntax(this::execute, player);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");
        I18nAPI.get(this).send(sender, "cmd.player.info", target.getName(),
                target.getAllPermissions().stream()
                        .map(Permission::getPermissionName)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
