package xbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection
{
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;

	public Connection()
	{
		System.out.print("Connecting");
		while(true)
		{
			try
			{
				socket = new Socket("192.168.0.4", 44666);
				// socket = new Socket("127.0.0.1", 44666);

				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				break;
			}
			catch(Exception e)
			{
				System.out.print(".");
			}
		}
		System.out.println("Connected");
	}

	public void write(String s)
	{
		System.out.println(s);
		out.println(s);
		out.flush();

		try
		{
			in.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
