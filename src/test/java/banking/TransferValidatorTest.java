package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {

    public static final String ID = "12345678";
    public static final String APR = "0.6";
    public static final String BALANCE = "1000";

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void transfer_command_missing_a_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_a_typo_in_transfer_keyword_is_invalid() {
        boolean actual = commandValidator.validateCommand("transfe 12345678 12345679 200");
        assertFalse(actual);
    }

    @Test
    void transfer_command_case_sensitivity_does_not_matter_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("tRanSFer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_with_given_to_id_that_does_not_exist_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_given_to_id_that_does_exist_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_with_given_from_id_that_does_not_exist_is_invalid() {
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_given_from_id_that_does_exist_is_valid() {
        bank.createCheckingAccount(APR, "12345679");
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_with_from_and_to_ids_being_the_same_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345678 200");
        assertFalse(actual);
    }

    @Test
    void transfer_command_checking_to_savings_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_savings_to_checking_is_valid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_savings_to_savings_is_valid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertTrue(actual);
    }

    @Test
    void transfer_command_transfer_to_cd_invalid() {
        bank.createCdAccount(APR, "12345679", "2000");
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "300");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertFalse(actual);
    }

    @Test
    void transfer_command_transfer_from_cd_invalid() {
        bank.createCdAccount(APR, ID, "2000");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 200");
        assertFalse(actual);
    }

    @Test
    void transfer_0_to_checking_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "500");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 0");
        assertTrue(actual);
    }

    @Test
    void transfer_0_to_savings_is_valid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "1500");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 0");
        assertTrue(actual);
    }

    @Test
    void transfer_command_more_than_checking_withdraw_limit_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 500");
        assertFalse(actual);
    }

    @Test
    void transfer_command_more_than_savings_withdraw_limit_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "1500");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_command_more_than_checking_withdraw_and_deposit_limit_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 1001");
        assertFalse(actual);
    }

    @Test
    void transfer_command_more_than_savings_withdraw_and_deposit_limit_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 2501");
        assertFalse(actual);
    }

    @Test
    void transfer_command_at_checking_withdraw_max_limit_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 400");
        assertTrue(actual);
    }

    @Test
    void transfer_command_at_savings_withdraw_max_limit_is_valid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "1500");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 1000");
        assertTrue(actual);
    }

    @Test
    void transfer_command_negative_amount_to_checking_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 -100");
        assertFalse(actual);
    }

    @Test
    void transfer_command_negative_amount_to_savings_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "1500");
        bank.createSavingsAccount(APR, "12345679");
        boolean actual = commandValidator.validateCommand("transfer 12345678 12345679 -1000");
        assertFalse(actual);
    }

}
