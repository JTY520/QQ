package CS;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Server.Socket.MessagerHandler;

public class Server {
	public static HashMap<String, PrintWriter> usersOnline= new HashMap<String,PrintWriter>();
	public static void main(String[] args) throws Exception{//һ�Զ������
		ServerSocket server = new ServerSocket(8880);
		while(true){
			System.out.println("���ڵȴ�������");
			Socket socket = server.accept();
			MessagerHandler handler = new MessagerHandler(socket);
			Thread thread = new Thread(handler);
			thread.start();
			}
		}
}
