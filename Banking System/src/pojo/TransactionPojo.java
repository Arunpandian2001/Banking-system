package pojo;

public class TransactionPojo {
	private String status;
	private String referenceId;
	private long customerId;
	private long accountNumber;
	private long transactionId;
	private long secondary;
	private long timeInMillis;
	private String transactionTypes;
	private double closingBalance;
	private double amount;
	private String mode;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public long getSecondary() {
		return secondary;
	}
	public void setSecondary(long secondary) {
		this.secondary = secondary;
	}
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionTypes() {
		return transactionTypes;
	}
	public void setTransactionTypes(String transactionTypes) {
		this.transactionTypes = transactionTypes;
	}
	public double getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getTimeInMillis() {
		return timeInMillis;
	}
	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}
	
}
