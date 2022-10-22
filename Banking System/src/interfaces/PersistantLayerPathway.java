package interfaces;

import java.util.List;
import java.util.Map;

import customexception.CustomException;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.TransactionPojo;

public interface PersistantLayerPathway {
	
	Map<Long, CustomerPojo> getLoginMap() throws CustomException;//USERPOJO  //cant replace parent in the place of child
	
	double getCurrentBalance(long accountNumber)throws CustomException;
	
	void depositUpdate(double balance, long accountNumber) throws CustomException;
	
	String getPasswordQuery(long customerId) throws CustomException;//swami name
	
	void withdrawUpdate(double finalAmount, long accountNumber) throws CustomException ;
	
	Map<Long, Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException; //swami map<map>
	
	void updateTransactionDetails(TransactionPojo pojo) throws CustomException;
	
	void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException; // combine with previous
	
	 Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException;//swami
	 
	 List<TransactionPojo> getRecentTransactions(long accountNumber) throws CustomException;
}
