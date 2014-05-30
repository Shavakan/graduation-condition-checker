package org.sparcs.gnu.gui;

import java.awt.EventQueue;
import java.io.File;
import java.sql.Connection;

import javax.swing.JFrame;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.checker.ProcessInfo;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class GUIMain {

	protected SelectFile selectFile;
	protected VisualizeResult vResult;
	
	protected JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
					
					window.selectFile = new SelectFile(window);
					window.vResult = new VisualizeResult(window);
					
					window.frame.setVisible(true);
					window.frame.setContentPane(window.selectFile.getContainer());
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
		initialize();
	}
	
	void showResult()
	{
		frame.getContentPane().invalidate();
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
