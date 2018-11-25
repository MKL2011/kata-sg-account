package sg.account.kata;

import java.time.LocalDateTime;

import sg.account.kata.account.Account;
import sg.account.kata.account.AccountType;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;
import sg.account.kata.services.AccountService;
import sg.account.kata.services.StatementService;

public class Main {
	static AccountService accountService = AccountService.getInstance();
	static StatementService statementService = StatementService.getInstance();

	public static void main(String[] args) {
		// create a client example
		Client client = new Client("1", "KALLEL", "Mohamed_Amine", "klm@gmail");

		// create an account for given client with 800 as starting balance 200 overdrawn
		// limits
		Account account = new Account("1 ", 800, client, AccountType.CURRENT, 200);
		// create different operations type with different amounts to play different
		// scenarios

		LocalDateTime currentDate = LocalDateTime.now();
		LocalDateTime lastMonth = currentDate.minusMonths(1);
		LocalDateTime lastQuarter = currentDate.minusMonths(3);
		LocalDateTime lastHalf = currentDate.minusMonths(6);

		Operation statementTestOperation = new Operation("4 ", OperationType.DEPOSIT, 100, account);

		// test statement
		statementTestOperation.setOperationDate(currentDate.minusHours(1));
		accountService.doOperation(account, statementTestOperation);
		statementService.getStatement(account, currentDate.minusDays(1), currentDate);

		// test negative amount operations
		Operation negatifAmountTestOperation = new Operation("6", OperationType.DEPOSIT, -300, account);
		accountService.doOperation(account, negatifAmountTestOperation);
		statementService.getStatement(account, LocalDateTime.now().minusDays(1), LocalDateTime.now());

		// test exceed overdrawn limit
		Operation thirdWithdrawl = new Operation("3 ", OperationType.WITHDRAWL, 2000, account);
		accountService.doOperation(account, thirdWithdrawl);
		statementService.getStatement(account, LocalDateTime.now().minusDays(1), LocalDateTime.now());

		// test Last Month Statement
		Operation monthTestOperation = new Operation("1", OperationType.WITHDRAWL, 100, account);
		monthTestOperation.setOperationDate(currentDate.minusDays(2));
		accountService.doOperation(account, monthTestOperation);
		statementService.getLastMonthStatement(account);

		// test Last 3 Months Statement
		Operation quarterTestOperation = new Operation("5", OperationType.DEPOSIT, 200, account);
		quarterTestOperation.setOperationDate(lastMonth);
		accountService.doOperation(account, quarterTestOperation);
		statementService.getLastQuarterStatement(account);

		// test Last 6 Months Statement
		Operation halfTestOperation = new Operation("2", OperationType.WITHDRAWL, 200, account);
		halfTestOperation.setOperationDate(lastQuarter);
		accountService.doOperation(account, halfTestOperation);
		statementService.getLastHalfStatemennt(account);

		// test Last Year Statement
		Operation lastYearTestOperation = new Operation("7", OperationType.DEPOSIT, 300, account);
		lastYearTestOperation.setOperationDate(lastHalf);
		accountService.doOperation(account, lastYearTestOperation);
		statementService.getLastYearStatement(account);

	}
}
