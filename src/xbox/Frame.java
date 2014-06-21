package xbox;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;


public class Frame
{
	private Point left;
	private Point right;
	private JFrame frame;
	private BufferStrategy buffer;
	
	
	public Frame(Point left, Point right)
	{
		this.left = left;
		this.right = right;
		initGUI();
	}
	
	private void initGUI()
	{
		frame = new JFrame("Controller");
		frame.setSize(400, 200);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.createBufferStrategy(2);
		buffer = frame.getBufferStrategy();
	}
	
	
	public void repaint()
	{
		Graphics g = buffer.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		draw(g2d);
		g.dispose();
		buffer.show();
	}

	
	private void draw(Graphics2D g)
	{
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, 400, 300);
		
		
		g.translate(40,50);
		g.setColor(new Color(255,255,255));
		g.drawRect(0, 0, 120, 120);	
		g.setColor(new Color(255,0,0));
		g.fillOval(10+left.x-5, 10+left.y-5, 10, 10);
		
		g.translate(200,0);
		g.setColor(new Color(255,255,255));
		g.drawRect(0, 0, 120, 120);
		g.setColor(new Color(255,0,0));
		g.fillOval(10+right.x-5, 10+right.y-5, 10, 10);
	}
	

	
	



}
