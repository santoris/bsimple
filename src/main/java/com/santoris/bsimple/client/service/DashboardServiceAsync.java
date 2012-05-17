package com.santoris.bsimple.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.santoris.bsimple.model.Dashboard;

public interface DashboardServiceAsync {

	void getDashboard(String login, Long customerId, AsyncCallback<Dashboard> callback);

}
