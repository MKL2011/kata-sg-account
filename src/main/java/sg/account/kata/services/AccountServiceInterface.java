package sg.account.kata.services;

import sg.account.kata.account.Account;
import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;

public interface AccountServiceInterface {

	public boolean checkOverdrawnLimit(Account account, Operation operation);

	public void doOperation(Account account, Operation operation);

	public double getBalance(Account account);

	public double getClientBalance(Client client);
}
