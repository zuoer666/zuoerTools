package ssh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import main.ZuoerToolsMain;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private static Login frame;
	private JRadioButton radioButton;
	private MyJson myJson = new MyJson();
	public HostNameLogin ghostNameLogin;
	

	/**
	 * Create the frame.
	 */
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ZuoerToolsMain.recoverLocationFirst();	
				ZuoerToolsMain.sshFrame=null;
			}
			@Override
			public void windowClosed(WindowEvent e) {
				ZuoerToolsMain.recoverLocationFirst();	
				ZuoerToolsMain.sshFrame=null;
				
			}
		});
		
		setTitle("Linux应急响应响应助手 - 登录");
		//setBounds(620, 300, 450, 300);
		setSize(450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("账号：");
		lblNewLabel.setBounds(94, 66, 49, 15);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(94, 119, 49, 15);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("登录");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyWait();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						login();
					}
				}).start();
				
			}
		});
		btnNewButton.setBounds(231, 201, 93, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("注册");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		btnNewButton_1.setBounds(107, 201, 93, 23);
		contentPane.add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(153, 63, 155, 21);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel lblLinux = new JLabel("用户登录");
		lblLinux.setBounds(194, 20, 59, 15);
		contentPane.add(lblLinux);

		passwordField = new JPasswordField();
		passwordField.setBounds(153, 116, 155, 21);
		contentPane.add(passwordField);

		radioButton = new JRadioButton("记住密码");
		radioButton.setSelected(true);
		radioButton.setBounds(107, 161, 111, 23);
		contentPane.add(radioButton);
		init();
		setResizable(false);
	}

	/**
	 * @return the frame
	 */
	public static Login getFrame() {
		return frame;
	}

	/**
	 * @param frame the frame to set
	 */
	public static void setFrame(Login frame) {
		Login.frame = frame;
	}

	private void init() {
		// TODO Auto-generated method stub
		UserObject userObject = null;
		StringBuffer readBuffer = null;
		try {

			readBuffer = MyFile.readFileByBytes(Config.userfileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "获取"+Config.userfileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);

		}
		try {
			userObject = JSON.parseObject(Encryption.encryption(String.valueOf(readBuffer)), UserObject.class);
		} catch (Exception e) {
			// TODO: handle exception
			//JOptionPane.showMessageDialog(frame, "解析"+StaticFile.userfileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
		}
		int showConfirmDialogCode = -1;
		if (userObject==null) {
			showConfirmDialogCode= JOptionPane.showConfirmDialog(frame,"解析"+Config.userfileName+"内容错误！是否重置", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		if (showConfirmDialogCode==0) {
			userObject=new UserObject("", "","0");
			String s=JSON.toJSONString(userObject);
			MyFile.writeFile(Config.userfileName,Encryption.encryption(s));
		}else if(showConfirmDialogCode==1){
			System.exit(0);
		}

		// TODO Auto-generated method stub
		if (userObject.getFlag().equals("1")) {
			textField.setText(userObject.getUsername());
			passwordField.setText(userObject.getPassword());
		}else {
			radioButton.setSelected(false);
		}
	}

	//注册
	private void register() {
		// TODO Auto-generated method stub
		RegeditHttpService regeditHttpService = new RegeditHttpService();
		String usernameString=textField.getText().trim();
		String passwordString=passwordField.getText().trim();
		if(usernameString.equals("")||passwordString.equals("")){
			JOptionPane.showMessageDialog(frame, "用户或密码不能为空", "温馨提示", JOptionPane.WARNING_MESSAGE);
		}else {
			int i=regeditHttpService.httpRegedit(usernameString,passwordString);
			System.out.println("register code:"+i);
			if (i==1) {
				JOptionPane.showMessageDialog(frame, "注册成功", "温馨提示", JOptionPane.WARNING_MESSAGE);	
			}else if (i==0) {
				JOptionPane.showMessageDialog(frame, "注册失败，账号被注册", "温馨提示", JOptionPane.WARNING_MESSAGE);			
			}else{
				JOptionPane.showMessageDialog(frame, "网络错误", "温馨提示", JOptionPane.WARNING_MESSAGE);	

			}
		}

	}

	//登录 
	private void login() {
		
		String usernameString=textField.getText().trim();
		String passwordString=passwordField.getText().trim();
		LoginHttpService loginHttpService = new LoginHttpService();
		int i = loginHttpService.httpLogin(usernameString, passwordString);
		// TODO Auto-generated method stub
		MyStopWait();
		if (i==1) {
			System.out.println("登陆成功");
			HostNameLogin hostNameLoginFrame = new HostNameLogin();
			hostNameLoginFrame.setFrame(hostNameLoginFrame);
			hostNameLoginFrame.setLocationRelativeTo(null);
			hostNameLoginFrame.setVisible(true);
			ghostNameLogin=hostNameLoginFrame;
			frame.setVisible(false);
			ZuoerToolsMain.setMyLocation(hostNameLoginFrame.getX(),hostNameLoginFrame.getY());
		}else if (i==2) {
			JOptionPane.showMessageDialog(frame, "账号或密码错误", "温馨提示", JOptionPane.WARNING_MESSAGE);	
			System.out.println("账号或密码错误");
		}else{
			JOptionPane.showMessageDialog(frame, "网络错误", "温馨提示", JOptionPane.WARNING_MESSAGE);	
			System.out.println("网络错误");
		}
		boolean flag=radioButton.isSelected();
		if (flag) {
			//保存密码
			String s=JSON.toJSONString(new UserObject(usernameString, passwordString,"1"));
			MyFile.writeFile(Config.userfileName,Encryption.encryption(s));
		}else {
			//删除密码
			String s=JSON.toJSONString(new UserObject("", "","0"));
			MyFile.writeFile(Config.userfileName,Encryption.encryption(s));
		}
		
	}
	InfiniteProgressPanel glasspane;
	public void MyWait(){
		glasspane = new InfiniteProgressPanel();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		glasspane.setBounds(100, 100, (dimension.width) / 2, (dimension.height) / 2);
		frame.setGlassPane(glasspane);
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							glasspane.start();//开始动画加载效果
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
		mainThread.start();
		frame.setVisible(true);
	}
	private void MyStopWait() {
		// TODO Auto-generated method stub
		glasspane.stop();
	}
}
