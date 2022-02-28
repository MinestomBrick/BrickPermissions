package org.minestombrick.permissions.api.data;

import net.minestom.server.permission.Permission;

import java.util.Collection;

public interface Group {

    String name();

    Collection<Permission> permissions();

}
