package com.santoris.bsimple.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santoris.bsimple.client.service.UserService;
import com.santoris.bsimple.dao.UserRepository;
import com.santoris.bsimple.model.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Long getBankCustomerIdByLogin(String login) {
		User user = userRepository.findByLogin(login);
		return user == null ? null : user.getCustomer().getBankId();
	}

}
