package Semi_Team_Project.View;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Semi_Team_Project.Controller.MainAppEvent;

@SuppressWarnings("serial")
public class MainAppDesign extends JFrame {
	/*
	 * Browse 버튼 제외하고 menuBar로 대체
	 */
	private JButton view;
	private JButton report;
	private JTextArea info;
	private JTextField startLog;
	private JTextField endLog;
	private JMenuBar menuBar;
	
	public JMenuItem getOpenMenuItem() {
		return openMenuItem;
	}

	private JMenu fileMenu;
    private JMenuItem openMenuItem;
	private boolean isAdmin;
	

	public MainAppDesign(boolean isAdmin) {
		super("로그 분석 어플");
		this.isAdmin = isAdmin;
		view = new JButton("View");
		report = new JButton("Report");
		info = new JTextArea();
		startLog = new JTextField(10);
		endLog = new JTextField(10);
		
		//입력값 기준이 수정되면 수정해야합니다
		//배경 투명으로 하고싶었는데 방법을 모르겠어서 야매로 jl로 두개붙여요,,
		JLabel jlHelp1 = new JLabel("라인 지정 없이 처음부터 끝까지의 데이터를 뽑으시려면");
		JLabel jlHelp2 = new JLabel("\n시작라인에 0을, 끝라인에 -1을 입력하세요");
		
		//초기값 설정
		startLog.setText("0");
		endLog.setText("-1");
		
		JScrollPane jsp = new JScrollPane(info);
		jsp.setFont(new Font("맑은 고딕",Font.PLAIN,13));
		jlHelp1.setFont(new Font("맑은 고딕",Font.PLAIN,25));
		jlHelp2.setFont(new Font("맑은 고딕",Font.PLAIN,25));
		
		
		setLayout(null);
		startLog.setBorder(new TitledBorder("시작 라인"));
		endLog.setBorder(new TitledBorder("끝 라인"));
		
		
		view.setBounds(40, 535, 210, 150);
		report.setBounds(285, 535, 210, 150);
		jsp.setBounds(0,0,785,500);
		startLog.setBounds(535, 535, 80, 45);
		endLog.setBounds(645, 535, 80, 45);
		jlHelp1.setBounds(40,680,700,90);
		jlHelp2.setBounds(40,720,700,90);
		
		
		add(jsp);
		add(view);
		add(report);
		add(startLog);
		add(endLog);
		add(jlHelp1);
		add(jlHelp2);
		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("파일");
		openMenuItem = new JMenuItem("열기");
		
		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		
		MainAppEvent me = new MainAppEvent(this);
		openMenuItem.addActionListener(me);
		report.addActionListener(me);
		view.addActionListener(me);
		addWindowFocusListener(me);
		
		setBounds(550,100,800,850);
		setVisible(true);
		setResizable(false);//창 크기 변경 불가능
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}//MainAppDesign
	
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	public JButton getView() {
		return view;
	}
	
	public JButton getReport() {
		return report;
	}
	
	public JTextArea getInfo() {
		return info;
	}
	
	public JTextField getStartLog() {
		return startLog;
	}
	
	public JTextField getEndLog() {
		return endLog;
	}
}//class
