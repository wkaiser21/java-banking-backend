package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    public static final String APR = "0.6";
    public static final String ID = "12345678";
    public static final String BALANCE = "0.0";
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_begins_with_zero_accounts() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void create_a_checking_account() {
        bank.createCheckingAccount(APR, ID);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }

    @Test
    void create_multiple_checking_accounts() {
        bank.createCheckingAccount(APR, ID);
        bank.createCheckingAccount(APR, ID + "1");
        assertEquals(ID + "1", bank.getAccounts().get(ID + "1").getId());
    }

    @Test
    void checking_account_starting_balance_is_zero() {
        bank.createCheckingAccount(APR, ID);
        assertEquals(0.0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void create_a_savings_account() {
        bank.createSavingsAccount(APR, ID);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }

    @Test
    void create_multiple_savings_accounts() {
        bank.createSavingsAccount(APR, ID);
        bank.createSavingsAccount(APR, ID + "1");
        assertEquals(ID + "1", bank.getAccounts().get(ID + "1").getId());
    }

    @Test
    void savings_account_starting_balance_is_zero() {
        bank.createSavingsAccount(APR, ID);
        assertEquals(0.0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void create_a_cd_account() {
        bank.createCdAccount(APR, ID, BALANCE);
        assertEquals(ID, bank.getAccounts().get(ID).getId());
    }

    @Test
    void create_multiple_cd_accounts() {
        bank.createCdAccount(APR, ID, BALANCE);
        bank.createCdAccount(APR, ID + "1", BALANCE);
        assertEquals(ID + "1", bank.getAccounts().get(ID + "1").getId());
    }

    @Test
    void cd_account_starting_balance_is_provided_amount() {
        bank.createCdAccount(APR, ID, BALANCE);
        assertEquals(Double.parseDouble(BALANCE), bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void create_different_types_of_accounts() {
        bank.createCheckingAccount(APR, ID);
        bank.createSavingsAccount(APR, ID + "1");
        bank.createCdAccount(APR, ID + "2", BALANCE);
        assertEquals(ID + "2", bank.getAccounts().get(ID + "2").getId());
    }

    @Test
    void make_a_deposit_into_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        assertEquals(800.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void make_multiple_deposits_into_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.depositToAccount(ID, "800");
        assertEquals(1600.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void make_a_withdraw_from_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.withdrawFromAccount(ID, "500");
        assertEquals(300.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void make_multiple_withdraws_from_an_account() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.withdrawFromAccount(ID, "500");
        bank.withdrawFromAccount(ID, "200");
        assertEquals(100.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void withdraw_cannot_make_account_balance_negative() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.withdrawFromAccount(ID, "900");
        assertEquals(0.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void make_a_transfer_from_one_account_to_another() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345677");
        bank.transferToAccount(ID, "12345677", "500");
        assertEquals(300.00, bank.getAccounts().get(ID).getBalance());
        assertEquals(500.00, bank.getAccounts().get("12345677").getBalance());
    }

    @Test
    void make_a_transfer_if_the_from_account_balance_is_less_than_transfer_amount() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345677");
        bank.transferToAccount(ID, "12345677", "900");
        assertEquals(0.00, bank.getAccounts().get(ID).getBalance());
        assertEquals(800.00, bank.getAccounts().get("12345677").getBalance());
    }

    @Test
    void make_a_transfer_if_the_from_account_balance_is_equal_to_transfer_amount() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "800");
        bank.createCheckingAccount(APR, "12345677");
        bank.transferToAccount(ID, "12345677", "800");
        assertEquals(0.00, bank.getAccounts().get(ID).getBalance());
        assertEquals(800.00, bank.getAccounts().get("12345677").getBalance());
    }

    @Test
    void apply_min_balance_fee_if_account_balance_is_less_than_100() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "75");
        bank.applyMinBalanceFee();
        assertEquals(50.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void cant_apply_min_balance_fee_if_account_balance_is_equal_to_100() {
        bank.createCheckingAccount(APR, ID);
        bank.depositToAccount(ID, "101");
        bank.applyMinBalanceFee();
        assertEquals(101.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void update_savings_account_withdraw_availability_status_to_not_allow() {
        bank.createSavingsAccount(APR, ID);
        bank.setNewWithdrawStatusForSavingsAndCdAccounts();
        assertFalse(bank.getAccounts().get(ID).notAllowWithdraw);
    }

    @Test
    void update_cd_accounts_total_months_passed_tracking() {
        bank.createCdAccount(APR, ID, "5000");
        bank.setNewWithdrawStatusForSavingsAndCdAccounts();
        assertEquals(1, bank.getAccounts().get(ID).totalMonthsFromPassTime);
    }

}
