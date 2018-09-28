package Personally;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.Account.Login;
import Client.Chat.Chat;
import Model.User;
import Server.Sql.GetUsers;

public class Personal extends JFrame {

	private static final long serialVersionUID = 1L;

	private User user;
	private JFrame frame = new JFrame("个人页面");
	private JPanel pan = new JPanel();// 内围
	private JLabel title;
	private JTextField searchTxt = new JTextField();
	private JButton searchBtn = new JButton("搜索");
	private JLabel borderLable = new JLabel("--------------");
	private JPanel friendsPan = new JPanel();// 内容
	private JButton logout = new JButton("注销");

	private Socket socket;
	private BufferedReader buffer_reader;
	private PrintWriter writer;

	public Personal(User user, Socket socket) throws Exception {
		this.user = user;
		this.socket = socket;
		this.writer = new PrintWriter(socket.getOutputStream());
		this.buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		personalView();
	}

	public void personalView() throws Exception {
		GetUsers get = new GetUsers();
		ArrayList<String> friends = get.GetFriends(user);
		ArrayList<String> groups = get.GetGroups(user);
		
		// 外围
		frame.setBounds(500, 150, 300, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		// 内围
		pan.setLayout(null);
		pan.setBounds(0, 0, 300, 650);
		pan.setBackground(Color.white);
		frame.add(pan);
		// title
		title = new JLabel(user.getName());
		title.setBounds(50, 0, 200, 80);
		title.setBackground(Color.white);
		title.setFont(new Font("华文新魏", 40, 40));
		pan.add(title);
		// search
		searchTxt.setBounds(10, 60, 200, 30);
		searchTxt.setBackground(Color.white);
		pan.add(searchTxt);
		searchBtn.setBounds(230, 60, 70, 30);
		searchBtn.setForeground(Color.black);
		pan.add(searchBtn);
		// btn 注销
		logout.setBounds(200, 95, 70, 30);
		logout.setBackground(Color.cyan);
		pan.add(logout);

		// border
		borderLable.setBounds(0, 126, 300, 3);
		borderLable.setBackground(Color.black);
		borderLable.setFont(new Font("宋体", 40, 40));
		pan.add(borderLable);
		// content
		friendsPan.setLayout(null);
		friendsPan.setBounds(10, 135, 280, 500);
		friendsPan.setBackground(Color.white);
		pan.add(friendsPan);
		
		//Friends
		JLabel friendLable = new JLabel("我的好友");
		friendLable.setBounds(10,0,200,30);
		friendsPan.add(friendLable);
		
		if(friends.size() == 0){
			 JButton button = new JButton("你还没有好友"); 
			 button.setBackground(Color.white);
			 friendsPan.add(button); 
			 button.setBounds(10, 30, 200, 30);
		}
		else{
			for(int i = 0; i < friends.size();i++){
				JButton button = new JButton(friends.get(i)); 
			    button.addActionListener(new ButtonListener(button));
			    friendsPan.add(button); 
			    button.setBounds(10, (i+1) * 30, 200, 30);
			    button.setBackground(Color.white);
			}
		}
		JLabel groupLable = new JLabel("我的群");
		groupLable.setBounds(10,250,250,30);
		friendsPan.add(groupLable);
		if(groups.size() == 0){
			JButton button = new JButton("你还没有加入群"); 
			 button.setBounds(10, 300, 200, 30);
			 button.setBackground(Color.white);
			 friendsPan.add(button); 
		}
		else{
			for(int i = 0; i < groups.size();i++){
				JButton button = new JButton(groups.get(i)); 
			    button.addActionListener(new ButtonListener(button));
			    friendsPan.add(button); 
			    button.setBackground(Color.white);
			    button.setBounds(10, 270+(i+1) * 30, 200, 30);
			}
		}
		 // 注销
		 logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, "退出？", "信息提示",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
					String message = "<logout  name='" + user.getName() + "' " + user.getPassword() + "'/>";
					writer.println(message);
					writer.flush();
					String echo = null;
					try {
						echo = buffer_reader.readLine();
						JOptionPane.showMessageDialog(null, echo);
						if (echo.startsWith("<result success")) {
							frame.dispose();
							try {
								new Login(socket);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.dispose();
					try {
						new Login(socket);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// frame.setDefaultCloseOperation(HIDE_ON_CLOSE);

			}
		});
	
		 //
		 searchBtn.addActionListener(new ActionListener() {
			 @Override
				public void actionPerformed(ActionEvent e) {
					String txt = searchTxt.getText().trim();
					if ("".equals(txt)) {
						JOptionPane.showMessageDialog(null, "内容不能为空");
						return;
					}
					try {
						new Search(socket,user,txt);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			 }
		 });
	}
	public class ButtonListener implements ActionListener{
		public JButton button;
		public ButtonListener(JButton button) {
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new Chat(socket,user,this.button.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//Client.chatInterfaces.put(this.button.getText(), chatInterface);
			//Server.chatInterfaces.add(chatInterface); 
		}
	} 
}
