package com.chengs.dict.view;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.chengs.dict.service.CreaterService;

public class MainView extends JFrame{

	private static final long serialVersionUID = -4798286459113840402L;
	
	public void init() {
	    this.setVisible(true);        // 可视化
	     this.setSize(500, 390);        // 大小
	     this.setTitle("数据字典生成工具");        // 标题
	     this.setLocationRelativeTo(null);
	     this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        // 关闭方式
	     this.setLayout(new FlowLayout(FlowLayout.LEFT));
	     
	     
	     
	     JPanel panel = new JPanel();
	     panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	     
	     JLabel urlLabel=new JLabel("数据库连接地址：");
	     urlLabel.setFont(new Font("宋体",Font.BOLD,16));
         panel.add(urlLabel);
	     
         JTextField urlText = new JTextField(36);
         urlText.setFont(new Font(null, Font.PLAIN, 16));
         panel.add(urlText);
         
         JLabel userLabel=new JLabel("数据库用户名：");
         userLabel.setFont(new Font("宋体",Font.BOLD,16));
         panel.add(userLabel);
	     
         JTextField userText = new JTextField(36);
         userText.setFont(new Font(null, Font.PLAIN, 16));
         panel.add(userText);
         
         JLabel pwdLabel=new JLabel("数据库密码：");
         pwdLabel.setFont(new Font("宋体",Font.BOLD,16));
         panel.add(pwdLabel);
	     
         JTextField pwdText = new JTextField(36);
         pwdText.setFont(new Font(null, Font.PLAIN, 16));
         panel.add(pwdText);
         
         JLabel dbLabel=new JLabel("需要创建字典的数据库名称：");
         dbLabel.setFont(new Font("宋体",Font.BOLD,16));
         panel.add(dbLabel);
	     
         JTextField dbText = new JTextField(36);
         dbText.setFont(new Font(null, Font.PLAIN, 16));
         panel.add(dbText);
         
         
         
         JTextArea console = new JTextArea(4,36);
         console.setFont(new Font(null, Font.PLAIN, 16));
         console.setEditable(false);
         panel.add(console);

         JButton btn = new JButton("生成");
         btn.setFont(new Font(null, Font.PLAIN, 16));
         btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	CreaterService cs=new CreaterService();
            	try {
					String path=cs.creater(urlText.getText(), userText.getText(), pwdText.getText(), dbText.getText());
					console.setText("生成成功，文件在"+path);
            	} catch (Exception e1) {
					console.setText("错误,请检查参数");
				}
            }
         });
         panel.add(btn);
         
         JButton clear = new JButton("清空");
         clear.setFont(new Font(null, Font.PLAIN, 16));
         clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	urlText.setText("");
            	userText.setText("");
            	pwdText.setText("");
            	dbText.setText("");
            }
         });
         panel.add(clear);
         
         this.setContentPane(panel);
         this.setVisible(true);
	 }
}
