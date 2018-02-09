package entities;

import java.awt.Polygon;
import javax.swing.JPanel;
import data.Constants;

@SuppressWarnings("serial")
public class Ship extends JPanel implements Constants{
	
	public Ship() {
		setOpaque(false);
		calculateShipSizes();
		setLocation(WINDOW_MID_WIDTH-shipSize/2, getY());
		setSize(shipSize, shipSize);
	}

	/**
	 * Square part of the Ship. Ship represents by 8x8 matrix of shippCells.
	 */
	protected int shipCell = 0;
	/**
	 * Equals 8x8 matrix of shipCells.
	 */
	public int shipSize;
	/**
	 * Polygon represents ship's figure.
	 */
	public Polygon ship = new Polygon();

	public void calculateShipSizes() {
		do {
			shipCell++;
		} while ((shipCell)*8 < MAX_SHIP_SIZE);
		shipSize = shipCell*8;
	}
}
