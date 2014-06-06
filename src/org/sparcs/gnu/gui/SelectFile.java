package org.sparcs.gnu.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import net.iharder.dnd.FileDrop;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.catalog.MutualRecog;
import org.sparcs.gnu.catalog.Rule;
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
	private JTextField mainConfText;
	private JTextField subConfText;
	private JFileChooser fc;

	private Border fileBoarder;
	private FileDrop.Listener transcriptListener;
	private FileDrop.Listener currentSugangListener;
	private FileDrop.Listener mainConfListener;
	private FileDrop.Listener subConfListener;

	private String transcript,currentSugang, mainProgram, secondProgram;
	private JTextField currentSugangText;


	/**
	 * Create the application.
	 */
	public SelectFile(GUIMain root) {
		super(root);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		fileBoarder = new MatteBorder(2, 2, 2, 2, Color.CYAN);

		/* GUI Arrangement */
		final Container me = this;

		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				GUIFileInputVerification next = new GUIFileInputVerification(
						txtfldTranscript.getText() , txtfldMainProgram.getText(), txtfldSecondProgram.getText(), txtfldThirdProgram.getText());
				next.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				next.setVisible(true);
				 */
				try {
					transcript = txtfldTranscript.getText().trim();
					currentSugang = currentSugangText.getText().trim();
					mainProgram = mainConfText.getText().trim();
					secondProgram = subConfText.getText().trim();
					Connection conn = SQLiteManager.createDatabase("tmp" + File.separator + "output.db", true);
					Converter conv = Converter.converterObject(transcript);
					conv.convert("tmp" + File.separator + "output.db");
					if(currentSugang != null && currentSugang.length()>0)
					{
						conv = Converter.converterObject(currentSugang);
						conv.convert("tmp" + File.separator + "output.db");
					}

					Class.forName("org.sparcs.gnu.course.GradeInfo");

					GradeInfo info = new GradeInfo(conn);
					
					Parse.parseRawInput(mainProgram, "tmp" + File.separator + "cs.xml");
					if(secondProgram != null && secondProgram.trim().length()>0)
						Parse.parseException(secondProgram, "tmp" + File.separator + "cs.xml");
					File replace = new File("conf" + File.separator + "replace.conf");
					if(replace.exists() && replace.isFile())
					{
						Parse.parseReplace(replace.getAbsolutePath(), "tmp" + File.separator + "cs.xml");
					}
					
					root.currentCatalog = Catalog.loadCatalog("tmp" + File.separator + "cs.xml");
					root.currentChecker = new GraduationChecker(root.currentCatalog);
					
					List<MutualRecog> param = new LinkedList<>();
					for(Rule rule : root.currentCatalog.getRules())
						for(MutualRecog m : rule.getMutualRecogs())
							param.add(m);
					
					if(param.size() > 0)
					{
						((SelectMutual)root.getWindow(GUIMain.selectMutual)).update(param);
						root.changeWindow(GUIMain.selectMutual);
					}
					else
					{
						ProcessInfo result = root.currentChecker.process(info);
						((VisualizeResult)root.getWindow(GUIMain.visualizeResult)).update(result);
						root.changeWindow(GUIMain.visualizeResult);
					}
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
		btnNext.setBounds(318, 313, 97, 23);
		this.add(btnNext);

		JPanel gradePanel = new JPanel();
		gradePanel.setForeground(Color.WHITE);
		gradePanel.setBackground(Color.WHITE);
		gradePanel.setBorder(null);
		gradePanel.setBounds(0, 0, 434, 72);
		add(gradePanel);
		gradePanel.setLayout(null);

		JLabel lblEnterYourTranscript = new JLabel("성적 정보를 입력하세요 (.xls):");
		lblEnterYourTranscript.setBounds(12, 10, 183, 15);
		gradePanel.add(lblEnterYourTranscript);

		JButton btnSearchTranscript = new JButton("Search...");
		btnSearchTranscript.setBounds(22, 35, 97, 23);
		gradePanel.add(btnSearchTranscript);

		txtfldTranscript = new JTextField();
		txtfldTranscript.setBounds(128, 36, 282, 21);
		gradePanel.add(txtfldTranscript);
		txtfldTranscript.setColumns(10);

		JPanel currentSugangPanel = new JPanel();
		currentSugangPanel.setForeground(Color.WHITE);
		currentSugangPanel.setBorder(null);
		currentSugangPanel.setBackground(Color.WHITE);
		currentSugangPanel.setBounds(0, 72, 434, 72);
		add(currentSugangPanel);
		currentSugangPanel.setLayout(null);

		JLabel currentSugangLabel = new JLabel("현재 수강 정보를 입력하세요 (.xls):");
		currentSugangLabel.setBounds(12, 10, 214, 15);
		currentSugangPanel.add(currentSugangLabel);

		currentSugangText = new JTextField();
		currentSugangText.setBounds(128, 41, 282, 21);
		currentSugangPanel.add(currentSugangText);
		currentSugangText.setColumns(10);

		JButton currentSugangButton = new JButton("Search...");
		currentSugangButton.setBounds(22, 39, 97, 23);
		currentSugangPanel.add(currentSugangButton);

		JPanel mainConfPanel = new JPanel();
		mainConfPanel.setBackground(Color.WHITE);
		mainConfPanel.setForeground(Color.WHITE);
		mainConfPanel.setBorder(null);
		mainConfPanel.setBounds(0, 143, 434, 72);
		add(mainConfPanel);
		mainConfPanel.setLayout(null);

		JLabel mainConfLabel = new JLabel("주전공 졸업요건 (.conf):");
		mainConfLabel.setBounds(12, 10, 188, 15);
		mainConfPanel.add(mainConfLabel);

		JButton mainConfButton = new JButton("Search...");
		mainConfButton.setBounds(22, 35, 97, 23);
		mainConfPanel.add(mainConfButton);

		mainConfText = new JTextField();
		mainConfText.setBounds(128, 36, 282, 21);
		mainConfPanel.add(mainConfText);
		mainConfText.setColumns(10);

		JPanel subConfPanel = new JPanel();
		subConfPanel.setBorder(null);
		subConfPanel.setForeground(Color.WHITE);
		subConfPanel.setBackground(Color.WHITE);
		subConfPanel.setBounds(0, 215, 434, 75);
		add(subConfPanel);
		subConfPanel.setLayout(null);

		JButton subConfButton = new JButton("Search...");
		subConfButton.setBounds(22, 42, 97, 23);
		subConfPanel.add(subConfButton);

		subConfText = new JTextField();
		subConfText.setBounds(131, 43, 282, 21);
		subConfPanel.add(subConfText);
		subConfText.setColumns(10);

		JLabel subConfeLabel = new JLabel("복/부 전공 졸업요건 (.conf):");
		subConfeLabel.setBounds(12, 17, 188, 15);
		subConfPanel.add(subConfeLabel);
		subConfButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(me);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					secondProgram = file.getAbsolutePath().toString();
					subConfText.setText(secondProgram);
				}
				else {
					System.out.println("File search failed: Sub program");
				}
			}
		});
		mainConfButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(me);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					mainProgram = file.getAbsolutePath().toString();
					mainConfText.setText(mainProgram);
				}
				else {
					System.out.println("File search failed: Main program");
				}
			}
		});
		currentSugangButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(root.frame);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					currentSugang = file.getAbsolutePath().toString();
					currentSugangText.setText(currentSugang);
				}
				else {
					System.out.println("File search failed: currentSugang");
				}
			}
		});
		btnSearchTranscript.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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

		transcriptListener = new FileDrop.Listener(){
			public void  filesDropped( java.io.File[] files ){
				if(files.length > 0)
				{
					File file = files[0];
					transcript = file.getAbsolutePath().toString();
					txtfldTranscript.setText(transcript);
				}
			}
		};

		currentSugangListener = new FileDrop.Listener(){
			public void  filesDropped( java.io.File[] files ){
				if(files.length > 0)
				{
					File file = files[0];
					currentSugang = file.getAbsolutePath().toString();
					currentSugangText.setText(currentSugang);
				}
			}
		};

		mainConfListener = new FileDrop.Listener(){
			public void  filesDropped( java.io.File[] files ){
				if(files.length > 0)
				{
					File file = files[0];
					mainProgram = file.getAbsolutePath().toString();
					mainConfText.setText(mainProgram);
				}
			}
		};

		subConfListener = new FileDrop.Listener(){
			public void  filesDropped( java.io.File[] files ){
				if(files.length > 0)
				{
					File file = files[0];
					secondProgram = file.getAbsolutePath().toString();
					subConfText.setText(secondProgram);
				}
			}
		};

		new FileDrop(gradePanel, fileBoarder, true, transcriptListener);
		new FileDrop(currentSugangPanel, fileBoarder, true, currentSugangListener);
		new FileDrop(mainConfPanel, fileBoarder, true, mainConfListener);
		new FileDrop(subConfPanel, fileBoarder, true, subConfListener);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtfldTranscript.setText("");
				mainConfText.setText("");
				subConfText.setText("");
				currentSugangText.setText("");
			}
		});
		clearButton.setBounds(209, 313, 97, 23);
		add(clearButton);
	}
}
