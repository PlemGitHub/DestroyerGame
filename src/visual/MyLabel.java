package visual;

import javax.swing.JLabel;
import data.Constants;

@SuppressWarnings("serial")
public class MyLabel extends JLabel implements Constants{

	public MyLabel(String str) {
		setText(str);
		setBounds(0, 0, LABEL_WIDTH, LABEL_WIDTH/2);
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		setFont(LABEL_FONT);
	}
}
