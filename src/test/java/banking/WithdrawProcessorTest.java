package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawProcessorTest {

    public static final String WITHDRAW_INPUT = "withdraw 12345678 100";
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
    void existing_checking_account_has_balance_subtracted_properly() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand(WITHDRAW_INPUT);
        assertEquals(100, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void existing_savings_account_has_balance_subtracted_properly() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand(WITHDRAW_INPUT);
        assertEquals(100, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void withdraw_on_balance_with_less_than_withdraw_amount_set_balance_to_zero() {
        bank.createSavingsAccount(APR, ID);
        commandProcessor.processCommand(WITHDRAW_INPUT);
        assertEquals(0.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void existing_cd_account_has_balance_subtracted_properly() {
        bank.createCdAccount(APR, ID, "5000");
        commandProcessor.processCommand("Pass 12");
        commandProcessor.processCommand("Withdraw 12345678 5121.32");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 0.00);
    }

    @Test
    void add_withdraw_command_in_transaction_history_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand(WITHDRAW_INPUT);
        List<String> local = new ArrayList<>();
        local.add(WITHDRAW_INPUT);
        assertEquals(local, bank.getAccounts().get(ID).allTransactionHistory);
    }

    @Test
    void add_multiple_withdraw_commands_in_transaction_history_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        commandProcessor.processCommand(WITHDRAW_INPUT);
        commandProcessor.processCommand("withdraw 12345678 100");
        List<String> local = new ArrayList<>();
        local.add(WITHDRAW_INPUT);
        local.add("withdraw 12345678 100");
        assertEquals(local, bank.getAccounts().get(ID).allTransactionHistory);
    }
}
