package bank.account.kata.account;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import sg.account.kata.account.Account;
import sg.account.kata.account.AccountType;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;
import sg.account.kata.services.AccountService;

public class AccountTest {

	public AccountTest() {
	}

	static Client client;

	static AccountService accountService;

	@Before
	public void executedBeforeEach() {
		client = new Client("1", "KALLEL", "Mohamed-Amine", "mkl@gmail.com");
		accountService = AccountService.getInstance();
	}

	@Test
	public void testOverdrawLimit() {
		// create an account for given client with 0 balance and 100 overdrawn limit
		Account account = new Account("1 ", 0, client, AccountType.CURRENT, 100);
		// create different operation withdraw of 200
		Operation firstWithdrawl = new Operation("1", OperationType.WITHDRAWL, 200, account);
		// operation should exceed the overdrawn limit
		boolean checkOverdrawnLimit = accountService.checkOverdrawnLimit(account, firstWithdrawl);

		assertEquals(true, checkOverdrawnLimit);
		// create different operation withdraw of 50
		Operation secondwithdrawl = new Operation("1", OperationType.WITHDRAWL, 50, account);
		checkOverdrawnLimit = accountService.checkOverdrawnLimit(account, secondwithdrawl);
		// operation should exceed the overdrawn limit
		assertEquals(false, checkOverdrawnLimit);
	}

	@Test
	public void testBalance() {
		// create an account for given client with 1000 balance
		Account account = new Account("1 ", 1000, client, AccountType.CURRENT, 0);
		// assert that balance is 1000
		assertEquals(accountService.getBalance(account), 1000, 0);
	}

	@Test
	public void testClientBalance() {
		// create an account for given client with 1000 balance
		new Account("1 ", 1000, client, AccountType.CURRENT, 0);
		// create a second savings account for given client with 2000 balance
		new Account("2 ", 2000, client, AccountType.SAVINGS, 0);
		// assert that balance is 3000
		assertEquals(accountService.getClientBalance(client), 3000, 0);
	}

	@Test
	public void testOperation() {
		Account account = new Account("1 ", 1000, client, AccountType.CURRENT, 0);
		// create different operation withdraw of 200
		Operation withdrawl = new Operation("1", OperationType.WITHDRAWL, 200, account);
		accountService.doOperation(account, withdrawl);
		assertEquals(account.getBalance(), 800, 0);
		// create different operation withdraw of 50
		Operation deposit = new Operation("1", OperationType.DEPOSIT, 50, account);
		accountService.doOperation(account, deposit);
		assertEquals(account.getBalance(), 850, 0);

	}

}
