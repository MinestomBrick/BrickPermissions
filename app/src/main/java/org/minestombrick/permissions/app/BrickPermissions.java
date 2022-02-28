package org.minestombrick.permissions.app;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extensions.Extension;
import org.minestombrick.commandtools.api.command.CommandGroupBuilder;
import org.minestombrick.i18n.api.I18nAPI;
import org.minestombrick.i18n.api.namespace.I18nNamespace;
import org.minestombrick.permissions.api.PermissionAPI;
import org.minestombrick.permissions.app.commands.group.GroupAddCommand;
import org.minestombrick.permissions.app.commands.group.GroupInfoCommand;
import org.minestombrick.permissions.app.commands.group.GroupListCommand;
import org.minestombrick.permissions.app.commands.group.GroupRemoveCommand;
import org.minestombrick.permissions.app.commands.group.permission.GroupPermissionAddCommand;
import org.minestombrick.permissions.app.commands.group.permission.GroupPermissionRemoveCommand;
import org.minestombrick.permissions.app.commands.player.PlayerInfoCommand;
import org.minestombrick.permissions.app.commands.player.group.PlayerGroupAddCommand;
import org.minestombrick.permissions.app.commands.player.group.PlayerGroupListCommand;
import org.minestombrick.permissions.app.commands.player.group.PlayerGroupRemoveCommand;
import org.minestombrick.permissions.app.commands.player.permission.PlayerPermissionAddCommand;
import org.minestombrick.permissions.app.commands.player.permission.PlayerPermissionRemoveCommand;
import org.minestombrick.permissions.app.data.BrickPermissionsDatabaseContext;
import org.minestombrick.permissions.app.listeners.PlayerJoinListener;
import org.minestombrick.permissions.app.listeners.PlayerQuitListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class BrickPermissions extends Extension {

    private BrickPermissionsDatabaseContext databaseContext;
    private BrickPermissionManager permissionManager;

    //

    @Override
    public void initialize() {
        getLogger().info("Enabling " + nameAndVersion() + ".");

        // LOAD CONFIG
        JsonObject config;
        try (
                InputStream is = getResource("config.json");
                InputStreamReader isr = new InputStreamReader(is);
        ) {
            config = JsonParser.parseReader(isr).getAsJsonObject().get("database").getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // INIT DATABASE
        databaseContext = new BrickPermissionsDatabaseContext();
        try {
            databaseContext.withContextClassLoader(() -> {
                databaseContext.init(config);

                permissionManager = new BrickPermissionManager(databaseContext);
                PermissionAPI.setPermissionManager(permissionManager);

                return null;
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        // TRANSLATIONS
        I18nNamespace namespace = new I18nNamespace(this, Locale.ENGLISH);
        namespace.loadValues(this, "languages");
        I18nAPI.get().register(namespace);

        // EVENTS
        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        geh.addListener(new PlayerJoinListener(permissionManager));
        geh.addListener(new PlayerQuitListener(permissionManager));

        // COMMANDS
        CommandManager gm = MinecraftServer.getCommandManager();
        gm.register(CommandGroupBuilder.of(new Command("brickperms", "bp"))
                .group("group", g1 -> g1
                        .withCommand(new GroupAddCommand())
                        .withCommand(new GroupInfoCommand())
                        .withCommand(new GroupListCommand())
                        .withCommand(new GroupRemoveCommand())
                        .group("permission", g2 -> g2
                                .withCommand(new GroupPermissionAddCommand())
                                .withCommand(new GroupPermissionRemoveCommand())
                        )
                )
                .group("player", g1 -> g1
                        .withCommand(new PlayerInfoCommand())
                        .group("group", g2 -> g2
                                .withCommand(new PlayerGroupAddCommand())
                                .withCommand(new PlayerGroupListCommand())
                                .withCommand(new PlayerGroupRemoveCommand())
                        )
                        .group("permission", g2 -> g2
                                .withCommand(new PlayerPermissionAddCommand())
                                .withCommand(new PlayerPermissionRemoveCommand())
                        )
                ).build());

        getLogger().info("Enabled " + nameAndVersion() + ".");
    }

    @Override
    public void terminate() {
        // PERMS
        if (permissionManager != null) {
            permissionManager.shutdown();
        }

        // DATABASE
        if (databaseContext != null) {
            databaseContext.shutdown();
        }

        getLogger().info("Disabled " + nameAndVersion() + ".");
    }

    private String nameAndVersion() {
        return getOrigin().getName() + " v" + getOrigin().getVersion();
    }

}
