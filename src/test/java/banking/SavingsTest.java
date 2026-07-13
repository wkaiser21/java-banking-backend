package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsTest {

    public static final String APR = "0.6";
    public static final String ID = "12345678";
    public static final String BALANCE = "0.0";
    private static final String ACCOUNT_TYPE = "Savings";
    Savings savings;

    @BeforeEach
    void setUp() {
        savings = new Savings(APR, ID, BALANCE);
    }

    @Test
    void account_type_exists_for_savings_account() {
        assertEquals(ACCOUNT_TYPE, savings.getType());
    }

    @Test
    void apr_exists_for_checking_account() {
        assertEquals(APR, Double.toString(savings.getApr()));
    }

    @Test
    void id_exists_for_checking_account() {
        assertEquals(ID, savings.getId());
    }

    @Test
    void balance_exists_for_checking_account() {
        assertEquals(BALANCE, Double.toString(savings.getBalance()));
    }

    @Test
    void withdraw_amount_greater_than_balance_set_balance_to_zero() {
        savings.deposit(200);
        savings.withdraw(201);
        assertEquals(0.00, savings.getBalance());
    }

    @Test
    void withdraw_amount_equal_to_balance_updates_balance_to_zero() {
        savings.deposit(200);
        savings.withdraw(200);
        assertEquals(0.00, savings.getBalance());
    }

}
