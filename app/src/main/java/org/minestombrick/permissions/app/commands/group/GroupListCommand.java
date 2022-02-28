package org.minestombrick.permissions.app.commands.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.api.data.Group;

import java.util.stream.Collectors;

public class GroupListCommand extends BrickCommand {

    public GroupListCommand() {
        super("list");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.list"));

        // usage
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        I18nAPI.get(this).send(sender, "cmd.group.list",
                PermissionAPI.get().groups().stream()
                        .map(Group::name)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
