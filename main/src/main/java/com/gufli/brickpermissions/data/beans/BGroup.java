package com.gufli.brickpermissions.data.beans;

import com.gufli.brickpermissions.data.Group;
import io.ebean.annotation.Index;
import net.minestom.server.permission.Permission;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Index(unique = true, columnNames = { "name" })
@Table(name = "groups")
public class BGroup extends BModel implements Group {

    @Id
    private UUID id;

    private final String name;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<BGroupPermission> permissions;

    public BGroup(String name) {
        this.name = name;
    }

    // getters

    @Override
    public String name() {
        return name;
    }

    public Optional<BGroupPermission> permissionByName(String name) {
        return permissions.stream()
                .filter(perm -> perm.permission().equalsIgnoreCase(name))
                .findFirst();
    }

    public List<BGroupPermission> permissions() {
        return Collections.unmodifiableList(permissions);
    }

    public BGroupPermission addPermission(Permission permission) {
        BGroupPermission bgp = new BGroupPermission(this, permission.getPermissionName(), permission.getNBTData());
        permissions.add(bgp);
        return bgp;
    }

    public Optional<BGroupPermission> removePermission(Permission permission) {
        Optional<BGroupPermission> bgp = permissions.stream()
                .filter(p -> p.permission().equalsIgnoreCase(permission.getPermissionName()))
                .findFirst();
        bgp.ifPresent(bGroupPermission -> permissions.remove(bGroupPermission));
        return bgp;
    }

}
