package semiTeamProject.Model;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class IntegerDocument extends PlainDocument {
	
	int currentValue = 0;
	public IntegerDocument() {
	} // IntegerDocument
	public int getValue() {
		return currentValue;
	} // getValue
	public void insertString(int offset, String string, AttributeSet attributes) throws BadLocationException {
		if (string == null) {
			return;
		} // end if
		
		String newValue;
		int length = getLength();
		if (length == 0) {
			newValue = string;
		} else {
			String currentContent = getText(0, length);
			StringBuffer currentBuffer = new StringBuffer(currentContent);
			currentBuffer.insert(offset, string);
			newValue = currentBuffer.toString();
		} // end else
		currentValue = checkInput(newValue, offset);
		super.insertString(offset, string, attributes);
	} // insertString
	public void remove(int offset, int length) throws BadLocationException {
		int currentLength = getLength();
		String currentContent = getText(0, currentLength);
		String before = currentContent.substring(0, offset);
		String after = currentContent.substring(length+offset, currentLength);
		String newValue = before.concat(after);
		currentValue = checkInput(newValue, offset);
		super.remove(offset, length);
	} // remove
	public int checkInput(String proposedValue, int offset) throws BadLocationException {
		int newValue = 0;
		if (proposedValue.length() > 0) {
			try {
				newValue = Integer.parseInt(proposedValue);
			} catch (NumberFormatException e) {
				throw new BadLocationException(proposedValue, offset);
			} // end catch
		} // end if
		return newValue;
	} // checkInput
} // class

