package org.minestombrick.permissions.app.data.beans;

import io.ebean.annotation.ConstraintMode;
import io.ebean.annotation.DbForeignKey;
import io.ebean.annotation.Index;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Index(unique = true, columnNames = {"group_id", "permission"})
@Table(name = "group_permissions")
public class BGroupPermission extends BPermission {

    @Id
    private int id;

    @ManyToOne
    @DbForeignKey(onDelete = ConstraintMode.CASCADE)
    private final BGroup group;

    public BGroupPermission(BGroup group, String permission, NBTCompound data) {
        super(permission, data);
        this.group = group;
    }

    public BGroupPermission(BGroup group, String permission) {
        this(group, permission, null);
    }

    // getters

    public BGroup group() {
        return group;
    }

}
