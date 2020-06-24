package ssh;

import javax.swing.JPanel;
import javax.sound.midi.Soundbank;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.w3c.dom.CDATASection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class File_Change extends JPanel {
	private JTextField textField;

	MultiUserBase multiUserBase;
	Main_FileChange main_FileChange;
	//显示的web目录
	String webdirString ="";
	//当前目录
	String pwdString="";
	//到这个目录 结束迭代
	String webendString="";
	List<FileChangeObjectBase> listFile ;
	FileChangeGetResultObject file;
	List<FileChangeObjectBase> listDir;
	FileChangeObjectBase fileChangeObjectBase;
	int nameNumber;
	//nameNbmber是否初始化
	boolean flag=true;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	Editor editor ;
	/**
	 * Create the panel.
	 */
	public File_Change(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		JButton btnNewButton = new JButton("检测文件修改");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(btnNewButton,"请输入web目录", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				MyWait();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						fileChangeDetection();
					}
				}).start();

			}
		});
		btnNewButton.setBounds(527, 6, 117, 29);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("重置时间");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(btnNewButton,"请输入web目录", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				MyWait();
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						reset();
					}
				}).start();


			}
		});
		btnNewButton_1.setBounds(656, 6, 117, 29);
		add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(133, 6, 382, 26);
		add(textField);
		textField.setColumns(10);

		JLabel lblWeb = new JLabel("web目录：");
		lblWeb.setBounds(49, 11, 72, 16);
		add(lblWeb);

		JLabel label = new JLabel("添加");
		label.setBounds(49, 58, 54, 15);
		add(label);

		JLabel label_1 = new JLabel("删除");
		label_1.setBounds(382, 58, 65, 15);
		add(label_1);

		JLabel label_2 = new JLabel("修改");
		label_2.setBounds(689, 58, 54, 15);
		add(label_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 95, 276, 265);
		add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
				}
				) 
				);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				System.out.println(clickTimes);
				if (clickTimes == 2) {
					//文件管理器table被双击
					String filename = table.getValueAt(table.getSelectedRow(),1).toString()+"/"+table.getValueAt(table.getSelectedRow(),0).toString();
					String catString =  catFile(filename);
					editor = new Editor(filename,filename,catString,multiUserBase);
					editor.setLocationRelativeTo(null);
					editor.setVisible(true);


				}
			}
		});
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(382, 95, 276, 265);
		add(scrollPane_1);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
				}
				));
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {

				}
			}
		});
		scrollPane_1.setViewportView(table_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(689, 95, 276, 265);
		add(scrollPane_2);

		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
				}
				));
		table_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {
					//文件管理器table被双击
					String filename = table_2.getValueAt(table_2.getSelectedRow(),1).toString()+"/"+table_2.getValueAt(table_2.getSelectedRow(),0).toString();
					String catString =  catFile(filename);
					editor = new Editor(filename,filename,catString,multiUserBase);
					editor.setLocationRelativeTo(null);
					editor.setVisible(true);


				}
			}
		});
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNewLabel = new JLabel("文件名、文件目录以及文件时间会保存在服务器，文件内容则不会。由于网络原因，如果文件较多，结果可能不准确，有时间我会改进。");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblNewLabel.setBounds(49, 359, 577, 16);
		add(lblNewLabel);
		init();
	}
	public void savewebdir(){
		webdirString=textField.getText().trim();
		ConfigObject configObject = new ConfigObject(webdirString, "");
		Config.static_config_map.put(multiUserBase.getHostName(), configObject);
		String s = JSON.toJSONString(Config.static_config_map);
		MyFile.writeFile(Config.configfileName,s);
	}

	private void init() {
		// TODO Auto-generated method stub

		//解析 Json to hashmap 然后 显示webdir
		SetText();

	}

	//解析 Json to hashmap 然后 显示webdir
	private void SetText() {
		// TODO Auto-generated method stub
		StringBuffer readBuffer = null;
		try {
			readBuffer = MyFile.readFileByBytes(Config.configfileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//hostStrings = myJson.JsontoStringarray1(String.valueOf(readBuffer));
			Config.static_config_map = (Map<String, ConfigObject>) JSON.parseObject(String.valueOf(readBuffer), new TypeReference<Map<String, ConfigObject>>() {
			});
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this,"获取"+Config.configfileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
		}
		ConfigObject configObject = Config.static_config_map.get(multiUserBase.getHostName());
		if (configObject!=null) {
			textField.setText(configObject.getWebdir());
		}


	}
	protected void fileChangeDetection() {
		// TODO Auto-generated method stub
		savewebdir();

		//获取本地listFile
		listFile = new ArrayList<FileChangeObjectBase>();
		listDir = new ArrayList<FileChangeObjectBase>();
		main_FileChange = new Main_FileChange(multiUserBase);
		cd__(textField.getText().trim());
		getAllFile();

		GetFileChangeHttpService service = new GetFileChangeHttpService();
		FileChangeGetResultObject file = service.GetFileChange(multiUserBase);
		if (file.getResultcode()==-2) {
			MyStopWait();
			JOptionPane.showMessageDialog(this,"请重新登录！", "温馨提示", JOptionPane.WARNING_MESSAGE);	
		}else{
			if (file.getList()==null) {
				MyStopWait();
				JOptionPane.showMessageDialog(this,"请求服务器超时登录！", "温馨提示", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if (file.getList().isEmpty()) {
				MyStopWait();
				JOptionPane.showMessageDialog(this,"请先重置时间！", "温馨提示", JOptionPane.WARNING_MESSAGE);	
				return;
			}else {

				List<FileChangeObjectBase> samelist = main_FileChange.getSame(listFile, file.getList());
				System.out.println("same"+samelist);


				List<FileChangeObjectBase> addlist = main_FileChange.delSame(listFile, samelist);
				System.out.println("add"+addlist);

				List<FileChangeObjectBase> dellist = main_FileChange.delSame(file.getList(), samelist);
				System.out.println("del"+dellist);

				List<FileChangeObjectBase> changelist = main_FileChange.getsamename(addlist, dellist);
				System.out.println("change"+changelist);
				//显示改变
				Object[][] changeobjects = new Object[changelist.size()][3];
				for (int i = 0; i < changeobjects.length; i++) {
					changeobjects[i][0]=changelist.get(i).getFile_name();
					changeobjects[i][1]=changelist.get(i).getPwd_directory();
					changeobjects[i][2]=changelist.get(i).getFile_time();
				}
				table_2.setModel(new DefaultTableModel(
						changeobjects,
						new String[] {
								"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
						}
						){
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				});
				//提示
				table_2.addMouseMotionListener(new MouseAdapter(){
					public void mouseMoved(MouseEvent e) {
						int row=table_2.rowAtPoint(e.getPoint());
						int col=table_2.columnAtPoint(e.getPoint());
						if(row>-1 && col==1){
							Object value=table_2.getValueAt(row, col);
							if(null!=value && !"".equals(value)){
								table_2.setToolTipText(value.toString());//悬浮显示单元格内容
							}else{
								table_2.setToolTipText(null);//关闭提示
							}
						}
					}
				});
				List<FileChangeObjectBase> add1list = main_FileChange.delSameByName(addlist, changelist);
				System.out.println("add1"+add1list);

				//显示增加
				Object[][] addobjects = new Object[add1list.size()][3];
				for (int i = 0; i < addobjects.length; i++) {
					addobjects[i][0]=add1list.get(i).getFile_name();
					addobjects[i][1]=add1list.get(i).getPwd_directory();
					addobjects[i][2]=add1list.get(i).getFile_time();
				}
				table.setModel(new DefaultTableModel(
						addobjects,
						new String[] {
								"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
						}
						){
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				});
				//提示
				table.addMouseMotionListener(new MouseAdapter(){
					public void mouseMoved(MouseEvent e) {
						int row=table.rowAtPoint(e.getPoint());
						int col=table.columnAtPoint(e.getPoint());
						if(row>-1 && col==1){
							Object value=table.getValueAt(row, col);
							if(null!=value && !"".equals(value)){
								table.setToolTipText(value.toString());//悬浮显示单元格内容
							}else{
								table.setToolTipText(null);//关闭提示
							}
						}
					}
				});

				List<FileChangeObjectBase> del1list = main_FileChange.delSameByName(dellist, changelist);
				System.out.println("del1"+del1list);

				//显示删除
				Object[][] delobjects = new Object[del1list.size()][3];
				for (int i = 0; i < delobjects.length; i++) {
					delobjects[i][0]=del1list.get(i).getFile_name();
					delobjects[i][1]=del1list.get(i).getPwd_directory();
					delobjects[i][2]=del1list.get(i).getFile_time();
				}
				table_1.setModel(new DefaultTableModel(
						delobjects,
						new String[] {
								"\u6587\u4EF6\u540D", "\u76EE\u5F55", "\u65F6\u95F4"
						}
						){
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				});
				//提示
				table_1.addMouseMotionListener(new MouseAdapter(){
					public void mouseMoved(MouseEvent e) {
						int row=table_1.rowAtPoint(e.getPoint());
						int col=table_1.columnAtPoint(e.getPoint());
						if(row>-1 && col==1){
							Object value=table_1.getValueAt(row, col);
							if(null!=value && !"".equals(value)){
								table_1.setToolTipText(value.toString());//悬浮显示单元格内容
							}else{
								table_1.setToolTipText(null);//关闭提示
							}
						}
					}
				});
				MyStopWait();
				if (addlist.isEmpty()&&dellist.isEmpty()&&changelist.isEmpty()) {
					JOptionPane.showMessageDialog(this,"一切正常", "温馨提示", JOptionPane.WARNING_MESSAGE);	
				}
				//List<FileChangeObjectBase> addlist = main_FileChange.addfile(listFile, file.getList());
				//System.out.println("增加的文件"+addlist);

				//List<FileChangeObjectBase> dellist = main_FileChange.delfile(listFile, file.getList());
				//System.out.println("删除的文件"+dellist);

				//List<FileChangeObjectBase> changelist = main_FileChange.addfile(addlist, dellist);
				//System.out.println("修改的文件"+changelist);


			}
		}

	}

	protected void reset() {
		savewebdir();
		// TODO Auto-generated method stub
		listFile = new ArrayList<FileChangeObjectBase>();
		listDir = new ArrayList<FileChangeObjectBase>();
		main_FileChange = new Main_FileChange(multiUserBase);
		cd__(textField.getText().trim());
		//main_FileChange.file_change_execute("cd "+textField.getText().trim(),"once");
		getAllFile();

		SetFileChangeHttpService service = new SetFileChangeHttpService();
		int result = service.SetFileChangeHttp(multiUserBase,listFile);
		MyStopWait();
		/*
		 *	0:uuid失败
		 *	1:成功
		 *	2:有一个或者多个设置不成功
		 *	3:网络错误
		 * 
		 * */
		switch (result) {
		case 1:
			JOptionPane.showMessageDialog(this,"重置成功！", "温馨提示", JOptionPane.WARNING_MESSAGE);
			break;
		case 3:
			JOptionPane.showMessageDialog(this,"网络错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(this,"有一个或者多个文件设置不成功！", "温馨提示", JOptionPane.WARNING_MESSAGE);
			break;
		case 0:
			JOptionPane.showMessageDialog(this,"请重新登陆！", "温馨提示", JOptionPane.WARNING_MESSAGE);
			break;
		default:
			break;
		}


		//		for (int i = 0; i < listFile.size(); i++) {
		//			System.out.println(listFile.get(i).toString());
		//		}
		//		for (int i = 0; i < listDir.size(); i++) {
		//			System.out.println(listDir.get(i).toString());
		//		}

	}




	private void getAllFile() {
		main_FileChange = new Main_FileChange(multiUserBase);
		main_FileChange.file_change_execute("ls -al","file_change");
		String[] strings =Config.static_outString_map.get(multiUserBase.getHostName()).getByString("file_change").split("\n");

		for (int i = 1; i < strings.length; i++) {
			if(strings[i].equals("")){
				continue;
			}
			String[] tempStrings=strings[i].split("\\s+");
			if (flag) {
				for (int j = 0; j < tempStrings.length; j++) {
					if (tempStrings[j].equals(".")) {
						nameNumber=j;
						flag=false;
					}
				}

			}
			if(tempStrings!=null&&tempStrings[0].substring(0,1).equals("d")){
				if (!tempStrings[nameNumber].equals(".")&&tempStrings[nameNumber].length()>1&&!tempStrings[nameNumber].equals("..")) {
					//是目录
					System.out.println(nameNumber+"aaaaaa");
					if (nameNumber==7) {
						fileChangeObjectBase = new FileChangeObjectBase(multiUserBase.getHostName(),pwdString,"1", tempStrings[nameNumber], tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1]);
					}else {
						fileChangeObjectBase = new FileChangeObjectBase(multiUserBase.getHostName(),pwdString,"1", tempStrings[nameNumber], tempStrings[nameNumber-3]+" "+tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1]);

					}
					listDir.add(fileChangeObjectBase);

					//进入目录
					cd__(tempStrings[nameNumber]);
					getAllFile();

				}

			}else if(tempStrings!=null&&tempStrings[0].substring(0,1).equals("-")){
				if (nameNumber==7) {
					fileChangeObjectBase = new FileChangeObjectBase(multiUserBase.getHostName(),pwdString,"1", tempStrings[nameNumber], tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1]);
				}else {
					fileChangeObjectBase = new FileChangeObjectBase(multiUserBase.getHostName(),pwdString,"1", tempStrings[nameNumber], tempStrings[nameNumber-3]+" "+tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1]);

				}
				listFile.add(fileChangeObjectBase);
			}
			if (i==strings.length-1) {
				if (pwdString.equals(webdirString)) {
					break;
				}else{
					cd__("..");
				}
			}
		}

	}

	public String pwd() {
		main_FileChange = new Main_FileChange(multiUserBase);
		return main_FileChange.file_change_execute("pwd","once");	
	}
	public String cd__(String s) {
		main_FileChange = new Main_FileChange(multiUserBase);
		String temp= main_FileChange.file_change_execute("cd "+s,"once");	
		if (temp.contains("没有")||temp.contains("file or")||temp.contains("no such")) {
			main_FileChange.file_change_execute("mkdir "+s+";cd "+s,"once");
		}
		pwdString = cd2();
		return pwdString;
	}

	public String cd2() {
		String pwdString = pwd();
		if(pwdString.contains("\n")) {
			return pwdString.split("\n")[1];
		}
		String tempString=pwd();
		String[] tempStrings=tempString.split("\n");
		if (tempStrings.length>1) {
			if (tempStrings[1].substring(0, 1).equals("/")) {
				return tempStrings[1];
			}else {
				return cd2();
			}

		}else {
			return cd2();
		}

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
		((Main) SwingUtilities.getWindowAncestor(this)).setGlassPane(glasspane);
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
		((Main) SwingUtilities.getWindowAncestor(this)).setVisible(true);
	}
	private void MyStopWait() {
		// TODO Auto-generated method stub
		glasspane.stop();
	}
	private String catFile(String com) {
		String retString="";
		Cat cat= new Cat(multiUserBase);
		retString = cat.cat_execute("cat "+com,"once");
		String[] rettemp=retString.split("\n");
		retString="";
		for (int i = 1; i < rettemp.length; i++) {
			if (rettemp[i].contains("cat /")&&i<2) {
				continue;
			}
			retString+=rettemp[i]+"\n";
		}
		return retString;
	}

}