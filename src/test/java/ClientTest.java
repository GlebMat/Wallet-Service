import org.homework.domain.Client;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Класс ClientTest представляет набор юнит-тестов для класса Client, который управляет клиентскими счетами.
 * Он проверяет различные сценарии дебетовых и кредитных транзакций, а также обработку исключений.
 */
public class ClientTest {
    private final double EPS = 1e-9;
    private Client c1;

    /**
     * Инициализирует клиентский объект c1 с начальным балансом 100 перед выполнением каждого тестового метода.
     */
    @Before
    public void createNewClient() {
        c1 = new Client();
        c1.setBalance(100);
    }

    /**
     * Проверяет, уменьшается ли баланс после дебетовой транзакции.
     */
    @Test()
    public void decreasedBalanceThroughDebit() {
        try {
            c1.debit(10);
        } catch (BigDebetException e) {
            System.out.println(e.getMessage());
        } catch (UniquIdExeption e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(90, c1.getBalance(), EPS);
    }

    /**
     * Проверяет, увеличивается ли баланс после кредитной транзакции.
     */
    @Test
    public void increaseBalanceThroughCredit() {
        try {
            c1.credit(100);
        } catch (UniquIdExeption e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(200, c1.getBalance(), EPS);
    }

    /**
     * Проверяет, возникает ли исключение BigDebetException, когда пытаемся выполнить дебетовую транзакцию с недостаточными средствами.
     *
     * @throws UniquIdExeption   исключение, которое может возникнуть при выполнении транзакции.
     * @throws BigDebetException ожидаемое исключение при выполнении дебетовой транзакции.
     */
    @Test(expected = BigDebetException.class)
    public void balanceLessThenDebit() throws UniquIdExeption, BigDebetException {
        c1.debit(1000);
    }
}
