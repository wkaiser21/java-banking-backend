package banking;

public class CreateProcessor extends  CommandProcessor {

    public CreateProcessor(Bank bank) {
        super(bank);
    }

    public void processCreate(String userInput) {

        String[] commandString = userInput.split(" ");

        if (commandString[1].equalsIgnoreCase("checking")) {
            bank.createCheckingAccount(commandString[3], commandString[2]);
        }
        else if (commandString[1].equalsIgnoreCase("savings")) {
            bank.createSavingsAccount(commandString[3], commandString[2]);
        }
        else if (commandString[1].equalsIgnoreCase("cd")) {
            bank.createCdAccount(commandString[3], commandString[2], commandString[4]);
        }
        addToAccountHistory(commandString[2], userInput);
    }
}
