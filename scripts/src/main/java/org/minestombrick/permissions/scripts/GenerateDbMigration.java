package org.minestombrick.permissions.scripts;

import org.minestombrick.ebean.migration.MigrationGenerator;
import org.minestombrick.permissions.app.data.BrickPermissionsDatabaseContext;
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
        BrickPermissionsDatabaseContext.classes().forEach(generator::addClass);
        generator.generate();
    }
}