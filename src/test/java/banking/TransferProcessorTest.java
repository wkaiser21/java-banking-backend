package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferProcessorTest {

    public static final String TRANSFER_INPUT = "transfer 12345678 12345679 200";
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
    void transfer_from_checking_to_checking_successful() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createCheckingAccount(APR, "12345679");
        commandProcessor.processCommand(TRANSFER_INPUT);
        assertEquals(200, bank.getAccounts().get("12345679").getBalance());
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void transfer_from_savings_to_savings_successful() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createSavingsAccount(APR, "12345679");
        commandProcessor.processCommand(TRANSFER_INPUT);
        assertEquals(200, bank.getAccounts().get("12345679").getBalance());
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void transfer_from_checking_to_savings_successful() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createSavingsAccount(APR, "12345679");
        commandProcessor.processCommand(TRANSFER_INPUT);
        assertEquals(200, bank.getAccounts().get("12345679").getBalance());
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void transfer_from_savings_to_checking_successful() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createCheckingAccount(APR, "12345679");
        commandProcessor.processCommand(TRANSFER_INPUT);
        assertEquals(200, bank.getAccounts().get("12345679").getBalance());
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void transfer_multiple_times() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createCheckingAccount(APR, "12345679");
        commandProcessor.processCommand("transfer 12345678 12345679 50");
        commandProcessor.processCommand("transfer 12345678 12345679 50");
        assertEquals(100, bank.getAccounts().get("12345679").getBalance());
        assertEquals(100, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void add_transfer_command_in_transaction_history_for_both_accounts() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        bank.createCheckingAccount(APR, "12345679");
        commandProcessor.processCommand(TRANSFER_INPUT);
        List<String> local = new ArrayList<>();
        local.add(TRANSFER_INPUT);
        assertEquals(local, bank.getAccounts().get(ID).allTransactionHistory);
        assertEquals(local, bank.getAccounts().get("12345679").allTransactionHistory);
    }


}
