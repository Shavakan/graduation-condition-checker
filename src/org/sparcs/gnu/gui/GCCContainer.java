package org.sparcs.gnu.gui;

import java.awt.Color;

import javax.swing.JPanel;

public class GCCContainer extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GUIMain root;

	public GCCContainer(GUIMain root)
	{
		this.root = root;
		super.setLayout(null);
		super.setForeground(Color.WHITE);
		super.setBackground(Color.WHITE);
		super.setSize(800, 600);
	}
}
