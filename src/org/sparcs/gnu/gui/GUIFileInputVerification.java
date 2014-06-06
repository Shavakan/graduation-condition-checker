package org.sparcs.gnu.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIFileInputVerification extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GUIFileInputVerification dialog = new GUIFileInputVerification("This", "is", "not", "main");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GUIFileInputVerification(String transcript, String main, String second, String third) {
		setBounds(100, 100, 450, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPleaseVerifyThe = new JLabel("Please verify the following information:");
			lblPleaseVerifyThe.setBounds(12, 10, 410, 15);
			contentPanel.add(lblPleaseVerifyThe);
		}
		{
			JLabel lblTranscript = new JLabel("Transcript:");
			lblTranscript.setBounds(22, 35, 108, 15);
			contentPanel.add(lblTranscript);
			JLabel lblTranscriptLocation = new JLabel(transcript);
			lblTranscriptLocation.setBounds(142, 35, 57, 15);
			contentPanel.add(lblTranscriptLocation);
		}
		{
			JLabel lblMainProgram = new JLabel("Main Program:");
			lblMainProgram.setBounds(22, 60, 108, 15);
			contentPanel.add(lblMainProgram);
			JLabel lblMainLocation = new JLabel(main);
			lblMainLocation.setBounds(142, 60, 57, 15);
			contentPanel.add(lblMainLocation);
		}
		if (!second.equals("")) {
			JLabel lblSecondProgram = new JLabel("Second Program:");
			lblSecondProgram.setBounds(22, 85, 108, 15);
			contentPanel.add(lblSecondProgram);
			JLabel lblSecondLocation = new JLabel(second);
			lblSecondLocation.setBounds(142, 85, 57, 15);
			contentPanel.add(lblSecondLocation);
			if (!third.equals("")) {
				JLabel lblThirdProgram = new JLabel("Third Program:");
				lblThirdProgram.setBounds(22, 107, 108, 15);
				contentPanel.add(lblThirdProgram);
				JLabel lblThirdLocation = new JLabel(third);
				lblThirdLocation.setBounds(142, 107, 57, 15);
				contentPanel.add(lblThirdLocation);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO: Cancel button action
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
	}

}
