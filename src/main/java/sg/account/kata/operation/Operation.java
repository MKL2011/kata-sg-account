package sg.account.kata.operation;

import java.time.LocalDateTime;
import java.util.Objects;

import sg.account.kata.account.Account;

public class Operation {
	private String idoperation;
	private OperationType operationType;
	private LocalDateTime operationDate;
	private Account account;
	private double amount;

	/**
	 * @param idoperation
	 * @param operationType
	 * @param account
	 * @param amount
	 */
	public Operation(String idoperation, OperationType operationType, double amount, Account account) {
		this.idoperation = idoperation;
		this.operationType = operationType;
		this.operationDate = LocalDateTime.now();
		this.account = account;
		this.amount = amount;
	}

	/**
	 * @return the idoperation
	 */
	public String getIdoperation() {
		return idoperation;
	}

	/**
	 * @param idoperation the idoperation to set
	 */
	public void setIdoperation(String idoperation) {
		this.idoperation = idoperation;
	}

	/**
	 * @return the operationType
	 */
	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the operationDate
	 */
	public LocalDateTime getOperationDate() {
		return operationDate;
	}

	/**
	 * @param operationDate the operationDate to set
	 */
	public void setOperationDate(LocalDateTime operationDate) {
		this.operationDate = operationDate;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return true if amount is positive
	 */
	public boolean checkOperationAmount() {
		return this.amount > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(account, amount, idoperation, operationDate, operationType);
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
		Operation other = (Operation) obj;
		return Objects.equals(account, other.account)
				&& Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(idoperation, other.idoperation) && Objects.equals(operationDate, other.operationDate)
				&& operationType == other.operationType;
	}

}
