package com.gufli.brickpermissions.commands.player;

import com.gufli.brickpermissions.BrickPermissionManager;
import com.gufli.brickutils.commands.ArgumentPlayer;
import com.gufli.brickutils.commands.BrickCommand;
import com.gufli.brickutils.translation.TranslationManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;

import java.util.stream.Collectors;

public class PlayerInfoCommand extends BrickCommand {

    private final BrickPermissionManager permissionManager;

    public PlayerInfoCommand(BrickPermissionManager permissionManager) {
        super("info");
        this.permissionManager = permissionManager;

        // conditions
        setCondition(b -> b.permission("brickpermissions.player.info"));

        // usage
        setInvalidUsageMessage("cmd.player.info.usage");

        // arguments
        ArgumentPlayer player = new ArgumentPlayer("player");
        setInvalidArgumentMessage(player, "cmd.error.args.player");

        addSyntax(this::execute, player);
    }

    private void execute(CommandSender sender, CommandContext context) {
        Player target = context.get("player");
        TranslationManager.get().send(sender, "cmd.player.info", target.getName(),
                target.getAllPermissions().stream()
                        .map(Permission::getPermissionName)
                        .sorted().collect(Collectors.joining(", "))
        );
    }

}
