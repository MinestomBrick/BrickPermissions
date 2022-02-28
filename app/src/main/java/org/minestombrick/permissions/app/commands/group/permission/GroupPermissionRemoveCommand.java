package org.minestombrick.permissions.app.commands.group.permission;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.api.data.Group;

public class GroupPermissionRemoveCommand extends BrickCommand {

    public GroupPermissionRemoveCommand() {
        super("remove");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.permission.remove"));

        // usage
        setInvalidUsageMessage("cmd.group.permission.remove.usage");

        // arguments
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        ArgumentWord permission = ArgumentType.Word("permission");

        addSyntax(this::execute, group, permission);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Group group = context.get("group");

        String permission = context.get("permission");
        PermissionAPI.get().removePermission(group, permission);

        I18nAPI.get(this).send(sender, "cmd.group.permission.remove", permission, group.name());
    }

}
