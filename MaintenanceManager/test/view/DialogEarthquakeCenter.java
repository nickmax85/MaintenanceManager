package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Dialog;
import javafx.util.Duration;
import javafx.util.Pair;

public class DialogEarthquakeCenter {

	public static final int SHAKE_DISTANCE = 10;
	public static final double SHAKE_CYCLE = 50;
	public static final int SHAKE_DURATION = 500;
	public static final int SHAKE_UPDATE = 5;

	private Dialog<Pair<String, String>> dialog;
	private int x, y;
	private long startTime;
	private Timer shakeTimer;
	private final double TWO_PI = Math.PI * 2.0;
	private Timeline timeline;

	public static void main(final String[] args) {

		new DialogEarthquakeCenter(null).startTimer();
		;

	}

	public DialogEarthquakeCenter(Dialog<Pair<String, String>> parent) {
		dialog = parent;
	}

	/**
	 * Creates and starts the timer
	 *
	 * @return Scene
	 */
	public void startTimer() {
		x = (int) dialog.getX();
		y = (int) dialog.getY();
		startTime = System.currentTimeMillis();
		// Set up earth time text update
		timeline = new Timeline(new KeyFrame(Duration.millis(SHAKE_DURATION), ae -> startNudging()));
		// timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();
	}

	public void startNudging() {
		x = (int) dialog.getX();
		y = (int) dialog.getY();
		startTime = System.currentTimeMillis();
		shakeTimer = new Timer(SHAKE_UPDATE, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shake();
			}
		});

		shakeTimer.start();
	}

	public void shake() {
		// calculate elapsed time
		long elapsed = System.currentTimeMillis() - startTime;
		// System.out.println("elapsed is " + elapsed);
		// use sin to calculate an x-offset
		double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
		double angle = waveOffset * TWO_PI;
		// offset the x-location by an amount
		// proportional to the sine, up to shake_distance
		int shakenX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + x);

		Platform.runLater(() -> {
			// dialog.hide();
			dialog.setX(shakenX);
			// System.out.println("set shakenX to " + shakenX);
			dialog.setY(y);
			dialog.show();
		});

		// try {Thread.sleep(20);}
		// catch (InterruptedException ex) {}

		// should we stop timer
		if (elapsed >= SHAKE_DURATION) {
			stopShake();

		}
	}

	public void stopShake() {
		shakeTimer.stop();
		Platform.runLater(() -> {
			timeline.stop();
			dialog.close();
		});
	}
}