package banking;

public class CreateValidator extends CommandValidator {

    public CreateValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validateCommand(String command) {
        String[] commandString = command.split(" ");

        if (commandString.length < 4 || commandString.length > 5) {
            return false;
        }

        if (commandString.length == 4 && commandString[1].equalsIgnoreCase("cd")) {
            return false;
        }
        
        if (commandString.length == 5 && !(commandString[1].equalsIgnoreCase("cd"))) {
            return false;
        }

        if (commandString.length == 5 && (Double.parseDouble(commandString[4]) < 1000 || Double.parseDouble(commandString[4]) > 10000)) {
            return false;
        }

        return isCreateAccountTypeValid(commandString[1]) && isIdAllowed(commandString[2]) && isAprValid(commandString[3]);
    }
}
