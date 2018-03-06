package mailmanager;

import java.io.File;
import java.util.Calendar;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Session;

import com.maintenance.db.util.HibernateUtil;
import com.maintenance.model.Station;
import com.maintenance.model.User;
import com.maintenance.util.ApplicationProperties;
import com.maintenance.util.ProzentCalc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private ResourceBundle resources = ResourceBundle.getBundle("language");
	private static String ip;

	private Stage primaryStage;

	private MailManager mailManager;

	ListView<String> listView;

	Thread thread;

	@Override
	public void start(Stage primaryStage) {

		this.primaryStage = primaryStage;

		initProperties();

		initRootLayout();

		mailManager = new MailManager();

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

		primaryStage.setTitle("MaintenanceMailManager");
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

						for (Station station : mailManager.getStationenForMail()) {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									listView.getItems().add(0,
											station.getAnlage().getName() + ": " + station.getName());

								}
							});

							try {
								requestMail(station, ProzentCalc.calcProzent(station), "");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							for (User user : station.getAnlage().getUsers()) {

								Platform.runLater(new Runnable() {

									@Override
									public void run() {

										listView.getItems().add(0, user.getMail());
										listView.getItems().add(0, Calendar.getInstance().getTime().toString());

									}
								});

							}

						}

						Thread.sleep(5000);

					} catch (InterruptedException e) {

						Thread.currentThread().interrupt();
						e.printStackTrace();
					}

				}

			}
		});

		thread.start();

	}

	private void requestMail(Station station, float prozent, String remark) throws Exception {

		String smtpHostServer = "10.176.199.45";
		String from = "mpt_ilz_sys_maplat@magna.com";

		String to = "";
		// String to = "markus.thaler@magna.com,markus.thaler@gmx.at";

		for (User user : station.getAnlage().getUsers()) {
			to += user.getMail() + ",";
		}

		String betreff = "MaintenanceManager: " + station.getAnlage().getName() + "; " + station.getName();
		String text = "Diese Nachricht wurde an folgende Adressen versendet: " + to;

		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHostServer);
		props.put("mail.smtp.auth", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		EmailUtil.sendEmail(session, from, to, null, betreff, text, null);

	}
}
