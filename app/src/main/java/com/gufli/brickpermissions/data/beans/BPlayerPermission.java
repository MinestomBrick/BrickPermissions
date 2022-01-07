package com.gufli.brickpermissions.data.beans;

import io.ebean.annotation.Index;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Index(unique = true, columnNames = { "player_id", "permission" })
@Index(columnNames = "player_id")
@Table(name = "player_permissions")
public class BPlayerPermission extends BPermission {

    @Id
    private int id;
    private final UUID playerId;

    public BPlayerPermission(UUID playerId, String permission, NBTCompound data) {
        super(permission, data);
        this.playerId = playerId;
    }

    public BPlayerPermission(UUID playerId, String permission) {
        this(playerId, permission, null);
    }

    // getters

    public UUID playerId() {
        return playerId;
    }


}
