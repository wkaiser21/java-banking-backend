package banking;

public class DepositProcessor extends CommandProcessor {
    public DepositProcessor(Bank bank) {
        super(bank);
    }

    public void processDeposit(String userInput) {

        String[] commandString = userInput.split(" ");

        bank.depositToAccount(commandString[1], commandString[2]);
        addToAccountHistory(commandString[1], userInput);
    }
}
