package Client.Account;

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

import Model.User;
import Personally.Personal;

public class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	private  JFrame frame = new JFrame("Login");
	private  JPanel pan = new JPanel();
	private  JLabel title = new JLabel("Q Q 登 陆");
	private  JLabel userLable = new JLabel("用户名");
	private JTextField userName = new JTextField();
	private  JLabel passwordLabel = new JLabel("密码");
	private JPasswordField password = new JPasswordField();
	private  JButton login = new JButton("登 陆 账 号");
	private  JButton exit = new JButton("退出");
	private  JButton register = new JButton("注 册");
	private  Font labelFont = new Font("华文新魏", 20, 20);
	
	private Socket socket;
	private BufferedReader buffer_reader;
	private PrintWriter writer;
	
	public Login(Socket socket) throws Exception {
		
		this.socket = socket;
		this.writer=new PrintWriter(socket.getOutputStream());
		this.buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    loginView();
	}
	
	public void loginView(){
		//外围
				frame.setSize(400, 400);
				frame.setBounds(500, 200, 400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				//内围
				pan.setLayout(null);
				pan.setBounds(0, 0, 400, 350);
				pan.setBackground(Color.white);
				frame.add(pan);
				//title
				title.setBounds(120, 30, 400, 80);
				title.setForeground(Color.black);
				title.setFont(new Font("华文新魏", 40, 40));
				pan.add(title);
				//用户名
				userLable.setBounds(30, 150, 100, 30);
				userLable.setFont(labelFont);
				userLable.setForeground(Color.black);
				userName.setBounds(120, 150, 200, 30);
				userName.setFont(new Font("华文新魏", Font.BOLD, 20));
				pan.add(userLable);
				pan.add(userName);
				//密码
				passwordLabel.setBounds(30, 210, 100, 30);
				passwordLabel.setFont(labelFont);
				passwordLabel.setForeground(Color.black);
				pan.add(passwordLabel);
				password.setBounds(120, 210, 200, 30);
				pan.add(password);
				//btn 登陆
				login.setBounds(120, 270, 120, 30);
				login.setForeground(Color.white);
				login.setBackground(new Color(9,163,220));
				login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				pan.add(login);
				//btn 退出
				exit.setBounds(250, 270, 70, 30);
				exit.setForeground(Color.white);
				exit.setBackground(new Color(9,163,220));
				exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				pan.add(exit);
				//btn 注册
				register.setBounds(300, 310, 70, 30);
				register.setBackground(Color.white);
				register.setForeground(Color.black);
				pan.add(register);
				//链接
				register.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e){
						//frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
						frame.dispose();
						try {
							new Register(socket);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				//监听器
				
				//退出
				exit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (JOptionPane.showConfirmDialog(null, "退出？", "信息提示",
								JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION){
							
						}
							
					}
				});
				//登陆
				login.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						String name = userName.getText().trim();
						String pass = new String(password.getPassword()).trim();
						if ("".equals(name)) {
							JOptionPane.showMessageDialog(null, "用户名空了");
							return;
						}
						if ("".equals(pass)) {
							JOptionPane.showMessageDialog(null, "确认密码为空");
							return;
						}
						//JOptionPane.showMessageDialog(null, "注册成功！");
						String message = "<login  name='" + name + "' password='" + pass + "'/>";
						//跳转界面
						writer.println(message);
						writer.flush();
						String echo= null;
						try {
							echo = buffer_reader.readLine();
							JOptionPane.showMessageDialog(null,echo);
							if(echo.startsWith("<result success")){
								User user = new User(name,pass);
						          frame.dispose();
						          new Personal(user,socket);
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					}
				});
	}
}
