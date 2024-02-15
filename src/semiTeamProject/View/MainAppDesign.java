package semiTeamProject.View;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import semiTeamProject.Controller.MainAppEvent;
import semiTeamProject.Model.IndexJTextField;
import semiTeamProject.Model.IntegerDocument;


@SuppressWarnings("serial")
public class MainAppDesign extends JFrame {
	private JButton view, report, jbtnLogout;
	private JTextArea info;
	private IndexJTextField startLog;
	private IndexJTextField endLog;
	private JMenuBar menuBar;
	private JLabel jlblWhosLogin;
	
	public JMenuItem getOpenMenuItem() {
		return openMenuItem;
	}

	private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem jmiUrlOption;
	private boolean isAdmin;
	

	public MainAppDesign(boolean isAdmin, String userId) {
		this.isAdmin = isAdmin;
		
		Path currentPath = Paths.get("");
        String path = currentPath.toAbsolutePath().toString().replace("\\", "/").concat("/");
		
		ImageIcon iView = new ImageIcon(new File(path + "image/view.png").getAbsolutePath());
		ImageIcon iReport = new ImageIcon(new File(path + "image/report.png").getAbsolutePath());
		ImageIcon iLogout = new ImageIcon(new File(path + "image/logout.png").getAbsolutePath());
		
		view = new JButton(iView);
		report = new JButton(iReport);
		jbtnLogout = new JButton(iLogout);
		info = new JTextArea();
		JScrollPane jsp = new JScrollPane(info);
		IntegerDocument idStart = new IntegerDocument();
		IntegerDocument idEnd = new IntegerDocument();
		startLog = new IndexJTextField(10);
		endLog = new IndexJTextField(10);
		startLog.setDocument(idStart);
		endLog.setDocument(idEnd);
		jlblWhosLogin = new JLabel("로그인된 아이디: " + userId);
		
		
		Font tbFont = new Font("맑은 고딕",Font.BOLD,13);
		TitledBorder tbStart = new TitledBorder(new LineBorder(new Color(0x200F31)), "시작 라인");
		TitledBorder tbEnd = new TitledBorder(new LineBorder(new Color(0x200F31)), "끝 라인");
		tbStart.setTitleFont(tbFont);
		tbEnd.setTitleFont(tbFont);
		
		startLog.setBorder(tbStart);
		endLog.setBorder(tbEnd);
		
		setLayout(null);
		
		view.setBounds(40, 535, 140, 100);
		report.setBounds(210, 535, 140, 100);
		jbtnLogout.setBounds(660, 535, 90, 100);
		jlblWhosLogin.setBounds(0, 0, 300, 20);
		jsp.setBounds(0,20,785,500);
		startLog.setBounds(380, 530, 80, 50);
		endLog.setBounds(380, 590, 80, 50);
		
		add(jsp);
		add(view);
		add(report);
		add(jbtnLogout);
		add(startLog);
		add(endLog);
		add(jlblWhosLogin);
		
		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("파일");
		openMenuItem = new JMenuItem("열기");
		JMenu jmTool = new JMenu("도구");
		jmiUrlOption = new JMenuItem("옵션");
		
		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);
		jmTool.add(jmiUrlOption);
		menuBar.add(jmTool);
		setJMenuBar(menuBar);
		
		Font mainFont = new Font("맑은 고딕",Font.PLAIN,13);
		
		jsp.setFont(mainFont);
		jlblWhosLogin.setFont(mainFont);
		fileMenu.setFont(mainFont);
		jmTool.setFont(mainFont);
		openMenuItem.setFont(mainFont);
		jmiUrlOption.setFont(mainFont);
		
		
		//색
		Color backgroundColor = new Color(0xECEDFA);
		getContentPane().setBackground(backgroundColor);
		startLog.setBackground(backgroundColor);
		endLog.setBackground(backgroundColor);
		
		
		MainAppEvent me = new MainAppEvent(this);
		openMenuItem.addActionListener(me);
		report.addActionListener(me);
		view.addActionListener(me);
		jbtnLogout.addActionListener(me);
		jmiUrlOption.addActionListener(me);
		addWindowFocusListener(me);
		
		setBounds(550,100,800,715);
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

	public JMenuItem getJmiUrlOption() {
		return jmiUrlOption;
	}

	public JButton getJbtnLogout() {
		return jbtnLogout;
	}

	public JLabel getJlblWhosLogin() {
		return jlblWhosLogin;
	}
	
}//class
