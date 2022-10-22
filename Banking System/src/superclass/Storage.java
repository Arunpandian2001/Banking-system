package superclass;

import java.util.Map;

import DBLoadDriver.PersistantLayer;
import customexception.CustomException;
import interfaces.PersistantLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;

public enum Storage {

	VALUES;
	private long userId;
	private long accountNumber;
	private PersistantLayerPathway load=new PersistantLayer();
	private Map<Long,CustomerPojo> userDetails;
	private Map<Long,Map<Long,Accounts_pojo>> accountDetails;
	private Accounts_pojo currentAccountDetails;
	
	
	public Accounts_pojo getCurrentAccountDetails() {
		return currentAccountDetails;
	}
	public void setCurrentAccountDetails(long accountNumber) throws CustomException {
		this.currentAccountDetails = load.getAccountPojoQuery(accountNumber);
	}
	public Map<Long, Accounts_pojo> getuserSpecificAccounts(long userId) {
		return accountDetails.get(userId);
	}
	public Map<Long, Map<Long, Accounts_pojo>> getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails() throws CustomException {
		accountDetails = load.getAllAccountsOfUser();
	}

	public void updateAccountsDetails(Map<Long,Map<Long,Accounts_pojo>> accounts) {
		accountDetails=accounts;
	}
	

	public Map<Long, CustomerPojo> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails() throws CustomException {
		this.userDetails = load.getLoginMap();
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAccountNumber() {
//		Thread.dumpStack();        Find the mistake trace in intialization
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	
}
