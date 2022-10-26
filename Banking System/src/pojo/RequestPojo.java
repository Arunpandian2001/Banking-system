package pojo;

public class RequestPojo {

	private long customerId;
	private long accountNumber;
	private String referenceId;
	private double amount;
	private long requestedTime;
	private long processdeTime;
	private String status;
	private String type;
	
	
	public long getProcessdeTime() {
		return processdeTime;
	}
	public void setProcessdeTime(long processdeTime) {
		this.processdeTime = processdeTime;
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
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getRequestedTime() {
		return requestedTime;
	}
	public void setRequestedTime(long requestedTime) {
		this.requestedTime = requestedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
