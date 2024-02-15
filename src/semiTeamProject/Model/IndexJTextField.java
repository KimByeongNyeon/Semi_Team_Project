package semiTeamProject.Model;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class IndexJTextField extends JTextField {

	public IndexJTextField() {
		super();
	}
	public IndexJTextField(int len) {
		super(len);
	}
	
	
	@Override
	public String getText() {
		return convertToIndex(super.getText());
	}
	public String convertToIndex(String str) {
		int index = -1;
		if(!str.equals("") && str != null) {
			index = Integer.parseInt(str);
			if(index > 2147483647) {
				index = 2147483647;
			} else if (index <= 2147483647 && index > 0) {
				index --;
			} else {
				index = 0;
			} // end else
		}
		return String.valueOf(index);
	} 	
}
