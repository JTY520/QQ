package Personally;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import Client.Chat.Chat;
import Client.Chat.GroupChat;
import Model.User;
import Server.Sql.GetUsers;

public class Search extends JFrame {
	private static final long serialVersionUID = -4334402744766449030L;

	private User user;
	private JFrame frame = new JFrame("个人页面");
	private JPanel pan = new JPanel();// 内围
	private JLabel title ;
	private JTextField searchTxt = new JTextField();
	private JButton searchBtn = new JButton("搜索");
	private JLabel borderLable = new JLabel("--------------");
	private JPanel searchPan = new JPanel();// 内容
	private JButton exit = new JButton("退出");
	private String txt;
	private Socket socket;

	public Search(Socket socket,User user, String txt) throws Exception {
		this.user = user;
		this.txt = txt;
		this.socket = socket;
		searchView();
	}

	public void searchView() throws Exception {
		GetUsers get = new GetUsers();
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
		// border
		borderLable.setBounds(0, 126, 300, 3);
		borderLable.setBackground(Color.black);
		borderLable.setFont(new Font("宋体", 40, 40));
		pan.add(borderLable);

		searchPan.setLayout(null);
		searchPan.setBounds(10, 135, 280, 500);
		searchPan.setBackground(Color.white);
		pan.add(searchPan);
		
		JLabel searchLable = new JLabel("搜索记录");
		searchLable.setBounds(10,0,250,30);
		searchPan.add(searchLable);
		
		// 搜索
		ArrayList<String> search;
		search = get.Search(txt);
		if (search.size() == 0) {
			
		}
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
					frame.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}

}
