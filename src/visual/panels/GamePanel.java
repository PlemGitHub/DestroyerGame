package visual.panels;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import data.Constants;
import data.FileWork;
import entities.Ship;
import entities.ships.Bullet;
import entities.ships.Destroyer;
import entities.ships.Player;
import entities.ships.Shield;
import entities.ships.Target;
import mech.TimerBeforeOkMessage;
import mech.TimerBulletsDestroyerFly;
import mech.TimerBulletsFired;
import mech.TimerBulletsFly;
import mech.TimerDestroyerLogic;
import mech.TimerForLevel;
import mech.TimerShipMove;
import mech.TimerTargetsMove;
import visual.MainPanel;
import visual.MyLabel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Constants{
	private FileWork fileWork;
	private MainPanel mainPanel;
	private GamePanel gamePanel;
	private Ship ship;
	private TimerShipMove timerPlayerMove;
	
	private MyLabel timeLevelLbl;
	private TimerForLevel timerForLevel;
	private MyLabel levelNumberLbl;
	
	private MyLabel scoreLevelLbl;
	private MyLabel scoreOverallLbl;

	private int level;
	private int scoreOverall;
	private int scoreLevel;
	
	public Player player;
	public Bullet lastBullet;
	public ArrayList<TimerBulletsFly> bulletsTimers = new ArrayList<>();
	private TimerBulletsFired timerBulletsFired = new TimerBulletsFired(0, null, gamePanel, 0);
	private int currentMovingKdX;
	private int shots;
	private int targetsDestroyed;
	private int shieldsDestroyed;
	private int destroyerHits;
	
	public Destroyer destroyer;
	private TimerDestroyerLogic timerDestroyerLogic;
	public ArrayList<TimerBulletsDestroyerFly> bulletsDestroyerTimers = new ArrayList<>();
	
	private Target target;
	private Shield shield;
		public Target[][] targets = new Target[TARGETS_HORIZONTALLY][TARGETS_VERTICALLY];
		public Shield[][] shields = new Shield[SHIELDS_HORIZONTALLY][SHIELDS_VERTICALLY];
		TimerTargetsMove timerTargetsMove;
	
	private int targetsCount;
	
	private final String startMoveLeftStr = "startMoveLeft";
	private final String startMoveRightStr = "startMoveRight";
	private final String stopMoveLeftStr = "stopMoveLeft";
	private final String stopMoveRightStr = "stopMoveRight";
	private final String startShootStr = "startShoot";
	private final String stopShootStr = "stopShoot";
	private final String exitFromLevelStr = "exitFromLevel";
	private TimerBeforeOkMessage timerBeforeOkMessage;
	private AWTEventListener myListener;
	
	public GamePanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.fileWork = mainPanel.menuPanel.fileWork;
		setBackground(Color.white);
		setLayout(null);
		ship = new Ship();
			initTargetCoords();
			initShieldCoords();
		createKeyBindings();
		gamePanel = this;
	}

	private void createPlayer() {
		for (Component c : getComponents()) {
			if (c instanceof Player)
				remove(c);
		}
		player = new Player();
		add(player);
		shots = 0;
	}

	public void createTargets() {
		for (Component c : getComponents()) {
			if (c instanceof Target)
				remove(c);
		}
		targets = new Target[TARGETS_HORIZONTALLY][TARGETS_VERTICALLY];
		targetsDestroyed = 0;
		
		int k, n;
		Random rnd = new Random();
		for (int i = 0; i < level; i++) {
			do {
				k = rnd.nextInt(TARGETS_HORIZONTALLY);
				n = rnd.nextInt(TARGETS_VERTICALLY);
			} while (targets[k][n] != null);
			target = new Target(TARGETS_COORDS[k][n]);
			targets[k][n] = target;
			add(target);
		}
		timerTargetsMove = new TimerTargetsMove(TARGET_MOVE_DELAY, null, targets);
		timerTargetsMove.addActionListener(timerTargetsMove);
	}
		
	private void createShields() {
		for (Component c : getComponents()) {
			if (c instanceof Shield)
				remove(c);
		}
		shields = new Shield[SHIELDS_HORIZONTALLY][SHIELDS_VERTICALLY];
		shieldsDestroyed = 0;
		
		int k, n;
		Random rnd = new Random();
		int limit = (level > MAXIMUM_SHIELDS)? MAXIMUM_SHIELDS : level;
		for (int i = 0; i < limit; i++) {
			do {
				k = rnd.nextInt(SHIELDS_HORIZONTALLY);
				n = rnd.nextInt(SHIELDS_VERTICALLY);
			} while (shields[k][n] != null);
			shield = new Shield(SHIELDS_COORDS[k][n]);
			shields[k][n] = shield;
			add(shield);
		}
	}

	private void createDestroyer() {
		for (Component c : getComponents()) {
			if (c instanceof Destroyer)
				remove(c);
		}
		destroyerHits = 0;
		
		destroyer = new Destroyer();
		add(destroyer);
		timerDestroyerLogic = new TimerDestroyerLogic(DESTROYER_MOVE_DELAY, null, this);
		timerDestroyerLogic.addActionListener(timerDestroyerLogic);
	}

	private void createKeyBindings() {
		createPlayer();
		timerPlayerMove = new TimerShipMove(player, 0, 0, null);	// to except Null at moveAction()
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), startMoveLeftStr);
			getActionMap().put(startMoveLeftStr, new moveAction(-1, -1));
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released A"), stopMoveLeftStr);
			getActionMap().put(stopMoveLeftStr, new stopMoveAction(-1));
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), startMoveRightStr);
			getActionMap().put(startMoveRightStr, new moveAction(1, 1));
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released D"), stopMoveRightStr);
			getActionMap().put(stopMoveRightStr, new stopMoveAction(1));
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), startShootStr);
			getActionMap().put(startShootStr, startShootAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released SPACE"), stopShootStr);
			getActionMap().put(stopShootStr, stopShootAction);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), exitFromLevelStr);
			getActionMap().put(exitFromLevelStr, exitFromLevelAction);
	}

	public void setLevel(int level){
		this.level = level;
		targetsCount = level;
	}
	
		public String getLevelInString(){
			return Integer.toString(level);
		}
		
	public void decreaseTargetsNumber(){
		targetsCount--;
		targetsDestroyed++;
		if (targetsCount == 0)
			doEndOfLevel();
	}

	private void doEndOfLevel() {
		stopAllMovements();
		int seconds = timerForLevel.getRemainSeconds();
			scoreLevel = scoreLevel*seconds;
		int shotsScore = SCORE_SHOT*shots;
		int targetScore = SCORE_TARGET*targetsDestroyed;
		int shieldScore = SCORE_SHIELD*shieldsDestroyed;
		int destroyerScore = SCORE_DESTROYER*destroyerHits;
		int summary = shotsScore+targetScore+shieldScore+destroyerScore;
		int timeScore = summary*seconds;
		setScoreLevel(summary+timeScore);
		
		disableKeyBoard();			
		showJOptionPane(shotsScore, targetScore, shieldScore, destroyerScore, timeScore, summary, seconds);
		setScoreOverall(getScoreOverall()+getScoreLevel());
		setScoreLevel(0);
		doSaveGame();
		createNewLevel(level+1, scoreOverall);
	}

		private void disableKeyBoard() {
			myListener = new AWTEventListener() {
	
		        @Override
		        public void eventDispatched(AWTEvent event) {
		            if (event instanceof KeyEvent) {
		                ((KeyEvent) event).consume();
		            }
		        }
		    };
		    Toolkit.getDefaultToolkit().addAWTEventListener(myListener, AWTEvent.KEY_EVENT_MASK);
	
			
			timerBeforeOkMessage = new TimerBeforeOkMessage(2000, null, myListener);
			timerBeforeOkMessage.addActionListener(timerBeforeOkMessage);
			timerBeforeOkMessage.start();
		}

		private void showJOptionPane(int shotsScore, int targetScore, int shieldScore, int destroyerScore,
									int timeScore, int summary, int seconds) {
			JOptionPane.showMessageDialog(this,
				"Level #"+level+" completed!"+System.lineSeparator()+
				"Shots dealed: "+shots+"*("+SCORE_SHOT+") = "+shotsScore+" pts."+System.lineSeparator()+
				"Targets destroyed: "+targetsDestroyed+"*"+SCORE_TARGET+" = "+targetScore+" pts."+System.lineSeparator()+
				"Shields destroyed: "+shieldsDestroyed+"*"+SCORE_SHIELD+" = "+shieldScore+" pts."+System.lineSeparator()+
				"Destroyer hitted: "+destroyerHits+"*"+SCORE_DESTROYER+" = "+destroyerScore+" pts."+System.lineSeparator()+
				"Summary: "+shotsScore+"+"+targetScore+"+"+shieldScore+"+"+destroyerScore+" = "+summary+" pts."+System.lineSeparator()+
				"Time multiplicator: "+summary+"*"+seconds+" = "+timeScore+" pts."+System.lineSeparator()+
				"LEVEL SCORE: "+summary+"+"+timeScore+" = "+scoreLevel);
		}
		
		private void doSaveGame() {
			try {
			fileWork.saveGame(Integer.toString(level+1), getScoreOverallInString());
			mainPanel.menuPanel.loadGameBtn.setEnabled(true);
			mainPanel.menuPanel.deleteGameBtn.setEnabled(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	private void initTargetCoords(){
		int x0;
		int dXY = ship.shipSize*2;
		if (TARGETS_HORIZONTALLY %2 == 0){
			int halfTargets = TARGETS_HORIZONTALLY/2;
			x0 = WINDOW_MID_WIDTH - dXY*halfTargets+ship.shipSize/2;
		}
		else{
			int halfTargets = (TARGETS_HORIZONTALLY-1)/2;
			x0 = WINDOW_MID_WIDTH - dXY*halfTargets-ship.shipSize/2;
		}
		
		for (int i = 0; i < TARGETS_HORIZONTALLY; i++) {
			for (int j = 0; j < TARGETS_VERTICALLY; j++) {
				int x = x0 + dXY*i;
				int y = TARGET_TOP_Y + dXY/2*j;
				TARGETS_COORDS[i][j] = new Point(x,y);
			}
		}
	}
	
	private void initShieldCoords(){
		int dXY = ship.shipSize;
		int halfShields = SHIELDS_HORIZONTALLY/2;
		int x0 = WINDOW_MID_WIDTH - dXY*halfShields;
		
		for (int i = 0; i < SHIELDS_HORIZONTALLY; i++) {
			for (int j = 0; j < SHIELDS_VERTICALLY; j++) {
				int x = x0 + dXY*i;
				int y = SHIELD_Y + dXY/2*j;
				SHIELDS_COORDS[i][j] = new Point(x,y);
			}
		}
	}

	public void createNewLevel(int level, int score) {
		if (level > MAXIMUM_LEVEL){
			JOptionPane.showMessageDialog(this, "Congratulations! You've end the Game!"+System.lineSeparator()+
					"Your final score = "+scoreOverall+" pts.");
			mainPanel.enableMenuPanel();
			return;
		}
		setLevel(level);
		createLevelDependedInstances();
		clearBullets();
		clearLabels();
			createTimeLabel();
				createLabels();
				setScoreOverall(score);
		repaint();
		timerDestroyerLogic.start();
		timerTargetsMove.start();
	}

		private void createLevelDependedInstances() {
			createPlayer();
			createTargets();
			createShields();
			createDestroyer();
		}
	
		private void clearBullets() {
			for (Component c : getComponents()) {
				if (c instanceof Bullet)
					remove(c);
			}
			lastBullet = null;
		}
		
		private void clearLabels() {
			for (Component c: getComponents())
				if (c instanceof MyLabel)
					remove(c);
		}
		
		private void createTimeLabel() {
			timeLevelLbl = new MyLabel("");
			add(timeLevelLbl);
			
			timerForLevel = new TimerForLevel(1000, null, level, this);
			timerForLevel.addActionListener(timerForLevel);
			timerForLevel.start();
		}

		private void createLabels() {
			scoreLevelLbl = new MyLabel("0");
			scoreLevelLbl.setLocation(WINDOW_WIDTH-LABEL_WIDTH, 0);
			add(scoreLevelLbl);
			setScoreLevel(scoreLevel);
			
			scoreOverallLbl = new MyLabel(Integer.toString(scoreOverall));
			scoreOverallLbl.setLocation(WINDOW_WIDTH-LABEL_WIDTH, LABEL_WIDTH/2);
			add(scoreOverallLbl);
			setScoreOverall(scoreOverall);
			
			levelNumberLbl = new MyLabel(Integer.toString(level));
			levelNumberLbl.setLocation(0, LABEL_WIDTH/2);
			add(levelNumberLbl);
			levelNumberLbl.setText("Level #"+level);
		}
		
			public void setScoreLevel(int newScore) {
				this.scoreLevel = newScore;
				scoreLevelLbl.setText(Integer.toString(newScore));
			}			
			public int getScoreLevel(){
				return scoreLevel;
			}
			
			public void setScoreOverall(int newScore) {
				this.scoreOverall = newScore;
				scoreOverallLbl.setText(Integer.toString(newScore));
			}
			public int getScoreOverall(){
				return scoreOverall;
			}
			public String getScoreOverallInString(){
				return Integer.toString(scoreOverall);
			}
	
	private class moveAction extends AbstractAction{
		int kDX;
		int newKdX;
		public moveAction(int kDX, int newKdX) {
			this.kDX = kDX;
			this.newKdX = newKdX;		
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!timerPlayerMove.isRunning()
				|| newKdX != currentMovingKdX){
				if (timerPlayerMove != null)
					timerPlayerMove.stop();
				currentMovingKdX = kDX;
				timerPlayerMove = new TimerShipMove(player, kDX, PLAYER_MOVE_DELAY, null);
				timerPlayerMove.addActionListener(timerPlayerMove);
				timerPlayerMove.start();
			}
		}
	}
	
	private class stopMoveAction extends AbstractAction{
		int currentKdX;
		public stopMoveAction(int currentKdX) {
			this.currentKdX = currentKdX;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentKdX == timerPlayerMove.kDX)
				timerPlayerMove.stop();
		}
	}
	
	private Action startShootAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
//			if (myJOptionPane.isVisible()){
////				timerPlayerMove.stop();
//			}
//			else
  			if (lastBullet == null
				|| lastBullet.getY() < SHIELDS_COORDS[0][SHIELDS_VERTICALLY-1].y+MOVING_DX*2)
				if (!timerBulletsFired.isRunning()){
					timerBulletsFired = new TimerBulletsFired(10, null, gamePanel, -1);
					timerBulletsFired.addActionListener(timerBulletsFired);
					timerBulletsFired.start();
				}
		}
	};
	
	private Action stopShootAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			timerBulletsFired.stop();
		}
	};
	
	private Action exitFromLevelAction = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			stopAllMovements();
			int a = JOptionPane.showConfirmDialog(gamePanel, "Quit? No results on this level will be saved.",
												"Warning", JOptionPane.YES_NO_OPTION);
			switch (a) {
				case JOptionPane.YES_OPTION:
					mainPanel.enableMenuPanel();
					break;
				case JOptionPane.NO_OPTION:
				case JOptionPane.CLOSED_OPTION:{
					startAllMovements();
					break;
				}
			}
		}
	};
	
	public void stopAllMovements(){
		timerForLevel.stop();
		timerDestroyerLogic.stopDestroyerLogic();
		timerTargetsMove.stop();
		timerPlayerMove.stop();
		stopShootAction.actionPerformed(null);
		
		for (TimerBulletsFly t : bulletsTimers) {
			t.stop();
		}
		for (TimerBulletsDestroyerFly t : bulletsDestroyerTimers) {
			t.stop();
		}
	}
	
	public void startAllMovements(){
		timerForLevel.start();
		timerDestroyerLogic.start();
		timerTargetsMove.start();
		for (TimerBulletsFly t : bulletsTimers) {
			t.start();
		}
		for (TimerBulletsDestroyerFly t : bulletsDestroyerTimers) {
			t.start();
		}
	}
	
	public void setTextOnTimeLabel(String str){
		timeLevelLbl.setText(str);
	}

	public void gameOver() {
		stopAllMovements();
		String lvl = (level == 1)? "level":"levels";
		disableKeyBoard();
		JOptionPane.showMessageDialog(this, "Game over!"+System.lineSeparator()+
									"Your score is "+scoreOverall+" pts. in "+level+" "+lvl+" "
				+"(average "+scoreOverall/level+" pts./lvl).");
		fileWork.deleteSaveFile(mainPanel.menuPanel);
		mainPanel.enableMenuPanel();
	}
	
	public void increaseShots(){
		shots++;
	}
	
	public void increaseTaragetsDestroyed(){
		targetsDestroyed++;
	}	
	
	public void increaseShieldsDestroyed(){
		shieldsDestroyed++;
	}	
	
	public void increaseDestroyerHits(){
		destroyerHits++;
	}		
}
