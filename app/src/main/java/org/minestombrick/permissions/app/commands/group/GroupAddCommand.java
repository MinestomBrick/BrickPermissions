package org.minestombrick.permissions.app.commands.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;

public class GroupAddCommand extends BrickCommand {

    public GroupAddCommand() {
        super("add");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.add"));

        // usage
        setInvalidUsageMessage("cmd.group.add.usage");

        // arguments
        ArgumentWord group = ArgumentType.Word("group");
        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        if ( PermissionAPI.get().group(groupName).isPresent() ) {
            I18nAPI.get(this).send(sender, "cmd.group.add.invalid", groupName);
            return;
        }

        PermissionAPI.get().addGroup(groupName);
        I18nAPI.get(this).send(sender, "cmd.group.add", groupName);
    }

}
