package com.maintenance.view.mail;

import com.maintenance.view.user.UserProperties;

public class TreeData {

	private String name;

	private AnlageProperties anlageProperties;
	private UserProperties userProperties;

	public TreeData() {
		// TODO Auto-generated constructor stub
	}

	public AnlageProperties getAnlageProperties() {
		return anlageProperties;
	}

	public UserProperties getUserProperties() {
		return userProperties;
	}

	public void setAnlageProperties(AnlageProperties anlageProperties) {
		this.anlageProperties = anlageProperties;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserProperties(UserProperties userProperties) {
		this.userProperties = userProperties;
	}

	@Override
	public String toString() {
		return name;
	}

}
