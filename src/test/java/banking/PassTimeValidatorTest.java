package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {

    CommandValidator commandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new CommandValidator(bank);
    }

    @Test
    void pass_time_command_is_valid() {
        boolean actual = commandValidator.validateCommand("pass 5");
        assertTrue(actual);
    }

    @Test
    void pass_time_command_missing_a_part_is_invalid() {
        boolean actual = commandValidator.validateCommand("pass");
        assertFalse(actual);
    }

    @Test
    void pass_time_command_with_a_typo_in_pass_keyword_is_invalid() {
        boolean actual = commandValidator.validateCommand("pas 5");
        assertFalse(actual);
    }

    @Test
    void pass_time_command_case_sensitivity_does_not_matter_is_valid() {
        boolean actual = commandValidator.validateCommand("pAsS 5");
        assertTrue(actual);
    }

    @Test
    void pass_time_command_with_month_in_range_is_valid() {
        boolean actual = commandValidator.validateCommand("pass 30");
        assertTrue(actual);
    }

    @Test
    void pass_time_command_with_month_at_max_in_range_is_valid() {
        boolean actual = commandValidator.validateCommand("pass 60");
        assertTrue(actual);
    }

    @Test
    void pass_time_command_with_month_at_min_in_range_is_valid() {
        boolean actual = commandValidator.validateCommand("pass 1");
        assertTrue(actual);
    }

    @Test
    void pass_time_command_with_month_above_max_range_is_invalid() {
        boolean actual = commandValidator.validateCommand("pass 61");
        assertFalse(actual);
    }

    @Test
    void pass_time_command_with_month_below_min_range_is_invalid() {
        boolean actual = commandValidator.validateCommand("pass 0");
        assertFalse(actual);
    }

}
