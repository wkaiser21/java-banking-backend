package banking;

public class WithdrawValidator extends CommandValidator {

    public WithdrawValidator(Bank bank) {
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

        return (validateWithdraw(commandString[1], commandString[2]) && validWithdrawTime(commandString[1]));
    }

}
