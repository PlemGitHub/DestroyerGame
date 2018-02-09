package mech;

import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerBeforeOkMessage extends Timer implements ActionListener{
	private AWTEventListener myListener;

	public TimerBeforeOkMessage(int delay, ActionListener listener, AWTEventListener myListener) {
		super(delay, listener);
		this.myListener = myListener;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    Toolkit.getDefaultToolkit().removeAWTEventListener(myListener);
	}
	
}
