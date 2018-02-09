package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import visual.panels.MenuPanel;

public class FileWork {
	private File file;
	
	public FileWork() {
		defineSaveFilePath();
	}

	private void defineSaveFilePath() {
		String tmp = System.getenv("APPDATA");
		File plemFolder = new File(tmp+"/PlemCo");
		if (!plemFolder.exists())
			plemFolder.mkdir();
		File gameFolder = new File(plemFolder+"/Destroyer");
		if (!gameFolder.exists())
			gameFolder.mkdir();
		file = new File(gameFolder+"/save.txt");
	}

	public boolean saveFileExists(){
		if (file.exists())
			return true;
		else
			return false;
	}
	
	public int loadLevelNumber() throws IOException{
		String levelString;
		BufferedReader fileIn = new BufferedReader(new FileReader(file));
		do {
			levelString = fileIn.readLine();
		} while (!levelString.equals("level"));
		levelString = fileIn.readLine();
		fileIn.close();
		return Integer.valueOf(levelString);
	}

		public int loadScore() throws IOException {
			String scoreStr;
			BufferedReader fileIn = new BufferedReader(new FileReader(file));
			do {
				scoreStr = fileIn.readLine();
			} while (!scoreStr.equals("score"));
			scoreStr = fileIn.readLine();
			fileIn.close();
			return Integer.valueOf(scoreStr);
		}

	public void deleteSaveFile(MenuPanel menuPanel) {
		menuPanel.loadGameBtn.setEnabled(false);
		menuPanel.deleteGameBtn.setEnabled(false);
		file.delete();
	}

	public void saveGame(String level, String score) throws IOException {
		file.createNewFile();
		BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
			fileOut.write("level"+System.lineSeparator());
			fileOut.write(level+System.lineSeparator());
			fileOut.write("score"+System.lineSeparator());
			fileOut.write(score);
		fileOut.close();
	}
}
