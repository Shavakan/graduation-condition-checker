package org.sparcs.gnu.gui;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import org.sparcs.gnu.checker.ProcessInfo;

public class VisualizeResult extends GCCContainer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, JLabel> names;
	private HashMap<String, BarGraph> bars;
	private HashMap<String, JLabel> scores;

	/**
	 * Create the application.
	 */
	public VisualizeResult(GUIMain root) {
		super(root);
		names = new HashMap<String, JLabel>();
		bars = new HashMap<String, BarGraph>();
		scores = new HashMap<String, JLabel>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		{
			JLabel label = new JLabel();
			label.setText("기필학점");
			label.setBounds(20, 30, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("기선학점");
			label.setBounds(20, 70, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전필학점");
			label.setBounds(20, 110, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전선학점");
			label.setBounds(20, 150, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("연구학점");
			label.setBounds(20, 190, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("교필학점");
			label.setBounds(20, 230, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("인선학점");
			label.setBounds(20, 270, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("교필AU");
			label.setBounds(20, 310, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("자선학점");
			label.setBounds(20, 350, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전전공필수");
			label.setBounds(20, 390, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("전전공학점");
			label.setBounds(20, 430, 110, 23);
			names.put(label.getText(), label);
		}
		{
			JLabel label = new JLabel();
			label.setText("평점");
			label.setBounds(20, 470, 110, 23);
			names.put(label.getText(), label);
		}	
		{
			JLabel label = new JLabel();
			label.setText("이수학점");
			label.setBounds(20, 510, 110, 23);
			names.put(label.getText(), label);
		}
		for(JLabel l : names.values())
		{
			Rectangle r = l.getBounds();
			BarGraph b = new BarGraph(150, r.y, 550, r.height);
			bars.put(l.getText(), b);
			JLabel label = new JLabel();
			label.setBounds(710, r.y, 80, r.height);
			scores.put(l.getText(), label);
			this.add(l);
			this.add(b);
			this.add(label);
		}
	}

	public void update(ProcessInfo info)
	{
		for(String barName : bars.keySet())
		{
			String keyName = barName;
			if(!info.checkKey(barName))
			{
				if (barName.equals("전전공학점")||barName.equals("전전공필수"))
				{
					int flag = 0;
					for(String k : info.getKeys())
					{
						if(k.endsWith(barName))
						{
							flag = 1;
							keyName = k;
							break;
						}
					}
					if (flag != 1)
					{
						bars.get(barName).setVisible(false);
						scores.get(barName).setVisible(false);
						names.get(barName).setVisible(false);
					}
					else
					{
						names.get(barName).setText(keyName);
						bars.get(barName).setVisible(true);
						scores.get(barName).setVisible(true);
						names.get(barName).setVisible(true);
					}
				}
				else
					continue;
			}
			BarGraph bar = bars.get(barName);
			double total = info.getTotal(keyName);
			double complete = info.getComplete(keyName);
			double exception = info.getException(keyName);
			double mustFail = info.getFail(keyName);

			if(total < 0 || complete < 0 || exception < 0 || mustFail < 0)
			{
				System.err.println("No barname " + keyName);
				continue;
			}
			bar.setLength(Math.min(complete, total - exception - mustFail), exception, Math.max(mustFail, total - complete - exception));

			List<String> taken = info.getTakenList(keyName);
			if(taken != null)
			{
				String greenText = "<html><body>";
				for(String item : taken)
				{
					greenText += "<p><b>" + item + "</b></p><br>";
				}
				File pic = new File("resource" + File.separator + "green.jpg");
				long defaultWidth = 200;
				long defaultHeight = 200;
				try
				{
					BufferedImage bimg = ImageIO.read(pic);
					defaultHeight = bimg.getHeight();
					defaultWidth = bimg.getWidth();
				}
				catch(Exception e)
				{
					e.printStackTrace(System.err);
				}
				double ratio = (double)defaultWidth / 200;
				defaultWidth = 200;
				defaultHeight = Math.round((double)defaultHeight / ratio);
				greenText += "<img src=\"" + pic.toURI() + "\" height=" + defaultHeight + " width=" + defaultWidth + "></body></html>";
				bar.setGreenText(greenText);
			}
			List<String> exceptionList = info.getExceptionList(keyName);
			if(exceptionList != null)
			{
				String yellowText = "<html><body>";
				for(String item : exceptionList)
				{
					yellowText += "<p><b>" + item + "</b></p><br>";
				}
				File pic = new File("resource" + File.separator + "yellow.jpg");
				long defaultWidth = 200;
				long defaultHeight = 200;
				try
				{
					BufferedImage bimg = ImageIO.read(pic);
					defaultHeight = bimg.getHeight();
					defaultWidth = bimg.getWidth();
				}
				catch(Exception e)
				{
					e.printStackTrace(System.err);
				}
				double ratio = (double)defaultWidth / 200;
				defaultWidth = 200;
				defaultHeight = Math.round((double)defaultHeight / ratio);
				yellowText += "<img src=\"" + pic.toURI() + "\" height=" + defaultHeight + " width=" + defaultWidth + "></body></html>";
				bar.setYellowText(yellowText);
			}
			List<String> failList = info.getFailList(keyName);
			if(failList != null)
			{
				String redText = "<html><body>";
				for(String item : failList)
				{
					redText += "<p><b>" + item + "</b></p><br>";
				}
				File pic = new File("resource" + File.separator + "red.jpg");
				long defaultWidth = 200;
				long defaultHeight = 200;
				try
				{
					BufferedImage bimg = ImageIO.read(pic);
					defaultHeight = bimg.getHeight();
					defaultWidth = bimg.getWidth();
				}
				catch(Exception e)
				{
					e.printStackTrace(System.err);
				}
				double ratio = (double)defaultWidth / 200;
				defaultWidth = 200;
				defaultHeight = Math.round((double)defaultHeight / ratio);
				redText += "<img src=\"" + pic.toURI() + "\" height=" + defaultHeight + " width=" + defaultWidth + "></body></html>";
				bar.setRedText(redText);
			}

			JLabel label = scores.get(barName);
			if (barName.equals("평점"))
				label.setText("["+String.format("%.02f", (complete))+"/"+String.format("%.01f", (total-exception))+"]");	
			else
				label.setText("["+Math.round(complete)+"/"+Math.round(total-exception)+"]");	
		}
	}

	private class BarGraph extends JPanel
	{
		private String current = "None";

		private String greenText = "";
		private String yellowText = "";
		private String redText = "";

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
			setToolTipText("");
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
				setToolTipText(greenText);
				x = green_start + green_len / 2;
				y = this.getHeight()/2;
			}
			else if(e.getX() < yellow_start + yellow_len)
			{
				current = "Yellow";
				setToolTipText(yellowText);

				x = yellow_start + yellow_len / 2;
				y = this.getHeight()/2;
			}
			else
			{
				current = "Red";
				setToolTipText(redText);

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

		public void setGreenText(String html)
		{
			greenText = html;
		}

		public void setYellowText(String html)
		{
			yellowText = html;
		}

		public void setRedText(String html)
		{
			redText = html;
		}

		public void setLength(double green, double yellow, double red)
		{
			double total = green + yellow + red;
			if (total>0.00001)
			{
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
			}
			else
			{
				this.green_start = 0;
				this.yellow_start = this.red_start = this.getWidth();
				this.yellow_len = this.red_len = 0;
				this.green_len = this.getWidth();
			}

			this.invalidate();
		}
	}
}

