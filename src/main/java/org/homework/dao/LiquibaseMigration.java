package org.homework.dao;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.homework.connectiondb.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
public class LiquibaseMigration {
    public static void runMigrations() {
        DatabaseConfig databaseConfig = new DatabaseConfig(); // Создайте экземпляр DatabaseConfig

        try {
            Connection connection = DriverManager.getConnection(
                    databaseConfig.getUrl(), // Используйте параметры из DatabaseConfig
                    databaseConfig.getUsername(),
                    databaseConfig.getPassword()
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
