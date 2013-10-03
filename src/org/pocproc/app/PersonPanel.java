package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.pocproc.data.LoopManager;
import org.pocproc.data.Person;
import org.pocproc.data.PersonManager;

public class PersonPanel extends POCprocPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1414092013040586528L;

	private Person selPerson;

	private JTextField PersonName;
	private JTextField PersonSurname;
	private JTextField PersonEmail1;
	private JTextField PersonEmail2;
	private JTextField PersonProwlkey;
	private JTextField PersonNMAkey;
	private JTextField Personpushoverkey;
	private JTextField PersonMobile;
	private JList Selector;
	private JList LoopSelector;
	private CheckListManager LoopCheckList;

	private Vector<String> loops;
	private Vector<String> allloops;

	public static PersonPanel instance;

	public static void updateEditor(String name, String surname, String email1,
			String email2, String prowlkey, String nmakey, String pushoverkey,
			String mobile, Vector<String> Loops) {
		instance.PersonName.setText(name);
		instance.PersonSurname.setText(surname);
		instance.PersonEmail1.setText(email1);
		instance.PersonEmail2.setText(email2);
		instance.PersonProwlkey.setText(prowlkey);
		instance.PersonNMAkey.setText(nmakey);
		instance.Personpushoverkey.setText(pushoverkey);
		instance.PersonMobile.setText(mobile);
		instance.loops = Loops;
		// update the selector, loops are already locally stored, now paint
		// checkboxes
		updateEditorSelector();

	}

	public static void updateEditorSelector() {

		instance.allloops = LoopManager.getRicsLongCoded();
		String[] tmp = instance.allloops.toArray(new String[instance.allloops
				.size()]);

		if (instance.LoopSelector != null) {
			instance.LoopSelector.setEnabled(false);

			DefaultListModel model = (DefaultListModel) instance.LoopSelector
					.getModel();
			model.clear();

			for (int i = 0; i < tmp.length; i++) {
				model.addElement(tmp[i]);
			}

		} else {

			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < tmp.length; i++) {
				model.addElement((String) tmp[i]);
			}

			instance.LoopSelector = new JList(model);
		}

		if (instance.LoopCheckList != null) {

			instance.LoopCheckList.getSelectionModel().clearSelection();

		} else {
			instance.LoopCheckList = new CheckListManager(instance.LoopSelector);
		}

		for (int i = 0; i < tmp.length; i++) {

			if (tmp[i] != null) {
				if (tmp[i].split("/") != null) { //$NON-NLS-1$
					// AppWindow.log(tmp[i].split("/")[0]);
					String tmpstr = tmp[i].split("/")[0]; //$NON-NLS-1$
					if (instance.loops != null) {
						if (instance.loops.contains(tmpstr)) {
							instance.LoopCheckList.getSelectionModel()
									.addSelectionInterval(i, i);
						}
					}
				}
			}

		}

		instance.LoopSelector.setEnabled(true);

	}

	public JScrollPane scroller;

	public static void updateListbox() {
		Object[] data = PersonManager.getPersons();

		if (instance.Selector != null) {
			instance.Selector.setEnabled(false);
			instance.Selector.clearSelection();
			DefaultListModel model = (DefaultListModel) instance.Selector
					.getModel();
			model.clear();

			for (int i = 0; i < data.length; i++) {
				model.addElement((String) data[i]);
			}
			instance.Selector.setEnabled(true);

		} else {

			DefaultListModel model = new DefaultListModel();
			for (int i = 0; i < data.length; i++) {
				model.addElement((String) data[i]);
			}

			instance.Selector = new JList(model);
			instance.Selector
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			instance.Selector
					.addListSelectionListener(new ListSelectionListener() {
						@Override
						public void valueChanged(ListSelectionEvent arg0) {
							// int index = arg0.getFirstIndex();
							// int index =
							// LoopPanel.instance.Selector.getSelectedIndex();

							if (PersonPanel.instance.Selector.isEnabled()) {

								String key = (String) PersonPanel.instance.Selector
										.getSelectedValue();
								if (key != null) {
									Person temp = PersonManager.getPerson(key);
									if (temp != null) {
										PersonPanel.updateEditor(
												temp.getName(),
												temp.getSurname(),
												temp.getEmail(),
												temp.getEmail2(),
												temp.getProwlkey(),
												temp.getNmakey(),
												temp.getPushoverkey(),
												temp.getMobile(),
												temp.getLoops());

									}
								}
							}
						}
					});

		}

	}

	public PersonPanel(boolean b) {
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

		// BoxLayout layout = new BoxLayout(editPane, BoxLayout.PAGE_AXIS);

		editPane.setLayout(new BorderLayout());

		JPanel outerEditPane = new POCprocPanel();
		outerEditPane.setLayout(new BoxLayout(outerEditPane,
				BoxLayout.LINE_AXIS));
		outerEditPane.setPreferredSize(new Dimension(1024, 1024));

		JPanel innerEditPane = new POCprocPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		innerEditPane.setLayout(gridbag);

		c.gridwidth = GridBagConstraints.REMAINDER; // last
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1.0;
		// innerEditPane.add(actionLabel, c);
		innerEditPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(Messages
						.getString("PersonPanel.0")), //$NON-NLS-1$
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JPanel innerEditPane2 = new POCprocPanel();
		innerEditPane2.setLayout(new BoxLayout(innerEditPane2,
				BoxLayout.PAGE_AXIS));
		innerEditPane2.setMinimumSize(new Dimension(200, 500));
		innerEditPane2.setPreferredSize(new Dimension(1024, 1024));

		PersonSurname = new JTextField(50);
		PersonSurname.setMinimumSize(new Dimension(200, 24));
		PersonName = new JTextField(50);
		PersonEmail1 = new JTextField(50);
		PersonEmail2 = new JTextField(50);
		PersonProwlkey = new JTextField(50);
		PersonNMAkey = new JTextField(50);
		Personpushoverkey = new JTextField(50);
		PersonMobile = new JTextField(50);

		JLabel[] labels = { new JLabel(Messages.getString("PersonPanel.2")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.3")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.4")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.5")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.6")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.7")), //$NON-NLS-1$
				new JLabel(Messages.getString("PersonPanel.15")), //$NON-NLS-1$	
				new JLabel(Messages.getString("PersonPanel.8")) }; //$NON-NLS-1$
		Container[] textFields = { PersonSurname, PersonName, PersonEmail1,
				PersonEmail2, PersonProwlkey, PersonNMAkey, Personpushoverkey,
				PersonMobile };
		addLabelTextRows(labels, textFields, gridbag, innerEditPane);

		outerEditPane.add(innerEditPane);
		outerEditPane.add(Box.createRigidArea(new Dimension(20, 20)));
		outerEditPane.add(Box.createVerticalStrut(500));
		outerEditPane.setMinimumSize(new Dimension(200, 24));

		innerEditPane2.add(new JLabel(Messages.getString("PersonPanel.9"))); //$NON-NLS-1$

		// allloops = LoopManager.getRicsLongCoded();

		// LoopSelector = new JList(allloops);

		updateEditorSelector();

		JScrollPane LoopSelScroll = new JScrollPane(LoopSelector);

		LoopSelScroll.setPreferredSize(new Dimension(1024, 1024));
		LoopSelScroll.setMaximumSize(new Dimension(1024, 1024));

		innerEditPane2.add(LoopSelScroll);

		outerEditPane.add(innerEditPane2);

		// LoopCheckList = new CheckListManager(LoopSelector);

		JPanel endPanel = new POCprocPanel();

		JButton button = new JButton(Messages.getString("PersonPanel.10")); //$NON-NLS-1$
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppWindow.log(Messages.getString("PersonPanel.11") + PersonName.getText() //$NON-NLS-1$
						+ PersonSurname.getText());

				Vector<String> storeLoops = new Vector<String>();

				String[] tmp = instance.allloops
						.toArray(new String[instance.allloops.size()]);
				for (int i = 0; i < tmp.length; i++) {
					if (LoopCheckList.getSelectionModel().isSelectedIndex(i)) {
						storeLoops.add(tmp[i].split("/")[0]); //$NON-NLS-1$
					}

				}

				selPerson = new Person(PersonName.getText(), PersonSurname
						.getText(), PersonEmail1.getText(), PersonEmail2
						.getText(), PersonProwlkey.getText(), PersonNMAkey
						.getText(), Personpushoverkey.getText(), PersonMobile
						.getText(), storeLoops);
				PersonManager.put(selPerson);

				PersonPanel.updateListbox();
			}
		});
		endPanel.add(button, BorderLayout.PAGE_END);

		button = new JButton(Messages.getString("PersonPanel.13")); //$NON-NLS-1$
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = (String) Selector.getSelectedValue();
				// this.setText("Pressed Me");
				PersonManager.delete(key);
				updateListbox();
				AppWindow.log(Messages.getString("PersonPanel.14") + key); //$NON-NLS-1$
			}
		});
		endPanel.add(button, BorderLayout.PAGE_END);

		endPanel.add(Box.createHorizontalStrut(40));
		endPanel.add(new WriteSettingsButton());
		editPane.add(outerEditPane, BorderLayout.CENTER);
		editPane.add(endPanel, BorderLayout.PAGE_END);
		add(editPane);

	}

	private void addLabelTextRows(JLabel[] labels, Container[] textFields,
			GridBagLayout gridbag, Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}
}