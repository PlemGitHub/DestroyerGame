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
public class TimerBulletsFired extends Timer implements Constants, ActionListener{
	private TimerBeforeNextShot timerBeforeNextShot = new TimerBeforeNextShot(0, null);
	private GamePanel gamePanel;
	private int kDY;

	public TimerBulletsFired(int delay, ActionListener listener, GamePanel gamePanel, int kDY) {
		super(delay, listener);
		this.gamePanel = gamePanel;
		this.kDY = kDY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!timerBeforeNextShot.isRunning()){
			gamePanel.setScoreLevel(gamePanel.getScoreLevel()+SCORE_SHOT);
			timerBeforeNextShot = new TimerBeforeNextShot(BETWEEN_SHOTS_DELAY, null);
			timerBeforeNextShot.addActionListener(timerBeforeNextShot);
			timerBeforeNextShot.start();
			creatNewBullet();
			gamePanel.increaseShots();
		}
	}
	
	private void creatNewBullet() {
		int x = gamePanel.player.getX();
		int y = gamePanel.player.getY();
			Bullet bullet = new Bullet(new Point(x, y), Color.BLACK);
		gamePanel.add(bullet);
		gamePanel.repaint(bullet.getX(), bullet.getY(), MAX_SHIP_SIZE, MAX_SHIP_SIZE*2);
		
		TimerBulletsFly timerBulletsFly = new TimerBulletsFly(PLAYER_MOVE_DELAY, null, kDY, bullet, gamePanel);
		timerBulletsFly.addActionListener(timerBulletsFly);
		timerBulletsFly.start();
		gamePanel.bulletsTimers.add(timerBulletsFly);
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
