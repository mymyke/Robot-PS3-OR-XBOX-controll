package xbox;

import java.awt.Point;

public class XboxMain
{
	private static boolean debug=true;
	public static void main(String[] args)
	{
		Xbox360Controller c = new Xbox360Controller();
		if(!c.poll())
		{
			System.out.println("Controller disconnected!");
			System.exit(0);
		}

		Point left = new Point(50, 50);
		Point right = new Point(50, 50);
		
		//Connection connection = new Connection();
		Send connection=new Send(!debug);
		connection.write("verbunden");
		Frame frame = new Frame(left, right);

		String t = "";
		String l = "";

		long lastpoll = 0;

		for (;;)
		{
			if(!c.poll())
			{
				System.out.println("Controller disconnected!");
				System.exit(0);
			}

			left.x = (int)(c.getXAxis()*100);
			left.y = (int)(c.getYAxis()*(-100));
			
			right.x = (int)((c.getXRotation()+1)*1000);
			right.y = (int)(c.getYRotation() * 50 + 50);
			
			/*if(c.getRB() && lastpoll + 200 < System.currentTimeMillis())
			{
				right.x = (int)(c.getXRotation() * 50 + 50);
				right.y = (int)(c.getYRotation() * 50 + 50);

				lastpoll = System.currentTimeMillis();
			}*/
			
			int claws=(int)((c.getZAxis())*8);
			if(claws<0){
				claws*=(-1);
			}

			if(!c.getLB())
			{
				left.x /= 2;
				left.y /= 2;
			}
			
			if(c.getRB()){
				left.x/=2;
				left.y/=2;
			}
			int findcolor=(c.getDPadUp() ? 1:0)+(c.getDPadRight() ? 3:0)+
							(c.getDPadDown() ? 5:0)+(c.getDPadLeft() ? 7:0) ;
			
			boolean stopsearch=c.getRB();

			t = left.y + " " +left.x;
			t +=" "+right.y+ " " + right.x;
			t +=" "+claws;
			t +=" "+findcolor;
			t +=" "+stopsearch;
			//frame.repaint();

			if(!l.equals(t))
			{
				if(debug)
					System.out.println("\n"+t);
				
				connection.write(t);
				l = t;
				msleep(100);
			}
			
		}
	}

	private static void msleep(long ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
