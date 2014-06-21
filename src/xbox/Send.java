package xbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Send {
	private static Socket socket;
	private static PrintWriter outw;
	private static BufferedReader in;
	private static OutputStream out;
	private static boolean debug;
	private static String ip="192.168.43.206";
	private static int port=7777;
	
	public Send(){
		this(true);
	}
	public Send(boolean wait) {
		debug=!wait;
		System.out.print("Connecting");
		while (wait) {
			try {
				socket = new Socket(ip, port);
				out = socket.getOutputStream();
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				break;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.print(".");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.print(".");
			}
			
		}

	}

	public void write(String t) {
		try {
			socket = new Socket(ip, port);
			out = socket.getOutputStream();
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out.write(t.getBytes());


		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(debug)
					System.out.print("keine Verbindung\n");
			
		}
		
	}
}
