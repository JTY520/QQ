package Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileUpServer {
public static void main(String[] args){
	        Socket socket = null;
	        try {
	            ServerSocket ss = new ServerSocket(8001);
	                socket = ss.accept();

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

	                // ��ȡ�ļ���
	                String file="D:/JavaWork/TcpFile/" + inputStream.readUTF();
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
	        } catch (Exception e) {
	            System.out.println("������Ϣ����" + "\n");
	            e.printStackTrace();
	            return;
	        }
	    }
}
