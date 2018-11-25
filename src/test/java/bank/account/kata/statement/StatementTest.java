package bank.account.kata.statement;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sg.account.kata.account.Account;
import sg.account.kata.account.AccountType;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;
import sg.account.kata.services.AccountService;
import sg.account.kata.services.StatementService;

public class StatementTest {

	public StatementTest() {
	}

	private LocalDateTime currentDate;
	private LocalDateTime lastQuarter;
	private LocalDateTime lastHalf;
	private LocalDateTime lastYear;
	Client client;
	private static StatementService statemntService;
	private static AccountService accountService;

	@Before
	public void executedBeforeEach() {
		client = new Client("1", "KALLEL", "Mohamed-Amine", "mkl@gmail.com");
		currentDate = LocalDateTime.now();
		lastYear = currentDate.minusYears(1);
		lastQuarter = currentDate.minusMonths(3);
		lastHalf = currentDate.minusMonths(6);
		statemntService = StatementService.getInstance();
		accountService = AccountService.getInstance();
	}

	@Test
	public void testOperationPeriod() {
		// create an account for given client with 0 balance and 100 overdrawn limit
		Account account = new Account("1 ", 0, client, AccountType.CURRENT, 100);
		Operation firstWithdrawl = new Operation("1", OperationType.DEPOSIT, 200, account);
		firstWithdrawl.setOperationDate(lastHalf);
		accountService.doOperation(account, firstWithdrawl);
		boolean firstWithdrawlNotIncludedInperiod = statemntService
				.filterOperationsInPeriod(account.getAccountOperations(), lastQuarter, currentDate)
				.contains(firstWithdrawl);

		boolean firstWithdrawlIncludedInperiod = statemntService
				.filterOperationsInPeriod(account.getAccountOperations(), lastYear, currentDate)
				.contains(firstWithdrawl);
		assertEquals(true, firstWithdrawlIncludedInperiod);
		assertEquals(false, firstWithdrawlNotIncludedInperiod);
	}

	@Test
	public void testStatement() {
		// create an account for given client with 0 balance and 100 overdrawn limit
		Account account = new Account("1 ", 0, client, AccountType.CURRENT, 1000);
		accountService.doOperation(account, new Operation("1", OperationType.WITHDRAWL, 200, account));
		accountService.doOperation(account, new Operation("2", OperationType.DEPOSIT, 300, account));
		// construct statment
		StringBuilder statement = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		List<Operation> operations = account.getAccountOperations();
		double totalDeposits = 300;
		double totalwithdrawls = 200;
		// header of statement will contain account , client infos and period
		statement.append(account.getIdAccount());
		statement.append("ACCOUNT NUMBER . ");
		statement.append(" CLIENT ").append(account.getClient().getFirstName()).append(" ")
				.append(account.getClient().getLastName());
		statement.append(" START DATE ").append(currentDate.minusDays(1).format(formatter));
		statement.append(" END DATE ").append(currentDate.plusDays(1).format(formatter)).append("\n");
		statement.append(
				" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		statement.append("\n");
		if (operations != null) {
			// print operations with a column for deposit and a column for withdrawal
			statement.append("OPERATION DATE     ");
			statement.append("DEPOSIT       ");
			statement.append("WITHDRAWL    ");
			statement.append("\n");
			statement.append("--------- ----     -------       ---------");
			statement.append("\n");
			for (Operation operation : operations) {
				statement.append(operation.getOperationDate().format(formatter)).append(" ");
				if (OperationType.DEPOSIT.equals(operation.getOperationType())) {
					statement.append("         ");
					statement.append(operation.getAmount());
				} else {
					statement.append("                       ");
					statement.append(operation.getAmount());
				}
				statement.append("\n");
			}
			statement.append("TOTAL");
			statement.append("               ");
			statement.append(totalDeposits);
			statement.append("         ");
			statement.append(totalwithdrawls);
		} else {
			statement.append("NO Operations");
		}
		String statementResult = statement.toString();
		assertEquals(statemntService.getStatement(account, currentDate.minusDays(1), currentDate.plusDays(1)),
				statementResult);
	}

	@Test
	public void testSumOperations() {
		// Create an account for a given client with balance 0 and 100 overdrawn Limit
		Account account = new Account("1", 0, client, AccountType.CURRENT, 1000);
		accountService.doOperation(account, new Operation("1", OperationType.WITHDRAWL, 200, account));
		accountService.doOperation(account, new Operation("2", OperationType.WITHDRAWL, 200, account));
		accountService.doOperation(account, new Operation("3", OperationType.DEPOSIT, 400, account));
		accountService.doOperation(account, new Operation("4", OperationType.DEPOSIT, 300, account));
		double totalDeposit = statemntService.sumOperationsByTypeInPeriod(account.getAccountOperations(),
				OperationType.DEPOSIT);
		double totalWithdrawl = statemntService.sumOperationsByTypeInPeriod(account.getAccountOperations(),
				OperationType.WITHDRAWL);
		// compare values with delta 0
		assertEquals(400, totalWithdrawl, 0);
		assertEquals(700, totalDeposit, 0);
	}

}
