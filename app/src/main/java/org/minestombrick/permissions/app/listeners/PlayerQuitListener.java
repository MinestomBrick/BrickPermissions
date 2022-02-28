package org.minestombrick.permissions.app.listeners;

import org.minestombrick.permissions.app.BrickPermissionManager;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerQuitListener implements EventListener<PlayerDisconnectEvent> {

    private final BrickPermissionManager manager;

    public PlayerQuitListener(BrickPermissionManager manager) {
        this.manager = manager;
    }

    @Override
    public @NotNull Class<PlayerDisconnectEvent> eventType() {
        return PlayerDisconnectEvent.class;
    }

    @Override
    public @NotNull Result run(@NotNull PlayerDisconnectEvent event) {
        manager.unload(event.getPlayer());
        return Result.SUCCESS;
    }
}
