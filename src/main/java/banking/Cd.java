package banking;

public class Cd extends Account {

    Cd(String apr, String id, String balance) {
        super(apr, id, balance);
    }

    @Override
    public String getType() {
        return "Cd";
    }

    @Override
    public void withdraw(double withdrawAmount) {
        balance = 0;
    }

    @Override
    public boolean allowedWithdrawAtCurrentTime() {
        if (totalMonthsFromPassTime >= 12) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void calculateApr() {
        double accountAprDecimalForm = (((getApr() / 100) / 12) * balance);
        for (int i = 0; i < 4; i++) {
            balance += accountAprDecimalForm;
        }
        balance = Double.parseDouble(decimalFormat.format(balance));
    }
}
