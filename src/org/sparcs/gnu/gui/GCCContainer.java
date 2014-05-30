package org.sparcs.gnu.gui;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JPanel;

public class GCCContainer {
	private JPanel container;
	protected GUIMain root;
	
	public Container getContainer()
	{
		return container;
	}

	public GCCContainer(GUIMain root)
	{
		this.root = root;
		container = new JPanel();
		container.setLayout(null);
		container.setForeground(Color.WHITE);
		container.setBackground(Color.WHITE);
		container.setSize(800, 600);
	}
}
