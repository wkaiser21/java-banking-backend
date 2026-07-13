package banking;

public class TransferValidator extends CommandValidator {

    public TransferValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateCommand(String command) {
        String[] commandString = command.split(" ");

        if (commandString.length != 4) {
            return false;
        }
        if (commandString[1].equalsIgnoreCase(commandString[2])) {
            return false;
        }

        if (!(isIdInBankAlready(commandString[1]) && isIdInBankAlready(commandString[2]))) {
            return false;
        }

        return (validWithdrawTime(commandString[1]) && validateTransfer(commandString[1], commandString[2], commandString[3]));

    }

}
