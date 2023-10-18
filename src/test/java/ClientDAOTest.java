import org.homework.connectiondb.ConnectionDB;
import org.homework.connectiondb.DatabaseConfig;
import org.homework.dao.ClientDAO;
import org.homework.dao.LiquibaseMigration;
import org.homework.domain.Client;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;
import org.homework.service.TransactionService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

@Testcontainers
public class ClientDAOTest {
    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();

    @BeforeAll
    public static void beforeAll() {
        postgreSQLContainer.start();
        // Здесь вы можете выполнить настройки тестовой БД, миграции и т. д.
        DatabaseConfig databaseConfig = new DatabaseConfig();
        ConnectionDB connectionDB = new ConnectionDB(databaseConfig);
        LiquibaseMigration.runMigrations();
    }

    @AfterAll
    public static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    public void testClientInsertion() throws UniqueIdException {
        // Инициализация тестовых данных
        DatabaseConfig databaseConfig = new DatabaseConfig();
        ConnectionDB connectionDB = new ConnectionDB(databaseConfig);
        Connection connection = connectionDB.getConnection();
        Client client = new Client();
        client.setUsername("testUser");
        client.setPassword("testPassword");
        client.setBalance(new BigDecimal("1000.00"));

        try {
            // Вставка клиента в тестовую базу данных
            ClientDAO.insertRecord(connection, client.getUsername(), client.getPassword());
            // Проверка, что клиент был успешно добавлен
            Client insertedClient = ClientDAO.searchClient(client.getUsername(), client.getPassword(), connectionDB);
            assert insertedClient != null;
            assert insertedClient.getUsername().equals(client.getUsername());
            assert insertedClient.getPassword().equals(client.getPassword());
            assert insertedClient.getBalance().compareTo(client.getBalance()) == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Убедитесь, что соединение с базой данных закрывается в блоке finally
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testDebitTransaction() throws BigDebitException, UniqueIdException {
        // Инициализация тестовых данных
        DatabaseConfig databaseConfig = new DatabaseConfig();
        ConnectionDB connectionDB = new ConnectionDB(databaseConfig);
        Connection connection = connectionDB.getConnection();
        Client client = new Client();
        client.setUsername("testUser");
        client.setPassword("testPassword");
        client.setBalance(new BigDecimal("1000.00"));
        TransactionService.debit(new BigDecimal("100.00"), client, connectionDB);

        // Проверка, что баланс клиента уменьшился на 100
        assert client.getBalance().compareTo(new BigDecimal("900.00")) == 0;
    }
}
