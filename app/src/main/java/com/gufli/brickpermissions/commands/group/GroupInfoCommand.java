package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.permission.Permission;

import java.util.stream.Collectors;

public class GroupInfoCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public GroupInfoCommand(BrickPermissionManager permissionManager) {
        super("info");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.group.info"));

        // usage
        setInvalidUsageMessage("cmd.group.info.usage");

        // arguments
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Group group = context.get("group");
        TranslationManager.get().send(sender, "cmd.group.info", group.name(),
                group.permissions().stream()
                        .map(Permission::getPermissionName)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
