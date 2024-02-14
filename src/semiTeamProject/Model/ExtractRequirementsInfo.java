package semiTeamProject.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * (분류한 로그 데이터를) 분석하여 요구사항이 요구하는 데이터를 생성해내는 클래스<br><br>
 * 
 * 요구사항이 요구하는 값은 인스턴스 변수로 설정되어 있기 때문에 getter method로 바로 가져올 수 있고<br><br>
 * 
 * 파라미터로 시작 인덱스와 끝 인덱스를 줘서 일부분에 대한 분석을 할 수도 있으며<br><br>
 * 
 * 요구사항이 요구하는 값 외에 추가로 구하고 싶은 값이 있다면<br>
 * 데이터 분석에 쓰인 Map을 getter method로 가져와서 연산할 수 있다<br><br>
 * 
 * 이 클래스는 retainAllModified() 메소드를 제공하기 때문에<br> 
 * 엑셀의 '필터' 버튼처럼 column별 겹치는 라인이 몇 번인지를 구할 수 있다<br>
 */
public class ExtractRequirementsInfo {
	/**
	 * HTTP 요청에 대한 응답의 상태 코드 (예시: 200, 403, 404, 500, ...)를 key로, 
	 * 해당하는 라인의 List를 값으로 갖는 Map
	 */
	private Map<Integer, List<Integer>> responseResult;	
	/**
	 * 요청 경로 (예시: books)를 key로, 해당하는 라인의 List를 값을 갖는 Map 
	 */
	private Map<String, List<Integer>> path;	
	/**
	 * 쿼리의 key에 대한 값을 key로, 해당하는 라인의 List를 값을 갖는 Map
	 */
	private Map<String, List<Integer>> queryKey;	
	/**
	 * 사용자가 접속한 브라우저를 key로, 해당하는 라인의 List를 값을 갖는 Map
	 */
	private Map<String, List<Integer>> browser;	
	/**
	 * 요청에 대한 응답을 수행한 날짜와 '시'를 key로, 해당하는 라인의 List를 값을 갖는 Map
	 */
	private Map<LocalDateTime, List<Integer>> datetime;	
	
	/**
	 * 1. 최다사용 키의 이름
	 */
	private String oftenKeyName;
	/**
	 * 1. 최다사용 키의 횟수
	 */
	private int oftenKeyNum;
	/**
	 * 2. 브라우저별 접속횟수, 비율
	 */
	private List<BrowserRatioVO> browserRatio;
	/**
	 * 3. 서비스를 성공적으로 수행한(200) 횟수
	 */
	private int service200;
	/**
	 * 3. 서비스 실패(404) 횟수
	 */
	private int service404;
	private int service500, serviceEtc;
	/**
	 * 4. 요청이 가장 많은 시간
	 */
	private int oftenHour;
	/**
	 * 5. 비정상적인 요청(403)이 발생한 횟수
	 */
	private int service403;
	/**
	 * 5. 비정상적인 요청(403)이 발생한 비율
	 */
	private double service403Ratio;
	/**
	 * 6. books에 대한 요청 URL중 에러(500)가 발생한 횟수
	 */
	private int reqBooksErrNum;
	/**
	 * 6. books에 대한 요청 URL중 에러(500)가 발생한 비율
	 */
	private double reqBooksErrRatio;
	
	/**
	 * 데이터의 첫 번째 라인에 해당하는 인덱스는 0번
	 */
	private static final int FIRST = 0;
	/**
	 * 데이터의 마지막 라인에 해당하는 인덱스는 -1번
	 */
	private static final int LAST = -1;
	
	/**
	 * 요구사항 6번에서 URL의 말단 path가 가리키는 경로
	 */
	private static final String PATH_BOOKS = "books";
	/**
	 * 요구사항 6번에서 URL 요청 중 에러에 해당하는 상태 코드
	 */
	private static final int SERVICE_500 = 500;
	
	/**
	 * 요구사항이 요구하는 값을 만들기 위해 데이터를 분석해 저장할 Map을 만드는 기본 생성자 
	 */
	private ExtractRequirementsInfo() {
		responseResult = new HashMap<Integer, List<Integer>>();
		path = new HashMap<String, List<Integer>>();
		queryKey = new HashMap<String, List<Integer>>();
		browser = new HashMap<String, List<Integer>>();
		datetime = new HashMap<LocalDateTime, List<Integer>>();
	} // ExtractRequirementsInfo
	
	/**
	 * (분류한 로그 데이터를) 모두 분석하여 요구사항이 요구하는 데이터를 생성한다<br><br>
	 * 
	 * 요구사항이 요구하는 값은 인스턴스 변수로 설정되어 있기 때문에 getter method로 바로 가져올 수 있고<br><br>
	 * 
	 * 요구사항이 요구하는 값 외에 추가로 구하고 싶은 값이 있다면<br>
	 * 데이터 분석에 쓰인 Map을 getter method로 가져와서 연산할 수 있다<br><br>
	 * 
	 * 이 클래스는 retainAllModified() 메소드를 제공하기 때문에<br> 
	 * 엑셀의 '필터' 버튼처럼 column별 겹치는 라인이 몇 번인지를 구할 수 있다<br>
	 * 
	 * @param listLogInfo 분류한 로그 데이터
	 */
	public ExtractRequirementsInfo(List<LogInfoVO> listLogInfo) {
		this(listLogInfo, FIRST, LAST);
	} // ExtractRequirementsInfo
		
	/**
	 * (분류한 로그 데이터를) 파라미터로 받은 인덱스 범위만 분석하여 요구사항이 요구하는 데이터를 생성한다<br><br>
	 * 
	 * 요구사항이 요구하는 값은 인스턴스 변수로 설정되어 있기 때문에 getter method로 바로 가져올 수 있고<br><br>
	 * 
	 * 요구사항이 요구하는 값 외에 추가로 구하고 싶은 값이 있다면<br>
	 * 데이터 분석에 쓰인 Map을 getter method로 가져와서 연산할 수 있다<br><br>
	 * 
	 * 이 클래스는 retainAllModified() 메소드를 제공하기 때문에<br> 
	 * 엑셀의 '필터' 버튼처럼 column별 겹치는 라인이 몇 번인지를 구할 수 있다<br>
	 * 
	 * @param listLogInfo 분류한 로그 데이터
	 * @param indexStart 분석할 시작 라인의 인덱스
	 * @param indexEnd 분석할 마지막 라인의 인덱스
	 */
	public ExtractRequirementsInfo(List<LogInfoVO> listLogInfo, int indexStart, int indexEnd) {
		this();
		
		int listSize = listLogInfo.size();
		indexEnd = indexEnd == LAST ? listSize - 1 : indexEnd;
		indexEnd = indexEnd >= listSize ? listSize - 1 : indexEnd;
		indexStart = indexStart > indexEnd ? indexEnd : indexStart;
		indexStart = indexStart < FIRST ? FIRST : indexStart;
		
		System.out.println("ExtractRequirementsInfo: " + indexStart + " / " + indexEnd);
		
		LogInfoVO logInfo = null;
		
		for(int i = indexStart; i < indexEnd + 1; i++) {
			logInfo = listLogInfo.get(i);
			
			addClassifiedDataToMap(responseResult, logInfo.getResponseResult(), i);
			addClassifiedDataToMap(path, logInfo.getPath(), i);
			addClassifiedDataToMap(queryKey, logInfo.getKey(), i);
			addClassifiedDataToMap(browser, logInfo.getBrowser(), i);
			addClassifiedDataToMap(datetime, logInfo.getDatetime(), i);
		} // end for
		
		ExtractMostOftenKey();
		ExtractBrowserRatio();
		ExtractServiceCount();
		ExtractMostOftenHour();
		ExtractService500PerBooks();
		
	} // ExtractRequirementsInfo
	
	/**
	 * 항목별로 분류한 데이터를 자신의 Map에 추가하는 method
	 * Map의 value는 List〈Integer〉를 사용하여 라인 번호들을 저장한다
	 *  
	 * @param map 데이터를 추가할 대상 Map
	 * @param key Map의 키
	 * @param lineNo Map의 값인 List〈Integer〉에 추가할 정수
	 */
	private void addClassifiedDataToMap(Map<String, List<Integer>> map, String key, int lineNo) {
		if(map.containsKey(key)) {
			map.get(key).add(lineNo);
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(lineNo);
			map.put(key, list);
		} // end else
	} // addDataToMap
	
	/**
	 * 항목별로 분류한 데이터를 자신의 Map에 추가하는 method
	 * Map의 value는 List〈Integer〉를 사용하여 라인 번호들을 저장한다
	 *  
	 * @param map 데이터를 추가할 대상 Map
	 * @param key Map의 키
	 * @param lineNo Map의 값인 List〈Integer〉에 추가할 정수
	 */
	private void addClassifiedDataToMap(Map<Integer, List<Integer>> map, int key, int lineNo) {
		if(map.containsKey(key)) {
			map.get(key).add(lineNo);
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(lineNo);
			map.put(key, list);
		} // end else
	} // addDataToMap
	
	/**
	 * 항목별로 분류한 데이터를 자신의 Map에 추가하는 method
	 * Map의 value는 List〈Integer〉를 사용하여 라인 번호들을 저장한다
	 *  
	 * @param map 데이터를 추가할 대상 Map
	 * @param key Map의 키
	 * @param lineNo Map의 값인 List〈Integer〉에 추가할 정수
	 */
	private void addClassifiedDataToMap(Map<LocalDateTime, List<Integer>> map, LocalDateTime key, int lineNo) {
		if(map.containsKey(key)) {
			map.get(key).add(lineNo);
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(lineNo);
			map.put(key, list);
		} // end else
	} // addDataToMap

	/**
	 * 요구사항 1. 최다사용 키의 이름과 횟수를 구하는 method
	 */
	private void ExtractMostOftenKey() {
		Set<String> set = queryKey.keySet();
		Iterator<String> ita = set.iterator();
		List<Integer> listLines = null, listLinesMost = null;
		String key = "", keyMost = "";
		while(ita.hasNext()) {
			key = ita.next();
			listLines = queryKey.get(key);
			
			if(listLinesMost == null) {
				listLinesMost = listLines;
				keyMost = key;
				
				continue;
			} // end if
			
			if(listLines.size() > listLinesMost.size()) {
				listLinesMost = listLines;
				keyMost = key;
			} else if(listLines.size() == listLinesMost.size()) {
				keyMost = keyMost.concat(", ").concat(key);
			} // end if
		} // end while
		
		oftenKeyName = keyMost;
		oftenKeyNum = listLinesMost.size();
	} // ExtractMostOftenKey
	
	/**
	 * 요구사항 2. 브라우저별 접속횟수, 비율
	 */
	private void ExtractBrowserRatio() {
		browserRatio = new ArrayList<BrowserRatioVO>();

		List<Integer> listLines = null;
		String key = "";
		BrowserRatioVO br = null;
		int totalBrowser = 0;
		int count = 0;
		
		Set<String> set = browser.keySet();
		Iterator<String> ita = set.iterator();
		while(ita.hasNext()) {
			key = ita.next();
			listLines = browser.get(key);
			count = listLines.size();
			
			br = new BrowserRatioVO(key, count, (double)count);
			totalBrowser += listLines.size();
			browserRatio.add(br);
		} // end while
		
		for (int i = 0; i < browserRatio.size(); i++) {
			br = browserRatio.get(i);
			br.setRatio(br.getRatio() / totalBrowser * 100);
		} // end for
		
	} // ExtractBrowserRatio
	
	/**
	 * 요구사항 3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
	 * 요구사항 5. 비정상적인 요청(403)이 발생한 횟수, 비율
	 */
	private void ExtractServiceCount() {
		Set<Integer> set = responseResult.keySet();
		Iterator<Integer> ita = set.iterator();
		int status = 0;
		while(ita.hasNext()) {
			status = ita.next();
			switch(status) {
			case 200:
				service200 = responseResult.get(status).size();
				break;
			case 403:
				service403 = responseResult.get(status).size();
				break;
			case 404:
				service404 = responseResult.get(status).size();
				break;
			case 500:
				service500 = responseResult.get(status).size();
				break;
			default:
				serviceEtc += responseResult.get(status).size(); 
			} // end switch
		} // end while
		service403Ratio = (double) service403 / (service200 + service403 + service404 + service500) * 100;
	} // ExtractServiceCount
	
	/**
	 * 요구사항 4. 요청이 가장 많은 시간
	 */
	private void ExtractMostOftenHour() {
		Set<LocalDateTime> set = datetime.keySet();
		Iterator<LocalDateTime> ita = set.iterator();
		
		int mostCount = -1;
		int tempCount = 0;
		LocalDateTime ldtTemp = null;
		
		while(ita.hasNext()) {
			ldtTemp = ita.next();
			tempCount = datetime.get(ldtTemp).size();
			if(tempCount > mostCount) {
				mostCount = tempCount;
				oftenHour = ldtTemp.getHour();
			} // end if
		} // end while
	} // ExtractMostOftenHour
	
	/**
	 * 요구사항 6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율 구하기
	 */
	private void ExtractService500PerBooks() {
		List<Integer> booksErr = retainAllModified(path.get(PATH_BOOKS), responseResult.get(SERVICE_500));
		
		reqBooksErrNum = booksErr.size();
		if(path.containsKey(PATH_BOOKS)) {
			if(path.get(PATH_BOOKS) == null  || path.get(PATH_BOOKS).size() == 0) {
				reqBooksErrRatio = 0.0;
			} else {
				reqBooksErrRatio = (double)reqBooksErrNum / path.get(PATH_BOOKS).size() * 100;
			}			
		} else {
			reqBooksErrRatio = 0.0;
		} // end else
	} // ExtractService500PerBooks
	
	/**
	 * List가 제공하는 retainAll() 메소드의 변형.<br>
	 * List〈Integer〉를 List로 받아<br>
	 * 서로 중복되는 라인만 골라서 반환하는 일을 하는 method<br>
	 * 분석한 데이터를 저장한 Map의 값은 데이터형이 List〈Integer〉기 때문에 <br>  
	 * 항목별 겹치는 라인을 분석하는 데 사용할 수 있다 
	 * @param lists 분석한 데이터를 저장한 Map의 value인 List〈Integer〉를 generic으로 하는 List
	 * @return 겹치는 라인에 대한 List 
	 */
	public List<Integer> retainAllModified(List<List<Integer>> lists) {
		List<Integer> tempList = null;
		int lenList = lists.size();
		
		switch(lenList) {
		case 1:
			tempList = lists.get(0);
			break;
		default:
			if(lists.get(0) == null) {
				tempList = new LinkedList<Integer>();
			} else {
				tempList = new LinkedList<Integer>(lists.get(0));
				for (int i = 1; i < lenList; i++) {
					if(lists.get(i) == null) {
						tempList.clear();					
					} else {
						tempList.retainAll(lists.get(i));
					} // else
				} // end for
			} // end else
		} // end switch
		
		return tempList;
	} // retainAllModified
	
	/**
	 * List가 제공하는 retainAll() 메소드의 변형.<br>
	 * List〈Integer〉를 V.A로 받아<br>
	 * 서로 중복되는 라인만 골라서 반환하는 일을 하는 method<br>
	 * 분석한 데이터를 저장한 Map의 값은 데이터형이 List〈Integer〉기 때문에 <br>  
	 * 항목별 겹치는 라인을 분석하는 데 사용할 수 있다
	 * @param lists 분석한 데이터를 저장한 Map의 value인 List〈Integer〉를 generic으로 하는 List
	 * @return 겹치는 라인에 대한 List 
	 */
	@SafeVarargs
	public final List<Integer> retainAllModified(List<Integer> ... lists) {
		List<Integer> tempList = null;
		int lenList = lists.length;
		
		switch(lenList) {
		case 1:
			tempList = lists[0];
			break;
		default:
			if(lists[0] == null) {
				tempList = new LinkedList<Integer>();
			} else {
				tempList = new LinkedList<Integer>(lists[0]);
				for (int i = 1; i < lenList; i++) {
					if(lists[i] == null) {
						tempList.clear();
					} else {
						tempList.retainAll(lists[i]);
					} // end else
				} // end for
			} // end else
		} // end switch
		
		return tempList;
	} // retainAllModified
	
	/**
	 * 테스트용 요구사항 출력 메소드
	 * @param eri
	 */
	public void printRequirementsInfo() {
		System.out.println("1. 최다 사용 키의 이름과 횟수: " + oftenKeyName + " / " + oftenKeyNum);
		System.out.println("2. 브라우저별 접속 횟수, 비율: ");
		List<BrowserRatioVO> brvo = browserRatio;
		for(BrowserRatioVO b : brvo) {
			System.out.printf("\t%s: %d회, %.1f%%\n", b.getBrowser(), b.getCount(), b.getRatio());
		} // end for
		System.out.println("3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수: " + service200 + " / " + service404);
		System.out.println("4. 요청이 가장 많은 시간: " + oftenHour + "시");
		System.out.printf("5. 비정상적인 요청(403)이 발생한 횟수,비율: %d / %.1f%%\n", service403, service403Ratio);
		System.out.printf("6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율: %d / %.1f%%\n", reqBooksErrNum, reqBooksErrRatio);
	} // printRequirementsInfo
	

	public Map<Integer, List<Integer>> getResponseResult() {
		return responseResult;
	}

	public Map<String, List<Integer>> getPath() {
		return path;
	}

	public Map<String, List<Integer>> getQueryKey() {
		return queryKey;
	}

	public Map<String, List<Integer>> getBrowser() {
		return browser;
	}

	public Map<LocalDateTime, List<Integer>> getDatetime() {
		return datetime;
	}

	public String getOftenKeyName() {
		return oftenKeyName;
	}

	public int getOftenKeyNum() {
		return oftenKeyNum;
	}

	public List<BrowserRatioVO> getBrowserRatio() {
		return browserRatio;
	}

	public int getService200() {
		return service200;
	}

	public int getService403() {
		return service403;
	}

	public int getService404() {
		return service404;
	}

	public int getService500() {
		return service500;
	}

	public int getServiceEtc() {
		return serviceEtc;
	}

	public int getOftenHour() {
		return oftenHour;
	}

	public double getService403Ratio() {
		return service403Ratio;
	}

	public int getReqBooksErrNum() {
		return reqBooksErrNum;
	}

	public double getReqBooksErrRatio() {
		return reqBooksErrRatio;
	}

} // class
