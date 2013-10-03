package org.pocproc.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.pocproc.data.LoopManager;
import org.pocproc.data.PersonManager;
import org.pocproc.data.Subrics;

public class WriteSettingsButton extends JButton {

	private static final long serialVersionUID = 2799177154046686148L;

	public WriteSettingsButton() {
		super("Activate Configuration");

		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppWindow.log("Save button pressed");
				LoopManager.save();
				PersonManager.save();
				Mail.save();
				Subrics.save();
				POC32.save();
				pushover.save();
				int answer = JOptionPane.showConfirmDialog(null, Messages.getString("WriteSettingsButton.2"), Messages.getString("WriteSettingsButton.3"), JOptionPane.YES_NO_OPTION,  JOptionPane.QUESTION_MESSAGE, null);
				AppWindow.log("chosen: " + answer);
				if (answer == 0) POCproc.restart();
			}
		});

	}

}
