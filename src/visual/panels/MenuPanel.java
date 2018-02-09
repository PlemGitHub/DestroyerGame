package visual.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import data.Constants;
import data.FileWork;
import mech.MenuButtons;
import visual.MainPanel;
import visual.MyButton;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements Constants{
	private MenuButtons menuButtons;
	public FileWork fileWork;
	public MainPanel mainPanel;
	public MyButton newGameBtn;
	public MyButton loadGameBtn;
	public MyButton deleteGameBtn;
	public MyButton helpBtn;
	private final String exitFromGameStr = "exitFromGame";
	private final String newGameStr = "newGame";
	private final String loadGameStr = "loadGame";
	private final String deleteGameStr = "deleteGame";
	private final String helpStr = "help";
	
	public MenuPanel(MainPanel mp) {
		this.mainPanel = mp;
		setBackground(Color.WHITE);
		
		setLayout(null);
		createButtons();
		createFileWork();
		createKeyBindings();
	}

	private void createButtons() {
		newGameBtn = new MyButton("New Game", BUTTON_Y1);
		loadGameBtn = new MyButton("Load Game", BUTTON_Y2);
		deleteGameBtn = new MyButton("Delete Save", BUTTON_Y3);
		helpBtn = new MyButton("HELP", BUTTON_Y4);

		menuButtons = new MenuButtons(this);
		newGameBtn.addActionListener(menuButtons);
		loadGameBtn.addActionListener(menuButtons);
		deleteGameBtn.addActionListener(menuButtons);
		helpBtn.addActionListener(menuButtons);
		
		add(newGameBtn);
		add(loadGameBtn);
		add(deleteGameBtn);
		add(helpBtn);
	}

	private void createFileWork() {
		fileWork = new FileWork();
		checkSaveToEnableButtons();
	}

	private void createKeyBindings() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), exitFromGameStr);
		getActionMap().put(exitFromGameStr, exitFromGameAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("N"), newGameStr);
		getActionMap().put(newGameStr, menuButtons.newGameAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("L"), loadGameStr);
		getActionMap().put(loadGameStr, menuButtons.loadGameAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), deleteGameStr);
		getActionMap().put(deleteGameStr, menuButtons.deleteGameAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"), helpStr);
		getActionMap().put(helpStr, menuButtons.helpAction);
	}
	
	private Action exitFromGameAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainPanel.scr.windowClosing(null);
		}
	};
	
	public void checkSaveToEnableButtons(){
		if (!fileWork.saveFileExists()){
			loadGameBtn.setEnabled(false);
			deleteGameBtn.setEnabled(false);
		}
	}
}
