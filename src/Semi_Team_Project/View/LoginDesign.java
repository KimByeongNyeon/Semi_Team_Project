package Semi_Team_Project.View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Semi_Team_Project.Controller.LoginEvent;

@SuppressWarnings("serial")
public class LoginDesign extends JFrame{
	private JTextField iD;
	private JPasswordField password;
	private JButton login;
	
	public LoginDesign() {
		super("로그인");
		
		JLabel jlId = new JLabel("ID");
		JLabel jlPw = new JLabel("PW");
		
		iD = new JTextField(15);
		password = new JPasswordField(15);
		
		login = new JButton("로그인");
		
		setLayout(null);
		
		jlId.setBounds(25,30,100,25);
		iD.setBounds(55,30,150,25);
		jlPw.setBounds(25,70,100,25);
		password.setBounds(55,70,150,25);
		
		login.setBounds(28, 120, 177, 30);
		
		
		add(jlId);
		add(jlPw);
		add(iD);
		add(password);
		add(login);
		
		LoginEvent le = new LoginEvent(this);
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
