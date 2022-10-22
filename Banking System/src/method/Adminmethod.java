package method;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import customexception.CustomException;
import pojo.Accounts_pojo;
import pojo.CustomerPojo;
import superclass.Storage;
import superclass.User;

public class Adminmethod {

	public List<CustomerPojo> getCustomerDetails(Long ... customerIds) {
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
				list.add(map.get(customerIds[i]));
			}
		}
		return list;
	}
	
	public List<Map<Long, Accounts_pojo>> getAccountDetails(Long ... accountNumbers){
		int length=accountNumbers.length;
		Map<Long, Map<Long, Accounts_pojo>> map=Storage.VALUES.getAccountDetails();
		List<Map<Long, Accounts_pojo>> list=new ArrayList<>();
		if(length==0) {
			for(Map.Entry<Long, Map<Long, Accounts_pojo>> element:map.entrySet()) {
				list.add(element.getValue());
			}
		}
		else if(length>=1) {
			for(int i=0;i<length;i++) {
				list.add(map.get(accountNumbers[i]));
			}
		}
		return list;
	}
}
