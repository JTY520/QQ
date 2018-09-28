package Server.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Model.User;
import Server.Sql.Account;
import Server.Sql.Message;

public class MessagerHandler implements Runnable {
	private Socket socket;

	public MessagerHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void run(){
		try {
			InputStreamReader reader = new InputStreamReader(socket.getInputStream());
			BufferedReader buffer_reader = new BufferedReader(reader);
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			String client = "<" + socket.getInetAddress().toString() + " : " + socket.getPort() + ">";
			writer.flush();
			while (true) {
				String request = buffer_reader.readLine();
				if (request.startsWith("<register "))
				{
					Register(writer, request);
				} else if (request.startsWith("<login "))// <login name=”xu”/>登录
				{
					Login(writer, request);
				} else if (request.startsWith("<message"))// name="xu"toName="zhang"message="ddd" />
				{
					Speaking(writer, request);
				} else if (request.startsWith("<fileUp"))// name="xu"toName="zhang"message="ddd" />
				{
					Speaking(writer, request);
				}else if (request.startsWith("<createGroup "))// <login name=”xu”/>登录
				{
					createGroup(writer, request);
				} else if (request.startsWith("<logout"))// 退出登录//<logout name ="xu" />
				{
					Logout(writer, request);
					break;
				}
			}
			//request.close();
			socket.close();
			writer.close();
			buffer_reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Login(PrintWriter writer, String request) {
		String userName = null;
		String password = null;
		String[] temp = request.split("\\'");
		userName = temp[1];
		password = temp[3];
		User user = new User(userName,password);
		Account account = new Account();
		try {
			account.checkLogin(user,writer);
		} catch (Exception e) {
			writer.println("<result " + e.getMessage() + ">");
			writer.flush();
		}
	}
	
	public void FileUplode(PrintWriter writer, String request){
		String name = null;
		String time = null;
		String toName = null;// 得到收件人的名字
		String filePath = null;// 得到发送的信息
		String[] temp = request.split("\\'");
		name = temp[1];
		toName = temp[3];
		filePath = temp[5];
		time = temp[7];
		User user = new User(name,null);
		
		
		
	}

	public void Register(PrintWriter writer, String request) {
		String name = null;
		String password = null;
		String[] temp = request.split("\\'");
		name = temp[1];
		password = temp[3];
		User user = new User(name,password);
		Account account = new Account();
		try {
			account.register(user);
		} catch (Exception e) {
			writer.println("<result " + e.getMessage() + ">");
			writer.flush();
		}
	}

	public void Speaking(PrintWriter writer, String request) throws Exception {
		String name = null;
		String time = null;
		String toName = null;// 得到收件人的名字
		String message = null;// 得到发送的信息
		String[] temp = request.split("\\'");
		name = temp[1];
		toName = temp[3];
		message = temp[5];
		time = temp[7];
		User user = new User(name,null);
		Message m = new Message();
		if(CS.Server.usersOnline.get(toName) != null){
			PrintWriter toWriter = CS.Server.usersOnline.get(toName);
			toWriter.println("("+time+")"+name + " : " + message);// 向另一客户端写入信息
			toWriter.flush();
		}
		try{
			m.saveChatMessage(user,toName,message,time);
		}
		catch(Exception e){
			writer.println("<result " + e.getMessage() + ">");
			writer.flush();
		}
			
	}

	public void Logout(PrintWriter writer, String request) {
		String[] temp = request.split("\\'");
		String name = temp[1];
		User user = new User(name,null);
		Account account = new Account();
		try {
			account.logout(user);
		} catch (Exception e) {
			writer.println("<result " + e.getMessage() + ">");
			writer.flush();
		}
		
	}

	public void createGroup(PrintWriter writer, String request) {
		String groupName = null;
		String groupAdmin = null;
		String[] temp = request.split("\\'");
		groupName = temp[1];
		groupAdmin = temp[3];
		Server.Sql.Create c = new 	Server.Sql.Create();
		try{
			c.createGroup(groupName, groupAdmin);
		}
		 catch (Exception e) {
				writer.println("<result " + e.getMessage() + ">");
				writer.flush();
			}
	}

}
