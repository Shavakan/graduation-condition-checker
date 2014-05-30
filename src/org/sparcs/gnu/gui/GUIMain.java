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

	private VisualizeResult vresult;
	private SelectFile selectFile;
	
	private JFrame frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Converter conv = Converter.converterObject("tmp" + File.separator + "근홍.xls");
					conv.convert("tmp" + File.separator + "output.db");
					Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", false);
					Class.forName("org.sparcs.gnu.course.GradeInfo");
					
					GradeInfo info = new GradeInfo(conn);
					Parse.parseRawInput("conf" + File.separator + "cs.conf", "tmp" + File.separator + "cs.xml");
					Catalog catalog = Catalog.loadCatalog("tmp" + File.separator + "cs.xml");

					GraduationChecker checker = new GraduationChecker(catalog);
					ProcessInfo result = checker.process(info);
					
					/////////////////
					GUIMain window = new GUIMain();
					
					window.vresult = new VisualizeResult(window);
					window.vresult.update(result);
					
					window.selectFile = new SelectFile(window);
					
					
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
		frame.setContentPane(vresult.getContainer());
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
