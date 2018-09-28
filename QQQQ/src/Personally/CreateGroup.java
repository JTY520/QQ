package Personally;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.Chat.GroupChat;
import Model.User;

public class CreateGroup extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private  JFrame frame = new JFrame("create");
	private  JPanel pan = new JPanel();
	private  JLabel title = new JLabel("�� �� Ⱥ");
	private  JLabel nameLable = new JLabel("Ⱥ����");
	private JTextField groupName = new JTextField();
	private  JButton uplode = new JButton("�� �� �� ��");
	//private  JButton exit = new JButton("�˳�");
	private  Font labelFont = new Font("������κ", 20, 20);
	
	private BufferedReader buffer_reader;
	private PrintWriter writer;
	private Socket socket;
	
	public CreateGroup(Socket socket,User user) throws Exception{
		this.user = user;
		this.socket=socket;
		 this.writer=new PrintWriter(socket.getOutputStream());
		 this.buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    createView();
	}
	
	public void createView(){
		//��Χ
		frame.setSize(400, 400);
		frame.setBounds(500, 200, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//��Χ
		pan.setLayout(null);
		pan.setBounds(0, 0, 400, 350);
		pan.setBackground(Color.white);
		frame.add(pan);
		//title
		title.setBounds(120, 30, 400, 80);
		title.setForeground(Color.black);
		title.setFont(new Font("������κ", 40, 40));
		pan.add(title);
		//�û���
		nameLable.setBounds(30, 150, 100, 30);
		nameLable.setFont(labelFont);
		nameLable.setForeground(Color.black);
		groupName.setBounds(120, 150, 200, 30);
		groupName.setFont(new Font("������κ", Font.BOLD, 20));
		pan.add(nameLable);
		pan.add(groupName);
		
		//btn ��½
		uplode.setBounds(120, 270, 120, 30);
		uplode.setForeground(Color.white);
		uplode.setBackground(new Color(9,163,220));
		uplode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pan.add(uplode);
		
		uplode.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = groupName.getText().trim();
				if ("".equals(name)) {
					JOptionPane.showMessageDialog(null, "���ֲ���Ϊ��");
					return;
				}
				//JOptionPane.showMessageDialog(null, "ע��ɹ���");
				String message = "<createGroup  groupName='" +name + "' groupAdmin='" + user.getName()  + "'/>";
				//��ת����
				writer.println(message);
				writer.flush();
				String echo= null;
				try {
					echo = buffer_reader.readLine();
					JOptionPane.showMessageDialog(null,echo);
					if(echo.startsWith("<result success")){
						frame.dispose();
						new GroupChat(socket,user,name);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
	}

}
