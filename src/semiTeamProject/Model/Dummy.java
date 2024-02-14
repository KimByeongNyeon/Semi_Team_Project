package semiTeamProject.Model;

//public class Dummy {
//	File openFile;
//	int fileLastModified;
//	int indexStart, indexEnd;
//	List<String> logLines;
//	List<LogInfoVO> listLogInfo;
//	ExtractRequirementsInfo eri;
//
//	[ 파일 열기 ]
//	FileDialog fdOpen = new FileDialog(jmd, "열기", FileDialog.LOAD);
//
//	fdOpen.getDirectory()가 null일때의 early return 이라든가 처리
//	File file = fdOpen.getFiles()[0]에 대해 file.exsists()가 false일 때의 early return 이라든가 처리
//
//	file과 openFile가 같은지? (주소 비교가 아니라 canonical Path를 통한 값 비교로)
//	file.lastModified()와 fileLastModified()가 같은지?
//	비교하여 다르면 listLogInfo = null, eri = null로 만든 뒤 
//	openFile = file;
//	logLines = new StoreData(pathFile).loadData();
//	fileLastModified = file.lastModified();
//	같으면 동작 X
//
//
//	[ boolean check: 다른 파일이 열렸는지, 시작 끝 라인이 변경되었는지 체크하는 메소드 ]
//	int start = Integer.parseInt(jtfIndexStart.getText());
//	int end = Integer.parseInt(jtfIndexEnd.getText());
//	boolean flagChanged = false;
//
//	if(listLogInfo == null) {
//	    ClassifyLogLines cll = new ClassifyLogLines(logLines);
//	    listLogInfo = cll.getLogInfo();
//	    indexStart = start;
//	    indexEnd = end;
//	    eri = new ExtractRequirementsInfo(listLogInfo, indexStart, indexEnd);
//	    flagChanged = True;
//	} else { 
//	    if(start != indexStart || end != indexEnd) {
//	        indexStart = start;
//	        indexEnd = end;
//	        eri = new ExtractRequirementsInfo(listLogInfo, start, end);
//	        flagChanged = True;
//	    }
//	}
//	return flagChanged
//
//	[ View ]
//	check()
//	eri.get~ 으로 값 가져와서 다이얼로그에 출력하는 코드
//
//	[ Report ]
//	check()
//	eri.get~ 으로 값 가져와서 파일로
