package bank.account.kata.operation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sg.account.kata.account.Account;
import sg.account.kata.account.AccountType;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;

public class OperationTest {

	// create a client example
	Client client;
	// create an account for given client with 0 balance and 100 overdrawn limit
	Account account;
	Operation firstWithdrawl;

	public OperationTest() {
	}

	@Before
	public void executedBeforeEach() {
		client = new Client("1", "KALLEL", "Mohamed-Amine", "mkl@gmail.com");
		account = new Account("1", 0, client, AccountType.CURRENT, 100);
		firstWithdrawl = new Operation("1", OperationType.WITHDRAWL, -200, account);

	}

	@Test
	public void testOperation() {
		// check operation amount is negative
		assertEquals(false, firstWithdrawl.checkOperationAmount());
	}

}
