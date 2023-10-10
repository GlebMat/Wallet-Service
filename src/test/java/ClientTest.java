import org.homework.domain.Client;
import org.homework.exсeption.BigDebitException;
import org.homework.exсeption.UniqueIdException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The `ClientTest` class represents a set of unit tests for the `Client` class, which manages client accounts.
 * It verifies various scenarios of debit and credit transactions, as well as exception handling.
 */
public class ClientTest {
    private final double EPS = 1e-9;
    private Client c1;

    /**
     * Initializes the client object `c1` with an initial balance of 100 before executing each test method.
     */
    @Before
    public void createNewClient() {
        c1 = new Client();
        c1.setBalance(100);
    }

    /**
     * Checks whether the balance decreases after a debit transaction.
     */
    @Test()
    public void decreasedBalanceThroughDebit() {
        try {
            c1.debit(10);
        } catch (BigDebitException e) {
            System.out.println(e.getMessage());
        } catch (UniqueIdException e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(90, c1.getBalance(), EPS);
    }

    /**
     * Checks whether the balance increases after a credit transaction.
     */
    @Test
    public void increaseBalanceThroughCredit() {
        try {
            c1.credit(100);
        } catch (UniqueIdException e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(200, c1.getBalance(), EPS);
    }

    /**
     * Checks if the `BigDebitException` is thrown when attempting a debit transaction with insufficient funds.
     *
     * @throws UniqueIdException An exception that may occur during the transaction.
     * @throws BigDebitException The expected exception when performing a debit transaction.
     */
    @Test(expected = BigDebitException.class)
    public void balanceLessThenDebit() throws UniqueIdException, BigDebitException {
        c1.debit(1000);
    }
}
