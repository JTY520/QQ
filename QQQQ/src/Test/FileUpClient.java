package Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FileUpClient {
	public static void main(String[] args){
		
		
	  Socket s = null;
      try {
          s = new Socket("localhost", 8001);

          // ѡ����д�����ļ�
          File fi = new File("D:/JavaWork/" + "11.docx");
          System.out.println("�ļ�����:" + (int) fi.length());

          DataInputStream fis = new DataInputStream(new FileInputStream("D:/JavaWork/" + "11.docx"));
          DataOutputStream ps = new DataOutputStream(s.getOutputStream());
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
          // ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
          // ֱ��socket��ʱ���������ݲ�������
          fis.close();
          ps.close();
          s.close();
          System.out.println("�ļ��������");
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
