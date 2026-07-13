package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    public static final String APR = "0.6";
    public static final String ID = "12345678";
    public static final String BALANCE = "0.0";
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account(APR, ID, BALANCE);
    }

    @Test
    void account_has_an_apr() {
        assertEquals(APR, Double.toString(account.getApr()));
    }

    @Test
    void account_has_an_id() {
        assertEquals(ID, account.getId());
    }

    @Test
    void account_has_a_balance_initially() {
        assertEquals(BALANCE, Double.toString(account.getBalance()));
    }

    @Test
    void balance_updates_correctly_after_depositing() {
        account.deposit(200);
        assertEquals(200, account.getBalance());
    }

    @Test
    void balance_updates_correctly_after_withdrawing() {
        account.deposit(500);
        account.withdraw(100);
        assertEquals(400, account.getBalance());
    }

    @Test
    void get_account_type_null() {
        assertNull(account.getType());
    }

    @Test
    void withdraw_amount_equal_to_balance_updates_balance_to_zero() {
        account.deposit(200);
        account.withdraw(200);
        assertEquals(0, account.getBalance());
    }

    @Test
    void withdraw_amount_equal_to_balance_is_valid() {
        account.deposit(200);
        assertTrue(account.isValidWithdrawAmount(200));
    }

    @Test
    void withdraw_amount_greater_than_balance_is_valid() {
        account.deposit(200);
        assertTrue(account.isValidWithdrawAmount(300));
    }

    @Test
    void balance_greater_than_25_deduct_fee_correctly() {
        account.deposit(200);
        account.applyFee();
        assertEquals(175, account.getBalance());
    }

    @Test
    void balance_equal_to_25_then_balance_goes_to_zero() {
        account.deposit(25);
        account.applyFee();
        assertEquals(0, account.getBalance());
    }

    @Test
    void balance_less_than_25_then_balance_goes_to_zero() {
        account.deposit(24);
        account.applyFee();
        assertEquals(0, account.getBalance());
    }
}
