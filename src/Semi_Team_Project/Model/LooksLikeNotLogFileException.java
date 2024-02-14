package Semi_Team_Project.Model;

@SuppressWarnings("serial")
public class LooksLikeNotLogFileException extends Exception {
	public LooksLikeNotLogFileException() {
		super("해당 파일은 이 프로그램이 사용하는 로그 데이터 파일이 아니거나 데이터가 오염된 것 같습니다");
	}// LooksLikeNotLogFileException
	public LooksLikeNotLogFileException(String msg) {
		super(msg);
	} // LooksLikeNotLogFileException
} // class
