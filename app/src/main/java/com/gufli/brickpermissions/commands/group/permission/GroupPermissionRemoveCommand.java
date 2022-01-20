package com.gufli.brickpermissions.commands.group.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;

public class GroupPermissionRemoveCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public GroupPermissionRemoveCommand(BrickPermissionManager permissionManager) {
        super("remove");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.group.permission.remove"));

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
        permissionManager.removePermission(group, permission);

        TranslationManager.get().send(sender, "cmd.group.permission.remove", permission, group.name());
    }

}
