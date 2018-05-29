package com.maintenance.mail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Session;

import com.maintenance.db.dto.Anhang;
import com.maintenance.db.dto.Wartung.EWartungArt;
import com.maintenance.db.service.Service;
import com.maintenance.db.util.HibernateUtil;
import com.maintenance.model.Anlage;
import com.maintenance.model.Station;
import com.maintenance.model.User;
import com.maintenance.util.ApplicationProperties;
import com.maintenance.util.Constants;
import com.maintenance.util.ProzentCalc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private ResourceBundle resources = ResourceBundle.getBundle("language");

	private long sleep = 3600000 * 1; // Stunden in Millisekungen umrechnen
	private static String ip;

	private Stage primaryStage;

	private List<Station> stationenForMail;
	private ListView<String> listView;

	private Thread thread;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;

		initProperties();

		initRootLayout();

		generateThread();
	}

	public static void main(String[] args) {

		ip = null;
		if (args.length == 1) {
			ip = args[0];
		}

		launch(args);
	}

	private void initProperties() {

		String userHome = System.getProperty("user.home");

		ApplicationProperties.configure("application.properties",
				userHome + File.separator + resources.getString("appname"), "application.properties");
		ApplicationProperties.getInstance().setup();

		if (ip != null) {
			ApplicationProperties.getInstance().edit("db_host", ip);

		}

		HibernateUtil.getSessionFactory();
	}

	private void initRootLayout() {

		primaryStage.setTitle(
				"MaintenanceTPMMailChannel" + " " + "@" + ApplicationProperties.getInstance().getProperty("db_host"));

		primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream(Constants.APP_ICON)));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {

				Platform.exit();
				System.exit(0);

			}
		});

		listView = new ListView<>();

		StackPane root = new StackPane();
		root.getChildren().add(listView);
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();

	}

	private void generateThread() {

		thread = new Thread(new Runnable() {

			@Override
			public void run() {

				while (!Thread.currentThread().isInterrupted()) {

					try {

						addListElement(
								getCurrentTimeStamp() + " Thread is running: ID=" + Thread.currentThread().getId());

						for (Station station : getStationenForMail()) {

							try {

								if (!station.isMailSent()) {

									addListElement(getCurrentTimeStamp() + " " + station.getAnlage().getName() + ": "
											+ station.getName());

									requestMail(station, ProzentCalc.calcProzent(station), "");
									station.setMailSent(true);
									Service.getInstance().getStationService().update(station);

									String userText = "";
									for (User user : station.getAnlage().getUsers()) {
										userText += user.getMail() + ", ";

									}
									addListElement(getCurrentTimeStamp() + " " + userText);

								}

							} catch (Exception e) {

								addListElement(getCurrentTimeStamp() + " Nachrichten konnten nicht versendet werden");
								addListElement(getCurrentTimeStamp() + " Exception Message: " + e.getMessage());
								e.printStackTrace();
							}
						}

						Thread.sleep(sleep);

					} catch (InterruptedException e) {

						Thread.currentThread().interrupt();
						e.printStackTrace();
					}

				}

			}
		});

		thread.start();

	}

	private void addListElement(String text) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				listView.getItems().add(0, text);

			}
		});

	}

	private void requestMail(Station station, float prozent, String remark) throws Exception {

		String smtpHostServer = "10.176.199.45";
		String from = "mpt_ilz_sys_maplat@magna.com";

		String to = "";
		// String to = "markus.thaler@magna.com,markus.thaler@gmx.at";

		List<File> files = new ArrayList<>();

		for (Anhang anhang : Service.getInstance().getAnhangListStation(station)) {

			files.add(anhang.getFile());

		}

		for (User user : station.getAnlage().getUsers()) {
			to += user.getMail() + ",";

		}

		String betreff = "MaintenanceManager: TPM Wartungsanforderung für " + station.getAnlage().getName() + "; "
				+ station.getName();

		String text = "";
		text += "Anlage: " + station.getAnlage().getName() + "\n";
		text += "Komponente: " + station.getName() + "\n";

		Date nextWartungDate;
		if (station.getLastWartungDate() != null)
			nextWartungDate = ProzentCalc.calcNextWartungDate(station.getLastWartungDate(),
					station.getIntervallDateUnit(), station.getWartungDateIntervall());
		else
			nextWartungDate = ProzentCalc.calcNextWartungDate(station.getCreateDate(), station.getIntervallDateUnit(),
					station.getWartungDateIntervall());

		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		text += "Wartung ist fällig am : " + df.format(nextWartungDate);
		text += "\n\n";
		text += "Software für Wartungsrückmeldung: " + "http://10.176.45.4/software/TPMTool.jar";
		text += "\n\n";
		text += "Diese Nachricht wurde an folgende Adressen versendet: " + to;
		text += "\n\n\n\n\n";

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHostServer);
		props.put("mail.smtp.auth", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		EmailUtil.sendEmail(session, from, to, null, betreff, text, files);

	}

	public String getCurrentTimeStamp() {

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

	}

	private boolean checkStationElapsed(Station station) {

		String remark = null;
		float prozent = 0;
		boolean maintenanceElapsed = false;

		if (station.getWartungArt() == EWartungArt.STUECKZAHL.ordinal()) {

			prozent = ProzentCalc.calcProzent(station);

			if (prozent >= station.getWartungStueckWarnung() && prozent < station.getWartungStueckFehler())
				maintenanceElapsed = true;

			else if (prozent >= station.getWartungStueckFehler())
				maintenanceElapsed = true;
		}

		if (station.getWartungArt() == EWartungArt.TIME_INTERVALL.ordinal()) {

			if (station.getCreateDate() != null || station.getLastWartungDate() != null) {

				Date nextWarnungDate = null;
				Date nextWartungDate;

				if (station.getLastWartungDate() != null) {
					nextWartungDate = ProzentCalc.calcNextWartungDate(station.getLastWartungDate(),
							station.getIntervallDateUnit(), station.getWartungDateIntervall());
					nextWarnungDate = ProzentCalc.calcNextWarnungDate(station.getWarnungDateUnit(),
							station.getLastWartungDate(), nextWartungDate, station.getWartungDateWarnung());
					prozent = ProzentCalc.calcProzent(station.getLastWartungDate().getTime(),
							nextWartungDate.getTime());
				} else {
					nextWartungDate = ProzentCalc.calcNextWartungDate(station.getCreateDate(),
							station.getIntervallDateUnit(), station.getWartungDateIntervall());
					nextWarnungDate = ProzentCalc.calcNextWarnungDate(station.getWarnungDateUnit(),
							station.getCreateDate(), nextWartungDate, station.getWartungDateWarnung());
					prozent = ProzentCalc.calcProzent(station.getCreateDate().getTime(), nextWartungDate.getTime());
				}

				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				remark = df.format(nextWartungDate);

				if (Calendar.getInstance().getTime().after(nextWarnungDate)
						&& Calendar.getInstance().getTime().before(nextWartungDate))
					maintenanceElapsed = true;

				if (Calendar.getInstance().getTime().after(nextWartungDate))
					maintenanceElapsed = true;

			}

		}

		return maintenanceElapsed;

	}

	public List<Station> getStationenForMail() {

		stationenForMail = new ArrayList<>();

		for (Anlage anlage : Service.getInstance().getAnlageService().findAll()) {

			for (Station station : anlage.getStationen()) {

				if (station.isTpm()) {

					if (checkStationElapsed(station))
						stationenForMail.add(station);

				}

			}
		}

		return stationenForMail;
	}

}
