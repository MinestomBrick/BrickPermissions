package com.gufli.brickpermissions;

import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TestPlayer extends Player {

    public TestPlayer(@NotNull UUID uuid, @NotNull String username) {
        super(uuid, username, null);
    }

    @Override
    protected void playerConnectionInit() {}

    @Override
    public boolean isOnline() {
        return false;
    }
}
