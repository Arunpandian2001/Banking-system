package main;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.util.getvalues.InputMethods;

import customexception.CustomException;
import method.CustomerMethod;
import pojo.Accounts_pojo;
import pojo.TransactionPojo;
import superclass.Storage;

public class CustomerRunner {

	private long id=Storage.VALUES.getUserId();
	private long accountNumber;
	private Logger logger=Logger.getLogger(CustomerRunner.class.getName());
	private CustomerMethod method=new CustomerMethod();
	private InputMethods input=new InputMethods();
	private static List<Long> accounts_List=new ArrayList<>();
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
				logger.warning(e+"");
			}
			while(loop) {
				logger.info("Enter any one option");
				logger.info("\t1)Balance\n 2)Deposit\n 3)Withdraw\n 4)Transfer\n 5)Print user info \n6)Last 5 Transactions \n 7)Logout");
				int choice=input.isInteger();
				switch(choice) {
					case 1:{
							try {
								logger.info("Balance:: "+method.balance());
							} catch (CustomException e) {
								logger.warning(e+"");
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
							logger.warning(e+"");
						}
						break;
					}
					
					case 3:{
						logger.info("Enter the amount to withdraw");
						double amount=input.isDouble();
						
						try {
							method.toWithdraw(amount);
							logger.info("The amount is withdrawn.");
							
						} catch (CustomException e) {
							logger.warning(e+"");
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
							logger.warning(e+"");
						}
						break;
					}
					case 5:{
						Accounts_pojo pojo=Storage.VALUES.getCurrentAccountDetails();
						
						logger.info("Customer id:"+pojo.getCustomerId());
						logger.info("Account number:"+pojo.getAccountNumber());
						logger.info("Account type:"+pojo.getAccountType());
						logger.info("Balance:"+pojo.getBalance());
						logger.info("Branch::"+pojo.getBranch());
						logger.info("Account Status: "+pojo.getStatus());
						break;
					}
					
					case 6:{
						try {
							List<TransactionPojo> list=method.getRecentTransaction(Storage.VALUES.getAccountNumber());
							for(TransactionPojo pojo:list) {
								System.out.println("-------------------------------------------------------------");
								System.out.println("Reference ID :: "+pojo.getReferenceId());
								System.out.println("Customer id ::"+pojo.getCustomerId());
								System.out.println("Account number ::"+pojo.getAccountNumber());
								System.out.println("Secondary account ::"+pojo.getSecondary());
								System.out.println("Transaction id ::"+pojo.getTransactionId());
								System.out.println("Transaction type ::"+pojo.getTransactionTypes());
								System.out.println("Mode of Transaction ::"+pojo.getMode());
								System.out.println("Amount ::"+pojo.getAmount());
								System.out.println("Time ::"+pojo.getTimeInMillis());
								System.out.println("Closing Balance ::"+pojo.getClosingBalance());
								System.out.println("Status ::"+pojo.getStatus());
								System.out.println("------------------------------------------------------------------");

							}
						} catch (CustomException e) {
							e.printStackTrace();
						}
						break;
					}
					
					case 7:{
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
