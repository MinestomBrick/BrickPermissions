package com.gufli.brickpermissions.data;

import net.minestom.server.permission.Permission;

import java.util.Collection;

public interface Group {

    String name();

    Collection<Permission> permissions();

}
