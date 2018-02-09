package mech;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import javax.swing.Timer;
import data.Constants;
import entities.ships.Bullet;
import entities.ships.Player;
import entities.ships.Shield;
import entities.ships.Target;
import visual.panels.GamePanel;

@SuppressWarnings("serial")
public class TimerBulletsDestroyerFly extends Timer implements Constants, ActionListener{
	GamePanel gamePanel;
	private Bullet bullet;
	private int kDY;

	public TimerBulletsDestroyerFly(int delay, ActionListener listener, int kDY, Bullet bullet, GamePanel gamePanel) {
		super(delay, listener);
		this.gamePanel = gamePanel;
		this.bullet = bullet;
		this.kDY = kDY;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int x = bullet.getX();
		int newY = bullet.getY() + MOVING_DX*kDY;
		bullet.setLocation(x, newY);
		checkShieldCollision();
		checkTargetCollision();
		checkPlayerCollision();
		if (bullet.getY() > PLAYER_Y+MAX_SHIP_SIZE){
			gamePanel.remove(bullet);
			stop();
		}
	}

	private void checkTargetCollision() {
		Polygon bulletP = new Polygon(bullet.ship.xpoints, bullet.ship.ypoints, bullet.ship.npoints);
			bulletP.translate(bullet.getX(), bullet.getY());
			Area bulletArea = new Area(bulletP);
		for (int j = 0; j < TARGETS_VERTICALLY; j++) {
			for (int i = 0; i < TARGETS_HORIZONTALLY; i++) {
				Target target = gamePanel.targets[i][j];
				
				if (target != null){
					Polygon targetP = new Polygon(target.ship.xpoints, target.ship.ypoints, target.ship.npoints);
						targetP.translate(target.getX(), target.getY());
						Area targetArea = new Area(targetP);
					targetArea.intersect(bulletArea);
					if (!targetArea.isEmpty()){
						stop();
						gamePanel.remove(bullet);
						gamePanel.repaint(target.getX(), target.getY(), target.getWidth(), target.getHeight()+20);
						gamePanel.bulletsDestroyerTimers.remove(this);
						return;
					}
					
				}
			}
		}
	}

	private void checkShieldCollision() {
		Polygon bulletP = new Polygon(bullet.ship.xpoints, bullet.ship.ypoints, bullet.ship.npoints);
			bulletP.translate(bullet.getX(), bullet.getY());
			Area bulletArea = new Area(bulletP);
		for (int j = 0; j < SHIELDS_VERTICALLY; j++) {
			for (int i = 0; i < SHIELDS_HORIZONTALLY; i++) {
				Shield shield = gamePanel.shields[i][j];
				
				if (shield != null){
					Polygon shieldP = new Polygon(shield.ship.xpoints, shield.ship.ypoints, shield.ship.npoints);
						shieldP.translate(shield.getX(), shield.getY());
						Area shieldArea = new Area(shieldP);
					shieldArea.intersect(bulletArea);
					if (!shieldArea.isEmpty()){
						stop();
						gamePanel.remove(bullet);
						gamePanel.remove(shield);
						gamePanel.repaint(shield.getX()-20, shield.getY()-20, MAX_SHIP_SIZE*2,MAX_SHIP_SIZE*2);
						gamePanel.shields[i][j] = null;
						gamePanel.bulletsDestroyerTimers.remove(this);
						return;
					}
					
				}
			}
		}
	}

	private void checkPlayerCollision() {
		Polygon bulletP = new Polygon(bullet.ship.xpoints, bullet.ship.ypoints, bullet.ship.npoints);
			bulletP.translate(bullet.getX(), bullet.getY());
			Area bulletArea = new Area(bulletP);
			
		Player player = gamePanel.player;
		Polygon playerP = new Polygon(player.ship.xpoints, player.ship.ypoints, player.ship.npoints);
		playerP.translate(player.getX(), player.getY());
		Area shieldArea = new Area(playerP);
		
		shieldArea.intersect(bulletArea);
		if (!shieldArea.isEmpty()){
			stop();
			gamePanel.remove(bullet);
			gamePanel.remove(player);
			gamePanel.repaint(player.getX()-20, player.getY()-20, MAX_SHIP_SIZE*2,MAX_SHIP_SIZE*2);
			gamePanel.bulletsDestroyerTimers.remove(this);
			gamePanel.gameOver();
		}
	}
}
