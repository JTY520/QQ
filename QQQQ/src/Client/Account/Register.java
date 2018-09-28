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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Client.Account.Login;

import java.net.Socket;

public class Register extends JFrame{
	private static final long serialVersionUID = 1L;
	private  JFrame frame = new JFrame("register");
	private  JPanel pan = new JPanel();
	private  JLabel title = new JLabel("Q Q ע ��");
	private  JLabel userLable = new JLabel("�û���");
	private JTextField userName = new JTextField();
	private  JLabel newPasswordLabel = new JLabel("����");
	private JPasswordField newPassword = new JPasswordField();
	private  JLabel PasswordLabel = new JLabel("ȷ������");
	private JPasswordField password = new JPasswordField();
	private  JButton register = new JButton("ע �� �� ��");
	private  JButton exit = new JButton("�˳�");
	private  JButton login = new JButton("��  ¼");
	private  Font labelFont = new Font("������κ", 20, 20);
	
	private Socket socket;
	private BufferedReader buffer_reader;
	PrintWriter writer;
	
	public Register(Socket socket) throws IOException {
		
		this.socket = socket;
		this.writer=new PrintWriter(socket.getOutputStream());
		this.buffer_reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    registerView();
	}
	

	public void registerView() throws IOException {
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
		userLable.setBounds(30, 150, 100, 30);
		userLable.setFont(labelFont);
		userLable.setForeground(Color.black);
		userName.setBounds(120, 150, 200, 30);
		userName.setFont(new Font("������κ", Font.BOLD, 20));
		pan.add(userLable);
		pan.add(userName);
		//����
		newPasswordLabel.setBounds(30, 190, 100, 30);
		newPasswordLabel.setFont(labelFont);
		newPasswordLabel.setForeground(Color.black);
		pan.add(newPasswordLabel);
		newPassword.setBounds(120, 190, 200, 30);
		pan.add(newPassword);
		//ȷ������
		PasswordLabel.setBounds(30, 230, 100, 30);
		PasswordLabel.setFont(labelFont);
		PasswordLabel.setForeground(Color.black);
		pan.add(PasswordLabel);
		password.setBounds(120, 230, 200, 30);
		pan.add(password);
		//btn ע��
		register.setBounds(120, 270, 120, 30);
		register.setForeground(Color.white);
		register.setBackground(new Color(9,163,220));
		register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pan.add(register);
		//btn �˳�
		exit.setBounds(250, 270, 70, 30);
		exit.setForeground(Color.white);
		exit.setBackground(new Color(9,163,220));
		exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pan.add(exit);
		//btn ��½
		login.setBounds(300, 310, 70, 30);
		login.setBackground(Color.white);
		login.setForeground(Color.black);
		pan.add(login);
		//����
		login.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				frame.dispose();
				try {
					new Login(socket);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//������
		
		//�˳�
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (JOptionPane.showConfirmDialog(null, "�˳���", "��Ϣ��ʾ",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.YES_OPTION){
					System.exit(0);
				}
					
			}
		});
		//ע��
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = userName.getText().trim();
				String newPass = new String(newPassword.getPassword()).trim();
				String pass = new String(password.getPassword()).trim();
				if ("".equals(name)) {
					JOptionPane.showMessageDialog(null, "�û�������");
					return;
				}
				if ("".equals(newPass)) {
					JOptionPane.showMessageDialog(null, "�������");
					return;
				}
				if (newPass.length() < 5 || newPass.length() > 18) {
					JOptionPane.showMessageDialog(null, "���벻�ϸ񣬳���Ӧ��5~17λ֮��");
					return;
				}
				if ("".equals(pass)) {
					JOptionPane.showMessageDialog(null, "ȷ������Ϊ��");
					return;
				}
				if (!newPass.equals(pass)) {
					JOptionPane.showMessageDialog(null, "�������벻һ�£����������");
					return;
				}
				//JOptionPane.showMessageDialog(null, "ע��ɹ���");
				//��ת����
				String message = "<register  name='" + name + "' password='" + pass + "'/>";
				writer.println(message);
				writer.flush();
				String echo= null;
				try {
					echo = buffer_reader.readLine();
					JOptionPane.showMessageDialog(null,echo);
					if(echo.startsWith("<result success")){
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
				
			}
		});
	}
}