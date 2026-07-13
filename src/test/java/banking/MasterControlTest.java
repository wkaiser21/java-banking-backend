package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MasterControlTest {

    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank),
                new CommandStorage(bank));
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("depositt 12345678 100", actual);
    }

    @Test
    void two_typo_commands_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("depositt 12345678 100");

        List<String> actual = masterControl.start(input);

        List<String> local = new ArrayList<>();
        local.add("creat checking 12345678 1.0");
        local.add("depositt 12345678 100");

        assertEquals(2, actual.size());
        assertEquals(actual, local);
    }

    @Test
    void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Deposit 12345678 5000");
        input.add("creAte cHecKing 98765432 0.01");
        input.add("Deposit 98765432 300");
        input.add("Transfer 98765432 12345678 300");
        input.add("Pass 1");
        input.add("Create cd 23456789 1.2 2000");
        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Transfer 98765432 12345678 300", actual.get(2));
        assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
        assertEquals("Deposit 12345678 5000", actual.get(4));
    }

    @Test
    void shows_correct_checking_account_status() {
        input.add("Create checking 12345678 0.6");
        input.add("Deposit 12345678 700");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Checking 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
    }

    @Test
    void shows_correct_savings_account_status() {
        input.add("Create savings 12345678 0.6");
        input.add("Deposit 12345678 700");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Savings 12345678 700.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
    }

    @Test
    void shows_correct_cd_account_status() {
        input.add("Create cd 12345678 0.6 2000");
        input.add("Deposit 12345678 700");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("Cd 12345678 2000.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
    }

    @Test
    void shows_valid_transaction_commands_in_correct_order() {
        input.add("Create checking 12345678 0.6");
        input.add("Deposit 12345678 700");
        input.add("Withdraw 12345678 300");

        List<String> actual = masterControl.start(input);

        assertEquals(3, actual.size());
        assertEquals("Checking 12345678 400.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Withdraw 12345678 300", actual.get(2));
    }

    @Test
    void shows_invalid_transaction_commands_after_valid_commands_in_order() {
        input.add("Create checking 12345678 0.6");
        input.add("Create not_type 12345677 0.6");
        input.add("Deposit 12345677 200");
        input.add("Deposit 12345678 700");
        input.add("Withdraw 12345678 300");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 12345678 400.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 700", actual.get(1));
        assertEquals("Withdraw 12345678 300", actual.get(2));
        assertEquals("Create not_type 12345677 0.6", actual.get(3));
        assertEquals("Deposit 12345677 200", actual.get(4));
    }

    @Test
    void output_is_empty_because_account_was_closed() {
        input.add("Create checking 12345678 0.6");
        input.add("Pass 1");

        List<String> actual = masterControl.start(input);

        assertTrue(actual.isEmpty());
    }

    @Test
    void first_created_account_and_its_valid_transactions_appear_first_before_second_account() {
        input.add("Create checking 12345678 0.6");
        input.add("Create checking 12345677 0.6");
        input.add("Deposit 12345678 200");
        input.add("Deposit 12345677 700");
        input.add("Withdraw 12345678 100");

        List<String> actual = masterControl.start(input);

        assertEquals(5, actual.size());
        assertEquals("Checking 12345678 100.00 0.60", actual.get(0));
        assertEquals("Deposit 12345678 200", actual.get(1));
        assertEquals("Withdraw 12345678 100", actual.get(2));
        assertEquals("Checking 12345677 700.00 0.60", actual.get(3));
        assertEquals("Deposit 12345677 700", actual.get(4));
    }

    @Test
    void min_balance_fee_applied_to_both_accounts() {
        input.add("Create checking 12345678 0.6");
        input.add("Create checking 12345677 0.6");
        input.add("Deposit 12345678 75");
        input.add("Deposit 12345677 50");
        input.add("Pass 1");

        List<String> actual = masterControl.start(input);

        assertEquals(4, actual.size());
        assertEquals("Checking 12345678 50.02 0.60", actual.get(0));
        assertEquals("Deposit 12345678 75", actual.get(1));
        assertEquals("Checking 12345677 25.01 0.60", actual.get(2));
        assertEquals("Deposit 12345677 50", actual.get(3));
    }

}
