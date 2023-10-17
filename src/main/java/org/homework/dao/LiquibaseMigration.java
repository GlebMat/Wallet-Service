package org.homework.dao;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseMigration {
    public static void runMigrations() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/wallet_service",
                    "postgres",
                    "C2DnNimJTzte4XlCkwqM"
            );
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            // Установите схему перед выполнением миграций
            database.setDefaultSchemaName("private_scheme");

            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции успешно выполнены!");
        } catch (Exception e) {
            System.out.println("Подключение не удалось!!!!!!!!!");
            e.printStackTrace();
        }
    }
}
