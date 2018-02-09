package visual;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;
import visual.panels.GamePanel;
import visual.panels.HelpPanel;
import visual.panels.MenuPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{
	private CardLayout cl;
	private HelpPanel helpPanel;
	public Screen scr;
	public GamePanel gamePanel;
	public MenuPanel menuPanel;
	
	final static String GAMEPANEL_NAME = "Game Panel";
	final static String MENUPANEL_NAME = "Menu Panel";
	final static String HELPPANEL_NAME = "Help Panel";
	
	public MainPanel(Screen scr) {
		this.scr = scr;
		cl = new CardLayout();
		setLayout(cl);
		setBackground(Color.WHITE);
		
		menuPanel = new MenuPanel(this);
		gamePanel = new GamePanel(this);
		helpPanel = new HelpPanel(this);
		
		add(gamePanel, GAMEPANEL_NAME);
		add(menuPanel, MENUPANEL_NAME);
		add(helpPanel, HELPPANEL_NAME);
		cl.show(this, MENUPANEL_NAME);
	}

	public void enableGamePanel(int level, int score){
		cl.show(this, GAMEPANEL_NAME);
		gamePanel.createNewLevel(level, score);
	}
		public void enableMenuPanel(){
			cl.show(this, MENUPANEL_NAME);
		}
			public void enableHelpPanel(){
				cl.show(this, HELPPANEL_NAME);
			}
}
