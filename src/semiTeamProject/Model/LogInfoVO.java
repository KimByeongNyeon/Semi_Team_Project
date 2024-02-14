package semiTeamProject.Model;

import java.time.LocalDateTime;

/**
 * HTTP 응답 로그에 대한 정보를 저장하는 Value Object
 */
public class LogInfoVO {
	/**
	 * HTTP 요청에 대한 응답의 상태 코드
	 * (예시: 200, 403, 404, 500, ...)
	 */
	private int responseResult;
	
	/**
	 * 요청 경로
	 * (예시: books)
	 */
	private String path; 
	
	/**
	 * 쿼리의 key에 대한 값
	 */
	private String key;
	
	/**
	 * 사용자가 접속한 브라우저
	 */
	private String browser;
	
	/**
	 * 요청에 대한 응답을 수행한 날짜와 시
	 */
	private LocalDateTime datetime;
	
	/**
	 * HTTP 응답 로그에 대한 정보를 저장하는 Value Object
	 */
	public LogInfoVO() {
		
	} // LogInfoVO

	/**
	 * HTTP 응답 로그에 대한 정보를 저장하는 Value Object
	 * @param responseResult HTTP 요청에 대한 응답의 상태 코드
	 * @param path 요청 경로
	 * @param key 쿼리의 key에 대한 값
	 * @param browser 사용자가 접속한 브라우저
	 * @param datetime 요청에 대한 응답을 수행한 날짜와 시간
	 */
	public LogInfoVO(int responseResult, String path, String key, String browser, LocalDateTime datetime) {
		this.responseResult = responseResult;
		this.path = path;
		this.key = key;
		this.browser = browser;
		this.datetime = datetime;
	} // LogInfoVO

	public int getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(int responseResult) {
		this.responseResult = responseResult;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
} // class
