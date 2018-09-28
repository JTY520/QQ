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

          // 选择进行传输的文件
          File fi = new File("D:/JavaWork/" + "11.docx");
          System.out.println("文件长度:" + (int) fi.length());

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
          // 注意关闭socket链接哦，不然客户端会等待server的数据过来，
          // 直到socket超时，导致数据不完整。
          fis.close();
          ps.close();
          s.close();
          System.out.println("文件传输完成");
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
