package org.pocproc.app;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;

public class POCprocPanel extends javax.swing.JPanel {

	POCprocPanel(boolean b) {
		super(b);
		this.setBackground(new Color(0, 0, 0, 0));

	}

	POCprocPanel() {
		super();
		this.setBackground(new Color(0, 0, 0, 0));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3072810159270932338L;

	protected void addLabelTextRows(JComponent[] labels,
			JComponent[] textFields, GridBagLayout gridbag, Container container) {
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

	protected void addLabelTextRows3(JComponent[] row1, int minrow1,
			JComponent[] row2, int minrow2, JComponent[] row3,
			GridBagLayout gridbag, Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = row1.length;

		for (int i = 0; i < numLabels; i++) {

			c.gridwidth = 1; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.5; // reset to default
			row1[i].setMinimumSize(new Dimension(minrow1, 20));
			row1[i].setMaximumSize(new Dimension(minrow1, 20));
			container.add(row1[i], c);

			c.gridwidth = 1; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.5; // reset to default
			row2[i].setMinimumSize(new Dimension(minrow2, 20));
			row2[i].setMaximumSize(new Dimension(minrow2, 20));
			container.add(row2[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 5.0;
			container.add(row3[i], c);
		}
	}

}
