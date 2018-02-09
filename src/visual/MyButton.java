package visual;

import javax.swing.JButton;

import data.Constants;

@SuppressWarnings("serial")
public class MyButton extends JButton implements Constants{

	public MyButton(String str, int y) {
		setText(str);
		setBounds(BUTTON_X, y, BUTTON_WIDTH, BUTTON_HEIGHT);
		setFocusable(false);
	}
}
