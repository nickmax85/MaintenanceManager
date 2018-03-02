package com.maintenance.view.mail;

import com.maintenance.db.dto.Anlage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AnlageProperties {

	private StringProperty name;

	private StringProperty modifiedBy;
	private StringProperty timestamp;

	private Anlage anlage;

	/**
	 * Default constructor.
	 */
	public AnlageProperties() {
		this(null);
	}

	/**
	 * Constructor with some initial data.
	 * 
	 * @param name
	 * @param modifiedBy
	 * @param timestamp
	 */
	public AnlageProperties(Anlage anlage) {

		this.anlage = anlage;

		if (anlage != null) {

			this.name = new SimpleStringProperty(anlage.getName());
			this.modifiedBy = new SimpleStringProperty(anlage.getUser());

			if (anlage.getTimestamp() != null)
				this.timestamp = new SimpleStringProperty(anlage.getTimestamp().toString());
			else
				this.timestamp = new SimpleStringProperty(" ");

		}

	}

	public Anlage getAnlage() {
		return anlage;
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

	public StringProperty nameProperty() {
		return name;
	}

	public void setAnlage(Anlage anlage) {
		this.anlage = anlage;
	}

	public void setName(String name) {
		this.name.set(name);
		this.anlage.setName(name);
	}

	public void setTimestamp(String timestamp) {
		this.timestamp.set(timestamp);
		// this.user.setTimestamp(timestamp);
	}

	public void setUser(String user) {
		this.modifiedBy.set(user);
		this.anlage.setUser(user);
	}

	public StringProperty TimestampProperty() {

		return timestamp;
	}

	@Override
	public String toString() {
		return getName();
	}

	public StringProperty userProperty() {
		return modifiedBy;
	}

}
