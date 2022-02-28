package org.minestombrick.permissions.app.commands.group.permission;

import org.minestombrick.commandtools.api.command.BrickCommand;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.app.BrickPermissionManager;
import org.minestombrick.permissions.api.data.Group;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.ArgumentGroup;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.ArgumentWord;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

public class GroupPermissionAddCommand extends BrickCommand {

    public GroupPermissionAddCommand() {
        super("add");

        // conditions
        setCondition(b -> b.permission("brick.permissions.group.permission.add"));

        // usage
        setInvalidUsageMessage("cmd.group.permission.add.usage");

        // arguments
        ArgumentGroup group = new ArgumentGroup("group");
        setInvalidArgumentMessage(group, "cmd.error.args.group");

        ArgumentWord permission = ArgumentType.Word("permission");
        ArgumentNbtCompoundTag data = ArgumentType.NbtCompound("data");

        addSyntax(this::execute, group, permission);
        addSyntax(this::execute, group, permission, data);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Group group = context.get("group");

        String permission = context.get("permission");
        NBTCompound data = context.getOrDefault("data", null);

        PermissionAPI.get().addPermission(group, new Permission(permission, data));
        I18nAPI.get(this).send(sender, "cmd.group.permission.add", permission, group.name());
    }

}
