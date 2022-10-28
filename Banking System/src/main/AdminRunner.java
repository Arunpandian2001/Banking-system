package main;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.util.getvalues.InputMethods;

import customexception.CustomException;
import method.AdminOperations;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import pojo.RequestPojo;
import pojo.TransactionPojo;

public class AdminRunner {
	
	private Logger logger=Logger.getLogger(AdminRunner.class.getName());
	private AdminOperations method=new AdminOperations();
	private InputMethods input=new InputMethods();
	private static boolean loop=true;

	public void admin() {
		
		while(loop) {
			System.out.println("Enter any one option");		
			System.out.println("1)View Customer details\n "
					+ "2)View Account Details\n "
					+ "3)View Transaction details\n"
					+ "4)Accept transaction requests \n "
					+"5)Create new customer\n "
					+ "6)Create new Account\n"
					+ " 7)Logout");			
			int choice=input.isInteger();
			switch(choice) {
				case 1:{
						System.out.println("To get details of one or more Customer select 1");
						System.out.println("To get details of all customers select 2");
						int option=input.isInteger();
						switch(option) {
							case 1:{
								System.out.println("Enter the number of customers");
								int size=input.isInteger();
								Long[] customerIds=new Long[size];
								System.out.println("Enter the customer ids");
								for(int i=0;i<size;i++) {
									customerIds[i]=input.isLong();
								}
								List<CustomerPojo> list;
								try {
									list = method.getCustomerDetails(customerIds);
									for(CustomerPojo pojo:list) {
										System.out.println("-------------------------------------------------------------------");
										System.out.println("Customer id :: "+pojo.getId());
										System.out.println("Customer name :: "+pojo.getName());
										System.out.println("Customer Date of Birth :: "+pojo.getDob());
										System.out.println("Customer Mobile number :: "+pojo.getMobile());
										System.out.println("Customer email :: "+pojo.getEmail());
										System.out.println("Customer address :: "+pojo.getAddress());
										System.out.println("Customer aadhar number :: "+pojo.getAadhar());
										System.out.println("Customer pan number :: "+pojo.getPanNumber());
										System.out.println("Customer status :: "+pojo.getStatus());
										System.out.println("-------------------------------------------------------------------");
									}
								} catch (CustomException e) {
									e.printStackTrace();
								}
								
								break;
							}
							case 2:{
								List<CustomerPojo> list;
								try {
									list = method.getCustomerDetails();
									for(CustomerPojo pojo:list) {
										System.out.println("--------------------------------------------------------------------");
										System.out.println("Customer id :: "+pojo.getId());
										System.out.println("Customer name :: "+pojo.getName());
										System.out.println("Customer Date of Birth :: "+pojo.getDob());
										System.out.println("Customer Mobile number :: "+pojo.getMobile());
										System.out.println("Customer email :: "+pojo.getEmail());
										System.out.println("Customer address :: "+pojo.getAddress());
										System.out.println("Customer aadhar number :: "+pojo.getAadhar());
										System.out.println("Customer pan number :: "+pojo.getPanNumber());
										System.out.println("Customer status :: "+pojo.getStatus());
										System.out.println("---------------------------------------------------------------------");
									}
								} catch (CustomException e) {
									e.printStackTrace();
								}
								break;
							}
							default :{
								System.out.println("Enter valid input");
							}
						}
						break;
						
					}
				
				case 2:{
					System.out.println("To get accounts of one or more Customer select 1");
					System.out.println("To get accounts of all customers select 2");
					int option=input.isInteger();
					switch(option) {
						case 1:{
							System.out.println("Enter the number of customers");
							int size=input.isInteger();
							Long[] customerIds=new Long[size];
							System.out.println("Enter the account numbers");
							for(int i=0;i<size;i++) {
								customerIds[i]=input.isLong();
							}
							List<Map<Long, Accounts_pojo>> list=method.getAccountDetails(customerIds);
							for(Map<Long, Accounts_pojo> map:list) {
								for(Map.Entry<Long, Accounts_pojo> element:map.entrySet()) {
									Accounts_pojo pojo=element.getValue();
									System.out.println("-------------------------------------------------------------------");
									System.out.println("Customer id :: "+pojo.getCustomerId());
									System.out.println("Account number :: "+pojo.getAccountNumber());
									System.out.println("Account type :: "+pojo.getAccountType());
									System.out.println("Bank branch :: "+pojo.getBranch());
									System.out.println("Balance :: "+pojo.getBalance());
									System.out.println("Account status :: "+pojo.getStatus());
									System.out.println("-------------------------------------------------------------------");
								
								}
							}
							break;
						}
						case 2:{
							List<Map<Long, Accounts_pojo>> list=method.getAccountDetails();
							for(Map<Long, Accounts_pojo> map:list) {
								for(Map.Entry<Long, Accounts_pojo> element:map.entrySet()) {
									Accounts_pojo pojo=element.getValue();
									System.out.println("-------------------------------------------------------------------");
									System.out.println("Customer id :: "+pojo.getCustomerId());
									System.out.println("Account number :: "+pojo.getAccountNumber());
									System.out.println("Account type :: "+pojo.getAccountType());
									System.out.println("Bank branch :: "+pojo.getBranch());
									System.out.println("Balance :: "+pojo.getBalance());
									System.out.println("Account status :: "+pojo.getStatus());
									System.out.println("-------------------------------------------------------------------");
								
								}
							}
							break;
						}
						
						default :{
							System.out.println("Enter valid choice");
						}
					}
					break;
				}
				
				case 3:{
					System.out.println("To get transaction details of particular user 1");
					System.out.println("To get transaction details of particular account of a user 2");
					int option=input.isInteger();
					switch(option) {
						case 1:{
							
							System.out.println("Enter the customer id");
							long customerId=input.isLong();
							
							Map<Long, Map<String, TransactionPojo>> list;
							try {
								list = method.getTransactionDetails(0,customerId);
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
						case 2:{
							System.out.println("Enter the customer id");
							long customerId=input.isLong();
							System.out.println("Enter the account number");
							long accountNumber=input.isLong();
							
							Map<Long, Map<String, TransactionPojo>> list;
							try {
								list = method.getTransactionDetails(accountNumber,customerId);
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
						default :{
							System.out.println("Enter valid choice");
						}
					}
					break;
				}
				
				case 4:{
					try {
						List<RequestPojo> list=method.getRequestDetails();
						for(RequestPojo pojo:list) {
							System.out.println("----------------------------------------------------------------");
							System.out.println("Customer id:: "+pojo.getCustomerId());
							System.out.println("Account number:: "+pojo.getAccountNumber());
							System.out.println("Reference id:: "+pojo.getReferenceId());
							System.out.println("Amount:: "+pojo.getAmount());
							System.out.println("Requested time:: "+pojo.getRequestedTime());
							System.out.println("Status:: "+pojo.getStatus());
							System.out.println("----------------------------------------------------------------");

						}
						
						System.out.println("\n Select the account to process");
						int option=input.isInteger();
						System.out.println("\nTo accept the request press 1");
						System.out.println("\nTo reject the request press 2");
						int action=input.isInteger();
						switch(action) {
							case 1:{
								method.acceptRequest(list.get(option-1));
								break;
							}
							case 2:{
								method.declineRequest(list.get(option-1));
								break;
							}
						}
					} catch (CustomException e) {
						e.printStackTrace();
					}
					break;
				}
				case 5:{
					
					break;
				}
				
				case 6:{
					
					break;
				}
				
				case 7:{
					logout();
					break;
				}
				default:{
					System.out.println("Enter valid input");
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
}
