package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandStorage {

    public List<String> invalidCommands;
    public List<String> finalOutputList;
    Bank bank;

    public CommandStorage(Bank bank) {
        invalidCommands = new ArrayList<>();
        finalOutputList = new ArrayList<>();
        this.bank = bank;
    }

    public void addInvalidCommand(String command) {
        invalidCommands.add(command);
    }

    public List<String> getInvalidCommands() {
        return invalidCommands;
    }

    public List<String> getCompleteOutput() {
        addValidCommandsToOutput();
        addInvalidCommandsToOutput();
        return finalOutputList;
    }

    public void addValidCommandsToOutput() {
        for (Map.Entry<String, Account> accountEntry : bank.getAccounts().entrySet()) {
            String accountId = accountEntry.getValue().getId();
            List<String> newTransactions = bank.getAccounts().get(accountId).getPastTransactions();
            finalOutputList.addAll(newTransactions);
        }
    }

    public void addInvalidCommandsToOutput() {
        finalOutputList.addAll(invalidCommands);
    }

}
