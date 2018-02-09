package entities.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import data.Constants;
import entities.Ship;

@SuppressWarnings("serial")
public class Bullet extends Ship implements Constants{
	private final int K_DIV = 2;
	private Color color;
	
	public Bullet(Point p, Color color) {
		setBounds(p.x+getWidth()*3/8, p.y-getHeight()/K_DIV, getWidth()/(K_DIV*4), getHeight()/K_DIV);
		generateBulletPolygon();
		this.color = color;
	}

	private void generateBulletPolygon() {
		ship.addPoint(0, 0);
		ship.addPoint(getWidth(), 0);
		ship.addPoint(getWidth(), getHeight());
		ship.addPoint(0, getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
		g.fillPolygon(ship);
	}
}
