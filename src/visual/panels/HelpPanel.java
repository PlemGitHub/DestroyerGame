package visual.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import data.Constants;
import visual.MainPanel;
import visual.MyButton;
import visual.MyLabel;

@SuppressWarnings("serial")
public class HelpPanel extends JPanel implements Constants, ActionListener{
	private MainPanel mainPanel;
	private final String returnToMainMenuStr = "returnToMainMenu";
	private JButton backBtn;
	private MyLabel helpLbl;
	
	public HelpPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		setBackground(Color.WHITE);
		setLayout(null);
		createLabel();
		createBackButton();
		createKeyBindings();
//		хэлпа:
	//		задача уровня
	//		управление
	//		горячие клавиши меню
	//		горячие клавиши хэлпы
	//		начисление очков
	}

	private void createLabel() {
		String str = ("<html>A, D - move left/right <br>"+
						"Space - shoot <br>"+
						"Destroy all targets to complete levels. <br>"+
						"Avoid Destroyer's bullets!</html>");
		helpLbl = new MyLabel(str);
		helpLbl.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT/2);
		add(helpLbl);
	}

	private void createBackButton() {
		backBtn = new MyButton("BACK", BUTTON_Y4);
		backBtn.addActionListener(this);
		add(backBtn);
	}

	private void createKeyBindings() {
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("H"), returnToMainMenuStr);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("B"), returnToMainMenuStr);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), returnToMainMenuStr);
		getActionMap().put(returnToMainMenuStr, returnToMainMenuAction);
	}
	
	private Action returnToMainMenuAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainPanel.enableMenuPanel();
		}
	};

	@Override
	public void actionPerformed(ActionEvent e) {
		returnToMainMenuAction.actionPerformed(null);
	}
}
