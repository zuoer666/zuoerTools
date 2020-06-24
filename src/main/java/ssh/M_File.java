package ssh;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class M_File extends JPanel {
	String dirString;
	String filename;
	boolean dir;
	MultiUserBase multiUserBase;
	private JTable table;
	String pwdString="~";
	Main_File main_File;
	List<FileShowObject> showfileShowObjects;
	//ls-al 文件位置
	boolean flag=true;
	int nameNumber;
	boolean isfirst=true;
	String LS_AL_result;
	//选择器选择的文件
	File getJFileChooserFile;
	Thread ls_alThread;
	M_File m_File;
	NewFile newFile;
	Editor editor;
	/**
	 * @return the m_File
	 */
	public M_File getM_File() {
		return m_File;
	}
	/**
	 * @param m_File the m_File to set
	 */
	public void setM_File(M_File m_File) {
		this.m_File = m_File;
	}
	/**
	 * Create the panel.
	 */
	boolean windows;
	private JTextField currentDirectory;
	public M_File(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		ls_alThread=new Thread(){};
		JButton btnNewButton = new JButton("上一层");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterDir("..");
			}
		});
		btnNewButton.setBounds(36, 21, 117, 29);
		add(btnNewButton);

		JButton button = new JButton("下载");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(button,"请选择要下载的文件", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					return;
				}
				GetFileName();

				if (table.getValueAt(table.getSelectedRow(),0).toString().equals("true")) {
					JOptionPane.showMessageDialog(button,"请选择文件", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					return;
				}
				if (getJFileChooserFile==null) {
					return;
				}

				if (!dir) {
					JOptionPane.showMessageDialog(button,"请选择目录", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					return;
				}
				SSHSCP sshAgent = new SSHSCP();
				try {
					sshAgent.initSession(multiUserBase.getSsh().hostname, multiUserBase.getSsh().username,multiUserBase.getSsh().passwordString,Integer.valueOf(multiUserBase.getSsh().port));

					Boolean successBoolean=false;
					if (windows) {
						successBoolean=sshAgent.downloadFile(table.getValueAt(table.getSelectedRow(),2).toString(),pwdString,dirString+"\\");

					}else {
						successBoolean=sshAgent.downloadFile(table.getValueAt(table.getSelectedRow(),2).toString(),pwdString,dirString+"/");

					}
					if (successBoolean) {
						JOptionPane.showMessageDialog(button,"下载成功", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					}else {
						JOptionPane.showMessageDialog(button,"下载失败", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				sshAgent.close();
			}
		});
		button.setBounds(188, 21, 117, 29);
		add(button);

		JButton button_1 = new JButton("上传");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GetFileName();
				if (getJFileChooserFile==null) {
					return;
				}
				
				if (dir) {
					SSHSCP sshAgent = new SSHSCP();
					Boolean sucBoolean;
					try {
						sshAgent.initSession(multiUserBase.getSsh().hostname, multiUserBase.getSsh().username,multiUserBase.getSsh().passwordString,Integer.valueOf(multiUserBase.getSsh().port));

						System.out.println(dirString+"\\"+filename);
						sucBoolean=sshAgent.transferDirectory(dirString, pwdString);

						if (sucBoolean) {
							refresh();
							JOptionPane.showMessageDialog(button,"上传成功", "温馨提示", JOptionPane.WARNING_MESSAGE);	
						}else {
							JOptionPane.showMessageDialog(button,"上传失败", "温馨提示", JOptionPane.WARNING_MESSAGE);	
						}


						//sshAgent.transferDirectory("/home/xx/Documents", "/home/xx/book");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(button,"上传失败", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					}
					sshAgent.close();
					sshAgent.close();
				}else{
					if (filename==null) {
						JOptionPane.showMessageDialog(button,"请选择目录", "温馨提示", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (filename.contains(" ")) {
						JOptionPane.showMessageDialog(button_1,"上传的文件不能有空格", "温馨提示", JOptionPane.WARNING_MESSAGE);	
						return;
					}
					SSHSCP sshAgent = new SSHSCP();
					Boolean sucBoolean;
					try {
						sshAgent.initSession(multiUserBase.getSsh().hostname, multiUserBase.getSsh().username,multiUserBase.getSsh().passwordString,Integer.valueOf(multiUserBase.getSsh().port));
						if (windows) {
							System.out.println(dirString+"\\"+filename);
							sucBoolean=sshAgent.transferFile(dirString+"\\"+filename,pwdString);
						}else {
							System.out.println(dirString+"/"+filename);
							sucBoolean=sshAgent.transferFile(dirString+"/"+filename,pwdString);
						}
						if (sucBoolean) {
							refresh();
							JOptionPane.showMessageDialog(button,"上传成功", "温馨提示", JOptionPane.WARNING_MESSAGE);	
						}else {
							JOptionPane.showMessageDialog(button,"上传失败", "温馨提示", JOptionPane.WARNING_MESSAGE);	
						}


						//sshAgent.transferDirectory("/home/xx/Documents", "/home/xx/book");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sshAgent.close();
				}


			}
		});
		button_1.setBounds(343, 21, 117, 29);
		add(button_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 88, 926, 287);
		add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {
					//文件管理器table被双击
					String filename = table.getValueAt(table.getSelectedRow(),2).toString();
					if (table.getValueAt(table.getSelectedRow(),0).toString().equals("true")) {
						enterDir(filename);
					}else {
						String catString =  catFile(filename);
					    editor = new Editor(filename,filename,catString,multiUserBase);
						editor.setLocationRelativeTo(null);
						editor.setVisible(true);
					}

				}
			}
		});
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							table.setModel(new DefaultTableModel(
									new Object[][] {
									},
									new String[] {
											"isDir", "\u6743\u9650", "\u6587\u4EF6\u540D", "\u7528\u6237", "\u7EC4", "\u786C\u94FE\u63A5\u6570", "\u5927\u5C0F", "\u65F6\u95F4"
									}
									){
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								public boolean isCellEditable(int row, int column)
								{
									return false;
								}
							});
							HiddenCell(table,0);
						}
					});

				} catch (Exception e) {

					e.printStackTrace();
					System.exit(1);
				}
			}
		});
		mainThread.start();

		/*
		 * 
		 * TableColumnModel tcm = myTable.getColumnModel();  
			TableColumn tc = tcm.getColumn(0) ; 
			tcm.removeColumn(tc); 
		 * */

		scrollPane.setViewportView(table);

		JButton button_2 = new JButton("刷新");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		button_2.setBounds(803, 21, 117, 29);
		add(button_2);

		currentDirectory = new JTextField();
		currentDirectory.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (currentDirectory.getText().equals("")) {
					return;
				}
				int key = e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					enterDir(currentDirectory.getText());
				}

			}
		});
		currentDirectory.setBounds(39, 50, 924, 26);
		add(currentDirectory);
		currentDirectory.setColumns(10);

		JButton btnNewButton_1 = new JButton("删除");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(btnNewButton_1,"请选择要删除的文件", "温馨提示", JOptionPane.WARNING_MESSAGE);	
				}else {
					del();
				}

			}
		});
		btnNewButton_1.setBounds(495, 21, 117, 29);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("新建");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newFile = new NewFile(m_File); 
				newFile.setLocationRelativeTo(null);
				newFile.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(646, 21, 117, 29);
		add(btnNewButton_2);
		init();
	}
	protected void del() {
		// TODO Auto-generated method stub
		main_File = new Main_File(multiUserBase); 
		main_File.file_execute("rm -rf "+table.getValueAt(table.getSelectedRow(), 2), "once");
		refresh();
	}
	private void init() {
		// TODO Auto-generated method stub

	}

	//刷新
	public void refresh(){
		if (isfirst) {
			//第一次
			main_File = new Main_File(multiUserBase);
			enterDir("~");
			isfirst=false;
		}else {
			main_File = new Main_File(multiUserBase);
			enterDir(pwdString);
		}

	}
	//进入目录
	private void enterDir(String s) {
		ls_alThread.stop();
		Config.static_outString_map.get(multiUserBase.getHostName()).setFile_outString("");
		pwdString=main_File.cd__(s);
		currentDirectory.setText(pwdString);
		ls_al();
		showTable();

	}

	private void showTable() {
		showfileShowObjects=toList();
		// TODO Auto-generated method stub
		Object[][] tempShowObject = new Object[showfileShowObjects.size()][8];
		for (int i = 0; i < tempShowObject.length; i++) {
			/*
			 * 权限 文件名 用户 组 硬连接数 大小 时间
			 * */
			tempShowObject[i][0]=showfileShowObjects.get(i).isIs_dir();
			tempShowObject[i][1]=showfileShowObjects.get(i).getFile_permission();
			tempShowObject[i][2]=showfileShowObjects.get(i).getFile_name();
			tempShowObject[i][3]=showfileShowObjects.get(i).getFile_user();
			tempShowObject[i][4]=showfileShowObjects.get(i).getFile_group();
			tempShowObject[i][5]=showfileShowObjects.get(i).getFile_count();
			tempShowObject[i][6]=showfileShowObjects.get(i).getSize();
			tempShowObject[i][7]=showfileShowObjects.get(i).getTime();
		}

		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							table.setModel(new DefaultTableModel(
									tempShowObject,
									new String[] {
											"isDir", "\u6743\u9650", "\u6587\u4EF6\u540D", "\u7528\u6237", "\u7EC4", "\u786C\u94FE\u63A5\u6570", "\u5927\u5C0F", "\u65F6\u95F4"
									}
									){
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								public boolean isCellEditable(int row, int column)
								{
									return false;
								}
							});
							HiddenCell(table,0);
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
	private void ls_al() {

		main_File = new Main_File(multiUserBase);
		LS_AL_result=main_File.file_execute("ls -al","file");
		ls_alThread=new Thread(){

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int i = 0; i < Config.SDynamicDisplayNumber; i++) {
					if (LS_AL_result.equals(Config.static_outString_map.get(multiUserBase.getHostName()).getByString("file"))) {
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else {
						LS_AL_result=Config.static_outString_map.get(multiUserBase.getHostName()).getByString("file");
						showTable();
					}
				}
			}

		};
		ls_alThread.start();
	}

	private List<FileShowObject>  toList() {
		List<FileShowObject> fileShowObjects = new ArrayList<FileShowObject>();
		FileShowObject fileShowObject;
		// TODO Auto-generated method stub
		String[] strings =LS_AL_result.split("\n");

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
					if (nameNumber==8) {
						fileShowObject=new FileShowObject(tempStrings[nameNumber-8], tempStrings[nameNumber], tempStrings[nameNumber-6], tempStrings[nameNumber-5], tempStrings[nameNumber-7], tempStrings[nameNumber-4],tempStrings[nameNumber-3]+" "+tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], true);

					}else {
						fileShowObject=new FileShowObject(tempStrings[nameNumber-7], tempStrings[nameNumber], tempStrings[nameNumber-5], tempStrings[nameNumber-4], tempStrings[nameNumber-6], tempStrings[nameNumber-3],tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], true);		
					}	
					fileShowObjects.add(fileShowObject);
				}

			}else if(tempStrings!=null&&tempStrings[0].substring(0,1).equals("-")){
				if (nameNumber==8) {
					fileShowObject=new FileShowObject(tempStrings[nameNumber-8], tempStrings[nameNumber], tempStrings[nameNumber-6], tempStrings[nameNumber-5], tempStrings[nameNumber-7], tempStrings[nameNumber-4],tempStrings[nameNumber-3]+" "+tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], false);

				}else {
					fileShowObject=new FileShowObject(tempStrings[nameNumber-7], tempStrings[nameNumber], tempStrings[nameNumber-5], tempStrings[nameNumber-4], tempStrings[nameNumber-6], tempStrings[nameNumber-3],tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], false);		
				}	
				fileShowObjects.add(fileShowObject);
			}else if(tempStrings!=null&&tempStrings[0].substring(0,1).equals("l")){
				if (nameNumber==8) {
					fileShowObject=new FileShowObject(tempStrings[nameNumber-8], tempStrings[nameNumber], tempStrings[nameNumber-6], tempStrings[nameNumber-5], tempStrings[nameNumber-7], tempStrings[nameNumber-4],tempStrings[nameNumber-3]+" "+tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], false);

				}else {
					fileShowObject=new FileShowObject(tempStrings[nameNumber-7], tempStrings[nameNumber], tempStrings[nameNumber-5], tempStrings[nameNumber-4], tempStrings[nameNumber-6], tempStrings[nameNumber-3],tempStrings[nameNumber-2]+" "+tempStrings[nameNumber-1], false);		
				}	
				fileShowObjects.add(fileShowObject);
			}
		}
		return fileShowObjects;
	}
	// 隐藏列
	public void HiddenCell(JTable table, int column) {
		TableColumn tc = table.getTableHeader().getColumnModel().getColumn(
				column);
		tc.setMaxWidth(0);
		tc.setPreferredWidth(0);
		tc.setWidth(0);
		tc.setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column)
		.setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(column)
		.setMinWidth(0);
	}

	public void GetFileName(){
		JFileChooser jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
		jfc.showDialog(new JLabel(), "选择");
		getJFileChooserFile=jfc.getSelectedFile();
		if (getJFileChooserFile==null) {
			return;
		}
		if(getJFileChooserFile.isDirectory()){
			dir=true;
			if (isOsWindows()) {
				windows=true;
				//System.out.println("目录:"+file.getAbsolutePath().replaceAll("\\\\","\\\\\\\\"));
				System.out.println('a');
				System.out.println(getJFileChooserFile.getAbsolutePath());
				System.out.println('b');
				dirString=getJFileChooserFile.getAbsolutePath().replaceAll("\\\\","\\\\\\\\");
			}else {
				windows=false;
				System.out.println('a');
				System.out.println(getJFileChooserFile.getAbsolutePath());
				System.out.println('b');
				dirString=getJFileChooserFile.getAbsolutePath().replaceAll("\\\\","\\\\\\\\");
			}
		}else if(getJFileChooserFile.isFile()){
			dir=false;
			if (isOsWindows()) {
				windows=true;
				//System.out.println("文件:"+file.getAbsolutePath().replaceAll("\\\\","\\\\\\\\"));
				//System.out.println("目录:"+file.getParent().replaceAll("\\\\","\\\\\\\\"));
				dirString=getJFileChooserFile.getParent().replaceAll("\\\\","\\\\\\\\");
				filename=jfc.getSelectedFile().getName();
			}else {
				windows=false;
				dirString=getJFileChooserFile.getParent().replaceAll("\\\\","\\\\\\\\");
				filename=jfc.getSelectedFile().getName();
			}

		}
		//System.out.println(jfc.getSelectedFile().getName());
	}
	private static boolean isOsWindows() {
		String osname = System.getProperty("os.name").toLowerCase();
		boolean rt = osname.startsWith("windows");
		return rt;
	}

	public void NewFile(String fileName) {
		main_File.file_execute("touch "+pwdString+"/"+fileName,"file");

	}
	public Boolean NewDir(String dirName) {
		String resultString = main_File.file_execute("mkdir "+pwdString+"/"+dirName,"file");
		String[] temp =resultString.split("\n");
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].contains("已存在")||temp[i].contains("exists")) {
				return false;
			}
		}
		return true;
	}
}
