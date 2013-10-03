package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.pocproc.data.Subrics;

public class SubricPanel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235151091415943387L;

	public static SubricPanel instance;

	private JTextField a;
	private JTextField b;
	private JTextField c;
	private JTextField d;

	private JNumcField prio_A;
	private JNumcField prio_B;
	private JNumcField prio_C;
	private JNumcField prio_D;

	public static void getSettings() {
		AppWindow.log(Messages.getString(Messages.getString("SubricPanel.1"))); //$NON-NLS-1$
		//$NON-NLS-1$

		instance.a.setText(Subrics.getDefaultType("A")); //$NON-NLS-1$
		instance.b.setText(Subrics.getDefaultType("B")); //$NON-NLS-1$
		instance.c.setText(Subrics.getDefaultType("C")); //$NON-NLS-1$
		instance.d.setText(Subrics.getDefaultType("D")); //$NON-NLS-1$

		if (Subrics.getDefaultPrio("A") != null) //$NON-NLS-1$
			instance.prio_A.setText(Subrics.getDefaultPrio("A").toString()); //$NON-NLS-1$

		if (Subrics.getDefaultPrio("B") != null) //$NON-NLS-1$
			instance.prio_B.setText(Subrics.getDefaultPrio("B").toString()); //$NON-NLS-1$

		if (Subrics.getDefaultPrio("C") != null) //$NON-NLS-1$
			instance.prio_C.setText(Subrics.getDefaultPrio("C").toString()); //$NON-NLS-1$

		if (Subrics.getDefaultPrio("D") != null) //$NON-NLS-1$
			instance.prio_D.setText(Subrics.getDefaultPrio("D").toString()); //$NON-NLS-1$

	}

	public static void setSettings() {
		AppWindow.log(Messages.getString("SubricPanel.5")); //$NON-NLS-1$

		Subrics.put("A", instance.a.getText()); //$NON-NLS-1$
		Subrics.put("B", instance.b.getText()); //$NON-NLS-1$
		Subrics.put("C", instance.c.getText()); //$NON-NLS-1$
		Subrics.put("D", instance.d.getText()); //$NON-NLS-1$

		if (!(instance.prio_A.getText().equals(""))) { //$NON-NLS-1$
			Subrics.put_prio("A", Integer.parseInt(instance.prio_A.getText())); //$NON-NLS-1$
		} else
			Subrics.del_prio("A"); //$NON-NLS-1$

		if (!(instance.prio_B.getText().equals(""))) { //$NON-NLS-1$
			Subrics.put_prio("B", Integer.parseInt(instance.prio_B.getText())); //$NON-NLS-1$
		} else
			Subrics.del_prio("B"); //$NON-NLS-1$

		if (!(instance.prio_C.getText().equals(""))) { //$NON-NLS-1$
			Subrics.put_prio("C", Integer.parseInt(instance.prio_C.getText())); //$NON-NLS-1$
		} else
			Subrics.del_prio("C"); //$NON-NLS-1$

		if (!(instance.prio_D.getText().equals(""))) { //$NON-NLS-1$
			Subrics.put_prio("D", Integer.parseInt(instance.prio_D.getText())); //$NON-NLS-1$
		} else
			Subrics.del_prio("D"); //$NON-NLS-1$

	}

	public SubricPanel(boolean b) {
		super(b);
		instance = this;

		setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();
		// innerEditPane.setLayout(new BoxLayout(innerEditPane,
		// BoxLayout.PAGE_AXIS));
		// innerEditPane.setMinimumSize(new Dimension(200, 200));
		// innerEditPane.setPreferredSize(new Dimension(200, 200));

		a = new JTextField(50);
		// host.setDocument(new JTextFieldLimit(7));
		a.setPreferredSize(new Dimension(500, 24));
		a.setMaximumSize(new Dimension(500, 24));
		this.b = new JTextField(50);
		this.b.setPreferredSize(new Dimension(500, 24));
		this.b.setMaximumSize(new Dimension(500, 24));
		c = new JTextField(50);
		c.setPreferredSize(new Dimension(500, 24));
		c.setMaximumSize(new Dimension(500, 24));
		d = new JTextField(50);
		d.setPreferredSize(new Dimension(500, 24));
		d.setMaximumSize(new Dimension(500, 24));

		//		innerEditPane.add(new JLabel("A")); //$NON-NLS-1$
		//		innerEditPane.add(new JLabel("B")); //$NON-NLS-1$
		//		innerEditPane.add(new JLabel("C")); //$NON-NLS-1$
		//		innerEditPane.add(new JLabel("D")); //$NON-NLS-1$

		prio_A = new JNumcField(1);
		prio_A.setDocument(new JTextFieldLimit(1));
		prio_B = new JNumcField(1);
		prio_B.setDocument(new JTextFieldLimit(1));
		prio_C = new JNumcField(1);
		prio_C.setDocument(new JTextFieldLimit(1));
		prio_D = new JNumcField(1);
		prio_D.setDocument(new JTextFieldLimit(1));

		GridBagLayout gridbag2 = new GridBagLayout();
		// GridBagConstraints c2 = new GridBagConstraints();

		innerEditPane.setLayout(gridbag2);

		innerEditPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(Messages
						.getString("SubricPanel.23")), //$NON-NLS-1$
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JComponent[] col1 = {
				new JLabel(Messages.getString("SubricPanel.24")), new JLabel("A"), //$NON-NLS-1$ //$NON-NLS-2$
				new JLabel("B"), new JLabel("C"), new JLabel("D") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		JComponent[] col2 = {
				new JLabel(Messages.getString("SubricPanel.2")), prio_A, prio_B, prio_C, //$NON-NLS-1$
				prio_D };
		JComponent[] col3 = {
				new JLabel(Messages.getString("SubricPanel.30")), a, this.b, c, d }; //$NON-NLS-1$

		addLabelTextRows3(col1, 50, col2, 100, col3, gridbag2, innerEditPane);

		JPanel innerEditPane2 = new POCprocPanel();
		innerEditPane2.setLayout(new BoxLayout(innerEditPane2,
				BoxLayout.PAGE_AXIS));

		innerEditPane2.add(innerEditPane);
		innerEditPane2.add(Box.createRigidArea(new Dimension(20, 20)));
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.31"))); //$NON-NLS-1$
		innerEditPane2.add(Box.createRigidArea(new Dimension(20, 20)));
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.32"))); //$NON-NLS-1$
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.33"))); //$NON-NLS-1$
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.34"))); //$NON-NLS-1$
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.35"))); //$NON-NLS-1$
		innerEditPane2.add(new JLabel(Messages.getString("SubricPanel.36"))); //$NON-NLS-1$
		innerEditPane2.add(Box.createVerticalStrut(500));

		add(innerEditPane2, BorderLayout.CENTER);
		add(new WriteSettingsButton(), BorderLayout.PAGE_END);

		getSettings();
	}

}
