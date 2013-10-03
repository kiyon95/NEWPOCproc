package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import org.pocproc.data.Datagram;
import org.pocproc.data.LoopManager;

public class TestPanel extends POCprocPanel {

	private static final long serialVersionUID = 1414092513040586528L;

	private static DatagramComparer comparer;

	private JTextArea LoopComment;
	private JList Selector;

	private JRadioButton subricA;
	private JRadioButton subricB;
	private JRadioButton subricC;
	private JRadioButton subricD;

	public static TestPanel instance;

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
				model.addElement((String) data[i] + "/" + LoopManager.getRic((String) data[i]).getName());
			}
			instance.Selector.setEnabled(true);

		} else {

			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < data.length; i++) {
				model.addElement((String) data[i] + "/" + LoopManager.getRic((String) data[i]).getName());
			}

			instance.Selector = new JList(model);
			instance.Selector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}

	}

	public TestPanel(boolean b) {
		super(b);
		instance = this;

		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		updateListbox();

		scroller = new JScrollPane(Selector);

		scroller.setPreferredSize(new Dimension(200, 500));
		scroller.setMinimumSize(new Dimension(200, 100));
		scroller.setMaximumSize(new Dimension(200, 50000));

		// make your JList as check list
		
		add(scroller);

		JPanel editPane = new POCprocPanel();

		editPane.setLayout(new BorderLayout());

		JPanel innerEditPane = new POCprocPanel();

		LoopComment = new JTextArea(50, 10);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		innerEditPane.setLayout(gridbag);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		innerEditPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Meldung"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JScrollPane CommentScroller = new JScrollPane(LoopComment);
		CommentScroller.setMinimumSize(new Dimension(40, 200));

		JComponent[] labels = { new JLabel("Text") };
		JComponent[] textFields = { CommentScroller };
		addLabelTextRows(labels, textFields, gridbag, innerEditPane);

		JPanel SubricPane = new POCprocPanel();

		ButtonGroup SubricSwitch = new ButtonGroup();

		subricA = new JRadioButton("A");
		subricB = new JRadioButton("B");
		subricC = new JRadioButton("C");
		subricD = new JRadioButton("D");

		SubricSwitch.add(subricA);
		SubricSwitch.add(subricB);
		SubricSwitch.add(subricC);
		SubricSwitch.add(subricD);

		subricA.setSelected(true);

		SubricPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder(Messages.getString("TestPanel.6")), //$NON-NLS-1$
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		SubricPane.add(subricA);
		SubricPane.add(subricB);
		SubricPane.add(subricC);
		SubricPane.add(subricD);

		JPanel endPanel = new POCprocPanel();

		JButton button = new JButton(Messages.getString("LoopPanel.13")); //$NON-NLS-1$
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (instance.Selector.getSelectedValues() != null) {

					Object[] Rics = instance.Selector.getSelectedValues();

					for (int i = 0; i < Rics.length; i++) {

						String selloop = ((String) Rics[i]).split("/")[0]; //$NON-NLS-1$

						if (selloop != null) {

							char subric = 'A';

							if (subricA.isSelected() == true)
								subric = 'A';
							if (subricB.isSelected() == true)
								subric = 'B';
							if (subricC.isSelected() == true)
								subric = 'C';
							if (subricD.isSelected() == true)
								subric = 'D';

							Datagram test = new Datagram(selloop, subric, LoopComment.getText(), 'L', new Date(), false);

							if (comparer == null) {
								comparer = new DatagramComparer("Testpanel");
							}
							
							if (comparer.validate(test)) {
								Processor.process(test, "TestPanel");
							}
						}

					}

				} else {
					AppWindow.log("Bitte zumindest eine RIC ausw\u00e4hlen, um die Testmeldung zu senden"); //$NON-NLS-1$
				}

				AppWindow.log("Test message sent");
				
			}
		});

		endPanel.add(button, BorderLayout.PAGE_END);

		JPanel innerEditPane2 = new POCprocPanel();
		innerEditPane2.setLayout(new BoxLayout(innerEditPane2, BoxLayout.PAGE_AXIS));
		innerEditPane2.add(innerEditPane);
		innerEditPane2.add(SubricPane);
		editPane.add(innerEditPane2, BorderLayout.CENTER);
		editPane.add(endPanel, BorderLayout.PAGE_END);
		add(editPane);
	}
}
