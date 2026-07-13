package banking;

public class Checking extends Account {

    Checking(String apr, String id, String balance) {
        super(apr, id, balance);
    }

    @Override
    public String getType() {
        return "Checking";
    }

    @Override
    public boolean isValidDepositAmount(double depAmount) {
        if (depAmount >= 0 && depAmount <= 1000.00) {
            return true;
        } else {
            return false;
        }
   }

   @Override
   public boolean isValidWithdrawAmount(double withdrawAmount) {
        if (withdrawAmount >= 0 && withdrawAmount <= 400.00) {
            return true;
        } else {
            return false;
        }
   }
}
