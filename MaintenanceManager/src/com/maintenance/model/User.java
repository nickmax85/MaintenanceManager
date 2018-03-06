package com.maintenance.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.maintenance.model.Anlage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

@Entity
@Table(name = "user")
public class User {

	private int id;
	private String name;
	private String mail;

	private Timestamp timestamp;
	private String user;

	private List<Anlage> anlagen;

	private BooleanProperty active = new SimpleBooleanProperty();

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public String getMail() {
		return mail;
	}

	public String getName() {
		return name;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getUser() {
		return user;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "anlage_user", joinColumns = { @JoinColumn(name = "anlageId") }, inverseJoinColumns = {
			@JoinColumn(name = "userId") })
	public List<Anlage> getAnlagen() {
		return anlagen;
	}

	public void setAnlagen(List<Anlage> anlagen) {
		this.anlagen = anlagen;
	}

	@Override
	public String toString() {
		return getMail();
	}

	public BooleanProperty activeProperty() {
		return this.active;
	}

	@Transient
	public boolean isActive() {
		return this.activeProperty().get();
	}

	public void setActive(final boolean active) {
		this.activeProperty().set(active);
	}

}
