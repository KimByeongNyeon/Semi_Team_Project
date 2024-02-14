package Semi_Team_Project.Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import Semi_Team_Project.Model.User;
import Semi_Team_Project.View.LoginDesign;
import Semi_Team_Project.View.MainAppDesign;
//
public class LoginEvent extends WindowAdapter implements ActionListener {
	private LoginDesign ld;
	private Map<String, User> user = new HashMap<>();

	public LoginEvent(LoginDesign ld) {
		this.ld = ld;
		
		user.put("Admin", new User("Admin","1234", true));
		user.put("root",new User("root", "1111", false));
		user.put("administrator",new User("administrator", "1234", true));
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ld.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == ld.getLogin()) {
			login();
		}
	}


	public void login() {
		String userId = ld.getiD().getText().trim();
//		char[] userPw = ld.getPassword();
		String userPw = String.valueOf(ld.getPassword().getPassword());
//		String userPw = String.valueOf(ld.getPassword().getText());
		
		
		
		if (user.containsKey(userId)) {
			User currentUser = user.get(userId);
			if (!currentUser.getPassword().equals(userPw)) {
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력해주세요");
				System.out.println(userId);
				System.out.println(userPw);
			} else {
				ld.dispose();
				new MainAppDesign(currentUser.isAdmin());
			}
		} else {
			JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.");
		}
		
	}

}
