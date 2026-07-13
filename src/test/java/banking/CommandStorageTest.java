package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandStorageTest {

    Bank bank;
    CommandStorage commandStorage;
    List<String> actual = new ArrayList<>();

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandStorage = new CommandStorage(bank);
    }

    @Test
    void invalid_command_list_is_empty_at_start() {
        assertTrue(commandStorage.getInvalidCommands().isEmpty());
    }

    @Test
    void add_command_to_invalid_list_successfully() {
        commandStorage.addInvalidCommand("create nottype 12345678 0.6");
        assertEquals("create nottype 12345678 0.6", commandStorage.getInvalidCommands().get(0));
    }

    @Test
    void add_many_commands_to_invalid_list_successfully() {
        commandStorage.addInvalidCommand("create nottype 12345678 0.6");
        commandStorage.addInvalidCommand("creat checking 12345678 0.6");
        commandStorage.addInvalidCommand("create savings 123 0.6");

        actual.add("create nottype 12345678 0.6");
        actual.add("creat checking 12345678 0.6");
        actual.add("create savings 123 0.6");

        assertEquals(actual, commandStorage.getInvalidCommands());
    }

    @Test
    void get_all_strings_from_invalid_list_in_correct_order() {
        commandStorage.addInvalidCommand("create sav 12345678 0.6");
        commandStorage.addInvalidCommand("cre savings 12345677 0.6");

        actual.add("create sav 12345678 0.6");
        actual.add("cre savings 12345677 0.6");

        assertEquals(actual, commandStorage.getInvalidCommands());
    }
}
