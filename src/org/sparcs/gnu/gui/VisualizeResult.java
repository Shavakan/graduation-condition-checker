package org.sparcs.gnu.gui;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class VisualizeResult {
	HashMap<String, Canvas> bars;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisualizeResult window = new VisualizeResult();
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
	public VisualizeResult() {
		bars = new HashMap<String, Canvas>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 800, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		List<JLabel> allLabel = new LinkedList<>();
		{
			JLabel label = new JLabel();
			label.setText("기필학점");
			label.setBounds(50, 50, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("기선학점");
			label.setBounds(50, 90, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전필학점");
			label.setBounds(50, 130, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전선학점");
			label.setBounds(50, 170, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("연구학점");
			label.setBounds(50, 210, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("교필학점");
			label.setBounds(50, 250, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("인선학점");
			label.setBounds(50, 290, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("교필AU");
			label.setBounds(50, 330, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("자선학점");
			label.setBounds(50, 370, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("평점");
			label.setBounds(50, 410, 80, 23);
			allLabel.add(label);
		}
		{
			JLabel label = new JLabel();
			label.setText("이수학점");
			label.setBounds(50, 450, 80, 23);
			allLabel.add(label);
		}

		for(JLabel l : allLabel)
		{
			Rectangle r = l.getBounds();
			BarGraph b = new BarGraph(150, r.y, 550, r.height);
			bars.put(l.getText(), b);
			frame.getContentPane().add(l);
			frame.getContentPane().add(b);
		}
	}

	private class BarGraph extends Canvas
	{
		private boolean isMouseIn = false;
		private String current = "None";
		public BarGraph(int x, int y, int w, int h)
		{
			setBounds(x, y, w, h);
			this.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					isMouseIn = false;
					System.out.println("Mouse Out");
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					isMouseIn = true;
					System.out.println("Mouse In");
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
			this.addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent arg0) {
					// TODO Auto-generated method stub
					String prev = current;
					if(arg0.getX() < getWidth()/3)
					{
						current = "Red";
					}
					else if(arg0.getX() < getWidth()*2/3)
					{
						current = "Blue";
					}
					else
					{
						current = "Green";
					}
					if(!current.equals(prev))
					{
						System.out.println(current);
					}
				}
				
				@Override
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.decode("#7CE700"));
			g.fillRect(0, 0, getWidth()/3, getHeight());
			g.setColor(Color.black);
			g.drawRect(0, 0, getWidth()/3 , getHeight()-1);
			g.setColor(Color.decode("#FFDE00"));
			g.fillRect(getWidth()/3, 0, getWidth()/3, getHeight());
			g.setColor(Color.black);
			g.drawRect(getWidth()/3, 0, getWidth()/3 , getHeight()-1);
			g.setColor(Color.decode("#FF0000"));
			g.fillRect(getWidth()/3 * 2, 0, getWidth()/3, getHeight());
			g.setColor(Color.black);
			g.drawRect(getWidth()/3 * 2, 0, getWidth()/3, getHeight()-1);
		}
		
	}
}

