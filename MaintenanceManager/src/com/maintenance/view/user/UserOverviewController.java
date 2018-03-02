package com.maintenance.view.user;

import com.maintenance.Main;
import com.maintenance.db.service.Service;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserOverviewController {
	@FXML
	private TableView<UserProperties> userTable;
	@FXML
	private TableColumn<UserProperties, String> nameColumn;
	@FXML
	private TableColumn<UserProperties, String> mailColumn;

	@FXML
	private Label nameLabel;
	@FXML
	private Label mailLabel;
	@FXML
	private Label userLabel;
	@FXML
	private Label timestampLabel;

	// Reference to the main application.
	private Main main;

	private Stage dialogStage;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public UserOverviewController() {

	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeleteUser() {
		int selectedIndex = userTable.getSelectionModel().getSelectedIndex();

		UserProperties selectedAbteilung = userTable.getSelectionModel().getSelectedItem();

		Service.getInstance().deleteUser(selectedAbteilung.getUser());
		if (!Service.getInstance().isErrorStatus())
			userTable.getItems().remove(selectedIndex);
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected AbteilungDTO.
	 */
	@FXML
	private void handleEditUser() {
		// UserProperties selectedAbteilung =
		// userTable.getSelectionModel().getSelectedItem();
		//
		// if (selectedAbteilung != null) {
		// boolean okClicked = main.showUserEditDialog(dialogStage,
		// selectedAbteilung);
		//
		// if (okClicked) {
		// Service.getInstance().updateUser(selectedAbteilung.getUser());
		// if (!Service.getInstance().isErrorStatus())
		// showUserDetails(selectedAbteilung);
		// }
		//
		// } else {
		// // Nothing selected.
		//
		// }
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit details
	 * for a new Abteilung.
	 */
	@FXML
	private void handleNewUser() {
		// UserProperties userProperties = new UserProperties(new User());
		//
		// boolean okClicked = main.showUserEditDialog(dialogStage,
		// userProperties);
		//
		// if (okClicked) {
		// Service.getInstance().insertUser(userProperties.getUser());
		// if (!Service.getInstance().isErrorStatus())
		// userTable.setItems(Service.getInstance().getUserProperties());
		// }
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the User table with the columns.

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		mailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());

		// Clear details.
		showUserDetails(null);

		// Listen for selection changes and show the AbteilungDTO details when
		// changed.
		userTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showUserDetails(newValue));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMain(Main main) {

		this.main = main;

		// userTable.setItems(Service.getInstance().getUsers());
	}

	/**
	 * Fills all text fields to show details about the Abteilung. If the specified
	 * AbteilungDTO is null, all text fields are cleared.
	 * 
	 * @param Abteilung
	 * 
	 */
	private void showUserDetails(UserProperties user) {
		if (user != null) {
			// Fill the labels with info from the Abteilung object.
			nameLabel.setText(user.getName());
			mailLabel.setText(user.getMail());
			userLabel.setText(user.getModifiedBy());
			timestampLabel.setText(user.getTimestamp());

		} else {
			// User is null, remove all the text.
			nameLabel.setText("");
			mailLabel.setText("");
			userLabel.setText("");
			timestampLabel.setText("");
		}
	}
}