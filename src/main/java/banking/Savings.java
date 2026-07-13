package banking;

public class Savings extends Account {

    Savings(String apr, String id, String balance) {
        super(apr, id, balance);
    }

    @Override
    public String getType() {
        return "Savings";
    }

    @Override
    public boolean isValidDepositAmount(double depAmount) {
        if (depAmount >= 0 && depAmount <= 2500.00) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount >= 0 && withdrawAmount <= 1000.00) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            balance = 0;
        } else {
            balance -= withdrawAmount;
        }
        notAllowWithdraw = true;
    }

    @Override
    public boolean allowedWithdrawAtCurrentTime() {
        return !notAllowWithdraw;
    }
}
