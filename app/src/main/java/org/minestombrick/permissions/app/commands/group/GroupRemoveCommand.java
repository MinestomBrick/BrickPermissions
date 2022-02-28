package org.minestombrick.permissions.app.commands.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.api.data.Group;

public class GroupRemoveCommand extends BrickCommand {

    public GroupRemoveCommand() {
        super("remove");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.remove"));

        // usage
        setInvalidUsageMessage("cmd.group.remove.usage");

        // arguments
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Group group = context.get("group");
        PermissionAPI.get().removeGroup(group);
        I18nAPI.get(this).send(sender, "cmd.group.remove", group.name());
    }

}
