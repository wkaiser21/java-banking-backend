package banking;

public class PassTimeValidator extends CommandValidator {

    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateCommand(String command) {
        String[] commandString = command.split(" ");

        if (commandString.length != 2) {
            return false;
        }

        return validPassTime(commandString[1]);

    }

    private boolean validPassTime(String time) {
        if (Integer.parseInt(time) >= 1 && Integer.parseInt(time) <= 60) {
            return true;
        } else {
            return false;
        }
    }
}
