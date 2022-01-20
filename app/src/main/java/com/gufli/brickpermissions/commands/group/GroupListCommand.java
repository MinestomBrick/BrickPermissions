package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;

import java.util.stream.Collectors;

public class GroupListCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public GroupListCommand(BrickPermissionManager permissionManager) {
        super("list");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.group.list"));

        // usage
        setDefaultExecutor(this::execute);
    }

    private void execute(CommandSender sender, CommandContext context) {
        TranslationManager.get().send(sender, "cmd.group.list",
                permissionManager.groups().stream()
                        .map(Group::name)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
