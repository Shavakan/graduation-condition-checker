package org.sparcs.gnu.gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.checker.ProcessInfo;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class SelectFile extends GCCContainer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtfldTranscript;
	private JTextField txtfldMainProgram;
	private JTextField txtfldSecondProgram;
	private JTextField txtfldThirdProgram;
	private JFileChooser fc;

	private GUIMain root;

	private String transcript, mainProgram, secondProgram, thirdProgram;


	/**
	 * Create the application.
	 */
	public SelectFile(GUIMain root) {
		super(root);
		this.root = root;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/* GUI Arrangement */
		final Container me = this;
		JLabel lblEnterYourTranscript = new JLabel("Enter your transcript file (.csv):");
		lblEnterYourTranscript.setBounds(12, 10, 183, 15);
		this.add(lblEnterYourTranscript);

		txtfldTranscript = new JTextField();
		txtfldTranscript.setBounds(131, 35, 282, 21);
		this.add(txtfldTranscript);
		txtfldTranscript.setColumns(10);

		JButton btnSearchTranscript = new JButton("Search...");
		btnSearchTranscript.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(root.frame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					transcript = file.getAbsolutePath().toString();
					txtfldTranscript.setText(transcript);
				}
				else {
					System.out.println("File search failed: transcript");
				}
			}
		});
		btnSearchTranscript.setBounds(22, 34, 97, 23);
		this.add(btnSearchTranscript);

		JLabel lblEnterYourCondition = new JLabel("Enter your condition files (.csv):");
		lblEnterYourCondition.setBounds(12, 66, 188, 15);
		this.add(lblEnterYourCondition);

		txtfldMainProgram = new JTextField();
		txtfldMainProgram.setBounds(131, 86, 282, 21);
		this.add(txtfldMainProgram);
		txtfldMainProgram.setColumns(10);

		JButton btnSearchMainProgram = new JButton("Search...");
		btnSearchMainProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(me);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					mainProgram = file.getAbsolutePath().toString();
					txtfldMainProgram.setText(mainProgram);
				}
				else {
					System.out.println("File search failed: Main program");
				}
			}
		});
		btnSearchMainProgram.setBounds(22, 85, 97, 23);
		this.add(btnSearchMainProgram);

		txtfldSecondProgram = new JTextField();
		txtfldSecondProgram.setBounds(131, 117, 282, 21);
		this.add(txtfldSecondProgram);
		txtfldSecondProgram.setColumns(10);

		JButton btnSearchSecondProgram = new JButton("Search...");
		btnSearchSecondProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(me);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					secondProgram = file.getAbsolutePath().toString();
					txtfldSecondProgram.setText(secondProgram);
				}
				else {
					System.out.println("File search failed: Sub program");
				}
			}
		});
		btnSearchSecondProgram.setBounds(22, 116, 97, 23);
		this.add(btnSearchSecondProgram);

		txtfldThirdProgram = new JTextField();
		txtfldThirdProgram.setBounds(131, 148, 282, 21);
		this.add(txtfldThirdProgram);
		txtfldThirdProgram.setColumns(10);

		JButton btnSearchThirdProgram = new JButton("Search...");
		btnSearchThirdProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(me);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					thirdProgram = file.getAbsolutePath().toString();
					txtfldThirdProgram.setText(thirdProgram);
				}
				else {
					System.out.println("File search failed: Other program");
				}
			}
		});
		btnSearchThirdProgram.setBounds(22, 147, 97, 23);
		this.add(btnSearchThirdProgram);

		JButton btnNext = new JButton("Next >");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				GUIFileInputVerification next = new GUIFileInputVerification(
						txtfldTranscript.getText() , txtfldMainProgram.getText(), txtfldSecondProgram.getText(), txtfldThirdProgram.getText());
				next.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				next.setVisible(true);
				 */
				try {
					Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", true);
					Converter conv = Converter.converterObject(transcript);
					conv.convert("tmp" + File.separator + "output.db");
					
					Class.forName("org.sparcs.gnu.course.GradeInfo");

					GradeInfo info = new GradeInfo(conn);
					Parse.parseRawInput(mainProgram, "tmp" + File.separator + "cs.xml");
					Catalog catalog = Catalog.loadCatalog("tmp" + File.separator + "cs.xml");
					
					GraduationChecker checker = new GraduationChecker(catalog);	
					ProcessInfo result = checker.process(info);
					
					((VisualizeResult)root.getWindow(GUIMain.visualizeResult)).update(result);
					root.changeWindow(GUIMain.visualizeResult);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNext.setBounds(316, 179, 97, 23);
		this.add(btnNext);
	}
}
