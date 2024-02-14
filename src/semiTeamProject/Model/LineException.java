package semiTeamProject.Model;

public class LineException extends Exception {
	public LineException() {
		super("라인 번호 설정에 문제가 있습니다");
	}
	public LineException(String msg) {
		super( msg);
	}
}
