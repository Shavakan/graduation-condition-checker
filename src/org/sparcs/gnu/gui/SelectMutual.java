package org.sparcs.gnu.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import org.sparcs.gnu.catalog.MutualRecog;

public class SelectMutual extends GCCContainer {
	
	private static final long serialVersionUID = 1L;
	private JList<MutualRecog> listAllCourses;
	private JList<MutualRecog> listSelectedCourses;

	/**
	 * Create the panel.
	 */
	public SelectMutual(GUIMain root) {
		super(root);

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
		lblMutuallyRecognized.setBounds(327, 33, 131, 16);
		add(lblMutuallyRecognized);
		
		listAllCourses = new JList<>();
		listAllCourses.setBorder(new LineBorder(new Color(0, 0, 0)));
		listAllCourses.setBounds(26, 62, 160, 100);
		add(listAllCourses);
		
		listSelectedCourses = new JList<>();
		listSelectedCourses.setBorder(new LineBorder(new Color(0, 0, 0)));
		listSelectedCourses.setBounds(337, 62, 160, 100);
		add(listSelectedCourses);
		
		JButton btnAdd = new JButton("Add >");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<MutualRecog> selectedList = listAllCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					MutualRecog[] newList = new MutualRecog[listSelectedCourses.getModel().getSize() + selectedList.size()];
					for(int k=0; k<listSelectedCourses.getModel().getSize(); k++)
						newList[k] = listSelectedCourses.getModel().getElementAt(k);
					for(int k=0; k<selectedList.size(); k++)
						newList[k + listSelectedCourses.getModel().getSize()] = selectedList.get(k);
					listSelectedCourses.setListData(newList);
					
					MutualRecog[] removeList = new MutualRecog[listAllCourses.getModel().getSize() - selectedList.size()];
					int k=0;
					for(int j=0; j<listAllCourses.getModel().getSize(); j++)
					{
						MutualRecog recog = listAllCourses.getModel().getElementAt(j);
						boolean found = false;
						for(MutualRecog remove : selectedList)
						{
							if(remove == recog)
							{
								found = true;
								break;
							}
						}
						
						if(found == false)
						{
							removeList[k++] = recog;
						}
					}
					listAllCourses.setListData(removeList);
				}
			}
		});
		btnAdd.setBounds(198, 83, 110, 29);
		add(btnAdd);
		
		JButton buttonRemove = new JButton("< Remove");
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<MutualRecog> selectedList = listSelectedCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					MutualRecog[] newList = new MutualRecog[listAllCourses.getModel().getSize() + selectedList.size()];
					for(int k=0; k<listAllCourses.getModel().getSize(); k++)
						newList[k] = listAllCourses.getModel().getElementAt(k);
					for(int k=0; k<selectedList.size(); k++)
						newList[k + listAllCourses.getModel().getSize()] = selectedList.get(k);
					listAllCourses.setListData(newList);
					
					MutualRecog[] removeList = new MutualRecog[listSelectedCourses.getModel().getSize() - selectedList.size()];
					int k=0;
					for(int j=0; j<listSelectedCourses.getModel().getSize(); j++)
					{
						MutualRecog recog = listSelectedCourses.getModel().getElementAt(j);
						boolean found = false;
						for(MutualRecog remove : selectedList)
						{
							if(remove == recog)
							{
								found = true;
								break;
							}
						}
						
						if(found == false)
						{
							removeList[k++] = recog;
						}
					}
					listSelectedCourses.setListData(removeList);
				}
			}
		});
		buttonRemove.setBounds(198, 109, 110, 29);
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
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int size = listSelectedCourses.getModel().getSize();
				List<MutualRecog> ret = new LinkedList<>();
				for(int k=0; k<size; k++)
				{
					ret.add(listSelectedCourses.getModel().getElementAt(k));
				}
				root.currentMutualList = ret;
				
				root.changeWindow(GUIMain.selectSelectiveLecture);
				((SelectSelectiveLecture)root.getWindow(GUIMain.selectSelectiveLecture)).update(root.currentInfo.getSelectiveList());
			}
		});
		btnNext.setBounds(413, 174, 110, 29);
		add(btnNext);
	}
	
	public void update(List<MutualRecog> mutualList)
	{
		root.currentMutualList = mutualList;
		MutualRecog[] newList = new MutualRecog[mutualList.size()];
		for(int k=0; k<newList.length; k++)
			newList[k] = mutualList.get(k);
		listAllCourses.setListData(newList);
		listSelectedCourses.setListData(new MutualRecog[0]);
		if(newList.length == 0)
		{
			root.changeWindow(GUIMain.selectSelectiveLecture);
			((SelectSelectiveLecture)root.getWindow(GUIMain.selectSelectiveLecture)).update(root.currentInfo.getSelectiveList());
		}
	}
}
