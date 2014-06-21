import net.java.games.input.*;

public class USBControllerTest {

 	public static void main(String[] args) { 
		System.out.println("JInput version: " + Version.getVersion()); 
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment(); 
		Controller[] cs = ce.getControllers(); 
		for (int i = 0; i < cs.length; i++) 
			System.out.println(i + ". " + cs[i].getName() + ", " + cs[i].getType() ); 
		}	  

}