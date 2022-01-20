package com.gufli.brickpermissions.commands.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.Optional;
import java.util.stream.Collectors;

public class GroupAddCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public GroupAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.group.add"));

        // usage
        setInvalidUsageMessage("cmd.group.add.usage");

        // arguments
        ArgumentWord group = ArgumentType.Word("group");
        addSyntax(this::execute, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        String groupName = context.get("group");
        if ( permissionManager.group(groupName).isPresent() ) {
            TranslationManager.get().send(sender, "cmd.group.add.invalid", groupName);
            return;
        }

        permissionManager.addGroup(groupName);
        TranslationManager.get().send(sender, "cmd.group.add", groupName);
    }

}
