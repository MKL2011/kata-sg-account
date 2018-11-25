package sg.account.kata.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sg.account.kata.account.Account;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;

public class StatementService implements StatementServiceInterface {
	private static final Logger LOGGER = Logger.getLogger(StatementService.class.getName());

	private static StatementService instance;
	private static LocalDateTime now = LocalDateTime.now();

	private StatementService() {
	}

	public static StatementService getInstance() {
		if (instance == null) {
			instance = new StatementService();
		}
		return instance;
	}

	@Override
	public double sumOperationsByTypeInPeriod(List<Operation> operations, OperationType operationType) {
		Stream<Operation> operationStream = operations.stream();
		// retrieve deposit operations and sum them
		operationStream = operationStream.filter(operation -> operationType.equals(operation.getOperationType()));
		return operationStream.mapToDouble(operation -> operation.getAmount()).sum();
	}

	@Override
	public List<Operation> filterOperationsInPeriod(List<Operation> operations, LocalDateTime startDate,
			LocalDateTime endDate) {
		Stream<Operation> operationStream = operations.stream();
		// retrieve operations that are between 2 dates
		return operationStream.filter(operation -> operation.getOperationDate().isAfter(startDate)
				&& operation.getOperationDate().isBefore(endDate)).collect(Collectors.toList());
	}

	// print the statement from / to date on a given account
	@Override
	public String getStatement(Account account, LocalDateTime startDate, LocalDateTime endDate) {
		StringBuilder statement = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
		List<Operation> operations = account.getAccountOperations();
		double totalDeposits = sumOperationsByTypeInPeriod(operations, OperationType.DEPOSIT);
		double totalwithdrawls = sumOperationsByTypeInPeriod(operations, OperationType.WITHDRAWL);
		// header of statement will contain account , client infos and period
		statement.append(account.getIdAccount());
		statement.append("ACCOUNT NUMBER . ");
		statement.append(" CLIENT ").append(account.getClient().getFirstName()).append(" ")
				.append(account.getClient().getLastName());
		statement.append(" START DATE ").append(startDate.format(formatter));
		statement.append(" END DATE ").append(endDate.format(formatter)).append("\n");
		statement.append(
				" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		statement.append("\n");
		if (operations != null) {
			// retrieve operations that are between 2 dates
			List<Operation> filteredOperations = filterOperationsInPeriod(operations, startDate, endDate);
			// print operations with a column for deposit and a column for withdrawal
			statement.append("OPERATION DATE     ");
			statement.append("DEPOSIT       ");
			statement.append("WITHDRAWL    ");
			statement.append("\n");
			statement.append("--------- ----     -------       ---------");
			statement.append("\n");
			for (Operation operation : filteredOperations) {
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
		LOGGER.log(Level.INFO, statementResult);
		return statementResult;
	}

	// print the statement of last month on a given account
	@Override
	public String getLastMonthStatement(Account account) {
		return getStatement(account, now.minusMonths(1), now);
	}

	// print the statement of last 3 months on a given account
	@Override
	public String getLastQuarterStatement(Account account) {
		return getStatement(account, now.minusMonths(3), now);
	}

	// print the statement of last 6 months on a given account
	@Override
	public String getLastHalfStatemennt(Account account) {
		return getStatement(account, now.minusMonths(6), now);
	}

	// print the statement of last 9 months on a given account
	@Override
	public String getLastYearStatement(Account account) {
		return getStatement(account, now.minusYears(1), now);
	}
}
