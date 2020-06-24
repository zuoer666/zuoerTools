package ssh;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.color.ColorSpace;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.ZuoerToolsMain;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;

public class Main extends JFrame {
	public MultiUserBase mainmultiUserBase;

	Virus panel_virus;
	JPanel panel_proxy;
	JPanel panel_mining;
	JPanel panel_file_change;
	M_File panel_file;
	JPanel panel_process;
	Log panel_log;
	Shell panel_shell;
	String shell_out;
	JButton btn_virus;
	JButton btn_shell;
	JButton btn_log;
	JButton btn_file ;
	JButton btn_file_change;
	JButton btn_mining;
	JButton btn_proxy;
	JButton btn_process;
	
	public JPanel getPanel_shell() {
		return panel_shell;
	}

	public void setPanel_shell(Shell panel_shell) {
		this.panel_shell = panel_shell;
	}
	private JPanel contentPane;
	//当前显示面板
	String flags="virus";

	/**
	 * Create the frame.
	 */
	public Main(MultiUserBase multiUserBase) {
		this.mainmultiUserBase = multiUserBase;
		this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowActivated(WindowEvent e) {
				System.out.println("当前用户："+multiUserBase.getHostName());

			}
			
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				ZuoerToolsMain.recoverLocation();	
				ZuoerToolsMain.sshFrame.ghostNameLogin.main_frame=null;
				ZuoerToolsMain.sshButtonClick();
				close();
			}
		
			
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				ZuoerToolsMain.recoverLocation();	
				ZuoerToolsMain.sshFrame.ghostNameLogin.main_frame=null;
				ZuoerToolsMain.sshButtonClick();
				close();
			}

			
		});
		
		setTitle("Linux应急响应响应助手 - 命令行 "+mainmultiUserBase.getHostname());
		KeepConnect keepConnectThread = new KeepConnect(mainmultiUserBase);
		new Thread(keepConnectThread).start();
		//setBounds(320, 300, 1059, 500);
		setSize(1059, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btn_virus = new JButton("木马检测");
		btn_virus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="virus";
				mySetVisibletrue("virus");

			}
		});
		btn_virus.setBounds(30, 25, 107, 29);
		contentPane.add(btn_virus);

		btn_proxy = new JButton("代理检测");
		btn_proxy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="proxy";
				mySetVisibletrue("proxy");
			}
		});
		btn_proxy.setBounds(157, 25, 107, 29);
		contentPane.add(btn_proxy);

		btn_mining = new JButton("挖矿检测");
		btn_mining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="mining";
				mySetVisibletrue("mining");
			}
		});
		btn_mining.setBounds(284, 25, 107, 29);
		contentPane.add(btn_mining);

		btn_file_change = new JButton("文件修改检测");
		btn_file_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="file_change";
				mySetVisibletrue("file_change");
			}
		});
		btn_file_change.setBounds(411, 25, 107, 29);
		contentPane.add(btn_file_change);

		btn_file = new JButton("文件管理");
		btn_file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="file";
				mySetVisibletrue("file");
				panel_file.refresh();
			}
		});
		btn_file.setBounds(538, 25, 107, 29);
		contentPane.add(btn_file);

		btn_log = new JButton("日志管理");
		btn_log.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="log";
				mySetVisibletrue("log");
			}
		});
		btn_log.setBounds(792, 25, 107, 29);
		contentPane.add(btn_log);

		btn_shell = new JButton("命令行");
		btn_shell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="shell";
				mySetVisibletrue("shell");
			}
		});
		btn_shell.setBounds(919, 25, 107, 29);
		contentPane.add(btn_shell);
		
		btn_process = new JButton("进程管理");
		btn_process.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flags="process";
				mySetVisibletrue("process");
			}
		});
		btn_process.setBounds(665, 25, 107, 29);
		contentPane.add(btn_process);




		panel_virus = new Virus(mainmultiUserBase);
		panel_virus.setLocation(0, 75);
		contentPane.add(panel_virus);
		panel_virus.setVisible(false);

		panel_proxy = new Proxy(mainmultiUserBase);
		panel_proxy.setLocation(0, 75);
		contentPane.add(panel_proxy);
		panel_proxy.setVisible(false);


		panel_mining = new Mining(mainmultiUserBase);
		panel_mining.setLocation(0, 75);
		contentPane.add(panel_mining);
		panel_mining.setVisible(false);


		panel_file_change = new File_Change(mainmultiUserBase);
		panel_file_change.setLocation(0, 75);
		contentPane.add(panel_file_change);
		panel_file_change.setVisible(false);


		panel_file = new M_File(mainmultiUserBase);
		panel_file.setM_File(panel_file);
		panel_file.setLocation(0, 75);
		contentPane.add(panel_file);
		panel_file.setVisible(false);

		panel_process = new Process(mainmultiUserBase);
		panel_process.setLocation(0, 75);
		contentPane.add(panel_process);
		panel_process.setVisible(false);

		panel_log = new Log(mainmultiUserBase);
		panel_log.setLog(panel_log);
		panel_log.setLocation(0, 75);
		contentPane.add(panel_log);
		panel_log.setVisible(false);

		JSeparator separator = new JSeparator();
		separator.setBounds(40, 65, 999, 12);
		contentPane.add(separator);
		
		panel_shell = new Shell(mainmultiUserBase);
		panel_shell.setMyShell(panel_shell);
		panel_shell.setLocation(0, 75);
		contentPane.add(panel_shell);
		//panel_shell.setVisible(false);
		
		
		btn_shell.setSelected(true); 
		setResizable(false);
	}

	public void mySetVisibletrue(String myflags){
		mySetVisiblefalse();
		mySetBTNColorefalse();
		switch (myflags) {
		case "virus":
			btn_virus.setSelected(true); 
			panel_virus.setVisible(true);
			setTitle("Linux应急响应响应助手 - 木马检测 "+mainmultiUserBase.getHostname());
			break;
		case "proxy":
			btn_proxy.setSelected(true); 
			panel_proxy.setVisible(true);
			setTitle("Linux应急响应响应助手 - 代理检测 "+mainmultiUserBase.getHostname());
			break;
		case "mining":
			btn_mining.setSelected(true); 
			panel_mining.setVisible(true);
			setTitle("Linux应急响应响应助手 - 挖矿检测 "+mainmultiUserBase.getHostname());
			break;
		case "file_change":
			btn_file_change.setSelected(true); 
			panel_file_change.setVisible(true);
			setTitle("Linux应急响应响应助手 - 文件修改检测 "+mainmultiUserBase.getHostname());
			break;
		case "file":
			btn_file.setSelected(true);
			panel_file.setVisible(true);
			setTitle("Linux应急响应响应助手 - 文件管理 "+mainmultiUserBase.getHostname());
			break;
		case "process":
			btn_process.setSelected(true);
			panel_process.setVisible(true);
			setTitle("Linux应急响应响应助手 - 进程管理 "+mainmultiUserBase.getHostname());
			break;
		case "log":
			btn_log.setSelected(true); 
			panel_log.setVisible(true);
			setTitle("Linux应急响应响应助手 - 日志管理 "+mainmultiUserBase.getHostname());
			break;
		case "shell":
			btn_shell.setSelected(true); 
			panel_shell.setVisible(true);
			panel_shell.shell_textField.requestFocus();
			setTitle("Linux应急响应响应助手 - 命令行 "+mainmultiUserBase.getHostname());
			break;
		default:
			break;
		}
	}
	public void mySetVisiblefalse(){
		panel_virus.setVisible(false);
		panel_proxy.setVisible(false);
		panel_mining.setVisible(false);
		panel_file_change.setVisible(false);
		panel_file.setVisible(false);
		panel_process.setVisible(false);
		panel_log.setVisible(false);
		panel_shell.setVisible(false);
	}
	public void mySetBTNColorefalse(){
		btn_virus.setSelected(false); 
		btn_shell.setSelected(false);
		btn_log.setSelected(false);
		btn_file.setSelected(false);
		btn_process.setSelected(false);
		btn_file_change.setSelected(false);
		btn_mining.setSelected(false); 
		btn_proxy.setSelected(false); 
	}
	
	public void close(){
		if (panel_shell.commands!=null) {
			panel_shell.commands.dispose();
		}
		
		if (panel_log.frame!=null) {
			panel_log.frame.dispose();
		}
		
		if (panel_file.newFile!=null) {
			panel_file.newFile.dispose();
		}
		
		if (panel_file.editor!=null) {
			panel_file.editor.dispose();
		}
		
		if (panel_virus.editor!=null) {
			panel_virus.editor.dispose();
		}
		
		if (mainmultiUserBase!=null&&mainmultiUserBase.getSsh()!=null) {
			mainmultiUserBase.getSsh().exit();
		}
		
	}
	
}
