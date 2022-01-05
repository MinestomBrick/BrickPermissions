package com.gufli.brickpermissions.listeners;

import com.gufli.brickpermissions.BrickPermissionManager;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerSpawnEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements EventListener<PlayerSpawnEvent> {

    private final BrickPermissionManager manager;

    public PlayerJoinListener(BrickPermissionManager manager) {
        this.manager = manager;
    }

    @Override
    public @NotNull Class<PlayerSpawnEvent> eventType() {
        return PlayerSpawnEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull PlayerSpawnEvent event) {
        manager.load(event.getPlayer());
        return Result.SUCCESS;
    }
}
