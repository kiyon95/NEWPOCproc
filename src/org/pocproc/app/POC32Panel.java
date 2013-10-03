package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class POC32Panel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235151091415943387L;

	public static POC32Panel instance;

	private JTextField host;
	private JTextField port;
	private JPasswordField password;
	private JCheckBox enabled;

	public void getSettings() {
		AppWindow.log(Messages.getString("POC32Panel.0")); //$NON-NLS-1$

		host.setText(POC32.instance.getHost());
		port.setText(Integer.toString(POC32.instance.getPort()));
		password.setText(POC32.instance.getPassword());
		enabled.setSelected(POC32.instance.isActive());

	}

	public void setSettings() {
		AppWindow.log(Messages.getString("POC32Panel.1")); //$NON-NLS-1$

		POC32.instance.setHost(host.getText());
		POC32.instance.setPort(Integer.decode(port.getText()));
		POC32.instance.setPassword(String.valueOf(password.getPassword()));
		POC32.instance.setActive(enabled.isSelected());

	}

	public POC32Panel(boolean b) {
		super(b);
		instance = this;

		setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();
		innerEditPane.setLayout(new BoxLayout(innerEditPane,
				BoxLayout.PAGE_AXIS));
		innerEditPane.setMinimumSize(new Dimension(200, 500));
		innerEditPane.setPreferredSize(new Dimension(200, 500));

		host = new JTextField(50);
		// host.setDocument(new JTextFieldLimit(7));
		host.setPreferredSize(new Dimension(500, 24));
		host.setMaximumSize(new Dimension(500, 24));
		port = new JTextField(50);
		port.setPreferredSize(new Dimension(500, 24));
		port.setMaximumSize(new Dimension(500, 24));
		password = new JPasswordField(50);
		password.setPreferredSize(new Dimension(500, 24));
		password.setMaximumSize(new Dimension(500, 24));
		enabled = new JCheckBox(Messages.getString("POC32Panel.2")); //$NON-NLS-1$

		innerEditPane.add(enabled);

		innerEditPane.add(new JLabel(Messages.getString("POC32Panel.3"))); //$NON-NLS-1$
		innerEditPane.add(host);
		innerEditPane.add(new JLabel(Messages.getString("POC32Panel.4"))); //$NON-NLS-1$
		innerEditPane.add(port);
		innerEditPane.add(new JLabel(Messages.getString("POC32Panel.5"))); //$NON-NLS-1$
		innerEditPane.add(password);

		add(innerEditPane, BorderLayout.CENTER);
		add(new WriteSettingsButton(), BorderLayout.PAGE_END);

		getSettings();
	}

}
