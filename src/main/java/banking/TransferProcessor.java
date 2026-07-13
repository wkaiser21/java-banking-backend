package banking;

public class TransferProcessor extends CommandProcessor {
    public TransferProcessor(Bank bank) {
        super(bank);

    }

    public void processTransfer(String userInput) {
        String[] commandString = userInput.split(" ");

        bank.transferToAccount(commandString[1], commandString[2], commandString[3]);

        addToAccountHistory(commandString[1], userInput);
        addToAccountHistory(commandString[2], userInput);
    }
}
