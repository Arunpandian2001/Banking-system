package superclass;

import customexception.CustomException;

public abstract class User {

	public abstract boolean transferAmount(long accountNumber,double amount,String password) throws CustomException; 
	public abstract double balance() throws CustomException;
	public abstract boolean toWithdraw(double amount) throws CustomException;
	public abstract boolean toDeposit(double amount) throws CustomException;
}
