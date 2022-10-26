package login;

import DBLoadDriver.PersistentLayer;
import customexception.CustomException;
import interfaces.PersistentLayerPathway;
import superclass.Storage;

public class LoginLayer {
	
	PersistentLayerPathway load=new PersistentLayer();
	
	
	
	public boolean isAccountAvailable(long id,String password) throws CustomException {
		if(Storage.VALUES.getUserDetails()==null) {
			Storage.VALUES.setUserDetails();
		}

		if(Storage.VALUES.getUserDetails().containsKey(id) && Storage.VALUES.getUserDetails().get(id).getPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	public boolean isCustomer(long userId) {
		if(Storage.VALUES.getUserDetails().get(userId).getRole().equalsIgnoreCase("customer")) {
			return true;
		}
		return false;
	}
	
}