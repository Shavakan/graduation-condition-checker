package org.sparcs.gnu.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

import org.sparcs.gnu.catalog.MutualRecog;

public class SelectMutual extends GCCContainer {
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, MutualRecog> mutualAll;
	private HashMap<String, MutualRecog> mutualSelected;

	/**
	 * Create the panel.
	 */
	public SelectMutual(GUIMain root) {
		super(root);

		mutualAll = new HashMap<String, MutualRecog>();
		mutualSelected = new HashMap<String, MutualRecog>();
		initialize();
	}
	
	private void initialize() {
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
			// TODO: Get model from mutualAll
			// @undead, @shavakan
			String[] values = new String[] {"A", "b", "c", "d"};
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
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<String> selectedList = listAllCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					ListModel update = listSelectedCourses.getModel();
					for (String str : selectedList) {
					}
					listSelectedCourses.setModel(update);
				}
			}
		});
		btnAdd.setBounds(198, 83, 88, 29);
		add(btnAdd);
		
		JButton buttonRemove = new JButton("< Remove");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		
		JButton buttonPrev = new JButton("<< Previous");
		buttonPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				root.changeWindow(GUIMain.selectFile);
			}
		});
		buttonPrev.setBounds(6, 175, 107, 29);
		add(buttonPrev);
		
		JButton btnNext = new JButton("Next >>");
		btnNext.setBounds(371, 175, 110, 29);
		add(btnNext);
	}
	
	public void update(List<MutualRecog> mutualList)
	{
		for(MutualRecog mutual : mutualList) {
			mutualSelected.put(mutual.getExceptionOrigin(), mutual);
		}
	}
}
