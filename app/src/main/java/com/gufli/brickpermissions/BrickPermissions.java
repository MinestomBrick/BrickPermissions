package com.gufli.brickpermissions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gufli.brickpermissions.commands.BrickPermissionsCommand;
import com.gufli.brickpermissions.data.BrickPermissionsDatabaseContext;
import com.gufli.brickpermissions.listeners.PlayerJoinListener;
import com.gufli.brickpermissions.listeners.PlayerQuitListener;
import com.gufli.brickutils.translation.SimpleTranslationManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extensions.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BrickPermissions extends Extension {

    private BrickPermissionsDatabaseContext databaseContext;
    private BrickPermissionManager permissionManager;

    private final Set<Command> commands = new HashSet<>();
    private final Set<EventListener<?>> eventListeners = new HashSet<>();

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
        SimpleTranslationManager tm = new SimpleTranslationManager(this, Locale.ENGLISH);
        tm.loadTranslations(this, "languages");

        // EVENTS
        eventListeners.add(new PlayerJoinListener(permissionManager));
        eventListeners.add(new PlayerQuitListener(permissionManager));

        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        eventListeners.forEach(geh::addListener);

        // COMMANDS
        commands.add(new BrickPermissionsCommand(permissionManager));

        CommandManager gm = MinecraftServer.getCommandManager();
        commands.forEach(gm::register);

        getLogger().info("Enabled " + nameAndVersion() + ".");
    }

    @Override
    public void terminate() {
        // EVENTS
        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        eventListeners.forEach(geh::removeListener);

        // COMMANDS
        CommandManager gm = MinecraftServer.getCommandManager();
        commands.forEach(gm::unregister);

        // PERMS
        if ( permissionManager != null ) {
            permissionManager.shutdown();
        }

        // DATABASE
        if ( databaseContext != null ) {
            databaseContext.shutdown();
        }

        getLogger().info("Disabled " + nameAndVersion() + ".");
    }

    private String nameAndVersion() {
        return getOrigin().getName() + " v" + getOrigin().getVersion();
    }

}
