package banking;

public class DepositValidator extends CommandValidator {

    public DepositValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateCommand(String command) {
        String[] commandString = command.split(" ");

        if (commandString.length != 3) {
            return false;
        }

        if (!(isIdInBankAlready(commandString[1]))) {
            return false;
        }

        return validateDeposit(commandString[1], commandString[2]);
    }

}
