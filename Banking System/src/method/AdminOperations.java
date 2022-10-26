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

public class AdminOperations {

	private PersistentLayerPathway load=new PersistentLayer();
	
	public List<CustomerPojo> getCustomerDetails(Long ... customerIds) throws CustomException {
		int length=customerIds.length;
		Map<Long,CustomerPojo> map=Storage.VALUES.getUserDetails();
		List<CustomerPojo> list=new ArrayList<>();
		if(length==0) {
			for(Map.Entry<Long, CustomerPojo> element:map.entrySet()) {
				list.add(element.getValue());
			}
		}
		else if(length>=1) {
			for(int i=0;i<length;i++) {
				if(map.containsKey(customerIds[i])) {
					list.add(map.get(customerIds[i]));
				}
				else {
					throw new CustomException("User id not found ");
				}
			}
		}
		return list;
	}
	
	public List<Map<Long, Accounts_pojo>> getAccountDetails(Long... accountNumbers) {
		int length = accountNumbers.length;
		Map<Long, Map<Long, Accounts_pojo>> map = Storage.VALUES.getAccountDetails();
		List<Map<Long, Accounts_pojo>> list = new ArrayList<>();
		if (length == 0) {
			for (Map.Entry<Long, Map<Long, Accounts_pojo>> element : map.entrySet()) {
				list.add(element.getValue());
			}
		} else if (length >= 1) {
			for (int i = 0; i < length; i++) {
				if (map.containsKey(accountNumbers[i])) {
					list.add(map.get(accountNumbers[i]));
				}
			}
		}
		return list;
	}
	
	public Map<Long, Map<String, TransactionPojo>> getTransactionDetails(long accountNumber,long customerId) throws CustomException{
		
		return load.getTransactions(accountNumber, customerId);
	}

	public List<RequestPojo> getRequestDetails() throws CustomException {

		return load.getRequestDetails();
	}

	public void acceptRequest(RequestPojo requestPojo) throws CustomException {
		requestPojo.setStatus("Accepted");
		requestPojo.setProcessdeTime(System.currentTimeMillis());
		load.updateRequestStatus(requestPojo,requestPojo.getReferenceId());
		load.withdrawUpdate(requestPojo.getAmount(),requestPojo.getAccountNumber());
		load.acceptedRequestInTransaction(requestPojo.getAmount(),requestPojo.getReferenceId(),requestPojo.getCustomerId());
		
		TransactionPojo pojo=new TransactionPojo();
		pojo.setTimeInMillis(requestPojo.getProcessdeTime());
		pojo.setStatus(requestPojo.getStatus());
		load.updateTransactionAfterVerification(pojo,requestPojo.getReferenceId());
	}

	public void declineRequest(RequestPojo requestPojo) throws CustomException {
		requestPojo.setStatus("Rejected");
		requestPojo.setProcessdeTime(System.currentTimeMillis());
		load.updateRequestStatus(requestPojo,requestPojo.getReferenceId());
		load.declinedRequestInTransaction(requestPojo.getAmount(),requestPojo.getReferenceId(),requestPojo.getCustomerId());
		
		TransactionPojo pojo=new TransactionPojo();
		load.depositUpdate(requestPojo.getAmount(),requestPojo.getAccountNumber());
		pojo.setTimeInMillis(requestPojo.getProcessdeTime());
		pojo.setStatus(requestPojo.getStatus());
		load.updateTransactionAfterVerification(pojo,requestPojo.getReferenceId());
	}
	
	
}
