package method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import superclass.Storage;
import superclass.User;

public class CustomerOperations extends User{
	
	private PersistentLayerPathway load=new PersistentLayer();
	
	private long getTime() {
		return System.currentTimeMillis();
	}
	
	public List<Long> getList(long id) throws CustomException{
		Map<Long, Accounts_pojo> map=Storage.VALUES.getAccountDetails().get(id);
		List<Long> accountsList=new ArrayList<>();
		for( Map.Entry<Long, Accounts_pojo> element:map.entrySet()) {
			accountsList.add(element.getValue().getAccountNumber());
		}
		
		return accountsList;
	}

	@Override
	public double balance() throws CustomException {
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long,Accounts_pojo> map=Storage.VALUES.getuserSpecificAccounts(userId);
		return map.get(accountNumber).getBalance();
	}

	@Override
	public boolean toDeposit( double amount) throws CustomException {
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
	
		if(isSenderValid(userId,accountNumber,amount)) {
			load.depositUpdate(amount,accountNumber);
			map.get(userId).get(accountNumber).setBalance(load.getCurrentBalance(accountNumber));
			updateDeposit(accountNumber,amount,getTime(),map.get(userId).get(accountNumber).getBalance());
			return true;
		}
		return false;
	}
	@Override
	public boolean toWithdraw(double amount) throws CustomException {
		
		long userId=getUserId();
		long accountNumber=getAccountNumber();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
	
		if(isSenderValid(userId,accountNumber,amount)) {
//			load.withdrawUpdate(amount,accountNumber);
			map.get(userId).get(accountNumber).setBalance(load.getCurrentBalance(accountNumber));
			updateWithdraw(accountNumber,amount,getTime(),map.get(userId).get(accountNumber).getBalance());
			return true;
		}
		return false;
	}
	


	private void updateWithdraw(long userAccount, double amount, long time, double balance) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();	
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(balance);
		pojo.setMode("Withdraw");
		pojo.setAccountNumber(userAccount);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setStatus("Request pending");
		pojo.setReferenceId(referenceId);
		load.updateSelfTransactionDetails(pojo);
		
		RequestPojo request=new RequestPojo();
		request.setCustomerId(pojo.getCustomerId());
		request.setAccountNumber(pojo.getAccountNumber());
		request.setReferenceId(pojo.getReferenceId());
		request.setAmount(pojo.getAmount());
		request.setRequestedTime(pojo.getTimeInMillis());
		request.setStatus(pojo.getStatus());
		request.setType("Withdraw");
		load.updateCustomerWithdrawRequestLog(request);
	}
	
	private void updateDeposit(long userAccount, double amount, long time, double balance) throws CustomException {
		TransactionPojo pojo = new TransactionPojo();
		String referenceId="AP"+time;
		pojo.setAmount(amount);
		pojo.setMode("Deposit");
		pojo.setTransactionTypes("CREDIT");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(balance);
		pojo.setAccountNumber(userAccount);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setStatus("Success");
		pojo.setReferenceId(referenceId);

		load.updateSelfTransactionDetails(pojo);

	}
	
	@Override
	public boolean transferAmount(long receiverAccountNumber, double amount, String password) throws CustomException {
		
		long userId=getUserId();
		long currentUserAccountNumber=getAccountNumber();
		long receiverUserId=load.getAccountPojoQuery(receiverAccountNumber).getCustomerId();
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		
		if(isSenderValid(userId,currentUserAccountNumber,amount) && isReceiverValid(receiverUserId,receiverAccountNumber)) {
			if(password.equals(Storage.VALUES.getUserDetails().get(userId).getPassword()) ){

				load.depositUpdate(amount,receiverAccountNumber);
				map.get(receiverUserId).get(receiverAccountNumber).setBalance(load.getCurrentBalance(receiverAccountNumber));

				load.withdrawUpdate(amount,currentUserAccountNumber);
				map.get(userId).get(currentUserAccountNumber).setBalance(load.getCurrentBalance(currentUserAccountNumber));

				updateTransaction(currentUserAccountNumber,receiverAccountNumber,amount,getTime(),map.get(userId).get(currentUserAccountNumber).getBalance(),map.get(receiverUserId).get(receiverAccountNumber).getBalance(),receiverUserId);
			}
			return true;
		}
		return false;
	}
	
	private void updateTransaction(long curentUser, long receiver, double amount, long time, double currentUserBalance, double receiverBalance,long receiverId) throws CustomException {
		
		String referenceId="AP"+time;

		TransactionPojo pojo = new TransactionPojo();	
		pojo.setSecondary(receiver);
		pojo.setAmount(amount);
		pojo.setTransactionTypes("DEBIT");
		pojo.setMode("Transfer");
		pojo.setTimeInMillis(time);
		pojo.setClosingBalance(currentUserBalance);
		pojo.setAccountNumber(curentUser);
		pojo.setCustomerId(Storage.VALUES.getUserId());
		pojo.setReferenceId(referenceId);
		pojo.setStatus("success");

		load.updateTransactionDetails(pojo);
		
		TransactionPojo pojo1 = new TransactionPojo();	
		pojo1.setSecondary(curentUser);
		pojo1.setAmount(amount);
		pojo1.setMode("Transfer");
		pojo1.setTransactionTypes("CREDIT");
		pojo1.setTimeInMillis(time);
		pojo1.setClosingBalance(receiverBalance);
		pojo1.setAccountNumber(receiver);
		pojo1.setCustomerId(receiverId);    
		pojo1.setReferenceId(referenceId);
		pojo1.setStatus("success");

		load.updateTransactionDetails(pojo1);
		
	}
	
	
	private long getUserId() {
		return Storage.VALUES.getUserId();
	}
	
	private long getAccountNumber() {
		return Storage.VALUES.getAccountNumber();
	}
	
	private boolean isSenderValid(long userId,long accountNumber,double amount){
		Accounts_pojo userAccountPojo=Storage.VALUES.getAccountDetails().get(userId).get(accountNumber);
		CustomerPojo userCustomerDetails=Storage.VALUES.getUserDetails().get(userId);

		if(userAccountPojo.getStatus().equalsIgnoreCase("active") && userAccountPojo.getBalance()>amount && userCustomerDetails.getStatus().equalsIgnoreCase("active")) {
		return true;
		}
		return false;
	}
	
	private boolean isReceiverValid(long receiverUserId,long receiverAccountNumber){
		Accounts_pojo receiverAccountPojo=Storage.VALUES.getAccountDetails().get(receiverUserId).get(receiverAccountNumber);
		CustomerPojo receiverCustomerDetails=Storage.VALUES.getUserDetails().get(receiverUserId);

		if(receiverAccountPojo.getStatus().equalsIgnoreCase("active") && receiverCustomerDetails.getStatus().equalsIgnoreCase("active")) {
		return true;
		}
		return false;
	}

	public Map<Long,Map<String,TransactionPojo>> getRecentTransaction(long accountNumber,long customerId) throws CustomException {
		
		return load.getTransactions(accountNumber,customerId);
	}

	public Map<Long, Map<String, RequestPojo>> getRequestMap() throws CustomException {
		Storage.VALUES.setRequestDetails();
		return Storage.VALUES.getRequestDetails();
	}
	
}
