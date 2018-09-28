package Client.Chat;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Test {
public static void main(String[] args){
	 JFrame frame = new JFrame("chating");
	 JPanel pan = new JPanel();// 内围
	 JLabel title = new JLabel("toName");

		 JTextArea  content = new JTextArea ();
	 JPanel contentPan = new JPanel();// 内容
	 JLabel borderLable = new JLabel("----------------");
	  JTextArea  speakTxt = new JTextArea ();
		 JButton speakBtn = new JButton("发消息");
		 JButton fileBtn = new JButton("发文件");
	 JPanel searchPan = new JPanel();
	 frame.setBounds(400, 150,350, 550);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			// 内围
			pan.setLayout(null);
			pan.setBounds(0, 0,350, 550);
			pan.setBackground(Color.white);
			frame.add(pan);
			// title
			title.setBounds(0, 0, 200, 55);
			title.setBackground(Color.white);
			title.setFont(new Font("华文新魏", 40, 40));
			pan.add(title);
			//border
			borderLable.setBounds(0,60, 350, 3);
			borderLable.setBackground(Color.black);
			borderLable.setFont(new Font("宋体", 40, 40));
			pan.add(borderLable);
			
			/*contentPan.setBounds(0,65,350,250);
			pan.add(contentPan);*/
			
			speakTxt.setBounds(10,350,300,80);
			speakTxt.setLineWrap(true);
			speakTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			
			content.setBounds(0,65,330,250);
			content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			content.setEditable(false);
			content.setLineWrap(true);
			pan.add(content);
			pan.add(speakTxt);
			speakBtn.setBounds(20, 450, 90, 30);
			pan.add(speakBtn);
			
			fileBtn.setBounds(230, 450, 90, 30);
			pan.add(fileBtn);
			
}
}
