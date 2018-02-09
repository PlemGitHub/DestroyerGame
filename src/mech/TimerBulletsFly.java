package mech;

import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import javax.swing.Timer;
import data.Constants;
import entities.ships.Bullet;
import entities.ships.Destroyer;
import entities.ships.Shield;
import entities.ships.Target;
import visual.panels.GamePanel;

@SuppressWarnings("serial")
public class TimerBulletsFly extends Timer implements Constants, ActionListener{
	GamePanel gamePanel;
	private Bullet bullet;
	private int kDY;

	public TimerBulletsFly(int delay, ActionListener listener, int kDY, Bullet bullet, GamePanel gamePanel) {
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
		checkDestroyerCollision();
		if (bullet.getY() < DESTROYER_Y-bullet.getHeight()){
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
						gamePanel.setScoreLevel(gamePanel.getScoreLevel()+SCORE_TARGET);
						stop();
						gamePanel.remove(bullet);
						gamePanel.remove(target);
						gamePanel.repaint(target.getX(), target.getY(), target.getWidth(), target.getHeight()+20);
						gamePanel.targets[i][j] = null;
						gamePanel.bulletsTimers.remove(this);
						gamePanel.decreaseTargetsNumber();
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
						gamePanel.setScoreLevel(gamePanel.getScoreLevel()+SCORE_SHIELD);
						stop();
						gamePanel.remove(bullet);
						gamePanel.remove(shield);
						gamePanel.repaint(shield.getX()-20, shield.getY()-20, MAX_SHIP_SIZE*2,MAX_SHIP_SIZE*2);
						gamePanel.shields[i][j] = null;
						gamePanel.bulletsTimers.remove(this);
						gamePanel.increaseShieldsDestroyed();
						return;
					}
					
				}
			}
		}
	}

	private void checkDestroyerCollision() {
		Polygon bulletP = new Polygon(bullet.ship.xpoints, bullet.ship.ypoints, bullet.ship.npoints);
			bulletP.translate(bullet.getX(), bullet.getY());
			Area bulletArea = new Area(bulletP);
	
		Destroyer destroyer = gamePanel.destroyer;
		Polygon destroyerP = new Polygon(destroyer.ship.xpoints, destroyer.ship.ypoints, destroyer.ship.npoints);
			destroyerP.translate(destroyer.getX(), destroyer.getY());
			Area destroyerArea = new Area(destroyerP);
			
		destroyerArea.intersect(bulletArea);
		if (!destroyerArea.isEmpty()){
			gamePanel.setScoreLevel(gamePanel.getScoreLevel()+SCORE_DESTROYER);
			stop();
			gamePanel.remove(bullet);
			gamePanel.repaint(bullet.getX()-20, bullet.getY()-20, MAX_SHIP_SIZE*2,MAX_SHIP_SIZE*2);
			gamePanel.bulletsTimers.remove(this);
			gamePanel.increaseDestroyerHits();
			return;
		}
	}
}
