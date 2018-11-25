package sg.account.kata.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import sg.account.kata.client.Client;
import sg.account.kata.operation.Operation;

public class Account {
	private String idAccount;
	private double balance;
	private Client client;
	// account Type CURRENT OR sAvinGs
	private AccountType accountType;
	// limit of overdrawn for the account
	private double overdrawnLimit;
	private List<Operation> accountOperations;

	/**
	 * @param idAccount
	 * @param balance
	 * @param client
	 * @param accountType
	 * @param overdrawnLimit
	 */
	public Account(String idAccount, double balance, Client client, AccountType accountType, double overdrawnLimit) {
		this.idAccount = idAccount;
		this.balance = balance;
		this.client = client;
		client.addAccount(this);
		this.accountType = accountType;
		this.overdrawnLimit = overdrawnLimit;
	}

	/**
	 * @return the idAccount
	 */
	public String getIdAccount() {
		return idAccount;
	}

	/**
	 * @param idAccount the idAccount to set
	 */
	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the accountType
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the overdrawnLimit
	 */
	public double getOverdrawnLimit() {
		return overdrawnLimit;
	}

	/**
	 * @param overdrawnLimit the overdrawnLimit to set
	 */
	public void setOverdrawnLimit(double overdrawnLimit) {
		this.overdrawnLimit = overdrawnLimit;
	}

	/**
	 * @return the accountOperations
	 */
	public List<Operation> getAccountOperations() {
		return java.util.Collections.unmodifiableList(accountOperations);
	}

	/**
	 * @param accountOperations the accountOperations to set
	 */
	public void setAccountOperations(List<Operation> accountOperations) {
		this.accountOperations = accountOperations;
	}

	public void addAccountOperation(Operation operation) {
		if (accountOperations == null) {
			this.accountOperations = new ArrayList<>();
		}
		accountOperations.add(operation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(accountOperations, accountType, balance, client, idAccount, overdrawnLimit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountOperations, other.accountOperations)
				&& Objects.equals(accountType, other.accountType)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& Objects.equals(client, other.client) && Objects.equals(idAccount, other.idAccount)
				&& Double.doubleToLongBits(overdrawnLimit) == Double.doubleToLongBits(other.overdrawnLimit);
	}

}
