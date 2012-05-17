package com.santoris.bsimple.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.santoris.bsimple.model.Dashboard;

@RemoteServiceRelativePath("dashboard")
public interface DashboardService extends RemoteService {

	Dashboard getDashboard(String login, Long customerId);

}
