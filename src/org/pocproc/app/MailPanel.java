package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MailPanel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235151091415943387L;

	public static MailPanel instance;

	private JTextField server;
	private JTextField from;
	private JTextField user;
	private JPasswordField password;
	private JTextField port;
	private JCheckBox ssl;
	private JCheckBox auth;
	private JCheckBox enabled;

	public void getSettings() {
		AppWindow.log(Messages.getString("MailPanel.0")); //$NON-NLS-1$

		server.setText(Mail.instance.getServer());
		port.setText(Integer.toString(Mail.instance.getPort()));
		password.setText(Mail.instance.getPassword());
		from.setText(Mail.instance.getFrom());
		user.setText(Mail.instance.getUser());
		ssl.setSelected(Mail.instance.isSSL());
		auth.setSelected(Mail.instance.isAUTH());
		enabled.setSelected(Mail.instance.isEnabled());

	}

	public void setSettings() {
		AppWindow.log(Messages.getString("MailPanel.1")); //$NON-NLS-1$

		Mail.instance.setServer(server.getText());
		Mail.instance.setPort(Integer.decode(port.getText()));
		Mail.instance.setPassword(String.valueOf(password.getPassword()));
		Mail.instance.setFrom(from.getText());
		Mail.instance.setUser(user.getText());
		Mail.instance.setSSL(ssl.isSelected());
		Mail.instance.setAUTH(auth.isSelected());
		Mail.instance.setEnabled(enabled.isSelected());

	}

	public MailPanel(boolean b) {
		super(b);
		instance = this;

		setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();
		innerEditPane.setLayout(new BoxLayout(innerEditPane,
				BoxLayout.PAGE_AXIS));
		innerEditPane.setMinimumSize(new Dimension(200, 500));
		innerEditPane.setPreferredSize(new Dimension(200, 500));

		server = new JTextField(50);
		// host.setDocument(new JTextFieldLimit(7));
		server.setPreferredSize(new Dimension(500, 24));
		server.setMaximumSize(new Dimension(500, 24));
		port = new JTextField(50);
		port.setPreferredSize(new Dimension(500, 24));
		port.setMaximumSize(new Dimension(500, 24));
		password = new JPasswordField(50);
		password.setPreferredSize(new Dimension(500, 24));
		password.setMaximumSize(new Dimension(500, 24));
		from = new JTextField(50);
		from.setPreferredSize(new Dimension(500, 24));
		from.setMaximumSize(new Dimension(500, 24));
		user = new JTextField(50);
		user.setPreferredSize(new Dimension(500, 24));
		user.setMaximumSize(new Dimension(500, 24));
		ssl = new JCheckBox(Messages.getString("MailPanel.2")); //$NON-NLS-1$
		auth = new JCheckBox(Messages.getString("MailPanel.3")); //$NON-NLS-1$
		enabled = new JCheckBox(Messages.getString("MailPanel.4")); //$NON-NLS-1$

		innerEditPane.add(enabled);
		innerEditPane.add(new JLabel(Messages.getString("MailPanel.5"))); //$NON-NLS-1$
		innerEditPane.add(server);
		innerEditPane.add(new JLabel(Messages.getString("MailPanel.6"))); //$NON-NLS-1$
		innerEditPane.add(port);
		innerEditPane.add(new JLabel(Messages.getString("MailPanel.7"))); //$NON-NLS-1$
		innerEditPane.add(user);
		innerEditPane.add(new JLabel(Messages.getString("MailPanel.8"))); //$NON-NLS-1$
		innerEditPane.add(password);
		innerEditPane.add(new JLabel(Messages.getString("MailPanel.9"))); //$NON-NLS-1$
		innerEditPane.add(from);
		// innerEditPane.add(new JLabel("Use SSL"));
		innerEditPane.add(ssl);
		// innerEditPane.add(new JLabel("Use Authentication"));
		innerEditPane.add(auth);

		add(innerEditPane, BorderLayout.CENTER);
		add(new WriteSettingsButton(), BorderLayout.PAGE_END);

		getSettings();
	}

}
