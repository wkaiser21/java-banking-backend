package banking;

public class CommandValidator {

    protected Bank bank;
    CreateValidator createValidator;
    DepositValidator depositValidator;
    WithdrawValidator withdrawValidator;
    TransferValidator transferValidator;
    PassTimeValidator passTimeValidator;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validateCommand(String command) {

        createValidator = new CreateValidator(bank);
        depositValidator = new DepositValidator(bank);
        withdrawValidator = new WithdrawValidator(bank);
        transferValidator = new TransferValidator(bank);
        passTimeValidator = new PassTimeValidator(bank);

        String[] commandString = command.split(" ");

        if (!command.trim().isEmpty() && commandString[0].equalsIgnoreCase("Create")) {
            return createValidator.validateCommand(command);
        }
        else if (!command.trim().isEmpty() && commandString[0].equalsIgnoreCase("Deposit")) {
            return depositValidator.validateCommand(command);
        }
        else if (!command.trim().isEmpty() && commandString[0].equalsIgnoreCase("Withdraw")) {
            return withdrawValidator.validateCommand(command);
        }
        else if (!command.trim().isEmpty() && commandString[0].equalsIgnoreCase("Transfer")) {
            return transferValidator.validateCommand(command);
        }
        else if (!command.trim().isEmpty() && commandString[0].equalsIgnoreCase("Pass")) {
            return passTimeValidator.validateCommand(command);
        }
        else {
            return false;
        }
    }

    public boolean isAprValid(String apr) {
        try {
            if (Double.parseDouble(apr) >= 0 && Double.parseDouble(apr) <= 10) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException error) {
            return false;
        }
    }

    public boolean isIdValid(String id) {
        if (id.length() == 8 && id.matches("[0-9]+")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isIdInBankAlready(String id) {
        return (bank.accountExistsById(id));
    }

    public boolean isIdAllowed(String id) {
        if (!(isIdInBankAlready(id)) && isIdValid(id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCreateAccountTypeValid(String account_type) {
        if (account_type.equalsIgnoreCase("Checking")) {
            return true;
        }
        else if (account_type.equalsIgnoreCase("Savings")) {
            return true;
        }
        else if (account_type.equalsIgnoreCase("Cd")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean validateDeposit(String id, String amount) {
        return (bank.isDepositAmountInValidRange(id, Double.parseDouble(amount)));
    }

    public boolean validateWithdraw(String id, String amount) {
        return (bank.isWithdrawAmountInValidRange(id, Double.parseDouble(amount)));
    }

    public boolean validWithdrawTime(String id) {
        return bank.accountAllowedWithdrawAtTime(id);
    }

    public boolean validateTransfer(String fromId, String toId, String amount) {
        return (bank.isTransferAmountInValidRange(fromId, toId, Double.parseDouble(amount)));
    }
}
