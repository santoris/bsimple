package com.santoris.bsimple.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santoris.bsimple.axa.importer.AxaDataToBsimpleModelImporter;
import com.santoris.bsimple.client.service.DashboardService;
import com.santoris.bsimple.dao.UserRepository;
import com.santoris.bsimple.model.Dashboard;
import com.santoris.bsimple.model.User;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AxaDataToBsimpleModelImporter axaDataToBsimpleModelImporter;
	
	@Override
	public Dashboard getDashboard(String login, Long bankCustomerId) {
		User user = userRepository.findByLogin(login);
;
		if (user == null && bankCustomerId != null) {
			user = axaDataToBsimpleModelImporter.importDataForAUser(login, bankCustomerId);
		}
		
		Dashboard dashboard = new Dashboard();
		dashboard.setUser(user);
		
		return dashboard;
	}

}
