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
import net.minestom.server.command.builder.arguments.minecraft.ArgumentNbtCompoundTag;
import net.minestom.server.permission.Permission;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

public class GroupPermissionAddCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public GroupPermissionAddCommand(BrickPermissionManager permissionManager) {
        super("add");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.group.permission.add"));

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

        permissionManager.addPermission(group, new Permission(permission, data));
        TranslationManager.get().send(sender, "cmd.group.permission.add", permission, group.name());
    }

}
