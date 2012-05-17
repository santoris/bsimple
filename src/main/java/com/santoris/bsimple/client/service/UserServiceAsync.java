package com.santoris.bsimple.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	
	void getBankCustomerIdByLogin(String login, AsyncCallback<Long> callback);

}
