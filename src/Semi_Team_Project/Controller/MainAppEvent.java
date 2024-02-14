package Semi_Team_Project.Controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import Semi_Team_Project.Model.BrowserRatioVO;
import Semi_Team_Project.Model.ClassifyLogLines;
import Semi_Team_Project.Model.ExtractRequirementsInfo;
import Semi_Team_Project.Model.LogInfoVO;
import Semi_Team_Project.Model.LooksLikeNotLogFileException;
import Semi_Team_Project.Model.StoreData;
import Semi_Team_Project.View.MainAppDesign;
import Semi_Team_Project.View.ViewDialog;

public class MainAppEvent extends WindowAdapter implements ActionListener {
	MainAppDesign md;
	private boolean opened = false;
	private String logData;
	private JTextArea getInfo;
	private File openFile;
	private int fileLastModified;
	private int indexStart, indexEnd;
	List<String> logLines;
	List<LogInfoVO> listLogInfo;
	ExtractRequirementsInfo eri;

	public MainAppEvent(MainAppDesign md) {
		this.md = md;

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == md.getOpenMenuItem()) {
			try {
				openFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ae.getSource() == md.getView()) { // View
			try {
				printView();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (ae.getSource() == md.getReport()) {
			try {
				fileReport();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		md.dispose();
	}
	
	public String printRequirementsInfo(ExtractRequirementsInfo eri) {
		/*
		 * view 혹은 report 버튼에 필요한 요구사항 정보 메소드 생성
		 */
	    StringBuilder result = new StringBuilder();
	    /*
	     * result에 요구사항 정보값 넣기.
	     */
	    result.append("1. 최다 사용 키의 이름과 횟수: ").append(eri.getOftenKeyName()).append(" / ").append(eri.getOftenKeyNum()).append("\n");
	    result.append("2. 브라우저별 접속 횟수, 비율: \n");
	    List<BrowserRatioVO> brvo = eri.getBrowserRatio();
	    for (BrowserRatioVO b : brvo) {
	        result.append(String.format("%s: %d회, %.1f%%\n", b.getBrowser(), b.getCount(), b.getRatio()));
	    } // end for
	    result.append("3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수: ").append(eri.getService200()).append(" / ").append(eri.getService404()).append("\n");
	    result.append("4. 요청이 가장 많은 시간: ").append(eri.getOftenHour()).append("시").append("\n");
	    result.append(String.format("5. 비정상적인 요청(403)이 발생한 횟수,비율: %d / %.1f%%\n", eri.getService403(), eri.getService403Ratio()));
	    result.append(String.format("6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율: %d / %.1f%%\n", eri.getReqBooksErrNum(), eri.getReqBooksErrRatio()));
	    
	    /*
	     * String으로 반환
	     */
	    return result.toString();
	}

	public void openFile() throws IOException {
		/*
		 * 1. 파일 오픈에 필요한 다이얼로그 및 BufferedReader를 지역변수로 설정
		 */
		FileDialog fdOpen = new FileDialog(md, "열기", FileDialog.LOAD);
		BufferedReader br = null;
		fdOpen.setVisible(true);
		/*
		 * 2. fdOpen의 디렉토리의 값이 null일 경우 early return 처리
		 */
		if (fdOpen.getDirectory() == null) {
			return;
		}
		/*
		 * 3. file이 존재하지 않을 경우 early return 처리
		 */
		File file = fdOpen.getFiles()[0];
		if (file.exists() == false) {
			return;
		}
		/*
		 * 4. openfile과 file이 같은지 판단하고, 만약 같다면 early return 처리
		 */
		try {
			if (openFile != null && openFile.getCanonicalPath().equals(file.getCanonicalPath())
					&& openFile.lastModified() == fileLastModified) {
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		/*
		 * 5. file 경로 설정
		 */
		openFile = file;
		String pathFile = file.getAbsolutePath();
		try {
			/*
			 * 6. 파일에서 데이터를 읽어와 logLines에 저장
			 */
			logLines = new StoreData(pathFile).loadData();
			br = new BufferedReader(new FileReader(file));
			String lineData = "";
			StringBuilder sbLog = new StringBuilder();
			/*
			 * 7. 파일 내용을 읽고, StringBuilder에 추가하여 화면에 표시
			 */
			while ((lineData = br.readLine()) != null) {
				sbLog.append(lineData + "\n");
			} // end while

			md.getInfo().setText(sbLog.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logLines = null;
		} finally {
			if(br != null) {
			br.close();}
		}
		/*
		 * 8. 파일의 마지막 수정 시간 업데이트
		 */
		fileLastModified = (int) file.lastModified();
		
		/*
		 * 9. 로그 정보 및 요구사항 정보 초기화
		 */
		listLogInfo = null;
		eri = null;
	}

	public boolean check() throws LooksLikeNotLogFileException {
		int start = Integer.parseInt(md.getStartLog().getText());
		int end = Integer.parseInt(md.getEndLog().getText());
		boolean flagChanged = false;

		if (listLogInfo == null) {
			ClassifyLogLines cll = new ClassifyLogLines(logLines);
			listLogInfo = cll.getLogInfo();
			indexStart = start;
			indexEnd = end;
			eri = new ExtractRequirementsInfo(listLogInfo, indexStart, indexEnd);
			flagChanged = true;
		}
		return flagChanged;
	}

	public void printView() throws IOException {

		// 파일열기창 띄우고, 파일 선택 후 경로+파일명 설정
		FileDialog fdOpen = new FileDialog(md, "열기", FileDialog.LOAD);
		fdOpen.setVisible(true);

		String path = fdOpen.getDirectory() + fdOpen.getFile();
		File readFile = new File(path);

		// jta에 선택한 로그를 보여주는일
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(readFile));
			String lineData = "";
			StringBuilder sbLog = new StringBuilder();

			while ((lineData = br.readLine()) != null) {
				sbLog.append(lineData + "\n");
			} // end while

			md.getInfo().setText(sbLog.toString());

		} finally {
			if (br != null) {
				br.close();
			} // end if
		} // end finally

		// jta에 로그를 보여주고 Dialog로 요구사항에서 요구하는 값을 보여준다.
		new ViewDialog(md, path);

	}// printView

	

	public void fileReport() throws IOException {
		/*
		 * 1.isAdmin을 통해 아이디에 권한이 있는지 검증한 후, 권한이 없는 아이디(root)이면, 권한이 없다고 메시지 출력함.
		 * early return을 사용해 if 문 종료.
		 */
		if (!md.isAdmin()) {
			JOptionPane.showMessageDialog(null, "권한이 없습니다.");
			return;
		}
		try {
			/*
			 * check 메소드를 불러와 파일이 제대로 열려 있는지 검증 
			 */
			check();
			/*
			 * currentTimeMillis 변수를 생성하여, 현재시간을 밀리초로 변환해줌.
			 */
			long currentTimeMillis = System.currentTimeMillis();
			/*
			 * 파일 저장 시 입력 받은 값으로 파일을 저장하는 것이 아닌, report + 현재시간(밀리초) + dat 파일로 저장하는 것이기 때문에
			 * 변수로 따로 설정해줌.
			 */
			  String folderPath = "c:/dev/report";

			    // 폴더 객체 생성
			    File folder = new File(folderPath);

			    // 폴더가 존재하지 않으면 폴더 생성
			    if (!folder.exists()) {
			        if (!folder.mkdirs()) {
			            JOptionPane.showMessageDialog(null, "폴더를 생성하는 데 실패했습니다.");
			            return;
			        }
			    }
			String fileName = "report_" + currentTimeMillis + ".dat";

			File file = new File(folderPath, fileName);
			/*
			 * FileDialog를 띄워 파일 저장을 묻고, setFile을 통해, 지정해뒀던 파일의 이름으로 나오게 해줌.
			 */
			FileDialog fileDialog = new FileDialog(md, "파일 저장", FileDialog.SAVE);
			fileDialog.setFile(fileName);
			fileDialog.setVisible(true);
			/*
			 * fileDialog.getDiectory() 메소드를 통해, 디렉토리 경로를 반환받음.
			 */
			String filePath = fileDialog.getDirectory();
			/*
			 * if문을 통해, file경로와 파일 이름의 값이 null 이 아니면, 기존에 설정해뒀던 filePath의 경로에 fileName의 이름으로
			 * 파일을 생성함.
			 */
			if (filePath != null && fileName != null) {

				try (FileWriter writer = new FileWriter(file)) {
					/*
					 * write를 통해, 로그 분석 데이터를 파일에 출력
					 */
					writer.write(printRequirementsInfo(eri));
					writer.close();
				} catch (IOException e) {
					/*
					 * try~catch를 통해 예외처리를 해주었음.
					 */
					e.printStackTrace();
					/*
					 * 예외 발생 시 메시지 다이얼로그를 띄우도록 설정했음.
					 */
					JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.");
				}
			}

		} catch (LooksLikeNotLogFileException e) {
			e.printStackTrace();
		}
	}
}
