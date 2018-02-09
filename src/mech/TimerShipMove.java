package mech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import data.Constants;
import entities.Ship;
import entities.ships.Destroyer;
import entities.ships.Player;

@SuppressWarnings("serial")
public class TimerShipMove extends Timer implements Constants, ActionListener{
	private Ship ship;
	public int kDX;
	private int y;

	public TimerShipMove(Ship ship, int kDX, int delay, ActionListener listener) {
		super(delay, listener);
		this.ship = ship;
		this.kDX = kDX;
		if (ship instanceof Player)
			y = PLAYER_Y;;
		if (ship instanceof Destroyer)
			y = DESTROYER_Y;
		actionPerformed(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int newX = ship.getX();
		int limit = (kDX == 1)? RIGHT_SIDE-ship.getWidth():LEFT_SIDE;
		switch (kDX) {
			case 1: 
				newX = (newX+MOVING_DX*kDX > limit)? limit: newX+MOVING_DX*kDX;
				break;
			case -1:
				newX = (newX+MOVING_DX*kDX < limit)? limit: newX+MOVING_DX*kDX;
				break;
		}
		ship.setLocation(newX, y);
	}

}
