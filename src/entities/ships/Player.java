package entities.ships;

import java.awt.Graphics;
import data.Constants;
import entities.Ship;

@SuppressWarnings("serial")
public class Player extends Ship implements Constants{
	
	public Player() {
		generatePlayerPolygon();
		setLocation(getX(), PLAYER_Y);
	}
	
	private void generatePlayerPolygon() {
		ship.addPoint((shipCell*0), (shipCell*5));
		ship.addPoint((shipCell*3), (shipCell*2));
		ship.addPoint((shipCell*3), (shipCell*1));
		ship.addPoint((shipCell*4), (shipCell*0));
		ship.addPoint((shipCell*5), (shipCell*1));
		ship.addPoint((shipCell*5), (shipCell*2));
		ship.addPoint((shipCell*8), (shipCell*5));
		ship.addPoint((shipCell*5), (shipCell*5));
		ship.addPoint((shipCell*5), (shipCell*7));
		ship.addPoint((shipCell*6), (shipCell*7));
		ship.addPoint((shipCell*6), (shipCell*8));
		ship.addPoint((shipCell*2), (shipCell*8));
		ship.addPoint((shipCell*2), (shipCell*7));
		ship.addPoint((shipCell*3), (shipCell*7));
		ship.addPoint((shipCell*3), (shipCell*5));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.fillPolygon(ship);
	}
}
