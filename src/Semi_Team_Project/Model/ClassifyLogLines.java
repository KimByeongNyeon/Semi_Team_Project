package Semi_Team_Project.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt; 
//
/**
 * 로그 데이터 파일을 분류하는 클래스
 * 
 */
public class ClassifyLogLines {
	
	 /*
	 * [ 참고사항 ]<br>
	 * '?' 기호는 URL 주소에서 Path와 Parameter를 구분짓는 구분자이다<br>
	 * true: 원칙대로 '?' 기호를 구분자로 보고 '?' 기호가 없으면 Parameter를 없는 것으로 간주<br>
	 * false: '?' 기호가 없어도 Parameter로 추정되는 부분이 있으면 Parameter가 있는 것으로 간주  
	 */
	
	private List<LogInfoVO> logInfo;
	
	public ClassifyLogLines(List<String> lines) throws LooksLikeNotLogFileException {
		this(lines, false);
	} // ClassifyLogLines
	
	public ClassifyLogLines(List<String> lines, boolean strictUrlPattern) throws LooksLikeNotLogFileException {
		logInfo = new ArrayList<LogInfoVO>();
		
		String patternLog = "\\[(\\d{3})\\]\\[[a-zA-Z:/.]+/([a-zA-Z0-9]+)[?]key=([a-zA-Z0-9]+)&[a-zA-Z0-9=]+\\]\\[([a-zA-Z0-9]+)\\]\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2})";
		Pattern pattern = Pattern.compile(patternLog);
		Matcher matcher = null;
		LocalDateTime ldt = null;
		
		String logLine = "";
//		System.out.println("읽어들인 데이터 줄 수: " + lines.size());
		
		Iterator<String> ita = lines.iterator();
		while(ita.hasNext()) {
			logLine = ita.next();
			
			if(logLine.length() < 8) {	// 로그 데이터의 구분자가 8개이므로 라인의 길이는 최소 8 이상
				continue;
			} // end if
			
			matcher = pattern.matcher(logLine);
			if(matcher.find()) {
				ldt = LocalDateTime.of(parseInt(matcher.group(5)), parseInt(matcher.group(6)), parseInt(matcher.group(7)), parseInt(matcher.group(8)), 0);

				logInfo.add(new LogInfoVO(parseInt(matcher.group(1)), matcher.group(2), matcher.group(3), matcher.group(4), ldt));
			} else {
				logInfo.add(readAbnormalLogLine(logLine, strictUrlPattern));
			} // end else
		} // end while
		
//		System.out.println("패턴매칭한 데이터 줄 수: " + logInfo.size());
		
	} // ReadLogFile
	
	public String getKeyFromAbnormalUrl(String url) {
		String key = "";
		if(url.contains("key=")) {
			key = url.substring(url.indexOf("key=") + 4, url.indexOf("&"));
		} // end if
		return key;
	} // getKeyFromAbnormalUrl
	
	public LogInfoVO readAbnormalLogLine(String logLine, boolean strictUrlPattern) throws LooksLikeNotLogFileException {
		LogInfoVO logInfo = null;
		String path = "";
		String key = "";
		
		String patternLog = "\\[(\\d{3})\\]\\[([a-zA-Z0-9:/.?&=]+)\\]\\[([a-zA-Z0-9]+)\\]\\[([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2})";
		
		Matcher matcher = Pattern.compile(patternLog).matcher(logLine);
		if(!matcher.find()) {
			throw new LooksLikeNotLogFileException("해당 파일은 이 프로그램이 사용하는 로그 데이터 파일이 아니거나 데이터가 오염된 것 같습니다\n문제가 된 데이터: " + logLine);
		} else {
			String url = matcher.group(2);
			
			if(!strictUrlPattern) {
				key = getKeyFromAbnormalUrl(url);
				
				if(url.contains("?")) {
					path = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
				} // end if
			} else {
				if(url.contains("?")) {
					path = url.substring(url.lastIndexOf('/') + 1, url.indexOf('?'));
					
					key = getKeyFromAbnormalUrl(url);
				} else {
					path = url.substring(url.lastIndexOf('/') + 1);
				} // end else
			} // end else
			
			logInfo = new LogInfoVO(parseInt(matcher.group(1)), path, key, matcher.group(3), 
					LocalDateTime.of(parseInt(matcher.group(4)), parseInt(matcher.group(5)), parseInt(matcher.group(6)), parseInt(matcher.group(7)), 0));
		} // end else
		return logInfo;
	} // readAbnormalLogLine
	
	public List<LogInfoVO> getLogInfo() {
		return logInfo;
	}
	
} // class
