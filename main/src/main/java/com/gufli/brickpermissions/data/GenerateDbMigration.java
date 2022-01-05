package com.gufli.brickpermissions.data;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.annotation.Platform;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.dbmigration.DbMigration;

import java.io.IOException;

public class GenerateDbMigration {

    /**
     * Generate the next "DB schema DIFF" migration.
     */
    public static void main(String[] args) throws IOException {

        DbMigration dbMigration = DbMigration.create();
        dbMigration.addPlatform(Platform.H2, "h2");
        dbMigration.addPlatform(Platform.MYSQL, "mysql");
        dbMigration.setPathToResources("src/main/resources");
        dbMigration.setMigrationPath("migrations");

        // create mock db with same name as used in the app
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:h2:mem:migrationdb;");
        dataSourceConfig.setUsername("dbuser");
        dataSourceConfig.setPassword("");

        DatabaseConfig config = new DatabaseConfig();
        config.setDataSourceConfig(dataSourceConfig);
        config.setName(DatabaseContext.DATASOURCE_NAME); // all for this

        Database database = DatabaseFactory.create(config);
        dbMigration.setServer(database);

        dbMigration.generateMigration();
    }
}