package entities.ships;

import java.awt.Graphics;
import java.awt.Point;
import data.Constants;
import entities.Ship;

@SuppressWarnings("serial")
public class Shield extends Ship implements Constants{
	private final int K_DIV = 2;
	
	public Shield(Point p) {
		setBounds(p.x, p.y, getWidth(), getHeight()/K_DIV);
		generatePlayerPolygon();
	}
	
	private void generatePlayerPolygon() {
		ship.addPoint(0, 0);
		ship.addPoint(getWidth(), 0);
		ship.addPoint(getWidth(), getHeight());
		ship.addPoint(0, getHeight());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.fillPolygon(ship);
	}
}
