package sg.account.kata.services;

import java.time.LocalDateTime;
import java.util.List;

import sg.account.kata.account.Account;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;

public interface StatementServiceInterface {

	public double sumOperationsByTypeInPeriod(List<Operation> operations, OperationType operationType);

	public List<Operation> filterOperationsInPeriod(List<Operation> operations, LocalDateTime startDate,
			LocalDateTime endDate);

	public String getStatement(Account account, LocalDateTime startDate, LocalDateTime endDate);

	// print last month statement
	public String getLastMonthStatement(Account account);

	// print the statement of last 3 months on a given account
	public String getLastQuarterStatement(Account account);

	// print the statement of last 6 months on a given account
	public String getLastHalfStatemennt(Account account);

	// print the statement of last 9 months on a given account
	public String getLastYearStatement(Account account);

}
