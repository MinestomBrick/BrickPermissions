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

public class PlayerGroupRemoveCommand extends BrickCommand {

    public PlayerGroupRemoveCommand() {
        super("remove");
        // conditions
        setCondition(b -> b.permission("brickpermissions.player.group.remove"));

        // usage
        setInvalidUsageMessage("cmd.player.group.remove.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, player, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");
        Group group = context.get("group");

        PermissionAPI.get().removeGroup(target, group);
        I18nAPI.get(this).send(sender, "cmd.player.group.group", target.getName(), group.name());
    }

}
