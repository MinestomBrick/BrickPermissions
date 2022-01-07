package com.gufli.brickpermissions.data.beans;

import io.ebean.annotation.ConstraintMode;
import io.ebean.annotation.DbForeignKey;
import io.ebean.annotation.Index;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Index(unique = true, columnNames = { "player_id", "group_id" })
@Index(columnNames = "player_id")
@Table(name = "player_groups")
public class BPlayerGroup extends BModel {

    @Id
    private int id;

    private final UUID playerId;

    @ManyToOne
    @DbForeignKey(onDelete = ConstraintMode.CASCADE)
    public BGroup group;

    public BPlayerGroup(UUID playerId, BGroup group) {
        this.playerId = playerId;
        this.group = group;
    }

    // getters

    public UUID playerId() {
        return playerId;
    }

    public BGroup group() {
        return group;
    }

}
