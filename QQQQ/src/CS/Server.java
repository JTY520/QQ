package CS;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Server.Socket.MessagerHandler;

public class Server {
	public static HashMap<String, PrintWriter> usersOnline= new HashMap<String,PrintWriter>();
	public static void main(String[] args) throws Exception{//一对多的聊天
		ServerSocket server = new ServerSocket(8880);
		while(true){
			System.out.println("正在等待。。。");
			Socket socket = server.accept();
			MessagerHandler handler = new MessagerHandler(socket);
			Thread thread = new Thread(handler);
			thread.start();
			}
		}
}
