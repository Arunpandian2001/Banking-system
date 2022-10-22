package main;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.util.getvalues.InputMethods;

import method.Adminmethod;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;

public class AdminRunner {
	
	private Logger logger=Logger.getLogger(AdminRunner.class.getName());
	private Adminmethod method=new Adminmethod();
	private InputMethods input=new InputMethods();
	private static boolean loop=true;

	public void admin() {
		
		while(loop) {
			System.out.println("Enter any one option");		
			System.out.println("1)View Customer details\n 2)View Account Details\n 3)View Transaction details\n 4)See request table\n 5)Create new customer\n 6)Create new Account\n 7)Logout");			
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
								List<CustomerPojo> list=method.getCustomerDetails(customerIds);
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
								break;
							}
							case 2:{
								List<CustomerPojo> list=method.getCustomerDetails();
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
								break;
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
							System.out.println("Enter the customer ids");
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
					}
					break;
				}
				
				case 3:{
					
					break;
				}
				
				case 4:{
					
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
