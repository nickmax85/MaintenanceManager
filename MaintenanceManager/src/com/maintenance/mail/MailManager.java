package mailmanager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.maintenance.db.dto.Wartung.EWartungArt;
import com.maintenance.db.service.Service;
import com.maintenance.db.util.HibernateUtil;
import com.maintenance.model.Anlage;
import com.maintenance.model.Station;
import com.maintenance.model.User;
import com.maintenance.util.ApplicationProperties;
import com.maintenance.util.ProzentCalc;

public class MailManager {

	private ResourceBundle resources = ResourceBundle.getBundle("language");
	private static String ip;
	private static String userHome;

	private List<Station> stationenForMail;

	public static void main(String[] args) {

		ip = null;
		if (args.length == 1) {
			ip = args[0];
		}

		new MailManager();

	}

	public MailManager() {

		// init();

		// HibernateUtil.getSessionFactory().close();

	}

	private void init() {

		userHome = System.getProperty("user.home");

		ApplicationProperties.configure("application.properties",
				userHome + File.separator + resources.getString("appname"), "application.properties");
		ApplicationProperties.getInstance().setup();

		if (ip != null) {
			ApplicationProperties.getInstance().edit("db_host", ip);

		}

		HibernateUtil.getSessionFactory();
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

	private void requestMail(Station station, float prozent, String remark) {

	}

	public List<Station> getStationenForMail() {

		stationenForMail = new ArrayList<>();

		for (Anlage anlage : Service.getInstance().getAnlageService().findAll()) {

			System.out.println(anlage.getName());

			for (Station station : anlage.getStationen()) {

				if (station.isTpm()) {

					System.out.println(station.getName());
					if (checkStationElapsed(station))
						stationenForMail.add(station);

				}

			}
		}

		for (Station station : stationenForMail) {

			System.out
					.println("Anlage: " + station.getAnlage().getName() + "; \t\t\t" + "Station:" + station.getName());

			for (User user : station.getAnlage().getUsers()) {

				System.out.println(user.getMail());

			}

		}

		return stationenForMail;
	}

}
