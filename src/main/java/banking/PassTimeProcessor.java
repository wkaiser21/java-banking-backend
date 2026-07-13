package banking;

public class PassTimeProcessor extends CommandProcessor {

    public PassTimeProcessor(Bank bank) {
        super(bank);
    }

    public void processPassTime(String userInput) {

        String[] commandString = userInput.split(" ");

        bank.passTimeOverAccounts(Integer.parseInt(commandString[1]));
    }
}
