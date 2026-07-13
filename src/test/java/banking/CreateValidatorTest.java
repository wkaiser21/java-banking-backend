package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }


    @Test
    void create_command_missing_a_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("crEaTe cHecKINg 0.6");
        assertFalse(actual);
    }

    @Test
    void create_command_with_a_typo_in_create_keyword_is_invalid() {
        boolean actual = commandValidator.validateCommand("crate checking 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_command_with_non_numerical_characters_is_invalid() {
        boolean actual = commandValidator.validateCommand("create checking 1234hjkl 0.6");
        assertFalse(actual);
    }

    @Test
    void create_command_case_sensitivity_does_not_matter_is_valid() {
        boolean actual = commandValidator.validateCommand("crEaTe cHecKINg 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void create_command_account_type_is_not_one_of_three_valid_options_is_invalid() {
        boolean actual = commandValidator.validateCommand("create notAccountType 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void create_command_with_amount_for_checking_is_invalid() {
        boolean actual = commandValidator.validateCommand("create checking 12345678 0.6 1000");
        assertFalse(actual);
    }

    @Test
    void create_command_with_amount_for_savings_is_invalid() {
        boolean actual = commandValidator.validateCommand("create savings 12345678 0.6 1000");
        assertFalse(actual);
    }

    @Test
    void create_command_cd_account_less_than_one_thousand_is_invalid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6 100.0");
        assertFalse(actual);
    }

    @Test
    void create_command_cd_account_greater_than_ten_thousand_is_invalid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6 20000.0");
        assertFalse(actual);
    }

    @Test
    void create_command_cd_account_amount_equals_one_thousand_is_valid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6 1000.0");
        assertTrue(actual);
    }

    @Test
    void create_command_cd_account_amount_equals_ten_thousand_is_valid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6 10000.0");
        assertTrue(actual);
    }

    @Test
    void create_command_cd_account_amount_in_valid_range_is_valid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6 2000.0");
        assertTrue(actual);
    }

    @Test
    void create_command_cd_account_missing_balance_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("create cd 12345678 0.6");
        assertFalse(actual);
    }

}
