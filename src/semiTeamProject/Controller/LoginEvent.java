package semiTeamProject.Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import semiTeamProject.Model.UserVO;
import semiTeamProject.View.LoginDesign;
import semiTeamProject.View.MainAppDesign;

public class LoginEvent extends WindowAdapter implements ActionListener {
	private LoginDesign ld;
	private Map<String, UserVO> user = new HashMap<>();

	public LoginEvent(LoginDesign ld) {
		this.ld = ld;
		
		user.put("Admin", new UserVO("Admin","1234", true));
		user.put("root",new UserVO("root", "1111", false));
		user.put("administrator",new UserVO("administrator", "1234", true));
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
		if (ae.getSource() == ld.getiD() || ae.getSource() == ld.getPassword()) {
			String id = ld.getiD().getText();
			String pw = String.valueOf(ld.getPassword().getPassword());
			if(!id.equals("") && !pw.equals("")) {
				login();
			}
		}		
	}


	public void login() {
		String userId = ld.getiD().getText().trim();
		String userPw = String.valueOf(ld.getPassword().getPassword());
		
		
		
		if (user.containsKey(userId)) {
			UserVO currentUser = user.get(userId);
			if (!currentUser.getPassword().equals(userPw)) {
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력해주세요");
			} else {
				ld.dispose();
				new MainAppDesign(currentUser.isAdmin(), currentUser.getUserId());
			}
		} else {
			JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 입니다.");
		}
		
	}

}
