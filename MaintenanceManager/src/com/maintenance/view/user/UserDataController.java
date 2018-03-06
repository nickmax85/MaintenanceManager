package com.maintenance.view.user;

import java.util.ResourceBundle;

import com.maintenance.db.service.Service;
import com.maintenance.model.User;
import com.maintenance.view.alert.InputValidatorAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserDataController {

	@FXML
	private ResourceBundle resources;

	@FXML
	public TextField nameField;
	@FXML
	public TextField mailField;

	private User data;

	private Stage dialogStage;

	@FXML
	private void initialize() {
		clearFields();
	}

	public void setData(User data) {

		this.data = data;

		ObservableList<User> user = FXCollections.observableArrayList();
		user.setAll(Service.getInstance().getUserService().findAll());

		if (data != null) {
			nameField.setText(data.getName());
			mailField.setText(data.getMail());

		} else {
			clearFields();

		}

	}

	private void clearFields() {

		nameField.setText("");
		mailField.setText("");

	}

	public void setEditable(boolean editable) {

		nameField.setDisable(!editable);
		mailField.setDisable(!editable);

	}

	public boolean isInputValid() {

		String text = "";

		if (nameField.getText() == null || nameField.getText().length() == 0)
			text += "Kein gültiger Name!\n";

		if (mailField.getText() == null || mailField.getText().length() == 0)
			text += "Keine gültige Mail!\n";

		if (text.length() == 0) {
			return true;

		} else {
			new InputValidatorAlert(dialogStage, text).showAndWait();
			return false;
		}

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

}
