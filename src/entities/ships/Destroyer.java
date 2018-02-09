package entities.ships;

import java.awt.Color;
import java.awt.Graphics;

import entities.Ship;

@SuppressWarnings("serial")
public class Destroyer extends Ship{
	public Destroyer() {
		generateDestroyerPolygon();
		setLocation(getX(), DESTROYER_Y);
	}
	
	private void generateDestroyerPolygon() {
		ship.addPoint((shipCell*2), (shipCell*0));
		ship.addPoint((shipCell*6), (shipCell*0));
		ship.addPoint((shipCell*8), (shipCell*2));
		ship.addPoint((shipCell*8), (shipCell*5));
		ship.addPoint((shipCell*5), (shipCell*8));
		ship.addPoint((shipCell*3), (shipCell*8));
		ship.addPoint((shipCell*0), (shipCell*5));
		ship.addPoint((shipCell*0), (shipCell*2));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// figure
		g.fillPolygon(ship);
		g.setColor(Color.WHITE);
		// eyes and mouth
		g.fillRect(shipCell*2, shipCell*3, shipCell, shipCell);
		g.fillRect(shipCell*5, shipCell*3, shipCell, shipCell);
		g.fillRect(shipCell*3, shipCell*6, shipCell*2, shipCell);
	}
}
