package com.gufli.brickpermissions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gufli.brickpermissions.commands.BrickPermissionRootCommand;
import com.gufli.brickpermissions.data.DatabaseContext;
import com.gufli.brickpermissions.listeners.PlayerJoinListener;
import com.gufli.brickpermissions.listeners.PlayerQuitListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extensions.Extension;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BrickPermissions extends Extension {

    private DatabaseContext databaseContext;
    private BrickPermissionManager permissionManager;

    private final Set<Command> commands = new HashSet<>();
    private final Set<EventListener<?>> eventListeners = new HashSet<>();

    //

    @Override
    public void initialize() {
        getLogger().info("Enabling " + nameAndVersion() + ".");

        // DATABASE

        // some things never change
        ClassLoader originalContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

        try (
                InputStream is = getResource("config.json");
                InputStreamReader isr = new InputStreamReader(is);
        ) {
            JsonObject config = JsonParser.parseReader(isr).getAsJsonObject();
            config = config.get("database").getAsJsonObject();

            databaseContext = new DatabaseContext();
            databaseContext.init(
                    config.get("dsn").getAsString(),
                    config.get("username").getAsString(),
                    config.get("password").getAsString()
            );
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return;
        }

        permissionManager = new BrickPermissionManager(databaseContext);

        Thread.currentThread().setContextClassLoader(originalContextClassLoader);

        // EVENTS
        eventListeners.add(new PlayerJoinListener(permissionManager));
        eventListeners.add(new PlayerQuitListener(permissionManager));

        GlobalEventHandler geh = MinecraftServer.getGlobalEventHandler();
        eventListeners.forEach(geh::addListener);

        // COMMANDS
        commands.add(new BrickPermissionRootCommand(permissionManager));

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
            databaseContext.shutdownServer();
        }

        getLogger().info("Disabled " + nameAndVersion() + ".");
    }

    private String nameAndVersion() {
        return getOrigin().getName() + " v" + getOrigin().getVersion();
    }

}
