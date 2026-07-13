package banking;

public class CommandProcessor {

    protected Bank bank;
    protected CreateProcessor createProcessor;
    protected DepositProcessor depositProcessor;
    protected WithdrawProcessor withdrawProcessor;
    protected TransferProcessor transferProcessor;
    protected PassTimeProcessor passTimeProcessor;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
    }

    public void processCommand(String userInput) {
        createProcessor = new CreateProcessor(bank);
        depositProcessor = new DepositProcessor(bank);
        withdrawProcessor = new WithdrawProcessor(bank);
        transferProcessor = new TransferProcessor(bank);
        passTimeProcessor = new PassTimeProcessor(bank);

        String[] commandString = userInput.split(" ");

        if (commandString[0].equalsIgnoreCase("create")) {
            createProcessor.processCreate(userInput);
        }
        if (commandString[0].equalsIgnoreCase("deposit")) {
            depositProcessor.processDeposit(userInput);
        }
        if (commandString[0].equalsIgnoreCase("withdraw")) {
            withdrawProcessor.processWithdraw(userInput);
        }
        if (commandString[0].equalsIgnoreCase("transfer")) {
            transferProcessor.processTransfer(userInput);
        }
        if (commandString[0].equalsIgnoreCase("pass")) {
            passTimeProcessor.processPassTime(userInput);
        }
    }

    public void addToAccountHistory(String accountId, String command) {
        bank.addToHistory(accountId, command);
    }
}
