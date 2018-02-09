package mech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import data.Constants;
import entities.ships.Destroyer;
import entities.ships.Player;
import visual.panels.GamePanel;

@SuppressWarnings("serial")
public class TimerDestroyerLogic extends Timer implements Constants, ActionListener{

	private Player player;
	private Destroyer destroyer;
	private TimerShipMove timerDestroyerMove;
	private TimerBulletsDestroyerFired timerBulletsDestroyerFired;
	
	public TimerDestroyerLogic(int delay, ActionListener listener, GamePanel gamePanel) {
		super(delay, listener);
		this.destroyer = gamePanel.destroyer;
		this.player = gamePanel.player;

		timerBulletsDestroyerFired = new TimerBulletsDestroyerFired(10, null, gamePanel, 1);
		timerBulletsDestroyerFired.addActionListener(timerBulletsDestroyerFired);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!timerBulletsDestroyerFired.isRunning())
				timerBulletsDestroyerFired.start();
		int dX = player.getX() - destroyer.getX();
		int kDX = (dX > 0)? 1:-1;
		if (Math.abs(dX) < player.shipSize/3)
			kDX = 0;
		timerDestroyerMove = new TimerShipMove(destroyer, kDX, DESTROYER_MOVE_DELAY, null);
		timerDestroyerMove.actionPerformed(null);
	}
	
	public void stopDestroyerLogic(){
		stop();
		timerBulletsDestroyerFired.stop();
	}
}
