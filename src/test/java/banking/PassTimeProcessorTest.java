package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeProcessorTest {

    public static final String APR = "0.6";
    public static final String ID = "12345678";

    Bank bank;
    CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void pass_one_month_updates_balance_successfully() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand("Pass 1");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 200.10);
    }

    @Test
    void pass_two_month_updates_balance_successfully() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand("Pass 2");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 200.20);
    }

    @Test
    void pass_one_month_on_accounts_with_balance_zero_removes_account_successfully() {
        bank.createCheckingAccount(APR, ID);
        commandProcessor.processCommand("Pass 1");
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void pass_one_month_on_multiple_accounts_is_successful() {
        bank.createCheckingAccount(APR, ID);
        bank.createCheckingAccount(APR, "12345679");
        bank.depositToAccount(ID, "200");
        bank.depositToAccount("12345679", "200");
        commandProcessor.processCommand("Pass 1");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 200.10);
        assertEquals(bank.getAccounts().get("12345679").getBalance(), 200.10);
    }

    @Test
    void pass_one_month_on_cd_account_updates_balance_successfully() {
        bank.createCdAccount(APR, ID, "1000");
        commandProcessor.processCommand("Pass 1");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 1002.00);
    }

    @Test
    void minimum_balance_fee_applies_successfully_if_balance_less_than_100_but_greater_than_25() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.withdrawFromAccount(ID, "150");
        commandProcessor.processCommand("Pass 1");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 25.01);
    }

    @Test
    void minimum_balance_fee_applies_successfully_if_balance_less_than_25_set_balance_to_zero() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.withdrawFromAccount(ID, "180");
        commandProcessor.processCommand("Pass 1");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 0.00);
    }
}
