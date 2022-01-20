package com.gufli.brickpermissions.commands.player.group;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickpermissions.data.Group;
import com.gufli.brickutils.commands.ArgumentPlayer;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.Optional;

public class PlayerGroupAddCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public PlayerGroupAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.player.group.add"));

        // usage
        setInvalidUsageMessage("cmd.player.group.add.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        addSyntax(this::execute, player, group);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");
        Group group = context.get("group");
        permissionManager.addGroup(target, group);
        TranslationManager.get().send(sender, "cmd.player.group.add", target.getName(), group.name());
    }

}
