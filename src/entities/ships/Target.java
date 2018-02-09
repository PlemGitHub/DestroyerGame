package entities.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import data.Constants;
import entities.Ship;

@SuppressWarnings("serial")
public class Target extends Ship implements Constants{
	
	public Target(Point p) {
		generateTargetPolygon();
		setLocation(p.x, p.y);
	}
	
	private void generateTargetPolygon() {
		ship.addPoint((shipCell*3), (shipCell*0));
		ship.addPoint((shipCell*5), (shipCell*0));
		ship.addPoint((shipCell*8), (shipCell*2));
		ship.addPoint((shipCell*8), (shipCell*6));
		ship.addPoint((shipCell*5), (shipCell*8));
		ship.addPoint((shipCell*3), (shipCell*8));
		ship.addPoint((shipCell*0), (shipCell*6));
		ship.addPoint((shipCell*0), (shipCell*2));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// figure
		g.fillPolygon(ship);
		g.setColor(Color.WHITE);
		// eyes and mouth
		g.fillRect(shipCell*2, shipCell*2, shipCell, shipCell*4);
		g.fillRect(shipCell*5, shipCell*2, shipCell, shipCell*4);
		g.fillRect(shipCell*1, shipCell*3, shipCell*6, shipCell*2);
	}
}
