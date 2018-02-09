package mech;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import visual.panels.MenuPanel;

public class MenuButtons implements ActionListener {
	MenuPanel menuPanel;
	
	public MenuButtons(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonClicked = (JButton) e.getSource();
		
		if (buttonClicked.equals(menuPanel.newGameBtn))
			newGameAction.actionPerformed(null);
		
		if (e.getSource().equals(menuPanel.loadGameBtn))
			loadGameAction.actionPerformed(null);
		
		if (e.getSource().equals(menuPanel.deleteGameBtn))
			deleteGameAction.actionPerformed(null);
		
		if (e.getSource().equals(menuPanel.helpBtn))
			helpAction.actionPerformed(null);
	}
	
	@SuppressWarnings("serial")
	public Action newGameAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int a = JOptionPane.YES_OPTION;
			// if save exists - warn about save rewriting
			if (menuPanel.loadGameBtn.isEnabled()){
				a = JOptionPane.showConfirmDialog(menuPanel, "Saved game will be lost!", 
						"Warning", JOptionPane.YES_NO_OPTION);
			}
			if (a == JOptionPane.YES_OPTION){
				menuPanel.fileWork.deleteSaveFile(menuPanel);
				menuPanel.mainPanel.enableGamePanel(1, 0); // 396
			}
		}
	};
	
	@SuppressWarnings("serial")
	public Action loadGameAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int loadLevelNumber = 0;
			int loadScore = 0;
			try {
				loadLevelNumber = menuPanel.fileWork.loadLevelNumber();
				loadScore = menuPanel.fileWork.loadScore();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			menuPanel.mainPanel.enableGamePanel(loadLevelNumber, loadScore);
		}
	};	
	
	@SuppressWarnings("serial")
	public Action deleteGameAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int a = JOptionPane.showConfirmDialog(menuPanel, "Delete saved game?", 
					"Warning", JOptionPane.YES_NO_OPTION);
			if (a == JOptionPane.YES_OPTION){
				menuPanel.fileWork.deleteSaveFile(menuPanel);
			}
		}
	};
	
	@SuppressWarnings("serial")
	public Action helpAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			menuPanel.mainPanel.enableHelpPanel();
		}
	};
}
