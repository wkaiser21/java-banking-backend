package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CdTest {
    public static final String APR = "0.6";
    public static final String ID = "12345678";
    public static final String BALANCE = "5000.0";
    private static final String ACCOUNT_TYPE = "Cd";
    Cd cd;

    @BeforeEach
    void setUp() {
        cd = new Cd(APR, ID, BALANCE);
    }

    @Test
    void account_type_exists_for_checking_account() {
        assertEquals(ACCOUNT_TYPE, cd.getType());
    }

    @Test
    void apr_exists_for_checking_account() {
        assertEquals(APR, Double.toString(cd.getApr()));
    }

    @Test
    void id_exists_for_checking_account() {
        assertEquals(ID, cd.getId());
    }

    @Test
    void balance_exists_for_checking_account() {
        assertEquals(BALANCE, Double.toString(cd.getBalance()));
    }

    @Test
    void withdraw_amount_equal_to_balance_updates_balance_to_zero() {
        cd.withdraw(5000);
        assertEquals(0, cd.getBalance());
    }

    @Test
    void withdraw_amount_equal_to_balance_is_valid() {
        assertTrue(cd.isValidWithdrawAmount(5000));
    }

    @Test
    void withdraw_amount_greater_than_balance_is_valid() {
        assertTrue(cd.isValidWithdrawAmount(5400));
    }

    @Test
    void withdraw_amount_less_than_balance_is_invalid() {
        assertFalse(cd.isValidWithdrawAmount(1000));
    }

    @Test
    void allow_withdraw_with_12_months_passing() {
        cd.totalMonthsFromPassTime = 12;
        assertTrue(cd.allowedWithdrawAtCurrentTime());
    }

    @Test
    void allow_withdraw_with_greater_than_12_months_passing() {
        cd.totalMonthsFromPassTime = 22;
        assertTrue(cd.allowedWithdrawAtCurrentTime());
    }

    @Test
    void do_not_allow_withdraw_with_less_than_12_months_passing() {
        cd.totalMonthsFromPassTime = 11;
        assertFalse(cd.allowedWithdrawAtCurrentTime());
    }
}
