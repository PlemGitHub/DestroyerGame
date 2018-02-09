package visual;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import visual.panels.GamePanel;
import visual.panels.MenuPanel;

public class Screen implements WindowListener {
	
	private JFrame fr;
	private MainPanel mainPanel;
	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	
	public Screen() {
		
		mainPanel = new MainPanel(this);
			menuPanel = mainPanel.menuPanel;
			gamePanel = mainPanel.gamePanel;
		
		fr = new JFrame("Destroyer");
		fr.setContentPane(mainPanel);
		fr.setExtendedState(Frame.MAXIMIZED_BOTH);
		fr.setVisible(true);
		fr.addWindowListener(this);
		fr.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Screen scr = new Screen();
	}

	@Override
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		if (mainPanel.gamePanel.isVisible())
			mainPanel.gamePanel.stopAllMovements();
		
		int a = JOptionPane.showConfirmDialog(mainPanel, "Quit from game?",
			"Warning", JOptionPane.YES_NO_OPTION);
		if (a == JOptionPane.YES_NO_OPTION){
			if (mainPanel.gamePanel.isVisible())
				try {
					menuPanel.fileWork.saveGame(gamePanel.getLevelInString(), gamePanel.getScoreOverallInString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			System.exit(0);
		}
		if (mainPanel.gamePanel.isVisible())
			mainPanel.gamePanel.startAllMovements();
	}
}