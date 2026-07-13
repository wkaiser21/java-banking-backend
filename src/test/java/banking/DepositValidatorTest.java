package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositValidatorTest {

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
    void deposit_command_missing_a_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("deposit 12345678");
        assertFalse(actual);
    }

    @Test
    void deposit_command_with_a_typo_in_deposit_keyword_is_invalid() {
        boolean actual = commandValidator.validateCommand("depsit 12345678 100");
        assertFalse(actual);
    }

    @Test
    void deposit_command_case_sensitivity_does_not_matter_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("dEPoSIt 12345678 100");
        assertTrue(actual);
    }

    @Test
    void deposit_command_into_account_with_given_id_that_does_not_exist_is_invalid() {
        boolean actual = commandValidator.validateCommand("deposit 12345678 100");
        assertFalse(actual);
    }

    @Test
    void deposit_command_into_existing_account_with_given_id_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 100");
        assertTrue(actual);
    }

    @Test
    void deposit_command_into_anything_other_than_checking_or_savings_account_is_invalid() {
        bank.createCdAccount(APR, ID, BALANCE);
        boolean actual = commandValidator.validateCommand("deposit 12345678 100");
        assertFalse(actual);
    }

    @Test
    void deposit_command_within_checking_range_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 500");
        assertTrue(actual);
    }

    @Test
    void deposit_command_within_savings_range_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 1500");
        assertTrue(actual);
    }

    @Test
    void deposit_command_zero_into_checking_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 0");
        assertTrue(actual);
    }

    @Test
    void deposit_command_zero_into_savings_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 0");
        assertTrue(actual);
    }

    @Test
    void deposit_command_into_checking_at_max_amount_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void deposit_command_into_savings_at_max_amount_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 2500");
        assertTrue(actual);
    }

    @Test
    void deposit_command_greater_than_checking_max_amount_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void deposit_command_greater_than_savings_max_amount_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 2501");
        assertFalse(actual);
    }

    @Test
    void deposit_command_negative_amount_into_checking_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 -100");
        assertFalse(actual);
    }

    @Test
    void deposit_command_negative_amount_into_savings_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("deposit 12345678 -100");
        assertFalse(actual);
    }

}
