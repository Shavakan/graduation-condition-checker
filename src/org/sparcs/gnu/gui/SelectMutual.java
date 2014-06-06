package org.sparcs.gnu.gui;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.AbstractListModel;

import org.sparcs.gnu.catalog.MutualRecog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SelectMutual extends GCCContainer {

	/**
	 * Create the panel.
	 */
	public SelectMutual(GUIMain root) {
		super(root);
		setLayout(null);
		
		JLabel lblInstruction = new JLabel("Select mutually recognized courses for your double major / minor:");
		lblInstruction.setBounds(6, 6, 419, 16);
		add(lblInstruction);
		
		JLabel lblCoursesTaken = new JLabel("Courses Taken:");
		lblCoursesTaken.setBounds(16, 34, 97, 16);
		add(lblCoursesTaken);
		
		JLabel lblMutuallyRecognized = new JLabel("Mutually recognized:");
		lblMutuallyRecognized.setBounds(285, 34, 131, 16);
		add(lblMutuallyRecognized);
		
		final JList listAllCourses = new JList();
		listAllCourses.setModel(new AbstractListModel() {
			String[] values = new String[] {"피카츄", "라이츄", "파이리", "꼬부기", "버터플", "야도란", "피죤투", "또가스"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listAllCourses.setBorder(new LineBorder(new Color(0, 0, 0)));
		listAllCourses.setBounds(26, 62, 160, 100);
		add(listAllCourses);
		
		final JList listSelectedCourses = new JList();
		listSelectedCourses.setBorder(new LineBorder(new Color(0, 0, 0)));
		listSelectedCourses.setBounds(295, 63, 160, 100);
		add(listSelectedCourses);
		
		JButton btnAdd = new JButton("Add >");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				List<String> selectedList = listAllCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					ListModel update = listSelectedCourses.getModel();
					for (String str : selectedList) {
						((DefaultListModel) update).addElement(str);
					}
					listSelectedCourses.setModel(update);
				}
			}
		});
		btnAdd.setBounds(198, 83, 88, 29);
		add(btnAdd);
		
		JButton buttonRemove = new JButton("< Remove");
		buttonRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] selectedIdx = listSelectedCourses.getSelectedIndices();
				if (selectedIdx.length == 0) {
					return;
				}
				else {
					for (int selected : selectedIdx) {
						listSelectedCourses.remove(selected);
					}
				}
			}
		});
		buttonRemove.setBounds(198, 109, 88, 29);
		add(buttonRemove);
		
		JButton btnNext = new JButton("Next >>");
		btnNext.setBounds(371, 175, 110, 29);
		add(btnNext);
	}
	
	public void update(List<MutualRecog> mutualList)
	{
		
	}
}
