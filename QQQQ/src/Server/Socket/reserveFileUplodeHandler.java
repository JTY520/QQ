package Server.Socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class reserveFileUplodeHandler {
	private Socket socket;
	private String userName;
	private String toName;
	public reserveFileUplodeHandler(Socket socket,String userName,String toName){
		this.socket = socket;
		this.userName= userName;
		this.toName = toName;
	}
	public void run(){
	        try {
	                // public Socket accept() throws
	                // IOException侦听并接受到此套接字的连接。此方法在进行连接之前一直阻塞。
	                System.out.println("建立socket链接");
	                DataInputStream inputStream = new DataInputStream(
	                        new BufferedInputStream(socket.getInputStream()));
	                
	                // 本地保存路径，文件名会自动从服务器端继承而来。
	                int bufferSize = 8192;
	                byte[] buf = new byte[bufferSize];
	                long passedlen = 0;
	                long len = 0;
	                
	                if(makeDir(userName+"-"+toName)){
	                	 // 获取文件名
	                String file="D:/JavaWork/"+userName+"-"+toName +"/"+ inputStream.readUTF();
	                DataOutputStream fileOut = new DataOutputStream(
	                        new BufferedOutputStream(new FileOutputStream(file)));
	                len = inputStream.readLong();

	                System.out.println("文件的长度为:" + len + "\n");
	                System.out.println("开始接收文件!" + "\n");
	                 int read = 0;
	                while(true){
	                    if (inputStream != null) {
	                        read = inputStream.read(buf);
	                    }
	                    passedlen += read;
	                    if(read == -1){
	                    	break;
	                    }
	                }
	                      // 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
	                    System.out.println("文件接收了" + (passedlen * 100 / len)
	                            + "%\n");
	                    fileOut.write(buf, 0, read);
	                System.out.println("接收完成，文件存为" + file + "\n");

	                fileOut.close();
	                }
	        } catch (Exception e) {
	            System.out.println("接收消息错误" + "\n");
	            e.printStackTrace();
	            return;
	        }
	            

	               
	    }
	public boolean makeDir(String dirName){
		String folderName = "D:/JavaWork/"+dirName;
		
		File dir = new File(folderName);
		
		if(dir.isDirectory()){
			return true;
		}
		return false;
		
	}

}
