package com.maintenance.view.mail;

import com.maintenance.view.user.UserProperties;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditController {

	@FXML
	private TextField name;
	@FXML
	private TextField mail;
	@FXML
	private TextField user;
	@FXML
	private TextField timestamp;

	private Stage dialogStage;
	private UserProperties userProperties;
	private boolean okClicked;

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleOk() {

		userProperties.setName(name.getText());
		userProperties.setMail(mail.getText());

		userProperties.setUser(user.getText());
		userProperties.setTimestamp(timestamp.getText());

		okClicked = true;
		dialogStage.close();

	}

	@FXML
	private void initialize() {
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setUserProperties(UserProperties userProperties) {

		this.userProperties = userProperties;

		if (userProperties.getUser() != null) {

			name.setText(userProperties.getName());
			mail.setText(userProperties.getMail());

			user.setText(userProperties.getModifiedBy());
			timestamp.setText(userProperties.getTimestamp());

		}

	}

}