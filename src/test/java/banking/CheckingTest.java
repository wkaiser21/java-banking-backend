package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingTest {

    public static final String APR = "0.6";
    public static final String ID = "12345678";
    public static final String BALANCE = "0.0";
    private static final String ACCOUNT_TYPE = "Checking";
    Checking checking;

    @BeforeEach
    void setUp() {
        checking = new Checking(APR, ID, BALANCE);
    }

    @Test
    void account_type_exists_for_checking_account() {
        assertEquals(ACCOUNT_TYPE, checking.getType());
    }

    @Test
    void apr_exists_for_checking_account() {
        assertEquals(APR, Double.toString(checking.getApr()));
    }

    @Test
    void id_exists_for_checking_account() {
        assertEquals(ID, checking.getId());
    }

    @Test
    void balance_exists_for_checking_account() {
        assertEquals(BALANCE, Double.toString(checking.getBalance()));
    }
}
