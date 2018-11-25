package sg.account.kata.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import sg.account.kata.account.Account;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;
import sg.account.kata.operation.OperationType;

public class AccountService implements AccountServiceInterface {

	private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());

	private static AccountService instance;

	private AccountService() {
	}

	public static AccountService getInstance() {
		if (instance == null) {
			instance = new AccountService();
		}
		return instance;
	}

	// return balance of account
	@Override
	public double getBalance(Account account) {
		return account.getBalance();
	}

	// return balance of all client account
	@Override
	public double getClientBalance(Client client) {
		List<Account> clientAccounts = client.getAccountList();
		if (clientAccounts == null || clientAccounts.isEmpty())
			return 0;
		Stream<Account> accountsStream = clientAccounts.stream();
		return accountsStream.mapToDouble(account -> account.getBalance()).sum();
	}

	// return true if the operation don ' t exceed the limit
	@Override
	public boolean checkOverdrawnLimit(Account account, Operation operation) {
		return (OperationType.WITHDRAWL.equals(operation.getOperationType())
				&& account.getOverdrawnLimit() < operation.getAmount() - account.getBalance());
	}

	private void logSuccesOperation(Operation operation) {
		LOGGER.log(Level.INFO, operation.getIdoperation() + "TYPE : " + operation.getOperationType() + "AMOUNT : "
				+ operation.getAmount());
	}

	// execute the operation on a given account
	@Override
	public void doOperation(Account account, Operation operation) {
		// check if operation amount is greater than zero
		if (!operation.checkOperationAmount()) {
			LOGGER.log(Level.WARNING, "Cannot have an operation with negative amount");
		} else {
			// if the operation is a deposit than do it
			if (OperationType.DEPOSIT.equals(operation.getOperationType())) {
				account.addAccountOperation(operation);
				account.setBalance(account.getBalance() + operation.getAmount());
				logSuccesOperation(operation);
			}
			// else check if the user doesn ' t exceed the overdrawn limit
			else {
				if (checkOverdrawnLimit(account, operation)) {
					LOGGER.log(Level.WARNING, "Overdrawn Limit exceeded");
				} else {
					account.addAccountOperation(operation);
					account.setBalance(account.getBalance() - operation.getAmount());
					logSuccesOperation(operation);
				}
			}
		}
	}

}
