package Semi_Team_Project.Model;

import java.io.IOException;
import java.util.List;

/**
 * StoreData 클래스로 로그 파일의 CSV 데이터를 읽어들이고<br>
 * ClassifyLogLines클래스로 csv 값들을 필드별로 분류하고<br>
 * ExtractRequirementsInfo 클래스로 읽어들인 데이터를 분석하여<br>
 * 요구사항이 요구하는 값을 생성한다<br><br>
 *
 * ClassifyLogLines 클래스를 생성할 때 생성자의 파라미터로 URL의 key 값 검출 옵션을 설정할 수 있다
 * ExtractRequirementsInfo 클래스를 생성할 때 생성자의 파라미터로 시작 인덱스와 끝 인덱스를 지정할 수 있다<br> 
 * 사용할 땐 ExtractRequirementsInfo 클래스의 getter() 메소드를 통해 요구사항의 값을 가져온다<br><br>
 * 
 * 
 * ClassifyLogLines 클래스 생성시 <br>
 * 		1) 파라미터로 false를 주는 경우: URL에 '?' 가 없어도 key로 추정되는 값을 key로 받아 처리한다<br>
 * 		2) 파라미터로 true를 주는 경우: URL에 '?'가 없으면 key가 없는 것으로 판단한다<br><br>
 * 
 * ExtractRequirementsInfo 클래스를 생성시<br>
 * 		1) 파라미터로 리스트만 주는 경우: 전체 로그 데이터를 분석하여 요구사항이 요구하는 값 도출<br>
 * 		2) 파라미터로 리스트와 시작 인덱스, 끝 인덱스를 주는 경우: 해당 부분만 분석하여 요구사항의 값 도출<br><br><br>
 * 
 * 
 * ExtractRequirementsInfo 클래스에서 값을 가져오는 방법은 <br>
 * 		1-1) 최다 사용 키의 이름: eri.getOftenKeyName()<br>
 * 		1-2) 최다 사용 키의 횟수: eri.getOftenKeyNum()<br>
 * 		2) 브라우저별 접속 횟수, 비율<br>
 * 		&nbsp List〈BrowserRatioVO〉 brvo = eri.getBrowserRatio();<br>
 * 		&nbsp brvo.get(i).getBrowser()	// 접속한 브라우저<br>
 *		&nbsp brvo.get(i).getCount()		// 해당 브라우저의 접속 횟수<br>
 *		&nbsp brvo.get(i).getRatio()		// 해당 브라우저의 접속 비율<br>
 * 		3-1) 서비스를 성공적으로 수행한(200) 횟수: eri.getService200()<br>
 * 		3-2) 서비스 실패(404) 횟수: eri.getService404()<br>
 * 		4) 요청이 가장 많은 시간: eri.getOftenHour()<br>
 * 		5-1) 비정상적인 요청(403)이 발생한 횟수: eri.getService403()<br>
 * 		5-2) 비정상적인 요청(403)이 발생한 비율: eri.getService403Ratio()<br>
 * 		6-1) books에 대한 요청 URL중 에러(500)가 발생한 횟수: eri.getReqBooksErrNum()<br>
 * 		6-2) books에 대한 요청 URL중 에러(500)가 발생한 비율: eri.getReqBooksErrRatio()<br><br>
 * 
 * 요구사항에서 요구하는 값 이외에 추가적으로 얻고싶은 항목이 있다면<br>
 * ExtractRequirementsInfo 클래스가 데이터를 분류하고 정리한 Map을<br>
 * getter 메소드로 가져와서 직접 데이터를 가공할 수 있다<br><br>
 * 
 * 		1) 요청 상태 코드(예: 200, 403): Map〈Integer, List〈Integer〉〉 statusCode = eri.getStatusCode()<br>
 * 		2) URL의 말단 Path (예: books): Map〈String, List〈Integer〉〉 reqPath = eri.getReqPath()<br>
 * 		3) URL의 키 값: Map〈String, List〈Integer〉〉 queryKey = eri.getQueryKey()<br>
 * 		4) 브라우저 종류: Map〈String, List〈Integer〉〉 browser = eri.getBrowser()<br>
 * 		5) 요청이 이루어진 날짜+시간: Map〈LocalDateTime, List〈Integer〉〉 = eri.getReqHour()<br>
 */
public class TestReadLogFile {

	// 1. 디자인 클래스 또는 이벤트 클래스에선 List<LogInfoVO> listLogInfo을 instance 변수로 선언
	List<LogInfoVO> listLogInfo;
	
	public TestReadLogFile() {
		
		// 2. 파일 다이어그램으로 선택한 파일을 가져오기
		// (추후 File로 받는게 나은지 String으로 받는게 나은지 테스트해야)
//		File file = new File(pathFile);
		String pathFile = "C:/Temp/sist_input_1.log";
		
		
		List<String> logLines = null;
		ClassifyLogLines cll = null;
		try {
			// 3. 로그 데이터 파일을 읽기
			logLines = new StoreData(pathFile).loadData();
			
			// // 4. 읽어들인 로그 데이터 파일의 값을 필드별로 분류 (csv 데이터 분리)
			// boolean 매개변수 true: 원칙대로 URL의 Path와 Parameter의 구분자인 '?' 기호가 있어야만 Parameter가 있는 것으로 취급
			// boolean 매개변수가 없거나 false: '?'가 없어도 URL에 Parameter로 추정되는 값이 있으면 Parameter로 인식
			cll = new ClassifyLogLines(logLines);
			
		// 5. 각종 컴파일 예외 처리	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LooksLikeNotLogFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
		
		// 6. 분류한 데이터를 인스턴스 변수에 저장
		listLogInfo = cll.getLogInfo();
		
		// 7-1. 분류된 데이터 전체를 분석하여 요구사항이 요구하는 값 얻기
		ExtractRequirementsInfo eri = new ExtractRequirementsInfo(listLogInfo);
		
		// 8-1. 테스트용 콘솔 출력 메소드
		eri.printRequirementsInfo();
		
		System.out.println("--------------------------------");
		
		// 7-2. 분류된 데이터 일부분을 분석하여 요구사항이 요구하는 값 얻기
		int indexStart = 499;
		int indexEnd = 999;
		ExtractRequirementsInfo eriPart = new ExtractRequirementsInfo(listLogInfo, indexStart, indexEnd);
		
		// 8-2. 테스트용 콘솔 출력 메소드
		eriPart.printRequirementsInfo();
		
	} // TestReadLogFile
	
	public static void main(String[] args) {
		
		new TestReadLogFile();

	} // main
} // class
