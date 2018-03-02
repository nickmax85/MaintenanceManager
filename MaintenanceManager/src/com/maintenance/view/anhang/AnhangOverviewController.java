package com.maintenance.view.anhang;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.maintenance.db.dto.Anhang;
import com.maintenance.db.dto.Station;
import com.maintenance.db.dto.Wartung;
import com.maintenance.db.service.Service;
import com.maintenance.util.Constants;
import com.maintenance.view.alert.GeneralInfoAlert;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AnhangOverviewController {

	@FXML
	private AnchorPane pane;
	@FXML
	private ListView<Anhang> anhangListView;

	private Stage dialogStage;
	private boolean okClicked = false;

	private ObservableList<Anhang> anhangList;

	private Wartung wartung;
	private Station station;

	@FXML
	private void initialize() {

		anhangListView.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.DELETE) {

					Anhang a = anhangListView.getSelectionModel().getSelectedItem();
					Service.getInstance().deleteAnhang(anhangListView.getSelectionModel().getSelectedItem());
					anhangListView.getItems().remove(a);

				}

				if (event.getCode() == KeyCode.F5) {

					if (wartung != null) {
						anhangList.setAll(Service.getInstance().getAnhangList(wartung));
					}

					if (station != null) {
						anhangList.setAll(Service.getInstance().getAnhangList(station));
					}

					anhangListView.setItems(anhangList);

				}
			}
		});

		anhangListView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				if (event.getClickCount() == 2) {

					Anhang anhang = anhangListView.getSelectionModel().getSelectedItem();

					File file;
					file = anhang.getFile();
					if (file.exists()) {
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}

					}
				}
			}
		});

		anhangListView.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});

		anhangListView.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {

				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasFiles()) {
					success = true;
					String filePath = null;
					for (File file : db.getFiles()) {

						// Get length of file in bytes
						long fileSizeInBytes = file.length();
						// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
						long fileSizeInKB = fileSizeInBytes / 1024;
						// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
						long fileSizeInMB = fileSizeInKB / 1024;

						if (fileSizeInMB > 10) {
							Alert alert = new Alert(AlertType.ERROR);

							alert.setTitle("Fehler");
							alert.setHeaderText("Datei zu gross, bitte verkleinern");
							alert.setContentText("Die Datei darf maximal 10MB haben");
							alert.initOwner(dialogStage);
							DialogPane dialogPane = alert.getDialogPane();
							dialogPane.getStylesheets()
									.addAll(getClass().getResource(Constants.STYLESHEET).toExternalForm());
							alert.showAndWait();
							break;
						}
						filePath = file.getAbsolutePath();

						Anhang anhang = new Anhang();
						anhang.setName(file.getName());
						anhang.setFile(file);

						if (wartung != null)
							anhang.setWartungId(wartung.getId());

						if (station != null) {
							anhang.setStationId(station.getId());
						}

						Service.getInstance().insertAnhang(anhang);
						if (!Service.getInstance().isErrorStatus()) {
							anhangListView.getItems().add(anhang);
							new GeneralInfoAlert(dialogStage, "Speichern", "Anhang speichern",
									"Der Anhang wurde in der Datenbank gespeichert.").showAndWait();
						}

					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});

	}

	public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;

	}

	public void setData(Object data) {

		anhangList = FXCollections.observableArrayList();

		if (data instanceof Wartung) {
			this.wartung = (Wartung) data;
			anhangList.setAll(Service.getInstance().getAnhangList(wartung));
		}

		if (data instanceof Station) {
			this.station = (Station) data;
			anhangList.setAll(Service.getInstance().getAnhangList(station));
		}

		anhangListView.setItems(anhangList);
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {

		if (isInputValid()) {

			okClicked = true;
			dialogStage.close();

		}
	}

	@FXML
	private void handleAdd() {

		File file = null;
		FileChooser chooser = new FileChooser();

		file = chooser.showOpenDialog(dialogStage);

		if (file != null) {
			String filePath = null;

			// Get length of file in bytes
			long fileSizeInBytes = file.length();
			// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
			long fileSizeInKB = fileSizeInBytes / 1024;
			// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
			long fileSizeInMB = fileSizeInKB / 1024;

			if (fileSizeInMB > 10) {
				Alert alert = new Alert(AlertType.ERROR);

				alert.setTitle("Fehler");
				alert.setHeaderText("Datei zu gross, bitte verkleinern");
				alert.setContentText("Die Datei darf maximal 10MB haben");
				alert.initOwner(dialogStage);
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().addAll(getClass().getResource(Constants.STYLESHEET).toExternalForm());
				alert.showAndWait();
				return;
			}
			filePath = file.getAbsolutePath();

			Anhang anhang = new Anhang();
			anhang.setName(file.getName());
			anhang.setFile(file);

			if (wartung != null)
				anhang.setWartungId(wartung.getId());

			if (station != null) {
				anhang.setStationId(station.getId());
			}

			Service.getInstance().insertAnhang(anhang);
			if (!Service.getInstance().isErrorStatus()) {
				anhangListView.getItems().add(anhang);
				new GeneralInfoAlert(dialogStage, "Speichern", "Anhang speichern",
						"Der Anhang wurde in der Datenbank gespeichert.").showAndWait();
			}

		}

	}

	@FXML
	private void handleRemove() {

		Anhang a = anhangListView.getSelectionModel().getSelectedItem();
		Service.getInstance().deleteAnhang(anhangListView.getSelectionModel().getSelectedItem());
		anhangListView.getItems().remove(a);

	}

	private boolean isInputValid() {
		return true;
	}
}