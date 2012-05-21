package com.santoris.bsimple.model;

import java.io.Serializable;

public class Dashboard implements Serializable {
	
	User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
