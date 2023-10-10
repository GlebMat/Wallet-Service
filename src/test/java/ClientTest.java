import org.homework.domain.Client;
import org.homework.exeption.BigDebetException;
import org.homework.exeption.UniquIdExeption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClientTest {
    private final double EPS = 1e-9;

    private Client c1;

    @Before
    public void createNewClient() {
        c1 = new Client();
        c1.setBalance(100);
    }


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

    @Test
    public void increaseBalanceThroughCredit() {
        try {
            c1.credit(100);
        } catch (UniquIdExeption e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(200, c1.getBalance(), EPS);
    }

    @Test(expected = BigDebetException.class)
    public void balanceLessThenDebit() throws UniquIdExeption, BigDebetException {
        c1.debit(1000);
    }
}
