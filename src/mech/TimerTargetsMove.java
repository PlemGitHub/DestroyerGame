package mech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import data.Constants;
import entities.ships.Target;

@SuppressWarnings("serial")
public class TimerTargetsMove extends Timer implements ActionListener, Constants{
	
	private Target[][] targets;
	private int leftX, rightX;
	private int dX;
	private int randomK;

	public TimerTargetsMove(int delay, ActionListener listener, Target[][] targets) {
		super(delay, listener);
		this.targets = targets;
		randomK = (Math.random()>0.5)? 1: -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		defineLeftAndRightXX();
		if (rightX + dX > RIGHT_SIDE
			|| leftX + dX < LEFT_SIDE){
			randomK = -randomK;
			dX = -dX;
		}
		moveAll();
	}

	public void defineLeftAndRightXX() {
		defineLeftX();
		defineRightX();
	}

	private void defineRightX() {
		for (int i = 0; i < TARGETS_HORIZONTALLY; i++) {
			for (int j = 0; j < TARGETS_VERTICALLY; j++) {
				if (targets[i][j] != null){
					dX = randomK * targets[i][j].getHeight()/2;
					leftX = targets[i][j].getX();
					return;
				}
			}
		}
	}

	private void defineLeftX() {
		for (int i = TARGETS_HORIZONTALLY-1; i >= 0 ; i--) {
			for (int j = 0; j < TARGETS_VERTICALLY; j++) {
				if (targets[i][j] != null){
					rightX = targets[i][j].getX()+targets[i][j].getHeight();
					return;
				}
			}
		}
	}

	private void moveAll() {
		for (int i = 0; i < TARGETS_HORIZONTALLY; i++) {
			for (int j = 0; j < TARGETS_VERTICALLY; j++) {
				if (targets[i][j] != null){
					int x = targets[i][j].getX();
					targets[i][j].setLocation(x+dX, targets[i][j].getY());
				}
			}
		}
	}
}
