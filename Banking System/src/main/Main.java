package main;

import java.util.logging.Logger;

import com.util.getvalues.InputMethods;

import customexception.CustomException;
import login.LoginLayer;
import superclass.Storage;

public class Main {

	public static long id;
	public static boolean customerLogin=true;
	public static boolean logout=true;

	public static void main(String[] args) {
		Logger logger=Logger.getLogger(Main.class.getName());
		LoginLayer login=new LoginLayer();
		InputMethods input=new InputMethods();

		try {
			Storage.VALUES.setUserDetails();
			Storage.VALUES.setAccountDetails();

		} catch (CustomException e1) {
			e1.printStackTrace();
		}
		//		login
		while(logout) {
			boolean value=true;
			while(value) {
				logger.info("Enter the login id");
				id=input.isLong();
				logger.info("Enter the password");
				String password=input.getString();
				try {
					if(login.isAccountAvailable(id, password)) {
						logger.info("Welcome");
						value=false;
					}
					else {
						logger.info("Enter valid username / id");
					}
				} catch (CustomException e) {
					logger.warning(e+"");
				}
			}

			Storage.VALUES.setUserId(id);

			if(login.isCustomer(id)) {
				CustomerRunner customerRunner=new CustomerRunner();
				while(customerLogin) {
					customerRunner.customer();
					if(!customerRunner.toContinue()) {
						logout=false;
						customerLogin=false;
						logger.info("ShutDowning program");
					}
				}
			}
			else {
				AdminRunner adminRunner=new AdminRunner();
				adminRunner.admin();
			}
		}
		input.close();

	}

}

