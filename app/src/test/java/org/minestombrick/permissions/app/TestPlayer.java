package org.minestombrick.permissions.app;

import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.net.SocketAddress;
import java.util.UUID;

public class TestPlayer extends Player {

    public TestPlayer(@NotNull UUID uuid, @NotNull String username) {
        super(uuid, username, new NullPlayerConnection());
    }

    @Override
    protected void playerConnectionInit() {}

    @Override
    public boolean isOnline() {
        return false;
    }

    private static class NullPlayerConnection extends PlayerConnection {

        @Override
        public void sendPacket(@NotNull SendablePacket packet) {}

        @Override
        public @NotNull SocketAddress getRemoteAddress() {
            return null;
        }

        @Override
        public void disconnect() {}
    }
}
