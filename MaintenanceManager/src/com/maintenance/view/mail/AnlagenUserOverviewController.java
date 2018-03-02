package com.maintenance.view.mail;

import org.apache.log4j.Logger;

import com.maintenance.Main;
import com.maintenance.db.dto.AnlageUser;
import com.maintenance.db.service.Service;
import com.maintenance.view.user.UserProperties;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;

public class AnlagenUserOverviewController {

	private static final Logger logger = Logger.getLogger(AnlagenUserOverviewController.class);

	@FXML
	TreeView<TreeData> treeView;

	@FXML
	TableView<UserProperties> userTable;
	@FXML
	private TableColumn<UserProperties, String> nameColumn;
	@FXML
	private TableColumn<UserProperties, String> mailColumn;

	// Reference to the main application.
	private Main main;

	private UserProperties draggedUser;

	private Stage dialogStage;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public AnlagenUserOverviewController() {

	}

	public void createTree() {

		// // create root
		// TreeData rootTree = new TreeData();
		// rootTree.setName("Anlagen");
		// TreeItem<TreeData> root = new TreeItem<TreeData>(rootTree);
		// root.setExpanded(true);
		//
		// // create child
		// for (Anlage anlage :
		// Service.getInstance().getAllAnlageLeerflaecheAbteilungPanelFormat())
		// {
		//
		// TreeData anlageTree = new TreeData();
		// anlageTree.setAnlageProperties(anlage);
		// anlageTree.setName(anlage.getName());
		//
		// TreeItem<TreeData> itemChild = new TreeItem<TreeData>(anlageTree);
		// itemChild.setExpanded(false);
		//
		// for (AnlageUser anlagenUser :
		// Service.getInstance().getAnlagenUser(anlage.getAnlage().getId())) {
		//
		// TreeData userTree = new TreeData();
		// UserProperties usrProp = new
		// UserProperties(anlagenUser.getBenutzer());
		//
		// userTree.setAnlage(anlage);
		// userTree.setUserProperties(usrProp);
		// userTree.setName(anlagenUser.getBenutzer().getName() + " " +
		// anlagenUser.getBenutzer().getMail());
		// TreeItem<TreeData> anlageChild = new TreeItem<TreeData>(userTree);
		//
		// itemChild.getChildren().add(anlageChild);
		//
		// }
		//
		// // root is the parent of itemChild
		// root.getChildren().add(itemChild);
		//
		// }
		//
		// treeView.setRoot(root);

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
		//
		// }
		//
		// } else {
		// // Nothing selected.
		//
		// }
	}

	// @FXML
	// private void handleNewUser() {
	// UserProperties userProperties = new UserProperties(new User());
	//
	// boolean okClicked = main.showUserEditDialog(dialogStage, userProperties);
	//
	// if (okClicked) {
	// Service.getInstance().insertUser(userProperties.getUser());
	// if (!Service.getInstance().isErrorStatus())
	// userTable.setItems(Service.getInstance().getUserProperties());
	// }
	// }

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {

		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		mailColumn.setCellValueFactory(cellData -> cellData.getValue().mailProperty());

		// anlagenTree.setCellFactory(new Callback<TreeView<String>,
		// TreeCell<String>>() {
		// @Override
		// public TreeCell<String> call(TreeView<String> stringTreeView) {
		// TreeCell<String> treeCell = new TreeCell<String>() {
		// @Override
		// protected void updateItem(String item, boolean empty) {
		// super.updateItem(item, empty);
		// if (!empty && item != null) {
		// setText(item);
		// setGraphic(getTreeItem().getGraphic());
		// } else {
		// setText(null);
		// setGraphic(null);
		// }
		// }
		// };
		//
		// treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
		// @Override
		// public void handle(MouseEvent mouseEvent) {
		//
		// }
		// });
		//
		// return treeCell;
		// }
		// });

		initListeners();

	}

	private void initListeners() {

		userTable.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (logger.isInfoEnabled()) {
					logger.info("setOnDragDetected");
				}

				Dragboard dragBoard = userTable.startDragAndDrop(TransferMode.MOVE);

				ClipboardContent content = new ClipboardContent();

				content.putString(userTable.getSelectionModel().getSelectedItem().getName() + " ("
						+ userTable.getSelectionModel().getSelectedItem().getMail() + ")");

				draggedUser = userTable.getSelectionModel().getSelectedItem();

				dragBoard.setContent(content);
			}
		});

		userTable.setOnDragDone(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {

				if (logger.isInfoEnabled()) {
					logger.info("setOnDragDone");

				}

			}
		});

		treeView.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {

				if (logger.isInfoEnabled()) {
					logger.info("setOnDragEntered");
				}
				treeView.setBlendMode(BlendMode.DIFFERENCE);

			}
		});

		treeView.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {

				if (logger.isInfoEnabled()) {
					logger.info("setOnDragExited");
				}

				treeView.setBlendMode(null);
			}
		});

		treeView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				if (logger.isInfoEnabled()) {
					logger.info("setOnDragOver");
				}

				dragEvent.acceptTransferModes(TransferMode.MOVE);
			}
		});

		treeView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {

				if (logger.isInfoEnabled()) {
					logger.info("setOnDragDropped");
				}

				String name = dragEvent.getDragboard().getString();

				if (logger.isInfoEnabled()) {
					logger.info(name);
					logger.info(draggedUser.getName());
					logger.info(treeView.getSelectionModel().getSelectedItem());

				}

				TreeItem<TreeData> anlage = treeView.getSelectionModel().getSelectedItem();
				UserProperties user = draggedUser;

				AnlageUser anlageUser = new AnlageUser();
				anlageUser.setAnlageId(anlage.getValue().getAnlageProperties().getAnlage().getId());
				anlageUser.setUserId(draggedUser.getUser().getId());

				Service.getInstance().insertAnlageUser(anlageUser);

				if (!Service.getInstance().isErrorStatus()) {
					TreeData userTree = new TreeData();
					userTree.setUserProperties(user);
					userTree.setAnlageProperties(anlage.getValue().getAnlageProperties());
					userTree.setName(user.getName() + " " + user.getMail());

					TreeItem<TreeData> userItem = new TreeItem<TreeData>(userTree);
					anlage.getChildren().add(userItem);

					dragEvent.setDropCompleted(true);

					draggedUser = null;
					treeView.getSelectionModel().clearSelection();
				} else {
					;
					// Show the error message.
					// Dialogs.create().title("Error").masthead("").message(AppService.getInstance().getDAOException())
					// .showError();

				}
			}

		});

		treeView.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

			}
		});

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

		// this.main = main;
		//
		// userTable.setItems(Service.getInstance().getUserProperties());
		//
		// createTree();
		//
		// ContextMenu rootContextMenu = ContextMenuBuilder.create()
		// .items(MenuItemBuilder.create().text("Entfernen").onAction(new
		// EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent arg0) {
		// System.out.println("Menu Item Clicked!");
		//
		// TreeItem<TreeData> userItem =
		// treeView.getSelectionModel().getSelectedItem();
		// userItem.getValue().getUserProperties().getName();
		//
		// AnlageUser anlageUser = new AnlageUser();
		// anlageUser.setAnlageId(userItem.getValue().getAnlageProperties().getAnlage().getId());
		// anlageUser.setUserId(userItem.getValue().getUserProperties().getUser().getId());
		//
		// Service.getInstance().deleteAnlageUser(anlageUser);
		// if (!Service.getInstance().isErrorStatus()) {
		// TreeItem<TreeData> tree = userItem.getParent();
		// tree.getChildren().remove(userItem);
		// }
		// }
		// }).build()).build();
		//
		// treeView.setContextMenu(rootContextMenu);

	}

}
