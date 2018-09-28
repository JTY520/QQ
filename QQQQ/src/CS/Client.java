package CS;


import java.net.Socket;

import Client.Account.Register;


public class Client {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 8880);
		//BufferedReader buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//PrintWriter writer = new PrintWriter(socket.getOutputStream());
		new Register(socket);
		
	}
}
