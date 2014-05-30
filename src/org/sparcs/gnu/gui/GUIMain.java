package org.sparcs.gnu.gui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Panel;
import java.awt.TextField;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class GUIMain {

	private JFrame frame;
	private JTextField txtfldTranscript;
	private JTextField txtfldMainProgram;
	private JTextField txtfldSecondProgram;
	private JTextField txtfldThirdProgram;
	
	private JFileChooser fc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
					window.frame.setVisible(true);
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 256);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterYourTranscript = new JLabel("Enter your transcript file (.csv):");
		lblEnterYourTranscript.setBounds(12, 10, 183, 15);
		frame.getContentPane().add(lblEnterYourTranscript);
		
		txtfldTranscript = new JTextField();
		txtfldTranscript.setBounds(131, 35, 282, 21);
		frame.getContentPane().add(txtfldTranscript);
		txtfldTranscript.setColumns(10);
		
		JButton btnSearchTranscript = new JButton("Search...");
		btnSearchTranscript.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				
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
		frame.getContentPane().add(btnSearchTranscript);
		
		JLabel lblEnterYourCondition = new JLabel("Enter your condition files (.csv):");
		lblEnterYourCondition.setBounds(12, 66, 188, 15);
		frame.getContentPane().add(lblEnterYourCondition);
		
		txtfldMainProgram = new JTextField();
		txtfldMainProgram.setBounds(131, 86, 282, 21);
		frame.getContentPane().add(txtfldMainProgram);
		txtfldMainProgram.setColumns(10);
		
		JButton btnSearchMainProgram = new JButton("Search...");
		btnSearchMainProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				
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
		frame.getContentPane().add(btnSearchMainProgram);
		
		txtfldSecondProgram = new JTextField();
		txtfldSecondProgram.setBounds(131, 117, 282, 21);
		frame.getContentPane().add(txtfldSecondProgram);
		txtfldSecondProgram.setColumns(10);
		
		JButton btnSearchSecondProgram = new JButton("Search...");
		btnSearchSecondProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				
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
		frame.getContentPane().add(btnSearchSecondProgram);
		
		txtfldThirdProgram = new JTextField();
		txtfldThirdProgram.setBounds(131, 148, 282, 21);
		frame.getContentPane().add(txtfldThirdProgram);
		txtfldThirdProgram.setColumns(10);
		
		JButton btnSearchThirdProgram = new JButton("Search...");
		btnSearchThirdProgram.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(frame);
				
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
		frame.getContentPane().add(btnSearchThirdProgram);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GUIFileInputVerification next = new GUIFileInputVerification(
						txtfldTranscript.getText() , txtfldMainProgram.getText(), txtfldSecondProgram.getText(), txtfldThirdProgram.getText());
				next.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				next.setVisible(true);
			}
		});
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNext.setBounds(316, 179, 97, 23);
		frame.getContentPane().add(btnNext);
	}
}
