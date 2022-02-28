package org.minestombrick.permissions.app.commands.player.permission;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.Player;
import org.minestombrick.commandtools.api.arguments.ArgumentPlayer;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;

public class PlayerPermissionRemoveCommand extends BrickCommand {

    public PlayerPermissionRemoveCommand() {
        super("remove");

        // conditions
        setCondition(b -> b.permission("brick.permissions.player.permission.remove"));

        // usage
        setInvalidUsageMessage("cmd.player.permission.remove.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        ArgumentWord permission = ArgumentType.Word("permission");

        addSyntax(this::execute, player, permission);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("target");

        String permission = context.get("permission");
        PermissionAPI.get().removePermission(target, permission);

        I18nAPI.get(this).send(sender, "cmd.player.permission.remove", permission, target.getUsername());
    }

}
