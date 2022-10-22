package DBLoadDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import customexception.CustomException;
import interfaces.PersistantLayerPathway;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.TransactionPojo;

public class PersistantLayer implements PersistantLayerPathway{

	private final String url="jdbc:mysql://localhost/BANKING_SYSTEM";
	private final String user="root";
	private final String password="Root@123";
	 	
	private Connection getConnection() throws CustomException{
		 
		try{
			Connection connection=DriverManager.getConnection(url, user, password);
			return connection;
		}catch(SQLException e) {
			throw new CustomException("Exception occured while connection");
		}
	}
//	LOGIN POJO
	public Map<Long,CustomerPojo> getLoginMap() throws CustomException{
		String query="SELECT USER_DETAILS.*,CUSTOMER_DETAILS.* FROM USER_DETAILS INNER JOIN CUSTOMER_DETAILS ON"
				+ " USER_DETAILS.ID=CUSTOMER_DETAILS.CUSTOMER_ID";
		return setLoginMap(query);
	}

	private Map<Long,CustomerPojo> setLoginMap(String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				return getResult(result);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	private Map<Long, CustomerPojo> getResult(ResultSet result) throws CustomException{
		Map<Long,CustomerPojo> map=new LinkedHashMap<>();
		try {
			while(result.next()) {
				CustomerPojo pojo=new CustomerPojo();
				pojo.setId(result.getLong(1));	
				pojo.setName(result.getString(2));
				pojo.setDob(result.getString(3));
				pojo.setMobile(result.getString(4));
				pojo.setEmail(result.getString(5));
				pojo.setAddress(result.getString(6));
				pojo.setRole(result.getString(7));
				pojo.setPassword(result.getString(8));
				pojo.setAadhar(result.getLong(10));
				pojo.setPanNumber(result.getString(11));
				pojo.setStatus(result.getString(12));
				map.put(pojo.getId(),pojo);
			}
			return map;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}
	
//	gets password of a specific id
	
	public String getPasswordQuery(long id) throws CustomException {
		String query="select PASSWORD FROM LOGIN_CREDENTIALS WHERE ID=?";
		return getPassword(id,query);
	}
	
	private String getPassword(long id, String query) throws CustomException {
		String password=null;
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, id);
			try(ResultSet result=prepStatement.executeQuery()){
				while(result.next()) {
					password=result.getString(1);
				}
				return password;
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}	
	}

	//get every accounts in db

	public Map<Long,Map<Long, Accounts_pojo>> getAllAccountsOfUser() throws CustomException {
		String query="SELECT * FROM ACCOUNTS_DETAILS";
		return allAccounts(query);
	}
	
	private Map<Long, Map<Long, Accounts_pojo>> allAccounts(String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			try(ResultSet result=prepStatement.executeQuery()){
				return getAccountsResult(result);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}

	private Map<Long, Map<Long, Accounts_pojo>> getAccountsResult(ResultSet result) throws CustomException{
		Map<Long, Map<Long, Accounts_pojo>> map=new LinkedHashMap<>();
		try {
			while(result.next()) {
				Map<Long,Accounts_pojo> insideMap=new LinkedHashMap<>();
				Accounts_pojo pojo=new Accounts_pojo();
				pojo.setCustomerId(result.getLong(1));
				pojo.setAccountNumber(result.getLong(2));
				pojo.setAccountType(result.getString(3));
				pojo.setBranch(result.getString(4));
				pojo.setBalance(result.getLong(5));
				pojo.setStatus(result.getString(6));
				insideMap.put(pojo.getAccountNumber(),pojo);
				map.put(pojo.getCustomerId(),insideMap);
			}
			
			return map;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}
	
//	get current balance
	
	public double getCurrentBalance(long accountNumber) throws CustomException {
		String query="SELECT BALANCE FROM ACCOUNTS_DETAILS WHERE ACCOUNT_NUMBER=?";
		return getBalance(query,accountNumber);
	}
	private double getBalance(String query,long accountNumber) throws CustomException{
		double amount=0;
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, accountNumber);
			try(ResultSet result=prepStatement.executeQuery()){
				while(result.next()) {
					amount=result.getDouble(1);
				}
				return amount;
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
//	update balance
	@Override
	
	public void depositUpdate(double amount,long accountNumber) throws CustomException {
		String query="UPDATE ACCOUNTS_DETAILS SET BALANCE=BALANCE+? WHERE ACCOUNT_NUMBER=?";
		BalanceUpdated(amount,accountNumber,query);
	}
	
	public void withdrawUpdate(double amount,long accountNumber) throws CustomException {
		String query="UPDATE ACCOUNTS_DETAILS SET BALANCE=BALANCE-? WHERE ACCOUNT_NUMBER=?";
		BalanceUpdated(amount,accountNumber,query);
	}
	
	private void BalanceUpdated(double amount, long accountNumber, String query) throws CustomException{
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setDouble(1, amount);
			prepStatement.setLong(2, accountNumber);
			prepStatement.execute();
			}catch (SQLException e) {
				throw new CustomException("Exception occured while setting prepared statement",e);
			}
	}
	
	@Override
	public void updateTransactionDetails(TransactionPojo pojo) throws CustomException {

		String query="INSERT INTO TRANSACTION_DETAILS(CUSTOMER_ID,ACCOUNT_NUMBER,SECONDARY_ACCOUNT,"
				+ "TRANSACTION_TYPE,MODE_OF_TRANSACTION,AMOUNT,TIME,"
				+ "CLOSING_BALANCE,STATUS,REFERENCE_ID) VALUES(?,?,?,?,?,?,?,?,?,?) ";
		transactionUpdate(pojo,query);
	}
	private void transactionUpdate(TransactionPojo pojo, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setLong(2, pojo.getAccountNumber());
			prepStatement.setLong(3, pojo.getSecondary());
			prepStatement.setString(4, pojo.getTransactionTypes());
			prepStatement.setString(5, pojo.getMode());
			prepStatement.setDouble(6, pojo.getAmount());
			prepStatement.setLong(7, pojo.getTimeInMillis());
			prepStatement.setDouble(8, pojo.getClosingBalance());
			prepStatement.setString(9, pojo.getStatus());
			prepStatement.setString(10, pojo.getReferenceId());
			prepStatement.execute();
			}catch (SQLException e) {
				e.printStackTrace();
				throw new CustomException("Exception occured while setting prepared statement",e);
			}
	}
	@Override
	public void updateSelfTransactionDetails(TransactionPojo pojo) throws CustomException {

		String query="INSERT INTO TRANSACTION_DETAILS(CUSTOMER_ID,ACCOUNT_NUMBER,TRANSACTION_TYPE,MODE_OF_TRANSACTION,AMOUNT,TIME,"
				+ "CLOSING_BALANCE,STATUS,REFERENCE_ID) VALUES(?,?,?,?,?,?,?,?,?) ";
		selfTransactionUpdate(pojo,query);
	}
	private void selfTransactionUpdate(TransactionPojo pojo, String query) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, pojo.getCustomerId());
			prepStatement.setLong(2, pojo.getAccountNumber());
			prepStatement.setString(3, pojo.getTransactionTypes());
			prepStatement.setString(4, pojo.getMode());
			prepStatement.setDouble(5, pojo.getAmount());
			prepStatement.setLong(6, pojo.getTimeInMillis());
			prepStatement.setDouble(7, pojo.getClosingBalance());
			prepStatement.setString(8, pojo.getStatus());
			prepStatement.setString(9, pojo.getReferenceId());
			prepStatement.execute();
			}catch (SQLException e) {
				e.printStackTrace();
				throw new CustomException("Exception occured while setting prepared statement",e);
			}
	}
	//get all accounts of a user
	
	@Override
	public Accounts_pojo getAccountPojoQuery(long accountNumber) throws CustomException {
		String query="SELECT * FROM ACCOUNTS_DETAILS WHERE ACCOUNT_NUMBER=?";
		return getUserMap(accountNumber,query);
	}
	private Accounts_pojo getUserMap(long accountNumber, String query) throws CustomException {
		
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, accountNumber);
			try(ResultSet result=prepStatement.executeQuery()){
				return getMapResult(result,accountNumber);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	private Accounts_pojo getMapResult(ResultSet result,long accountNumber) throws CustomException  {
		Accounts_pojo pojo=new Accounts_pojo();

		try {
			while(result.next()) {
				pojo.setCustomerId(result.getLong(1));
				pojo.setAccountNumber(result.getLong(2));
				pojo.setAccountType(result.getString(3));
				pojo.setBranch(result.getString(4));
				pojo.setBalance(result.getDouble(5));
				pojo.setStatus(result.getString(6));
			}
			return pojo;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}
	@Override
	public List<TransactionPojo> getRecentTransactions(long accountNumber) throws CustomException{
		String query="select * from TRANSACTION_DETAILS WHERE ACCOUNT_NUMBER=? ORDER BY TIME DESC LIMIT 5";
		return transactionProcess(query,accountNumber);
	}
	private List<TransactionPojo> transactionProcess(String query, long number) throws CustomException {
		try(PreparedStatement prepStatement=getConnection().prepareStatement(query)){
			prepStatement.setLong(1, number);
			try(ResultSet result=prepStatement.executeQuery()){
				return getTransactionResult(result);
			}
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting prepared statement",e);
		}
	}
	private List<TransactionPojo> getTransactionResult(ResultSet result) throws CustomException {
		List<TransactionPojo> list=new ArrayList<>();
		try {
			while(result.next()) {
				TransactionPojo pojo=new TransactionPojo();
				pojo.setReferenceId(result.getString(1));
				pojo.setCustomerId(result.getLong(2));
				pojo.setAccountNumber(result.getLong(3));
				pojo.setSecondary(result.getLong(4));
				pojo.setTransactionId(result.getLong(5));
				pojo.setTransactionTypes(result.getString(6));
				pojo.setMode(result.getString(7));
				pojo.setAmount(result.getDouble(8));
				pojo.setTimeInMillis(result.getLong(9));
				pojo.setClosingBalance(result.getDouble(10));
				pojo.setStatus(result.getString(11));
				list.add(pojo);
			}
			return list;
		} catch (SQLException e) {
			throw new CustomException("Exception occured while setting result statement",e);
		}
	}
	
}
