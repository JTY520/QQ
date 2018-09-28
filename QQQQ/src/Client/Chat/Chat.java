package Client.Chat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Model.User;

public class Chat extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private BufferedReader buffer_reader;
	private PrintWriter writer;
	private String toName;
	
	private JFrame frame = new JFrame("chating");
	private JPanel pan = new JPanel();// 内围
	private JLabel title;
	private JLabel borderLable = new JLabel("--------------");
	private JTextArea  speakTxt = new JTextArea ();
	private JTextArea  content = new JTextArea ();
	private JButton speakBtn = new JButton("发消息");
	private JButton fileBtn = new JButton("发文件");
	
	
   public Chat(Socket socket,User user,String toName) throws IOException{
	   this.user=user;
	   this.toName=toName;
	   this.writer=new PrintWriter(socket.getOutputStream());
	   this.buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       chatView();
   }
   
   public void chatView(){
	   frame.setBounds(400, 150,350, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// 内围
		pan.setLayout(null);
		pan.setBounds(0, 0,350, 550);
		pan.setBackground(Color.white);
		frame.add(pan);
		// title
		title = new JLabel(toName);
		title.setBounds(0, 0, 200, 55);
		title.setBackground(Color.white);
		title.setFont(new Font("华文新魏", 40, 40));
		pan.add(title);
		//border
		borderLable.setBounds(0,60, 350, 3);
		borderLable.setBackground(Color.black);
		borderLable.setFont(new Font("宋体", 40, 40));
		pan.add(borderLable);
		
		content.setBounds(0,65,330,250);
		content.setEditable(false);
		content.setLineWrap(true);
		content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pan.add(content);
		
		speakTxt.setBounds(10,350,300,80);
		speakTxt.setLineWrap(true);
		speakTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		speakBtn.setBounds(20, 450, 90, 30);
		pan.add(speakTxt);
		pan.add(speakBtn);
		
		/*try {
			content.append(buffer_reader.readLine());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		
		fileBtn.setBounds(230, 450, 90, 30);
		pan.add(fileBtn);
		
		speakBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String content2 = speakTxt.getText().trim();
				if ("".equals(content2)) {
					JOptionPane.showMessageDialog(null, "内容不能为空");
					return;
				}
				content.append("我说："+content2+"\n");
				Date now = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String message = "<message UserName = '"+user.getName()+"' toName='"+toName+"'+ message = '"+content2+"' speakTime = '"+dateFormat.format( now )+"'/>";
				writer.println(message);
				writer.flush();
				String echo= null;
				try {
					echo = buffer_reader.readLine();
					if(echo.startsWith("<result")){
						JOptionPane.showMessageDialog(null,echo);
					}
					else{
						content.append(echo+"\n");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});

		fileBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String content = speakTxt.getText().trim();
				if ("".equals(content)) {
					JOptionPane.showMessageDialog(null, "内容不能为空");
					return;
				}
				File file = new File(content);
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "您输入的路径不存在,请重新输入:");
					return;
				}
				if(file.isDirectory()){
					JOptionPane.showMessageDialog(null, "占不支持文件夹上传!请输入一个文件路径");
					return;
				}
				String line =null;
				try {
					BufferedReader buffer =new BufferedReader(new FileReader(file));
					while((line=buffer.readLine())!=null)
					{
					   writer.println(line);
					}
					JOptionPane.showMessageDialog(null,"发送完成");
					String result=buffer_reader.readLine();
					 JOptionPane.showMessageDialog(null,result);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				Date now = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String message = "<fileUp UserName = '"+user.getName()+"' toName='"+toName+"'+ message = '"+content+"' speakTime = '"+dateFormat.format( now )+"'/>";
				writer.println(message);
				writer.flush();
				String echo= null;
				try {
					echo = buffer_reader.readLine();
					JOptionPane.showMessageDialog(null,echo);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
}
   
  
   
}
