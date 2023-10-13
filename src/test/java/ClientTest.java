import org.homework.dataacess.TransactionService;
import org.homework.domain.Client;
import org.homework.exception.BigDebitException;
import org.homework.exception.UniqueIdException;
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
    public void decreasedBalanceThroughDebit() throws BigDebitException, UniqueIdException {

        TransactionService.debit(10, c1);

        Assert.assertEquals(90, c1.getBalance(), EPS);
    }

    /**
     * Checks whether the balance increases after a credit transaction.
     */
    @Test
    public void increaseBalanceThroughCredit() throws UniqueIdException {

        TransactionService.credit(100, c1);

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
        TransactionService.debit(1000, c1);
    }
}
