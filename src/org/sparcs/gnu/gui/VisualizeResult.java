package org.sparcs.gnu.gui;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.sparcs.gnu.catalog.Catalog;
import org.sparcs.gnu.checker.GraduationChecker;
import org.sparcs.gnu.checker.ProcessInfo;
import org.sparcs.gnu.converter.Converter;
import org.sparcs.gnu.converter.SQLiteManager;
import org.sparcs.gnu.course.GradeInfo;
import org.sparcs.gnu.parser.Parse;

public class VisualizeResult {
	HashMap<String, BarGraph> bars;
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
					VisualizeResult window = new VisualizeResult();
					window.update(result);
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
		bars = new HashMap<String, BarGraph>();
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
			bar.setLength(complete, exception, total - complete - exception);
		}
	}

	private class BarGraph extends Canvas
	{
		private boolean isMouseIn = false;
		private String current = "None";
		
		private int green_start = 0;
		private int green_len = 0;
		private int yellow_start = 0;
		private int yellow_len = 0;
		private int red_start = 0;
		private int red_len = 0;
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
					current = "None";
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
					if(arg0.getX() < green_start + green_len)
					{
						current = "Green";
					}
					else if(arg0.getX() < yellow_start + yellow_len)
					{
						current = "Yellow";
					}
					else
					{
						current = "Red";
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
			
			
			green_start = 0;
			green_len = getWidth()/3;
			yellow_start = getWidth()/3;
			yellow_len = getWidth()/3;
			red_start = (getWidth() * 2)/3;
			red_len = getWidth()/3;			
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
			double red_start_ratio = 1-red/total;
			double red_len_ratio = red/total;
			double yellow_start_ratio = red_start_ratio - yellow/total;
			double yellow_len_ratio = yellow/total;
			double green_len_ratio = 1 - red_len_ratio - yellow_len_ratio;
			
			
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

