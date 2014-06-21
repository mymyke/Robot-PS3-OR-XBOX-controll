package xbox;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Xbox360Controller
{
	private static final String CONTROLLERNAME = "Controller (Xbox 360 Peripheriegerät)";
	private static final String CONTROLLERNAME2 = "Controller (Xbox 360 Wireless Receiver for Windows)";
	
	private Controller controller;

	private boolean disconnected;

	public Xbox360Controller()
	{
		this(0);
	}

	public Xbox360Controller(int index)
	{
		try
		{
			controller = getControllers()[index].getContoller();
		}
		catch(Exception e)
		{
			controller = null;
			disconnected = true;
		}
	}

	private Xbox360Controller(Controller controller)
	{
		this.controller = controller;
		disconnected = false;
	}

	public boolean poll()
	{
		if(disconnected)
			return false;
		try
		{
			controller.poll();
		}
		catch(Exception e)
		{
			disconnected = true;
			return false;
		}
		return true;
	}

	public int getYAxisPercentage()
	{
		System.out.println((int)(getYAxis() * 50 - 50));
		return (int)(getYAxis() * 50 - 50);
	}

	public float getYAxis()
	{
		if(disconnected)
			return 0;

		return controller.getComponents()[0].getPollData();
	}

	public int getXAxisPercentage()
	{
		return (int)(getXAxis() * 50 - 50);
	}

	public float getXAxis()
	{
		if(disconnected)
			return 0;
		return controller.getComponents()[1].getPollData();
	}

	public int getYRotationPercentage()
	{
		return (int)(getYRotation() * 50 - 50);
	}

	public float getYRotation()
	{
		if(disconnected)
			return 0;

		return controller.getComponents()[2].getPollData();
	}

	public int getXRotationPercentage()
	{
		return (int)(getXRotation() * 50 - 50);
	}

	public float getXRotation()
	{
		if(disconnected)
			return 0;

		return controller.getComponents()[3].getPollData();
	}

	public float getZAxis()
	{
		if(disconnected)
			return 0;
		return controller.getComponents()[4].getPollData()*100;
	}

	public boolean getA()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[5].getPollData() == 1f ? true : false;
	}

	public boolean getB()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[6].getPollData() == 1f ? true : false;
	}

	public boolean getX()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[7].getPollData() == 1f ? true : false;
	}

	public boolean getY()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[8].getPollData() == 1f ? true : false;
	}

	public boolean getLB()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[9].getPollData() == 1f ? true : false;
	}

	public boolean getRB()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[10].getPollData() == 1f ? true
				: false;
	}

	public boolean getBACK()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[11].getPollData() == 1f ? true
				: false;
	}

	public boolean getSTART()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[12].getPollData() == 1f ? true
				: false;
	}

	public boolean getLStick()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[13].getPollData() == 1f ? true
				: false;
	}

	public boolean getRStick()
	{
		if(disconnected)
			return false;
		return controller.getComponents()[14].getPollData() == 1f ? true
				: false;
	}

	public float getDPad()
	{
		if(disconnected)
			return 0;
		return controller.getComponents()[15].getPollData();
	}

	public boolean getDPadUp()
	{
		if(disconnected)
			return false;
		Float[] up = {0.25f, 0.125f, 0.375f};
		return contains(up, controller.getComponents()[15].getPollData());
	}

	public boolean getDPadDown()
	{
		if(disconnected)
			return false;
		Float[] down = {0.75f, 0.875f, 0.625f};
		return contains(down, controller.getComponents()[15].getPollData());
	}

	public boolean getDPadLeft()
	{
		if(disconnected)
			return false;
		Float[] left = {1f, 0.125f, 0.875f};
		return contains(left, controller.getComponents()[15].getPollData());
	}

	public boolean getDPadRight()
	{
		if(disconnected)
			return false;
		Float[] right = {0.5f, 0.375f, 0.625f};
		return contains(right, controller.getComponents()[15].getPollData());
	}

	private boolean contains(Object[] objects, Object object)
	{
		for (int i = 0; i < objects.length; i++)
		{
			if(objects[i].equals(object))
				return true;
		}
		return false;
	}

	private Controller getContoller()
	{
		return controller;
	}

	public static int count()
	{
		int count = 0;
		Controller[] controllers = ControllerEnvironment
				.getDefaultEnvironment().getControllers();

		for (int i = 0; i < controllers.length; i++)
		{
			String a=controllers[i].getName();
			if(controllers[i].getName().equals(CONTROLLERNAME)||controllers[i].getName().equals(CONTROLLERNAME2))
			{
				count++;
			}
		}

		return count;
	}

	private static Xbox360Controller[] getControllers()
	{
		Xbox360Controller[] xboxControllers = new Xbox360Controller[count()];
		int i2 = 0;

		Controller[] controllers = ControllerEnvironment
				.getDefaultEnvironment().getControllers();

		for (int i = 0; i < controllers.length; i++)
		{
			if(controllers[i].getName().equals(CONTROLLERNAME)||controllers[i].getName().equals(CONTROLLERNAME2))
			{
				xboxControllers[i2] = new Xbox360Controller(controllers[i]);
				i2++;

			}
		}

		return xboxControllers;
	}
	
}
