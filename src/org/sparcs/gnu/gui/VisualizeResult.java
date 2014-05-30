package org.sparcs.gnu.gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import org.sparcs.gnu.checker.ProcessInfo;

public class VisualizeResult extends GCCContainer{
	private HashMap<String, BarGraph> bars;
	
	/**
	 * Create the application.
	 */
	public VisualizeResult(GUIMain root) {
		super(root);
		bars = new HashMap<String, BarGraph>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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
			this.getContainer().add(l);
			this.getContainer().add(b);
		}
	}
	
	public void update(ProcessInfo info)
	{
		for(String barName : bars.keySet())
		{
			BarGraph bar = bars.get(barName);
			double total = info.getTotal(barName);
			double complete = info.getComplete(barName);
			double exception = info.getException(barName);

			if(total < 0 || complete < 0 || exception < 0)
			{
				System.err.println("No barname " + barName);
				continue;
			}
			bar.setLength(Math.min(complete, total - exception), exception, Math.max(0, total - complete - exception));
		}
	}

	private class BarGraph extends JPanel
	{
		private boolean isMouseIn = false;
		private String current = "None";
		
		private int green_start = 0;
		private int green_len = 0;
		private int yellow_start = 0;
		private int yellow_len = 0;
		private int red_start = 0;
		private int red_len = 0;
		
		private Point toolTipLocation;
		public BarGraph(int x, int y, int w, int h)
		{
			setBounds(x, y, w, h);
			setToolTipText("기본");
			this.addMouseListener(new MouseListener() {
				
				private int prevInitDelay;
				private int prevDismissDelay;
				private int prevReshowDelay;

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
					current = "None";
					toolTipLocation = null;
					
					ToolTipManager.sharedInstance().setInitialDelay(prevInitDelay);
					ToolTipManager.sharedInstance().setDismissDelay(prevDismissDelay);
					ToolTipManager.sharedInstance().setReshowDelay(prevReshowDelay);
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					isMouseIn = true;
					System.out.println("Mouse In");
					current = "None";
					toolTipLocation = e.getPoint();
					
					prevInitDelay = ToolTipManager.sharedInstance().getInitialDelay();
					prevDismissDelay = ToolTipManager.sharedInstance().getDismissDelay();
					prevReshowDelay = ToolTipManager.sharedInstance().getReshowDelay();
					ToolTipManager.sharedInstance().setInitialDelay(0);
					ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
					ToolTipManager.sharedInstance().setReshowDelay(0);
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
					
				}
				
				@Override
				public void mouseDragged(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			
			green_start = 0;
			green_len = getWidth()/3;
			yellow_start = getWidth()/3;
			yellow_len = getWidth()/3;
			red_start = (getWidth() * 2)/3;
			red_len = getWidth()/3;			
		}
		
		@Override
		public Point getToolTipLocation(MouseEvent e)
		{
			String prev = current;
			int x = 0;
			int y = 0;
			if(e.getX() < green_start + green_len)
			{
				current = "Green";
				setToolTipText("그린");
				x = green_start + green_len / 2;
				y = this.getHeight()/2;
			}
			else if(e.getX() < yellow_start + yellow_len)
			{
				current = "Yellow";
				setToolTipText("옐로");
				
				x = yellow_start + yellow_len / 2;
				y = this.getHeight()/2;
			}
			else
			{
				current = "Red";
				setToolTipText("레드");
				
				x = red_start + red_len / 2;
				y = this.getHeight()/2;
			}
			if(!current.equals(prev))
			{
				System.out.println(current + " " + x + " " + y);
				toolTipLocation = new Point(x,y);
			}
			
			return toolTipLocation;
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paint(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.decode("#7CE700"));
			g.fillRect(green_start, 0, green_len, getHeight());
			g.setColor(Color.black);
			g.drawRect(green_start, 0, Math.min(getWidth()-green_start-1,green_len), getHeight()-1);
			g.setColor(Color.decode("#FFDE00"));
			g.fillRect(yellow_start, 0, yellow_len, getHeight());
			g.setColor(Color.black);
			g.drawRect(yellow_start, 0, Math.min(getWidth()-yellow_start-1,yellow_len), getHeight()-1);
			g.setColor(Color.decode("#FF0000"));
			g.fillRect(red_start, 0, red_len, getHeight());
			g.setColor(Color.black);
			g.drawRect(red_start, 0, Math.min(getWidth()-red_start-1,red_len), getHeight()-1);
		}
		
		public void setLength(double green, double yellow, double red)
		{
			double total = green + yellow + red;
			double green_start_ratio = 0;
			double green_len_ratio = green / total;
			double yellow_start_ratio = green_start_ratio + green_len_ratio;
			double yellow_len_ratio = yellow/total;

			double red_start_ratio = yellow_start_ratio + yellow_len_ratio;
			double red_len_ratio = red/total;
			
			
			this.green_start = (int)Math.floor(this.getWidth() * green_start_ratio);
			this.yellow_start = (int)Math.floor(this.getWidth() * yellow_start_ratio);
			this.red_start = (int)Math.floor(this.getWidth() * red_start_ratio);
			
			this.green_len = (int)Math.floor(this.getWidth() * green_len_ratio);
			this.yellow_len = (int)Math.floor(this.getWidth() * yellow_len_ratio);
			this.red_len = (int)Math.floor(this.getWidth() * red_len_ratio);
			
			this.invalidate();
		}
	}
}

