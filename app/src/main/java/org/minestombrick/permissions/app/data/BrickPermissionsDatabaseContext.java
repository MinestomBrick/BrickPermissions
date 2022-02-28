package org.minestombrick.permissions.app.data;

import io.ebean.config.DatabaseConfig;
import org.minestombrick.ebean.context.AbstractDatabaseContext;
import org.minestombrick.permissions.app.data.beans.*;
import org.minestombrick.permissions.app.data.converters.NBTConverter;

import java.util.Collection;
import java.util.Set;

public class BrickPermissionsDatabaseContext extends AbstractDatabaseContext {

    public final static String DATASOURCE_NAME = "BrickPermissions";

    public BrickPermissionsDatabaseContext() {
        super(DATASOURCE_NAME);
    }

    @Override
    protected void buildConfig(DatabaseConfig config) {
        classes().forEach(config::addClass);
    }

    public static Collection<Class<?>> classes() {
        return Set.of(
                NBTConverter.class,

                BPermission.class,
                BGroup.class,
                BGroupPermission.class,
                BPlayerGroup.class,
                BPlayerPermission.class
        );
    }

}
