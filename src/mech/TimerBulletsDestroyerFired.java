package mech;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import data.Constants;
import entities.ships.Bullet;
import visual.panels.GamePanel;

@SuppressWarnings("serial")
public class TimerBulletsDestroyerFired extends Timer implements Constants, ActionListener{
	private TimerBeforeNextShot timerBeforeNextShot = new TimerBeforeNextShot(0, null);
	private GamePanel gamePanel;
	private int kDY;

	public TimerBulletsDestroyerFired(int delay, ActionListener listener, GamePanel gamePanel, int kDY) {
		super(delay, listener);
		this.gamePanel = gamePanel;
		this.kDY = kDY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!timerBeforeNextShot.isRunning()){
			timerBeforeNextShot = new TimerBeforeNextShot(BETWEEN_DESTROYER_SHOTS_DELAY, null);
			timerBeforeNextShot.addActionListener(timerBeforeNextShot);
			timerBeforeNextShot.start();
			createNewBullet();
		}
	}
	
	private void createNewBullet() {
		int x = gamePanel.destroyer.getX();
		int y = gamePanel.destroyer.getY()+gamePanel.destroyer.getHeight();
			Bullet bullet = new Bullet(new Point(x, y), Color.RED);
		gamePanel.add(bullet);
		gamePanel.repaint(bullet.getX(), bullet.getY(), MAX_SHIP_SIZE, MAX_SHIP_SIZE*2);
		
		TimerBulletsDestroyerFly timerBulletsDestroyerFly = new TimerBulletsDestroyerFly(PLAYER_MOVE_DELAY*2, null, kDY, bullet, gamePanel);
		timerBulletsDestroyerFly.addActionListener(timerBulletsDestroyerFly);
		timerBulletsDestroyerFly.start();
		gamePanel.bulletsDestroyerTimers.add(timerBulletsDestroyerFly);
		if (kDY == -1)
			gamePanel.lastBullet = bullet;
	}

	private class TimerBeforeNextShot extends Timer implements ActionListener{

		public TimerBeforeNextShot(int delay, ActionListener listener) {
			super(delay, listener);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			stop();
		}
	}
}
