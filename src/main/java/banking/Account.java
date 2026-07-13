package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Account {
    protected double apr;
    protected String id;
    protected double balance;
    protected boolean notAllowWithdraw;
    protected int totalMonthsFromPassTime = 0;
    DecimalFormat decimalFormat;
    protected List<String> allTransactionHistory;

    Account(String apr, String id, String balance) {
        decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        this.apr = Double.parseDouble(apr);
        this.id = id;
        this.balance = Double.parseDouble(decimalFormat.format(Double.parseDouble(balance)));
        allTransactionHistory = new ArrayList<>();
    }

    public double getApr() {
        return apr;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return null;
    }

    public void deposit(double depositAmount) {
        balance += depositAmount;
    }

    public void withdraw(double withdrawAmount) {
        if (withdrawAmount > balance) {
            balance = 0;
        } else {
            balance -= withdrawAmount;
        }
    }

    public boolean isValidDepositAmount(double depAmount) {
        return false;
    }

    public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount >= getBalance()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean allowedWithdrawAtCurrentTime() {
        return true;
    }

    public void applyFee() {
        if (balance > 25) {
            balance -= 25;
        } else if (balance <= 25) {
            balance = 0;
        }
    }

    public void calculateApr() {
        double accountAprDecimalForm = (((getApr() / 100) / 12) * balance);
        balance += accountAprDecimalForm;
        balance = Double.parseDouble(decimalFormat.format(balance));
    }

    public List<String> getPastTransactions() {
        String accountInfo = getType() + " " + getId() + " " + decimalFormat.format(getBalance()) + " " + decimalFormat.format(getApr());
        allTransactionHistory.set(0, accountInfo);
        return allTransactionHistory;
    }
}