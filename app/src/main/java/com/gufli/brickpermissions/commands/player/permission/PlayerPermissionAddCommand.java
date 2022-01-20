package com.gufli.brickpermissions.commands.player.permission;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickutils.commands.ArgumentPlayer;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

public class PlayerPermissionAddCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public PlayerPermissionAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.player.permission.add"));

        // usage
        setInvalidUsageMessage("cmd.player.permission.add.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        ArgumentWord permission = ArgumentType.Word("permission");
        ArgumentNbtCompoundTag data = ArgumentType.NbtCompound("data");

        addSyntax(this::execute, player, permission);
        addSyntax(this::execute, player, permission, data);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");

        String permission = context.get("permission");
        NBTCompound data = context.getOrDefault("data", null);

        permissionManager.addPermission(target, new Permission(permission, data));
        TranslationManager.get().send(sender, "cmd.player.permission.add", permission, target.getName());
    }

}
