package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class pushoverPanel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235151091415943387L;

	public static pushoverPanel instance;

	private JCheckBox enabled;
	private JTextField appkey;
	private JTextField event;
	private JTextField description;

	public void getSettings() {
		AppWindow.log(Messages.getString("pushoverPanel.0")); //$NON-NLS-1$

		enabled.setSelected(pushover.instance.isEnabled());
		appkey.setText(pushover.instance.getAppKey());
		event.setText(pushover.instance.getEvent());
		description.setText(pushover.instance.getDescription());

	}

	public void setSettings() {
		AppWindow.log(Messages.getString("pushoverPanel.1")); //$NON-NLS-1$

		pushover.instance.setEnabled(enabled.isSelected());
		pushover.instance.setAppKey(appkey.getText());
		pushover.instance.setEvent(event.getText());
		pushover.instance.setDescription(description.getText());

	}

	public pushoverPanel(boolean b) {
		super(b);
		instance = this;

		setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();
		innerEditPane.setLayout(new BoxLayout(innerEditPane,
				BoxLayout.PAGE_AXIS));
		innerEditPane.setMinimumSize(new Dimension(200, 500));
		innerEditPane.setPreferredSize(new Dimension(200, 500));

		enabled = new JCheckBox(Messages.getString("pushoverPanel.2")); //$NON-NLS-1$
		appkey = new JTextField(50);
		appkey.setPreferredSize(new Dimension(520, 24));
		appkey.setMaximumSize(new Dimension(520, 24));

		event = new JTextField(50);
		// host.setDocument(new JTextFieldLimit(7));
		event.setPreferredSize(new Dimension(500, 24));
		event.setMaximumSize(new Dimension(500, 24));

		description = new JTextField(50);
		// host.setDocument(new JTextFieldLimit(7));
		description.setPreferredSize(new Dimension(500, 24));
		description.setMaximumSize(new Dimension(500, 24));

		innerEditPane.add(enabled);
		innerEditPane.add(appkey);
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.10"))); //$NON-NLS-1$

		innerEditPane.add(Box.createVerticalStrut(40));
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.11"))); //$NON-NLS-1$
		innerEditPane.add(Box.createVerticalStrut(20));

		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.12"))); //$NON-NLS-1$
		innerEditPane.add(event);
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.13"))); //$NON-NLS-1$
		innerEditPane.add(description);
		innerEditPane.add(Box.createVerticalStrut(20));
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.14"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.15"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.16"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.17"))); //$NON-NLS-1$

		innerEditPane.add(Box.createVerticalStrut(40));
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.3"))); //$NON-NLS-1$

		innerEditPane.add(Box.createVerticalStrut(20));
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.4"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.5"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.6"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.7"))); //$NON-NLS-1$
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.8"))); //$NON-NLS-1$
		innerEditPane.add(Box.createVerticalStrut(20));
		innerEditPane.add(new JLabel(Messages.getString("pushoverPanel.9"))); //$NON-NLS-1$

		add(innerEditPane, BorderLayout.CENTER);
		add(new WriteSettingsButton(), BorderLayout.PAGE_END);

		getSettings();
	}

}
