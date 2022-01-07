package com.gufli.brickpermissions.data;

import com.gufli.brickpermissions.data.beans.*;
import com.gufli.brickpermissions.data.converters.NBTConverter;
import io.ebean.DB;
import io.ebean.DatabaseFactory;
import io.ebean.Transaction;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import io.ebean.datasource.DataSourceFactory;
import io.ebean.datasource.DataSourcePool;
import io.ebean.migration.MigrationConfig;
import io.ebean.migration.MigrationRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class DatabaseContext {

    public final static String DATASOURCE_NAME = "BrickPermissions";

    private DataSourcePool pool;

    public void init(String dsn, String username, String password) throws SQLException {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(dsn);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);

        String driver;
        if (dsn.startsWith("jdbc:h2")) {
            driver = "org.h2.Driver";
        } else if (dsn.startsWith("jdbc:mysql")) {
            driver = "com.mysql.cj.jdbc.Driver";
        } else {
            throw new IllegalArgumentException("Invalid dsn, driver not available");
        }

        try {
            Class.forName(driver);
            dataSourceConfig.setDriver(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        pool = DataSourceFactory.create(DATASOURCE_NAME, dataSourceConfig);

        // run migrations
        migrate(pool);

        // create database
        connect(pool);
    }

    private void migrate(DataSourcePool pool) throws SQLException {
        MigrationConfig config = new MigrationConfig();

        Connection conn = pool.getConnection();
        String platform = conn.getMetaData().getDatabaseProductName().toLowerCase();
        config.setMigrationPath("migrations/" + platform);

        MigrationRunner runner = new MigrationRunner(config);
        runner.run(conn);
    }

    private void connect(DataSourcePool pool) {
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSource(pool);
        config.setRegister(true);
        config.setDefaultServer(false);
        config.setName(DATASOURCE_NAME);

        // register converters
        config.addClass(NBTConverter.class);

        // register beans
        config.addClass(BPermission.class);
        config.addClass(BGroup.class);
        config.addClass(BGroupPermission.class);
        config.addClass(BPlayerGroup.class);
        config.addClass(BPlayerPermission.class);

        DatabaseFactory.create(config);
    }

    public void shutdownServer() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    // UTILS

    public CompletableFuture<Void> saveAsync(BModel... models) {
        return saveAsync(Arrays.asList(models));
    }

    public CompletableFuture<Void> saveAsync(Collection<? extends BModel> models) {
        return CompletableFuture.runAsync(() -> save(models))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
    }

    private void save(Collection<? extends BModel> models) {
        try (Transaction transaction = DB.byName(DATASOURCE_NAME).beginTransaction()) {
            for (BModel m : models) {
                m.save();
            }

            transaction.commit();
        }
    }

    public CompletableFuture<Void> deleteAsync(BModel... models) {
        return deleteAsync(Arrays.asList(models));
    }

    public CompletableFuture<Void> deleteAsync(Collection<? extends BModel> models) {
        return CompletableFuture.runAsync(() -> delete(models))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;
                });
    }

    private void delete(Collection<? extends BModel> models) {
        try (Transaction transaction = DB.byName(DATASOURCE_NAME).beginTransaction()) {
            for (BModel m : models) {
                m.delete();
            }

            transaction.commit();
        }
    }

}
