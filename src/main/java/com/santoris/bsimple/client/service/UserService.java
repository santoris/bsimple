package com.santoris.bsimple.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {
	
	/**
	 * 
	 * @param login of the user
	 * @return null if the user does not exist else the bank customer id associated to the user
	 */
	  Long getBankCustomerIdByLogin(String login);
	  
}
