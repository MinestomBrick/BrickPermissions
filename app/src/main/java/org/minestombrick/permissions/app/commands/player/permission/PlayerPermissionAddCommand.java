package org.minestombrick.permissions.app.commands.player.permission;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.minestombrick.commandtools.api.arguments.ArgumentPlayer;
import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;

public class PlayerPermissionAddCommand extends BrickCommand {

    public PlayerPermissionAddCommand() {
        super("add");

        // conditions
        setCondition(b -> b.permission("brick.permissions.player.permission.add"));

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

        PermissionAPI.get().addPermission(target, new Permission(permission, data));
        I18nAPI.get(this).send(sender, "cmd.player.permission.add", permission, target.getName());
    }

}
