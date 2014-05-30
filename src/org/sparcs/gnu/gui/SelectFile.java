package org.sparcs.gnu.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SelectFile extends GCCContainer{

	private JTextField txtfldTranscript;
	private JTextField txtfldMainProgram;
	private JTextField txtfldSecondProgram;
	private JTextField txtfldThirdProgram;
	
	private JFileChooser fc;


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
	private void initialize() {
		
		JLabel lblEnterYourTranscript = new JLabel("Enter your transcript file (.csv):");
		lblEnterYourTranscript.setBounds(12, 10, 183, 15);
		this.getContainer().add(lblEnterYourTranscript);
		
		txtfldTranscript = new JTextField();
		txtfldTranscript.setBounds(131, 35, 282, 21);
		this.getContainer().add(txtfldTranscript);
		txtfldTranscript.setColumns(10);
		
		JButton btnSearchTranscript = new JButton("Search...");
		btnSearchTranscript.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(getContainer());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtfldTranscript.setText(file.getAbsolutePath().toString());
				}
				else {
					System.out.println("File search failed: transcript");
				}
			}
		});
		btnSearchTranscript.setBounds(22, 34, 97, 23);
		this.getContainer().add(btnSearchTranscript);
		
		JLabel lblEnterYourCondition = new JLabel("Enter your condition files (.csv):");
		lblEnterYourCondition.setBounds(12, 66, 188, 15);
		this.getContainer().add(lblEnterYourCondition);
		
		txtfldMainProgram = new JTextField();
		txtfldMainProgram.setBounds(131, 86, 282, 21);
		this.getContainer().add(txtfldMainProgram);
		txtfldMainProgram.setColumns(10);
		
		JButton btnSearchMainProgram = new JButton("Search...");
		btnSearchMainProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(getContainer());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtfldMainProgram.setText(file.getAbsolutePath().toString());
				}
				else {
					System.out.println("File search failed: Main program");
				}
			}
		});
		btnSearchMainProgram.setBounds(22, 85, 97, 23);
		this.getContainer().add(btnSearchMainProgram);
		
		txtfldSecondProgram = new JTextField();
		txtfldSecondProgram.setBounds(131, 117, 282, 21);
		this.getContainer().add(txtfldSecondProgram);
		txtfldSecondProgram.setColumns(10);
		
		JButton btnSearchSecondProgram = new JButton("Search...");
		btnSearchSecondProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(getContainer());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtfldSecondProgram.setText(file.getAbsolutePath().toString());
				}
				else {
					System.out.println("File search failed: Sub program");
				}
			}
		});
		btnSearchSecondProgram.setBounds(22, 116, 97, 23);
		this.getContainer().add(btnSearchSecondProgram);
		
		txtfldThirdProgram = new JTextField();
		txtfldThirdProgram.setBounds(131, 148, 282, 21);
		this.getContainer().add(txtfldThirdProgram);
		txtfldThirdProgram.setColumns(10);
		
		JButton btnSearchThirdProgram = new JButton("Search...");
		btnSearchThirdProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(getContainer());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					txtfldThirdProgram.setText(file.getAbsolutePath().toString());
				}
				else {
					System.out.println("File search failed: Other program");
				}
			}
		});
		btnSearchThirdProgram.setBounds(22, 147, 97, 23);
		this.getContainer().add(btnSearchThirdProgram);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIFileInputVerification next = new GUIFileInputVerification(
						txtfldTranscript.getText() , txtfldMainProgram.getText(), txtfldSecondProgram.getText(), txtfldThirdProgram.getText());
				next.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				next.setVisible(true);
				root.showResult();
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNext.setBounds(316, 179, 97, 23);
		this.getContainer().add(btnNext);
	}
}
