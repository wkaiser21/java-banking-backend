package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void createCheckingAccount(String apr, String id) {
        accounts.put(id, new Checking(apr, id, Double.toString(0.0)));
    }

    public void createSavingsAccount(String apr, String id) {
        accounts.put(id, new Savings(apr, id, Double.toString(0.0)));
    }

    public void createCdAccount(String apr, String id, String balance) {
        accounts.put(id, new Cd(apr, id, balance));
    }

    public void depositToAccount(String id, String depositAmount) {
        accounts.get(id).deposit(Double.parseDouble(depositAmount));
    }

    public void withdrawFromAccount(String id, String withdrawAmount) {
        accounts.get(id).withdraw(Double.parseDouble(withdrawAmount));
    }

    public void transferToAccount(String fromId, String toId, String transferAmount) {
        if (accounts.get(fromId).getBalance() < Double.parseDouble(transferAmount)) {
            accounts.get(toId).deposit(accounts.get(fromId).getBalance());
        } else {
            accounts.get(toId).deposit(Double.parseDouble(transferAmount));
        }
        accounts.get(fromId).withdraw(Double.parseDouble(transferAmount));
    }

    public boolean accountExistsById(String id) {
        if (accounts.get(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDepositAmountInValidRange(String id, double depAmount) {
        return accounts.get(id).isValidDepositAmount(depAmount);
    }

    public boolean isWithdrawAmountInValidRange(String id, double withdrawAmount) {
        return accounts.get(id).isValidWithdrawAmount(withdrawAmount);
    }

    public boolean accountAllowedWithdrawAtTime(String id) {
        return accounts.get(id).allowedWithdrawAtCurrentTime();
    }

    public boolean isTransferAmountInValidRange(String fromId, String toId, double transferAmount) {
        return (isWithdrawAmountInValidRange(fromId, transferAmount) && isDepositAmountInValidRange(toId, transferAmount));
    }

    public void passTimeOverAccounts(int timeToPass) {
        List<Account> accountsForRemoval = new ArrayList<>();

        for (int i = 0; i < timeToPass; i++) {
            removeAccountInBankWithBalanceOfZero(accountsForRemoval);
            applyMinBalanceFee();
            calculateAccountApr();
            setNewWithdrawStatusForSavingsAndCdAccounts();
        }
    }

    private void removeAccountInBankWithBalanceOfZero(List<Account> accountsToClose) {
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            String accountId = accountEntry.getValue().getId();
            if (accounts.get(accountId).getBalance() == 0) {
                accountsToClose.add(accounts.get(accountId));
            }
        }

        for (Account account : accountsToClose) {
            accounts.remove(account.getId());
        }
    }

    public void applyMinBalanceFee() {
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            String accountId = accountEntry.getValue().getId();
            if (accounts.get(accountId).getBalance() < 100) {
                accounts.get(accountId).applyFee();
            }
        }
    }

    private void calculateAccountApr() {
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            String accountId = accountEntry.getValue().getId();
            accounts.get(accountId).calculateApr();
        }
    }

    public void setNewWithdrawStatusForSavingsAndCdAccounts() {
        for (Map.Entry<String, Account> accountEntry : accounts.entrySet()) {
            String accountId = accountEntry.getValue().getId();
            if (accounts.get(accountId).getType().equalsIgnoreCase("Savings")) {
                accounts.get(accountId).notAllowWithdraw = false;
            }
            else if (accounts.get(accountId).getType().equalsIgnoreCase("CD")) {
                accounts.get(accountId).totalMonthsFromPassTime += 1;
            }
        }
    }

    public void addToHistory(String accountId, String command) {
        accounts.get(accountId).allTransactionHistory.add(command);
    }
}
