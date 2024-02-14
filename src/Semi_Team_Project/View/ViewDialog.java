package Semi_Team_Project.View;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import Semi_Team_Project.Model.BrowserRatioVO;
import Semi_Team_Project.Model.ClassifyLogLines;
import Semi_Team_Project.Model.ExtractRequirementsInfo;
import Semi_Team_Project.Model.LogInfoVO;
import Semi_Team_Project.Model.LooksLikeNotLogFileException;
import Semi_Team_Project.Model.StoreData;
//

@SuppressWarnings("serial")
public class ViewDialog extends JDialog {
	
	// 1. 디자인 클래스 또는 이벤트 클래스에선 List<LogInfoVO> listLogInfo을 instance 변수로 선언
	List<LogInfoVO> listLogInfo;
	MainAppDesign ad;
	
	
	String path;
	String data1, data2, data3, data4, data5, data6;
	
	
	
	
	public ViewDialog(MainAppDesign ad, String path) {
		super(ad, "View : " + path, true);
		this.ad = ad;
		this.path = path;
		design();
		
	}//ViewDialogDesign
	
	private void design() {
		
		Font font = new Font("맑은 고딕",Font.BOLD,15);
		
		
		TitledBorder tb1 = new TitledBorder("최다 사용 키의 이름과 횟수");
		TitledBorder tb2 = new TitledBorder("브라우저별 접속 횟수, 비율");
		TitledBorder tb3 = new TitledBorder("서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수");
		TitledBorder tb4 = new TitledBorder("요청이 가장 많은 시간");
		TitledBorder tb5 = new TitledBorder("비정상적인 요청(403)이 발생한 횟수,비율");
		TitledBorder tb6 = new TitledBorder("books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율");
		
		JTextArea jta1 = new JTextArea();
		JTextArea jta2 = new JTextArea();
		JTextArea jta3 = new JTextArea();
		JTextArea jta4 = new JTextArea();
		JTextArea jta5 = new JTextArea();
		JTextArea jta6 = new JTextArea();
		
		tb1.setTitleFont(font);
		tb2.setTitleFont(font);
		tb3.setTitleFont(font);
		tb4.setTitleFont(font);
		tb5.setTitleFont(font);
		tb6.setTitleFont(font);
		
		jta1.setBorder(tb1);
		jta2.setBorder(tb2);
		jta3.setBorder(tb3);
		jta4.setBorder(tb4);
		jta5.setBorder(tb5);
		jta6.setBorder(tb6);
		
		readLogFile();
		
		jta1.setText(data1);
		jta2.setText(data2);
		jta3.setText(data3);
		jta4.setText(data4);
		jta5.setText(data5);
		jta6.setText(data6);
		
		
		setLayout(new GridLayout(6,1));
		jta1.setEditable(false);
		jta2.setEditable(false);
		jta3.setEditable(false);
		jta4.setEditable(false);
		jta5.setEditable(false);
		jta6.setEditable(false);
		
		
		add(jta1);
		add(jta2);
		add(jta3);
		add(jta4);
		add(jta5);
		add(jta6);
		
		
		setBounds(200,200,500,700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
	}//design
	
	private void readLogFile() {
		
		// 2. 파일 다이어그램으로 선택한 파일을 가져오기
		
		String pathFile = path;
		
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
			e.printStackTrace();
		} catch (LooksLikeNotLogFileException e) {
			e.printStackTrace();
		} // end catch
		
		// 6. 분류한 데이터를 인스턴스 변수에 저장
		listLogInfo = cll.getLogInfo();
		
		// 7-1. 분류된 데이터 전체를 분석하여 요구사항이 요구하는 값 얻기
		ExtractRequirementsInfo eri = new ExtractRequirementsInfo(listLogInfo);
		
		// 8. 데이터를 인스턴스 변수에 담기
		eri.printRequirementsInfo();
		
			// 8-1. 최다 사용 키의 이름과 횟수
		data1 = eri.getOftenKeyName() + " / " + String.valueOf(eri.getOftenKeyNum());
		
			// 8-2. 브라우저별 접속 횟수, 비율
		List<BrowserRatioVO> brvo = eri.getBrowserRatio();
		String temp = "";
		StringBuilder sb = new StringBuilder();
		for(BrowserRatioVO b : brvo) {
			temp = b.getBrowser() + " : " + b.getCount() + "회 " +
					String.format("%.2f", b.getRatio()) + "%\n";
			sb.append(temp);
		}//end for
		data2 = sb.toString();
		
			// 8-3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
		data3 = eri.getService200() + " / " + eri.getService404();
		
			// 8-4. 요청이 가장 많은 시간
		data4 = eri.getOftenHour() + "시";
		
			// 8-5. 비정상적인 요청(403)이 발생한 횟수,비율
		data5 = eri.getService403() + " / " +
				String.format("%.2f", eri.getService403Ratio());
		
			// 8-6. books에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율
		data6 = eri.getReqBooksErrNum() + " / " +
				String.format("%.2f",eri.getReqBooksErrRatio());
		
		
	}//readLogFile
	

}//class
