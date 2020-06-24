package ssh;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.alibaba.fastjson.JSON;

import main.ZuoerToolsMain;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.naming.InitialContext;
import javax.swing.AbstractListModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class HostNameLogin extends JFrame {

	static HostNameLogin frame;
	private Ssh ssh;
	private JTextField tf_hostname;
	private JTextField tf_port;
	private JPasswordField pf_password;
	private JTextField tf_username;
	public String hostname;
	public String username;
	public String passwordString;
	public String port;
	JRadioButton rdbtnNewRadioButton;
	List<HostNameObject> hostNameObjects; //json解析HostNameObject
	List<HostNameObject> temphostNameObjects; //临时 HostNameObject 用于删除配置信息
	JList list;
	String[] setModelstring; //设置hostname
	int selectHostInt=0; //正在编辑的hostname
	boolean isBlank = true; //是否成功解析hostnameconfig信息
	String fileName; //文件保存地址
	MyJson myJson = new MyJson();
	public Main main_frame=null;
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					frame = new HostNameLogin();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	/**
	 * @return the frame
	 */
	public HostNameLogin getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public void setFrame(HostNameLogin frame) {
		HostNameLogin.frame = frame;
	}
	/**
	 * Create the frame.
	 */
	
	public HostNameLogin() {
		setLocation(6, 0);
		setTitle("Linux服务器应急响应助手 - 服务器登陆");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (main_frame!=null) {
					main_frame.dispose();
				}
				ZuoerToolsMain.recoverLocationFirst();	
				ZuoerToolsMain.sshFrame=null;
				
			}
			public void windowClosed(WindowEvent e) {
				ZuoerToolsMain.recoverLocationFirst();	
				ZuoerToolsMain.sshFrame=null;
			}
		});
		
		//setBounds(620, 300, 460, 315);
		setSize(450, 300);
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("帐号：");
		lblNewLabel.setBounds(200, 92, 61, 16);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("密码：");
		lblNewLabel_1.setBounds(200, 137, 61, 16);
		getContentPane().add(lblNewLabel_1);

		tf_hostname = new JTextField();
		tf_hostname.setBounds(282, 45, 130, 26);
		getContentPane().add(tf_hostname);
		tf_hostname.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("端口：");
		lblNewLabel_2.setBounds(200, 186, 61, 16);
		getContentPane().add(lblNewLabel_2);

		tf_port = new JTextField();
		tf_port.setBounds(282, 181, 130, 26);
		getContentPane().add(tf_port);
		tf_port.setColumns(10);

		pf_password = new JPasswordField();
		pf_password.setBounds(282, 132, 130, 26);
		getContentPane().add(pf_password);

		JLabel lblLinux = new JLabel("服务器登陆");
		lblLinux.setBounds(190, 17, 70, 16);
		getContentPane().add(lblLinux);

		JLabel lblIp = new JLabel("服务器地址:");
		lblIp.setBounds(200, 50, 70, 16);
		getContentPane().add(lblIp);

		tf_username = new JTextField();
		tf_username.setColumns(10);
		tf_username.setBounds(282, 87, 130, 26);
		getContentPane().add(tf_username);

		rdbtnNewRadioButton = new JRadioButton("记住密码");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setBounds(10, 227, 111, 23);
		getContentPane().add(rdbtnNewRadioButton);


		JButton bt_login = new JButton("登陆");
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyWait();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						saveHostNameConfig();
						showLeftHostname();
						login();
					}
				}).start();



			}
		});
		bt_login.setBounds(141, 224, 240, 29);
		getContentPane().add(bt_login);

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				listReleased();
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 183, 157);
		scrollPane.add(list);
		scrollPane.setViewportView(list);
		getContentPane().add(scrollPane);

		JLabel lblNewLabel_3 = new JLabel(" +");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				addButton();
			}
		});
		lblNewLabel_3.setBounds(20, 207, 15, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel label = new JLabel(" -");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				removeButton();
			}
		});
		label.setBounds(47, 207, 15, 15);
		getContentPane().add(label);

		JLabel label_1 = new JLabel(" ↑");
		label_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row=list.getSelectedIndex();
				if (row==-1) {
					JOptionPane.showMessageDialog(frame, "请选择要移动的服务器配置！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				moveUp(row);
			}
		});
		label_1.setBounds(139, 209, 15, 15);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel(" ↓");
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row=list.getSelectedIndex();
				if (row==-1) {
					JOptionPane.showMessageDialog(frame, "请选择要移动的服务器配置！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				moveDown(row);

			}
		});
		label_2.setBounds(159, 208, 15, 15);
		getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("密码保存在本地,不保存到服务器");
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		label_3.setBounds(20, 256, 200, 16);
		getContentPane().add(label_3);

		//初始化
		Init();
		setResizable(false);
	}
	
	protected void moveUp(int row) {
		if (row==0) {
			return;
		}
		// TODO Auto-generated method stub
		HostNameObject hostNameObjectTempHostNameObject = hostNameObjects.get(selectHostInt-1);
		hostNameObjects.set(selectHostInt-1, hostNameObjects.get(selectHostInt));
		hostNameObjects.set(selectHostInt, hostNameObjectTempHostNameObject);
		showLeftHostname();
		selectHostInt--;
		list.setSelectedIndex(selectHostInt);
	}
	protected void moveDown(int row) {
		// TODO Auto-generated method stub
		if (row==hostNameObjects.size()-1) {
			return;
		}
		HostNameObject hostNameObjectTempHostNameObject = hostNameObjects.get(selectHostInt+1);
		hostNameObjects.set(selectHostInt+1, hostNameObjects.get(selectHostInt));
		hostNameObjects.set(selectHostInt, hostNameObjectTempHostNameObject);
		showLeftHostname();
		selectHostInt++;
		list.setSelectedIndex(selectHostInt);
	}
	//list点击
	private void listReleased() {
		// TODO Auto-generated method stub
		saveHostNameConfig();
		selectHostInt=list.getSelectedIndex();
		if (selectHostInt==-1) {
			selectHostInt=0;
		}
		showRightHostname(selectHostInt);
		System.out.println("当前选择"+selectHostInt);
		showLeftHostname();
		list.setSelectedIndex(selectHostInt);
	}

	//添加按钮
	private void addButton() {
		// TODO Auto-generated method stub
		saveHostNameConfig();
		addHostNameConfig();
		showLeftHostname();
		list.setSelectedIndex(hostNameObjects.size()-1);
		selectHostInt=hostNameObjects.size()-1;
		System.out.println("当前选择"+selectHostInt);
		showRightHostname(selectHostInt);
	}

	//删除按钮
	private void removeButton() {
		// TODO Auto-generated method stub
		saveHostNameConfig();
		removeHostNameConfig();
		selectHostInt=selectHostInt-1;
		if (selectHostInt<0) {
			selectHostInt=0;
		}
		showLeftHostname();
		list.setSelectedIndex(selectHostInt);
		System.out.println("当前选择"+selectHostInt);
		showRightHostname(selectHostInt);
	}

	//登录
	private void login() {
		// TODO Auto-generated method stub
		Callable<Integer> callable = () -> {
			hostname = tf_hostname.getText().trim();
			username =tf_username.getText().trim();
			passwordString=pf_password.getText().trim();
			port=tf_port.getText().trim();
			if (hostname.equals("")||username.equals("")||passwordString.equals("")||port.equals("")) {
				JOptionPane.showMessageDialog(frame, "输入不能为空", "温馨提示", JOptionPane.WARNING_MESSAGE);
				MyStopWait();
				return -1;
			}
			boolean flag=getRadioButtonCode();
			if (flag) {
				//保存密码
				//解析json
				String s=JSON.toJSONString(hostNameObjects);
				MyFile.writeFile(fileName,Encryption.encryption(s));
			}else {
				//删除密码
				hostNameObjects = new ArrayList<HostNameObject>();
				String s=JSON.toJSONString(hostNameObjects);
				MyFile.writeFile(fileName,Encryption.encryption(s));
			}

			Config.tempstatic_outString_map = Config.static_outString_map;
			Config.tempshellOutObject = Config.shellOutObject;


			Config.shellOutObject=new ShellOutObject(hostname,"shell");
			MultiUserOutStringBase tempmMultiUserOutStringBase = new MultiUserOutStringBase();
			Config.static_outString_map.put(hostname,tempmMultiUserOutStringBase);
			ssh = new Ssh(hostname,username,passwordString,port);



			boolean aaa = ssh.ssh_login();
			if (Config.sshCode==1) {
				JOptionPane.showMessageDialog(frame, "网络不可达", "温馨提示", JOptionPane.WARNING_MESSAGE);
				MyStopWait();
				return -2;
			}
			if (aaa) {
				//登录成功
				MyStopWait();
				list.setSelectedIndex(selectHostInt);
				main_frame = new Main(new MultiUserBase(hostname,username,passwordString,port,ssh));
				main_frame.setLocationRelativeTo(null);
				main_frame.setVisible(true);
				main_frame.btn_shell.doClick();
				ZuoerToolsMain.setMyLocation(main_frame.getX(),main_frame.getY());
				
				//tempmMultiUserOutStringBase
				//frame.setVisible(false);
			}else {
				MyStopWait();
				JOptionPane.showMessageDialog(frame, "账号或密码错误", "温馨提示", JOptionPane.WARNING_MESSAGE);
				System.out.println("no");
				Config.static_outString_map=Config.tempstatic_outString_map;
				Config.shellOutObject=Config.tempshellOutObject;

			}
			return -3;
		};
		int timeout = 18000;
		Integer timeoutValue = -1;
		TimeoutCallable<Integer> timeoutCallable = new TimeoutCallable<>(callable, timeout, timeoutValue);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Integer> future = executor.submit(timeoutCallable);
		Integer result = null;
		try {
			 result = future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executor.shutdown();

//		System.out.println(timeoutValue);
//		System.out.println(result);
		if (timeoutValue.equals(result)) {
			MyStopWait();
			JOptionPane.showMessageDialog(frame, "连接超时", "温馨提示", JOptionPane.WARNING_MESSAGE);
		} else {
			System.out.println("任务结果：" + result);
		}
	}
	//初始化
	private void Init() {
		//读host信息
		fileName = Config.hostmessagefileName;
		StringBuffer readBuffer = null;
		try {
			readBuffer = MyFile.readFileByBytes(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			//hostStrings = myJson.JsontoStringarray1(String.valueOf(readBuffer));
			hostNameObjects = JSON.parseArray(Encryption.encryption(String.valueOf(readBuffer)), HostNameObject.class);
		} catch (Exception e) {
			// TODO: handle exception
			isBlank=false;
		}
		int showConfirmDialogCode = -1;
		if (hostNameObjects==null) {
			showConfirmDialogCode= JOptionPane.showConfirmDialog(frame,"获取"+fileName+"错误！是否重置该文件", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		}
		if (showConfirmDialogCode==0) {
			hostNameObjects = new ArrayList<HostNameObject>();
			String s=JSON.toJSONString(hostNameObjects);
			MyFile.writeFile(fileName,Encryption.encryption(s));
		}else if(showConfirmDialogCode==1){
			System.exit(0);
		}
		if (hostNameObjects.isEmpty()) {
			addHostNameConfig();
		}

		// TODO Auto-generated method stub

		//显示右边	
		showRightHostname(selectHostInt);

		//显示左边
		showLeftHostname();
		list.setSelectedIndex(selectHostInt);
		list.setBounds(0, 0, 176, 128);
	}
	//显示右边信息
	private void showRightHostname(int i) {
		if (!hostNameObjects.isEmpty()) {
			tf_hostname.setText(hostNameObjects.get(i).getHostname());
			tf_username.setText(hostNameObjects.get(i).getHostuser());
			pf_password.setText(hostNameObjects.get(i).getHostpassword());
			tf_port.setText(hostNameObjects.get(i).getHostport());
		}

	}
	//添加配置信息到hostNameObjects里
	private void addHostNameConfig() {
		HostNameObject tempHostNameObject =new HostNameObject("temp.com","","","",getRadioButtonCodeString());
		hostNameObjects.add(tempHostNameObject);

	}
	//删除配置信息到hostNameObjects里
	private void removeHostNameConfig() {
		temphostNameObjects= new ArrayList<HostNameObject>();
		int hostNameObjectsSize =hostNameObjects.size();
		if (hostNameObjectsSize>0) {
			for (int i = 0; i < hostNameObjectsSize; i++) {
				if (i!=selectHostInt) {
					temphostNameObjects.add(hostNameObjects.get(i));
				}
			}
		}
		hostNameObjects=temphostNameObjects;
	}
	//保存添加或修改的配置信息到hostNameObjects里
	private void saveHostNameConfig() {
		if (!hostNameObjects.isEmpty()) {
			HostNameObject tempHostNameObject =new HostNameObject(tf_hostname.getText(),tf_username.getText(), pf_password.getText(), tf_port.getText(),getRadioButtonCodeString());
			hostNameObjects.remove(selectHostInt);
			hostNameObjects.add(selectHostInt, tempHostNameObject);
		}

	}
	//显示左边信息
	private void showLeftHostname() {
		setModelstring = new String[hostNameObjects.size()];
		if (hostNameObjects.size()>0) {
			list.setSelectedIndex(0);
			//添加List
			for (int i = 0; i < hostNameObjects.size(); i++) {
				setModelstring[i]=hostNameObjects.get(i).getHostname();
			}

		}else if(!isBlank){
			JOptionPane.showMessageDialog(frame, "获取"+fileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
		}
		list.setModel(new AbstractListModel() {
			//String[] setModelstring = new String[] {"123", "321"};
			public int getSize() {
				return setModelstring.length;
			}
			public Object getElementAt(int index) {
				return setModelstring[index];
			}
		});
	}

	private String getRadioButtonCodeString() {
		String resultString="";
		boolean flag=rdbtnNewRadioButton.isSelected();
		if (flag) {
			//被选中
			resultString="1";
		}else {
			//未选中
			resultString="0";
		}
		return resultString;
	}

	private boolean getRadioButtonCode() {
		boolean flag=rdbtnNewRadioButton.isSelected();
		return flag;
	}
	public void successTip(){
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(null, "一切正常！", "温馨提示", JOptionPane.WARNING_MESSAGE);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		});
		mainThread.start();
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