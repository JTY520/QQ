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
	                // IOException���������ܵ����׽��ֵ����ӡ��˷����ڽ�������֮ǰһֱ������
	                System.out.println("����socket����");
	                DataInputStream inputStream = new DataInputStream(
	                        new BufferedInputStream(socket.getInputStream()));
	                
	                // ���ر���·�����ļ������Զ��ӷ������˼̳ж�����
	                int bufferSize = 8192;
	                byte[] buf = new byte[bufferSize];
	                long passedlen = 0;
	                long len = 0;
	                
	                if(makeDir(userName+"-"+toName)){
	                	 // ��ȡ�ļ���
	                String file="D:/JavaWork/"+userName+"-"+toName +"/"+ inputStream.readUTF();
	                DataOutputStream fileOut = new DataOutputStream(
	                        new BufferedOutputStream(new FileOutputStream(file)));
	                len = inputStream.readLong();

	                System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
	                System.out.println("��ʼ�����ļ�!" + "\n");
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
	                      // �����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
	                    System.out.println("�ļ�������" + (passedlen * 100 / len)
	                            + "%\n");
	                    fileOut.write(buf, 0, read);
	                System.out.println("������ɣ��ļ���Ϊ" + file + "\n");

	                fileOut.close();
	                }
	        } catch (Exception e) {
	            System.out.println("������Ϣ����" + "\n");
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
