package semiTeamProject.View;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import semiTeamProject.Controller.LoginEvent;

@SuppressWarnings("serial")
public class LoginDesign extends JFrame{
	private JTextField iD;
	private JPasswordField password;
	private JButton login;
	
	public LoginDesign() {
		super("로그인");
		
		Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString().replace("\\", "/").concat("/");
		ImageIcon iLogin = new ImageIcon(new File(path + "/image/login.png").getAbsolutePath());
		
		JLabel jlId = new JLabel("ID");
		JLabel jlPw = new JLabel("PW");
		
		iD = new JTextField(15);
		password = new JPasswordField(15);
		login = new JButton(iLogin);
		
		setLayout(null);
		
		jlId.setBounds(25,29,100,25);
		iD.setBounds(55,30,150,25);
		jlPw.setBounds(25,70,100,25);
		password.setBounds(55,70,150,25);
		login.setBounds(28, 120, 177, 30);
		
		add(jlId);
		add(jlPw);
		add(iD);
		add(password);
		add(login);
		
		Color backgroundColor = new Color(0xECEDFA);
		getContentPane().setBackground(backgroundColor);
		
		LoginEvent le = new LoginEvent(this);
		iD.addActionListener(le);
		password.addActionListener(le);
		login.addActionListener(le);
		addWindowFocusListener(le);
		
		setVisible(true);
		setResizable(false);//창 크기 변경 불가능
		setBounds(850,360,250,220);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//LoginDesign



	public JTextField getiD() {
		return iD;
	}

	public JPasswordField getPassword() {
		return password;
	}

	public JButton getLogin() {
		return login;
	}

}
