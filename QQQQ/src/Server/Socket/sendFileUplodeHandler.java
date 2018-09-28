package Server.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class sendFileUplodeHandler {
private Socket socket;
private String filePath;
public sendFileUplodeHandler(Socket socket,String filePath){
	this.socket = socket;
	this.filePath=filePath;
}

public void run(){
	try{
	 File fi = new File(filePath);
	 DataInputStream fis = new DataInputStream(new FileInputStream(filePath));
     DataOutputStream ps = new DataOutputStream(socket.getOutputStream());
     ps.writeUTF(fi.getName());
     ps.flush();
     ps.writeLong((long) fi.length());
     ps.flush();

     int bufferSize = 8192;
     byte[] buf = new byte[bufferSize];

     while (true) {
         int read = 0;
         if (fis != null) {
             read = fis.read(buf);
         }

         if (read == -1) {
             break;
         }
         ps.write(buf, 0, read);
     }
     ps.flush();
     // 注意关闭socket链接哦，不然客户端会等待server的数据过来，
     // 直到socket超时，导致数据不完整。
     fis.close();
     ps.close();
     socket.close();
     System.out.println("文件传输完成");
 } catch (Exception e) {
     e.printStackTrace();
 }
}
}

