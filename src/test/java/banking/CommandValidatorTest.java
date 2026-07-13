package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandValidatorTest {

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
    void apr_value_from_zero_to_ten_is_valid() {
        boolean actual = commandValidator.isAprValid(APR);
        assertTrue(actual);
    }

    @Test
    void apr_value_of_zero_is_valid() {
        boolean actual = commandValidator.isAprValid("0.0");
        assertTrue(actual);
    }

    @Test
    void apr_value_of_ten_is_valid() {
        boolean actual = commandValidator.isAprValid("10.0");
        assertTrue(actual);
    }

    @Test
    void apr_value_greater_than_ten_is_invalid() {
        boolean actual = commandValidator.isAprValid("20.0");
        assertFalse(actual);
    }

    @Test
    void apr_value_less_than_zero_is_invalid() {
        boolean actual = commandValidator.isAprValid("-2.0");
        assertFalse(actual);
    }

    @Test
    void apr_not_number_is_invalid() {
        boolean actual = commandValidator.isAprValid("hello");
        assertFalse(actual);
    }

    @Test
    void id_eight_digits_in_length_is_valid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.isIdValid(ID);
        assertTrue(actual);
    }

    @Test
    void id_less_than_eight_digits_in_length_is_invalid() {
        bank.createCheckingAccount(APR, "1234567");
        boolean actual = commandValidator.isIdValid("1234567");
        assertFalse(actual);
    }

    @Test
    void id_greater_than_eight_digits_in_length_is_invalid() {
        bank.createCheckingAccount(APR, ID + "1");
        boolean actual = commandValidator.isIdValid(ID + "1");
        assertFalse(actual);
    }

    @Test
    void id_with_non_numerical_characters_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.isIdValid("1234b67k");
        assertFalse(actual);
    }

    @Test
    void already_existing_id_is_invalid() {
        bank.createCheckingAccount(APR, ID);
        boolean actual = commandValidator.isIdAllowed(ID);
        assertFalse(actual);
    }

    @Test
    void id_not_existing_already_is_valid() {
        boolean actual = commandValidator.isIdAllowed(ID);
        assertTrue(actual);
    }

    @Test
    void enters_nothing_is_not_allowed() {
        boolean actual = commandValidator.validateCommand("");
        assertFalse(actual);
    }

    @Test
    void enters_only_a_white_space_is_not_allowed() {
        boolean actual = commandValidator.validateCommand(" ");
        assertFalse(actual);
    }

    @Test
    void enters_only_multiple_white_spaces_is_not_allowed() {
        boolean actual = commandValidator.validateCommand("         ");
        assertFalse(actual);
    }

}
