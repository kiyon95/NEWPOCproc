package org.pocproc.app;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

;

public class CheckListCellRenderer extends JPanel implements ListCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5887211614239981027L;
	private ListCellRenderer delegate;
	private ListSelectionModel selectionModel;
	private JCheckBox checkBox = new JCheckBox();

	public CheckListCellRenderer(ListCellRenderer renderer,
			ListSelectionModel selectionModel) {
		this.delegate = renderer;
		this.selectionModel = selectionModel;
		setLayout(new BorderLayout());
		setOpaque(false);
		checkBox.setOpaque(false);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		Component renderer = delegate.getListCellRendererComponent(list, value,
				index, isSelected, cellHasFocus);
		checkBox.setSelected(selectionModel.isSelectedIndex(index));
		removeAll();
		add(checkBox, BorderLayout.WEST);
		add(renderer, BorderLayout.CENTER);
		return this;
	}
}
