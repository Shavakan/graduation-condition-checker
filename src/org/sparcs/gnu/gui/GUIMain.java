package org.sparcs.gnu.gui;

import java.awt.Container;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.course.GradeInfo;

public class GUIMain {
	static String selectFile = "SelectFile";
	static String visualizeResult = "VisualizeResult";
	static String selectMutual = "SelectMutual";
	
	private Map<String, Container> allContainer;
	
	protected JFrame frame;
	
	GradeInfo currentInfo;
	Catalog currentCatalog;
	GraduationChecker currentChecker;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
					
					window.addWindow(selectFile, new SelectFile(window));
					window.addWindow(visualizeResult, new VisualizeResult(window));
					window.addWindow(selectMutual, new SelectMutual(window));
					
					window.frame.setVisible(true);
					window.changeWindow(GUIMain.selectFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIMain() {
		allContainer = new HashMap<>();
		initialize();
	}
	
	private void addWindow(String name, Container panel)
	{
		allContainer.put(name, panel);
	}
	
	Container getWindow(String name)
	{
		return allContainer.get(name);
	}
	
	boolean changeWindow(String name)
	{
		if(allContainer.containsKey(name))
		{
			frame.setContentPane(allContainer.get(name));
			frame.getContentPane().invalidate();
			return true;
		}
		return false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
