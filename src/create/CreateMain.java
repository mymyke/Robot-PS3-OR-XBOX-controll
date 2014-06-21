package create;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
//import linkjvm.create.Create;
//import linkjvm.motors.Servo;

public class CreateMain
{
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static BufferedReader in;

	public static void main(String[] args)
	{
		//Servo xservo = new Servo(2);
		//Servo yservo = new Servo(3);

		//xservo.enable();
		//yservo.enable();

		//Create create = new Create();
		//create.connect();
		//create.setMode(Create.Mode.FULL);

		System.out.println("Waiting for connection.");

		for (;;)
		{

			try
			{
				serverSocket = new ServerSocket(44666);
				socket = serverSocket.accept();

				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				System.out.println("Connected.");

				String line = in.readLine();
				out.println("1");
				out.flush();
				try
				{
					while((line = in.readLine()) != null)
					{

						System.out.println(line);
						int left = Integer.parseInt(line.split(" ")[0]);
						int right = Integer.parseInt(line.split(" ")[1]);
						//create.driveDirect(left, right);

						//xservo.setPosition(Integer.parseInt(line.split(" ")[2]));
						//yservo.setPosition(Integer.parseInt(line.split(" ")[3]));

						out.println("1");
						out.flush();
					}
				}
				catch(SocketException e)
				{

				}

				//create.driveDirect(0, 0);

			}
			catch(IOException e)
			{
				System.out
						.println("Connection lost, waiting for new connection...");

				try
				{
					in.close();
					socket.close();
					serverSocket.close();
					in = null;
					socket = null;
					serverSocket = null;
				}
				catch(IOException e2)
				{
					e2.printStackTrace();
				}

			}
		}
	}
}
