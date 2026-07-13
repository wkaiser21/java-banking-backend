package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositProcessorTest {

    public static final String DEPOSIT_INPUT = "deposit 12345678 500";
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
    void checking_account_just_created_with_no_balance_has_a_balance_of_what_was_inputted() {
        bank.createCheckingAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        assertEquals(500, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void savings_account_just_created_with_no_balance_has_a_balance_of_what_was_inputted() {
        bank.createSavingsAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        assertEquals(500, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void checking_account_with_existing_balance_has_amount_added_correctly() {
        bank.createCheckingAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        assertEquals(1000, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void savings_account_with_existing_balance_has_amount_added_correctly() {
        bank.createSavingsAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        assertEquals(1000, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void add_deposit_command_in_transaction_history_an_account() {
        bank.createCheckingAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        List<String> local = new ArrayList<>();
        local.add(DEPOSIT_INPUT);
        assertEquals(local, bank.getAccounts().get(ID).allTransactionHistory);
    }

    @Test
    void add_multiple_deposit_commands_in_transaction_history_an_account() {
        bank.createCheckingAccount(APR, ID);
        commandProcessor.processCommand(DEPOSIT_INPUT);
        commandProcessor.processCommand("deposit 12345678 100");
        List<String> local = new ArrayList<>();
        local.add(DEPOSIT_INPUT);
        local.add("deposit 12345678 100");
        assertEquals(local, bank.getAccounts().get(ID).allTransactionHistory);
    }

}
