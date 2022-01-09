package com.gufli.brickpermissions;

import com.gufli.brickdatabase.migration.MigrationGenerator;
import com.gufli.brickpermissions.data.BrickPermissionsDatabaseContext;
import io.ebean.annotation.Platform;

import java.io.IOException;
import java.nio.file.Path;

public class GenerateDbMigration {

    /**
     * Generate the next "DB schema DIFF" migration.
     */
    public static void main(String[] args) throws IOException {
        MigrationGenerator generator = new MigrationGenerator(
                BrickPermissionsDatabaseContext.DATASOURCE_NAME,
                Path.of("BrickPermissions/app/src/main/resources"),
                Platform.H2, Platform.MYSQL
        );
        generator.generate();
    }
}