package com.gufli.brickpermissions.data;

import com.gufli.brickdatabase.DatabaseContext;
import com.gufli.brickpermissions.data.beans.*;
import com.gufli.brickpermissions.data.converters.NBTConverter;
import io.ebean.config.DatabaseConfig;

public class BrickPermissionsDatabaseContext extends DatabaseContext {

    public final static String DATASOURCE_NAME = "BrickPermissions";

    public BrickPermissionsDatabaseContext() {
        super(DATASOURCE_NAME);
    }

    @Override
    protected void buildConfig(DatabaseConfig config) {
        // register converters
        config.addClass(NBTConverter.class);

        // register beans
        config.addClass(BPermission.class);
        config.addClass(BGroup.class);
        config.addClass(BGroupPermission.class);
        config.addClass(BPlayerGroup.class);
        config.addClass(BPlayerPermission.class);
    }

}
