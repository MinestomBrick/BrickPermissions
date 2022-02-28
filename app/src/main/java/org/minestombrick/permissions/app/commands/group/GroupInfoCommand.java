package org.minestombrick.permissions.app.commands.group;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.permission.Permission;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.data.Group;

import java.util.stream.Collectors;

public class GroupInfoCommand extends BrickCommand {

    public GroupInfoCommand() {
        super("info");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.info"));

        // usage
        setInvalidUsageMessage("cmd.group.info.usage");

        // arguments
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Group group = context.get("group");
        I18nAPI.get(this).send(sender, "cmd.group.info", group.name(),
                group.permissions().stream()
                        .map(Permission::getPermissionName)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
