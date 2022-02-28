package org.minestombrick.permissions.app.commands.player.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.entity.Player;
import org.minestombrick.commandtools.api.arguments.ArgumentPlayer;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.api.data.Group;

public class PlayerGroupAddCommand extends BrickCommand {

    public PlayerGroupAddCommand() {
        super("add");

        // conditions
        setCondition(b -> b.permission("brick.permissions.player.group.add"));

        // usage
        setInvalidUsageMessage("cmd.player.group.add.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, player, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");
        Group group = context.get("group");
        PermissionAPI.get().addGroup(target, group);
        I18nAPI.get(this).send(sender, "cmd.player.group.add", target.getName(), group.name());
    }

}
