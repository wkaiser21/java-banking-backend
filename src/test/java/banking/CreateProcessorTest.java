package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateProcessorTest {

    public static final String CREATE_CHECKING_INPUT = "create checking 12345678 0.6";
    public static final String CREATE_SAVINGS_INPUT = "create checking 12345679 0.6";
    public static final String CREATE_CD_INPUT = "create cd 12345677 0.6 2000";

    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "12345679";
    public static final String CD_ID = "12345677";

    Bank bank;
    CommandProcessor commandProcessor;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandProcessor = new CommandProcessor(bank);
    }

    @Test
    void creating_checking_account_adds_it_to_bank_account_list() {
        commandProcessor.processCommand(CREATE_CHECKING_INPUT);
        assertTrue(bank.accountExistsById(CHECKING_ID));
    }

    @Test
    void creating_savings_account_adds_it_to_bank_account_list() {
        commandProcessor.processCommand(CREATE_SAVINGS_INPUT);
        assertTrue(bank.accountExistsById(SAVINGS_ID));
    }

    @Test
    void creating_cd_account_adds_it_to_bank_account_list() {
        commandProcessor.processCommand(CREATE_CD_INPUT);
        assertTrue(bank.accountExistsById(CD_ID));
    }

    @Test
    void created_account_has_the_correct_account_type_from_input() {
        commandProcessor.processCommand(CREATE_CHECKING_INPUT);
        String type = bank.getAccounts().get(CHECKING_ID).getType();
        assertEquals("Checking", type);
    }

    @Test
    void created_account_has_the_correct_id_from_input() {
        commandProcessor.processCommand(CREATE_CHECKING_INPUT);
        String id = bank.getAccounts().get(CHECKING_ID).getId();
        assertEquals(CHECKING_ID, id);
    }

    @Test
    void created_account_has_the_correct_apr_from_input() {
        commandProcessor.processCommand(CREATE_CHECKING_INPUT);
        String apr = Double.toString(bank.getAccounts().get(CHECKING_ID).getApr());
        assertEquals("0.6", apr);
    }

    @Test
    void created_account_has_balance_of_zero_from_input() {
        commandProcessor.processCommand(CREATE_CHECKING_INPUT);
        String balance = Double.toString(bank.getAccounts().get(CHECKING_ID).getBalance());
        assertEquals("0.0", balance);
    }

    @Test
    void created_cd_account_has_correct_balance_from_input() {
        commandProcessor.processCommand(CREATE_CD_INPUT);
        String balance = Double.toString(bank.getAccounts().get(CD_ID).getBalance());
        assertEquals("2000.0", balance);
    }

}
