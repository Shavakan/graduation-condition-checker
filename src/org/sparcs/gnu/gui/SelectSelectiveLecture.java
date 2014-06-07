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

import org.sparcs.gnu.catalog.SelectiveLecture;

public class SelectSelectiveLecture extends GCCContainer {
	
	private static final long serialVersionUID = 1L;
	private JList<SelectiveLecture> listAllCourses;
	private JList<SelectiveLecture> listSelectedCourses;

	/**
	 * Create the panel.
	 */
	public SelectSelectiveLecture(GUIMain root) {
		super(root);

		initialize();
	}
	
	private void initialize() {
		JLabel lblInstruction = new JLabel("학석 통합 과목 선택");
		lblInstruction.setBounds(6, 6, 419, 16);
		add(lblInstruction);
		
		JLabel lblMasterCandidate = new JLabel("석사 인정");
		lblMasterCandidate.setBounds(16, 34, 97, 16);
		add(lblMasterCandidate);
		
		JLabel lblUnder = new JLabel("학사 인정");
		lblUnder.setBounds(327, 33, 131, 16);
		add(lblUnder);
		
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
				List<SelectiveLecture> selectedList = listAllCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					SelectiveLecture[] newList = new SelectiveLecture[listSelectedCourses.getModel().getSize() + selectedList.size()];
					for(int k=0; k<listSelectedCourses.getModel().getSize(); k++)
						newList[k] = listSelectedCourses.getModel().getElementAt(k);
					for(int k=0; k<selectedList.size(); k++)
						newList[k + listSelectedCourses.getModel().getSize()] = selectedList.get(k);
					listSelectedCourses.setListData(newList);
					
					SelectiveLecture[] removeList = new SelectiveLecture[listAllCourses.getModel().getSize() - selectedList.size()];
					int k=0;
					for(int j=0; j<listAllCourses.getModel().getSize(); j++)
					{
						SelectiveLecture recog = listAllCourses.getModel().getElementAt(j);
						boolean found = false;
						for(SelectiveLecture remove : selectedList)
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
				List<SelectiveLecture> selectedList = listSelectedCourses.getSelectedValuesList();
				if (selectedList.size() == 0) {
					return;
				}
				else {
					SelectiveLecture[] newList = new SelectiveLecture[listAllCourses.getModel().getSize() + selectedList.size()];
					for(int k=0; k<listAllCourses.getModel().getSize(); k++)
						newList[k] = listAllCourses.getModel().getElementAt(k);
					for(int k=0; k<selectedList.size(); k++)
						newList[k + listAllCourses.getModel().getSize()] = selectedList.get(k);
					listAllCourses.setListData(newList);
					
					SelectiveLecture[] removeList = new SelectiveLecture[listSelectedCourses.getModel().getSize() - selectedList.size()];
					int k=0;
					for(int j=0; j<listSelectedCourses.getModel().getSize(); j++)
					{
						SelectiveLecture recog = listSelectedCourses.getModel().getElementAt(j);
						boolean found = false;
						for(SelectiveLecture remove : selectedList)
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
			public void actionPerformed(ActionEvent e) {
				int size = listSelectedCourses.getModel().getSize();
				List<SelectiveLecture> ret = new LinkedList<>();
				for(int k=0; k<size; k++)
				{
					ret.add(listSelectedCourses.getModel().getElementAt(k));
				}
				root.currentSelectiveLectureList = ret;
				
				((VisualizeResult)root.getWindow(GUIMain.visualizeResult)).update(root.processAll());
				root.changeWindow(GUIMain.visualizeResult);
			}
		});
		btnNext.setBounds(413, 174, 110, 29);
		add(btnNext);
	}
	
	public void update(List<SelectiveLecture> selectionList)
	{
		root.currentSelectiveLectureList = selectionList;
		SelectiveLecture[] newList = new SelectiveLecture[selectionList.size()];
		for(int k=0; k<newList.length; k++)
			newList[k] = selectionList.get(k);
		listAllCourses.setListData(newList);
		listSelectedCourses.setListData(new SelectiveLecture[0]);
		
		if(newList.length == 0)
		{
			root.changeWindow(GUIMain.visualizeResult);
			((VisualizeResult)root.getWindow(GUIMain.visualizeResult)).update(root.processAll());
		}
	}
}
