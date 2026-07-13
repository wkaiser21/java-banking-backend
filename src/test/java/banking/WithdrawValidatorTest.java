package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawValidatorTest {

    public static final String ID = "12345678";
    public static final String APR = "0.6";

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void withdraw_command_missing_a_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("withdraw 12345678");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_with_a_typo_in_withdraw_keyword_is_invalid() {
        boolean actual = commandValidator.validateCommand("withdra 12345678 100");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_case_sensitivity_does_not_matter_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "200");
        boolean actual = commandValidator.validateCommand("wIThdRAw 12345678 100");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_into_account_with_given_id_that_does_not_exist_is_invalid() {
        boolean actual = commandValidator.validateCommand("withdraw 12345678 100");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_from_existing_account_with_given_id_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 100");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_within_checking_range_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_within_savings_range_and_not_withdrawn_yet_this_month_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 800");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_zero_from_checking_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_zero_from_savings_and_not_withdrawn_this_month_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_from_checking_at_max_amount_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 400");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_from_savings_at_max_amount_and_first_withdraw_of_month_is_valid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 1000");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_greater_than_checking_max_amount_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 401");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_greater_than_savings_max_amount_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 1001");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_negative_amount_from_checking_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 -100");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_negative_amount_from_savings_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 -100");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_multiple_times_from_checking_is_valid() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "600");
        bank.withdrawFromAccount(ID, "200");
        boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_multiple_times_in_same_month_from_savings_is_invalid() {
        bank.createSavingsAccount(APR, ID);
        bank.depositToAccount(ID, "600");
        bank.withdrawFromAccount(ID, "200");
        boolean actual = commandValidator.validateCommand("withdraw 12345678 200");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_full_balance_and_after_12_months_for_cd_account_is_valid() {
        bank.createCdAccount(APR, ID, "1000");
        bank.passTimeOverAccounts(12);
        bank.withdrawFromAccount(ID, "1024.22");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 0.00);
    }

    @Test
    void withdraw_command_greater_than_balance_and_after_12_months_for_cd_account_is_valid() {
        bank.createCdAccount(APR, ID, "1000");
        bank.passTimeOverAccounts(12);
        bank.withdrawFromAccount(ID, "1030.00");
        assertEquals(bank.getAccounts().get(ID).getBalance(), 0.00);
    }

    @Test
    void withdraw_command_less_than_balance_and_after_12_months_for_cd_account_is_invalid() {
        bank.createCdAccount(APR, ID, "1000");
        bank.passTimeOverAccounts(12);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 800");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_full_balance_and_after_less_than_12_months_for_cd_account_is_invalid() {
        bank.createCdAccount(APR, ID, "1000");
        bank.passTimeOverAccounts(11);
        boolean actual = commandValidator.validateCommand("withdraw 12345678 1022.18");
        assertFalse(actual);
    }
}
