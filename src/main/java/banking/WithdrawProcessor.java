package banking;

public class WithdrawProcessor extends CommandProcessor {

    public WithdrawProcessor(Bank bank) {
        super(bank);
    }

    public void processWithdraw(String userInput) {

        String[] commandString = userInput.split(" ");

        bank.withdrawFromAccount(commandString[1], commandString[2]);

        addToAccountHistory(commandString[1], userInput);
    }
}
