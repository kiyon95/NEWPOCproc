package org.pocproc.app;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class JNumcField extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2704671036679719273L;
	final static String badchars = "`06789~!@#$%^&*()_+=\\|\"':;?/>.<,- ";

	public void processKeyEvent(KeyEvent ev) {
		char c = ev.getKeyChar();
		if ((Character.isLetter(c) && !ev.isAltDown())
				|| badchars.indexOf(c) > -1) {
			ev.consume();
			return;
		}
		super.processKeyEvent(ev);
	}

	JNumcField() {
		super();
	}

	JNumcField(int i) {
		super(i);
	}
}