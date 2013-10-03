package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.pocproc.data.Loop;
import org.pocproc.data.LoopManager;
import org.pocproc.data.Person;
import org.pocproc.data.PersonManager;

public class LoopPanel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1414092013040586528L;

	private Loop selLoop;

	private JTextField LoopKey;
	private JTextField LoopDesc;

	private JTextField prio_A;
	private JTextField prio_B;
	private JTextField prio_C;
	private JTextField prio_D;

	private JTextField type_A;
	private JTextField type_B;
	private JTextField type_C;
	private JTextField type_D;

	private JTextArea LoopComment;
	private JList Selector;

	public static LoopPanel instance;

	public static void updateEditor(String key, String desc, String comment,
			Integer prio_A, Integer prio_B, Integer prio_C, Integer prio_D,
			String type_A, String type_B, String type_C, String type_D) {
		instance.LoopDesc.setText(desc);
		instance.LoopKey.setText(key);
		instance.LoopComment.setText(comment);
		instance.type_A.setText(type_A);
		instance.type_B.setText(type_B);
		instance.type_C.setText(type_C);
		instance.type_D.setText(type_D);
		if (prio_A != null)
			instance.prio_A.setText(prio_A.toString());
		else
			instance.prio_A.setText(null);
		if (prio_B != null)
			instance.prio_B.setText(prio_B.toString());
		else
			instance.prio_B.setText(null);
		if (prio_C != null)
			instance.prio_C.setText(prio_C.toString());
		else
			instance.prio_C.setText(null);
		if (prio_D != null)
			instance.prio_D.setText(prio_D.toString());
		else
			instance.prio_D.setText(null);

	}

	public JScrollPane scroller;

	public static void updateListbox() {
		Object[] data = LoopManager.getRics();

		if (instance.Selector != null) {
			instance.Selector.setEnabled(false);
			instance.Selector.clearSelection();
			DefaultListModel model = (DefaultListModel) instance.Selector
					.getModel();
			model.clear();

			for (int i = 0; i < data.length; i++) {
				model.addElement((String) data[i] + "/" //$NON-NLS-1$
						+ LoopManager.getRic((String) data[i]).getName());
			}
			instance.Selector.setEnabled(true);

		} else {

			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < data.length; i++) {
				model.addElement((String) data[i] + "/" //$NON-NLS-1$
						+ LoopManager.getRic((String) data[i]).getName());
			}

			instance.Selector = new JList(model);
			instance.Selector
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			instance.Selector
					.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							if (LoopPanel.instance.Selector.isEnabled()) {

								String key = (String) LoopPanel.instance.Selector
										.getSelectedValue();
								key = key.split("/")[0]; //$NON-NLS-1$
								if (key != null) {
									Loop temp = LoopManager.getRic(key);
									if (temp != null) {
										LoopPanel.updateEditor(key,
												temp.getName(),
												temp.getComment(), temp.prio_A,
												temp.prio_B, temp.prio_C,
												temp.prio_D, temp.type_A,
												temp.type_B, temp.type_C,
												temp.type_D);
									}
								}
							}
						}
					});

		}

	}

	public LoopPanel(boolean b) {
		super(b);
		instance = this;

		// setLayout(new GridLayout(1, 2));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		updateListbox();

		scroller = new JScrollPane(Selector);

		scroller.setPreferredSize(new Dimension(200, 500));
		scroller.setMinimumSize(new Dimension(200, 100));
		scroller.setMaximumSize(new Dimension(200, 50000));

		// make your JList as check list
		// CheckListManager checkListManager = new CheckListManager(list);

		add(scroller);

		JPanel editPane = new POCprocPanel();

		editPane.setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();

		LoopKey = new JTextField(7);
		LoopKey.setDocument(new JTextFieldLimit(7));
		LoopDesc = new JTextField(50);

		LoopComment = new JTextArea(50, 10);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		innerEditPane.setLayout(gridbag);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		// innerEditPane.add(actionLabel, c);
		innerEditPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(Messages
						.getString("LoopPanel.0")), //$NON-NLS-1$
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JScrollPane CommentScroller = new JScrollPane(LoopComment);
		CommentScroller.setMinimumSize(new Dimension(40, 200));

		JComponent[] labels = {
				new JLabel(
						Messages.getString(Messages.getString("LoopPanel.1"))), //$NON-NLS-1$
				new JLabel(
						Messages.getString(Messages.getString("LoopPanel.2"))), //$NON-NLS-1$
				new JLabel(
						Messages.getString(Messages.getString("LoopPanel.9"))) }; //$NON-NLS-1$
		JComponent[] textFields = { LoopKey, LoopDesc, CommentScroller };
		addLabelTextRows(labels, textFields, gridbag, innerEditPane);

		JPanel SubricPane = new POCprocPanel();

		prio_A = new JNumcField(1);
		prio_A.setDocument(new JTextFieldLimit(1));
		prio_B = new JNumcField(1);
		prio_B.setDocument(new JTextFieldLimit(1));
		prio_C = new JNumcField(1);
		prio_C.setDocument(new JTextFieldLimit(1));
		prio_D = new JNumcField(1);
		prio_D.setDocument(new JTextFieldLimit(1));

		type_A = new JTextField(50);
		type_B = new JTextField(50);
		type_C = new JTextField(50);
		type_D = new JTextField(50);

		GridBagLayout gridbag2 = new GridBagLayout();
		// GridBagConstraints c2 = new GridBagConstraints();

		SubricPane.setLayout(gridbag2);

		SubricPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(Messages.getString("LoopPanel.14")), //$NON-NLS-1$
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JComponent[] col1 = {
				new JLabel(Messages.getString("LoopPanel.15")), new JLabel("A"), //$NON-NLS-1$ //$NON-NLS-2$
				new JLabel("B"), new JLabel("C"), new JLabel("D") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		JComponent[] col2 = {
				new JLabel(Messages.getString("LoopPanel.23")), prio_A, prio_B, prio_C, //$NON-NLS-1$
				prio_D };
		JComponent[] col3 = {
				new JLabel(Messages.getString("LoopPanel.24")), type_A, type_B, type_C, //$NON-NLS-1$
				type_D };

		addLabelTextRows3(col1, 50, col2, 100, col3, gridbag2, SubricPane);

		JPanel endPanel = new POCprocPanel();

		JButton button = new JButton(Messages.getString("LoopPanel.6")); //$NON-NLS-1$
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppWindow.log(Messages.getString("LoopPanel.7") + LoopKey.getText()); //$NON-NLS-1$

				Integer tmp_A = null;
				Integer tmp_B = null;
				Integer tmp_C = null;
				Integer tmp_D = null;

				if (prio_A.getText() != null) {
					if (!prio_A.getText().equals("")) //$NON-NLS-1$
						tmp_A = Integer.parseInt(prio_A.getText());
				}
				if (prio_B.getText() != null) {
					if (!prio_B.getText().equals("")) //$NON-NLS-1$
						tmp_B = Integer.parseInt(prio_B.getText());
				}
				if (prio_C.getText() != null) {
					if (!prio_C.getText().equals("")) //$NON-NLS-1$
						tmp_C = Integer.parseInt(prio_C.getText());
				}
				if (prio_D.getText() != null) {
					if (!prio_D.getText().equals("")) //$NON-NLS-1$
						tmp_D = Integer.parseInt(prio_D.getText());
				}

				selLoop = new Loop(LoopDesc.getText(), LoopComment.getText(),
						tmp_A, tmp_B, tmp_C, tmp_D, type_A.getText(), type_B
								.getText(), type_C.getText(), type_D.getText());
				LoopManager.put(LoopKey.getText(), selLoop);
				LoopPanel.updateListbox();
				TestPanel.updateListbox();
				PersonPanel.updateEditorSelector();
			}
		});
		endPanel.add(button, BorderLayout.PAGE_END);

		button = new JButton(Messages.getString("LoopPanel.8")); //$NON-NLS-1$
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String selloop = ((String) LoopPanel.instance.Selector
						.getSelectedValue()).split("/")[0]; //$NON-NLS-1$

				Person[] used = PersonManager.getPersonsForLoop(selloop);
				if (used.length == 0) {

					LoopManager.delete(selloop);
					AppWindow.log(Messages.getString("LoopPanel.10") + selloop); //$NON-NLS-1$
					PersonPanel.updateEditorSelector();
				} else {
					AppWindow.log(Messages.getString("LoopPanel.11") + selloop //$NON-NLS-1$
							+ Messages.getString("LoopPanel.12")); //$NON-NLS-1$
					for (int i = 0; i < used.length; i++) {
						AppWindow.log(used[i].getName() + used[i].getSurname());
					}
				}

				LoopPanel.updateListbox();

				// AppWindow.log("Delete Loop button pressed");
				// this.setText("Pressed Me");
			}
		});

		endPanel.add(button, BorderLayout.PAGE_END);

		//		button = new JButton(Messages.getString("LoopPanel.13")); //$NON-NLS-1$
		// button.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		//
		// if (LoopPanel.instance.Selector.getSelectedValue() != null) {
		// String selloop = ((String) LoopPanel.instance.Selector
		//							.getSelectedValue()).split("/")[0]; //$NON-NLS-1$
		//
		// // Person[] used = PersonManager.getPersonsForLoop(selloop);
		// // if (used.length == 0) {
		// //
		// // LoopManager.delete(selloop);
		// // AppWindow.log("Deleted RIC " + selloop);
		// // PersonPanel.updateEditorSelector();
		// // } else {
		// // AppWindow.log("Could not delete RIC " + selloop
		// // + " because still used for following persons");
		// // for (int i = 0; i < used.length; i++) {
		// // AppWindow.log(used[i].getName() + used[i].getSurname());
		// // }
		// // }
		//
		// if (selloop != null) {
		//
		// Datagram test = new Datagram(
		// selloop,
		// 'A',
		//								Messages.getString("LoopPanel.16"), 'L', new Date(), false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		//
		// Processor.process(test,
		//								Messages.getString("LoopPanel.18")); //$NON-NLS-1$
		//
		// }
		//
		// LoopPanel.updateListbox();
		//
		//					AppWindow.log(Messages.getString("LoopPanel.19")); //$NON-NLS-1$
		// // this.setText("Pressed Me");
		// } else {
		// AppWindow.log("Please select a RIC to run test");
		// }
		// }
		// });
		//
		// endPanel.add(button, BorderLayout.PAGE_END);

		endPanel.add(Box.createHorizontalStrut(40));
		endPanel.add(new WriteSettingsButton());

		JPanel innerEditPane2 = new POCprocPanel();
		innerEditPane2.setLayout(new BoxLayout(innerEditPane2,
				BoxLayout.PAGE_AXIS));

		innerEditPane2.add(innerEditPane);
		innerEditPane2.add(SubricPane);
		editPane.add(innerEditPane2, BorderLayout.CENTER);
		editPane.add(endPanel, BorderLayout.PAGE_END);
		add(editPane);

	}

}
