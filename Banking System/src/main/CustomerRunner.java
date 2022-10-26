package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.util.getvalues.InputMethods;

import customexception.CustomException;
import method.CustomerOperations;
import pojo.Accounts_pojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;
import superclass.Storage;

public class CustomerRunner {

	private long id=Storage.VALUES.getUserId();
	private long accountNumber;
	private Logger logger=Logger.getLogger(CustomerRunner.class.getName());
	private CustomerOperations method=new CustomerOperations();
	private InputMethods input=new InputMethods();
	private List<Long> accounts_List=new ArrayList<>();
	private static boolean loop=true;
	
	public void customer() {
		
//		Displaying available accounts
			try {
				accounts_List=method.getList(id);
				
				if(accounts_List.size()>1) {
					logger.info("Available Accounts");
					int count=0;
					for(int i=0;i<accounts_List.size();i++) {
						count++;
						logger.info(""+count+")"+accounts_List.get(i));
					}
					logger.info("Enter the option to select account");
					int option = input.isInteger();
					
					this.accountNumber=accounts_List.get(--option);
					Storage.VALUES.setAccountNumber(accountNumber);
					Storage.VALUES.setCurrentAccountDetails(accountNumber);
					
				}
				else {
					
					this.accountNumber=(accounts_List.get(0));
					Storage.VALUES.setAccountNumber(accountNumber);
					Storage.VALUES.setAccountDetails();
					Storage.VALUES.setCurrentAccountDetails(accountNumber);

				}
			} catch (CustomException e) {
				e.printStackTrace();
			}
			while(loop) {
				logger.info("Enter any one option");
				logger.info("\t1)Balance\n 2)Deposit\n 3)Withdraw\n 4)Transfer\n 5)View request status\n "
						+ "6)Print account info \n 7)Last 5 Transactions \n 8)Logout");
				int choice=input.isInteger();
				switch(choice) {
					case 1:{
							try {
								logger.info("Balance:: "+method.balance());
							} catch (CustomException e) {
								e.printStackTrace();
							}
							break;
						}
					
					case 2:{
						logger.info("Enter the amount to deposit");
						double amount=input.isDouble();
						
						
						try {
							method.toDeposit(amount);
							logger.info("The amount is deposited.");
							
						} catch (CustomException e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 3:{
						logger.info("Enter the amount to withdraw");
						double amount=input.isDouble();
						
						try {
							method.toWithdraw(amount);
							logger.info("The request has been placed.");
							
						} catch (CustomException e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 4:{
						logger.info("Enter the account number to transfer");
						long receiverAccountNumber=input.isLong();
						logger.info("Enter the amount to transfer");
						double amount=input.isDouble();
						logger.info("Enter the password");
						String password=input.getString();
						try {
							method.transferAmount(receiverAccountNumber,amount,password);
						} catch (CustomException e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 5:{
						Map<Long, Map<String, RequestPojo>> map;
						try {
							map = method.getRequestMap();
							Map<String,RequestPojo> innerMap=map.get(id);
							for(Map.Entry<String,RequestPojo> element: innerMap.entrySet()) {
								RequestPojo pojo=element.getValue();
								System.out.println("-----------------------------------------------------------");
								System.out.println("CUSTOMER_ID :: "+pojo.getCustomerId());
								System.out.println("ACCOUNT_NUMBER :: "+pojo.getAccountNumber());
								System.out.println("REFERENCE_ID ::"+pojo.getReferenceId());
								System.out.println("AMOUNT ::"+pojo.getAmount());
								System.out.println("REQUESTED_TIME ::"+pojo.getRequestedTime());
								System.out.println("PROCESSED_TIME ::"+pojo.getProcessdeTime());
								System.out.println("STATUS ::"+pojo.getStatus());
								System.out.println("REQUEST_TYPE ::"+pojo.getType());
							}
						} catch (CustomException e) {
							e.printStackTrace();
						}
					}
					
					case 6:{
						Accounts_pojo pojo=Storage.VALUES.getCurrentAccountDetails();
						
						logger.info("Customer id:"+pojo.getCustomerId());
						logger.info("Account number:"+pojo.getAccountNumber());
						logger.info("Account type:"+pojo.getAccountType());
						logger.info("Balance:"+pojo.getBalance());
						logger.info("Branch::"+pojo.getBranch());
						logger.info("Account Status: "+pojo.getStatus());
						break;
					}
					
					case 7:{
						try {
							Map<Long,Map<String,TransactionPojo>> list=method.getRecentTransaction(Storage.VALUES.getAccountNumber(),Storage.VALUES.getUserId());
							for(Map.Entry<Long, Map<String, TransactionPojo>> map: list.entrySet()) {
								Map<String, TransactionPojo> innerMap=map.getValue();

								for(Map.Entry<String, TransactionPojo> element:innerMap.entrySet()) {
									TransactionPojo pojo=element.getValue();
									System.out.println("-------------------------------------------------------------------");
									System.out.println("Reference id:: "+pojo.getReferenceId());
									System.out.println("Customer id :: "+pojo.getCustomerId());
									System.out.println("Account number :: "+pojo.getAccountNumber());
									System.out.println("Secondary Account :: "+pojo.getSecondary());
									System.out.println("Transaction id :: "+pojo.getTransactionId());
									System.out.println("Transaction type :: "+pojo.getTransactionTypes());
									System.out.println("Mode of Transaction :: "+pojo.getMode());
									System.out.println("Transaction Amount:: "+pojo.getAmount());
									System.out.println("Transaction Time:: "+pojo.getTimeInMillis());
									System.out.println("Closing balance :: "+pojo.getClosingBalance());
									System.out.println("Transaction Status :: "+pojo.getStatus());
									System.out.println("-------------------------------------------------------------------");
								
								}
							}

						} catch (CustomException e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 8:{
						if(accounts_List.size()==1) {
							logout();
						}
						else {
							logger.info("To logout from app press 1");
							logger.info("To logout from current account press 2");
							int number=input.isInteger();
							if(number==2) {
								logout();
							}
							if(number==1) {
								loop=false;
								Main.customerLogin=false;
								Main.logout=false;
								logger.info("Shuttingdown the app");
							}
						}
						
						break;
					}
					default:{
						System.err.println("Enter valid option");
					}
				}
				
			}					
	}

	private void logout() {
		logger.warning("Do you really want to logout from current press 1");
		logger.info("To stay logged in press 2");
		int accountLogOut=input.isInteger();
		if(accountLogOut==1) {
			loop=false;
			
		}
	}

	public boolean toContinue() {
		if(accounts_List.size()>1) {
			return true;
		}
		return false;
	}

	
}
