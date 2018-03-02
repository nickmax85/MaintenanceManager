package com.maintenance.view.user;

import com.maintenance.db.dto.User;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperties {

	private StringProperty name;
	private StringProperty mail;

	private StringProperty modifiedBy;
	private StringProperty timestamp;

	private User user;

	/**
	 * Default constructor.
	 */
	public UserProperties() {
		this(null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param name
	 * @param mail
	 * @param modifiedBy
	 * @param timestamp
	 */
	public UserProperties(User user) {

		this.user = user;

		if (user != null) {

			this.name = new SimpleStringProperty(user.getName());
			this.mail = new SimpleStringProperty(user.getMail());
			this.modifiedBy = new SimpleStringProperty(user.getUser());

			if (user.getTimestamp() != null)
				this.timestamp = new SimpleStringProperty(user.getTimestamp().toString());
			else
				this.timestamp = new SimpleStringProperty(" ");

		}

	}

	public String getMail() {
		return mail.get();
	}

	public String getModifiedBy() {
		return modifiedBy.get();
	}

	public String getName() {
		return name.get();
	}

	public String getTimestamp() {
		return timestamp.get();
	}

	public User getUser() {
		return user;
	}

	public StringProperty mailProperty() {
		return mail;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setMail(String mail) {
		this.mail.set(mail);
		this.user.setMail(mail);
	}

	public void setName(String name) {
		this.name.set(name);
		this.user.setName(name);
	}

	public void setTimestamp(String timestamp) {
		this.timestamp.set(timestamp);
		// this.user.setTimestamp(timestamp);
	}

	public void setUser(String user) {
		this.modifiedBy.set(user);
		this.user.setUser(user);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StringProperty TimestampProperty() {

		return timestamp;
	}

	public StringProperty userProperty() {
		return modifiedBy;
	}

}
