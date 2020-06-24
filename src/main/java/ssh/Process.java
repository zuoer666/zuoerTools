package ssh;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Process extends JPanel {
	MultiUserBase multiUserBase;
	private JTable table;
	JLabel label;
	Thread processReThread;
	String exeprocessResultString;
	String[] processResult;
	Boolean delprocess=false;
	List<String> processSelectList;
	List<String> processSelectListTemp;
	List<String> processPIDList;
	List<ProcessShowObject> processLists;
	JButton btnNewButton;
	Boolean detection=true;
	Boolean delete=true;
	/**
	 * Create the panel.
	 */
	public Process(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		processReThread=new Thread(){};
		JButton button = new JButton("刷新");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (detection) {
					detection=false;
					delprocess=false;
					processReThread.stop();
					Config.static_outString_map.get(multiUserBase.getHostName()).setProcess_outString("");
					processDetection();
				}else {
					//连续点击
					JOptionPane.showMessageDialog(null, "正在刷新，无需多次点击！", "温馨提示", JOptionPane.WARNING_MESSAGE);

				}

			}
		});

		button.setBounds(31, 19, 117, 29);
		add(button);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 60, 940, 284);
		add(scrollPane);

		table = new JTable();
		

		scrollPane.setViewportView(table);
		btnNewButton = new JButton("结束");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (delete) {
					delete=false;
					processReThread.stop();

					Config.static_outString_map.get(multiUserBase.getHostName()).setProcess_outString("");
					processSelectList=new ArrayList<String>();
					for (int i = 0; i < table.getModel().getRowCount() ; i++) {
						if ((boolean) table.getValueAt(i, 0)) {
							processSelectList.add((String) table.getValueAt(i, 2));
						}

					}
					//					if (!delprocess) {
					//						JOptionPane.showMessageDialog(null, "请先检测！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					//						delprocess=true;
					//						return;
					//					}
					delprocess=true;
					if (processSelectList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "请先选中要结束的进程！", "温馨提示", JOptionPane.WARNING_MESSAGE);
						delete=true;
						return;
					}


					String processCommandString="";
					for (int j = 0; j < processSelectList.size(); j++) {
						//System.out.println(selectList.get(j));
						String processTmp = processSelectList.get(j);
						processCommandString+="kill "+processTmp+";\n";
					}

					Main_Process main_process = new Main_Process(multiUserBase);
					String processCommandString2=processCommandString;
					System.out.println(processCommandString);
					//String processCommandString2="";
					new Thread(){

						/* (non-Javadoc)
						 * @see java.lang.Thread#run()
						 */
						@Override
						public void run() {
							// TODO Auto-generated method stub

							processSelectListTemp=processSelectList;
							exeprocessResultString=main_process.process_execute(processCommandString2, "process");


							try {

								Thread.sleep(1000);
								Config.static_outString_map.get(multiUserBase.getHostName()).setProcess_outString("");
								getList();
								Thread.sleep(1000);
								if (processSelectListTemp!=null) {

									if (!processPIDList.containsAll(processSelectListTemp)) {
										JOptionPane.showMessageDialog(null, "结束成功！", "温馨提示", JOptionPane.WARNING_MESSAGE);

									}else {
										JOptionPane.showMessageDialog(null, "结束失败，请切换权限更高用户！", "温馨提示", JOptionPane.WARNING_MESSAGE);
									}
								}


							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							delete=true;
						}

					}.start();
				}else {
					//双击
					JOptionPane.showMessageDialog(null, "正在结束，无需多次点击点击！", "温馨提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(160, 19, 117, 29);
		btnNewButton.setVisible(false);
		add(btnNewButton);
		button.doClick();
	}
	protected void processDetection() {

		//initTable(table);
		initTable1();
		//initcheck();
		//initTable();
		getList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initTable() {
		btnNewButton.setVisible(true);
		Vector headerNames=new Vector();
		headerNames.add("全选");
		//USER       PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
		headerNames.add("USER");
		headerNames.add("PID");
		headerNames.add("CPU");
		headerNames.add("MEM");
		headerNames.add("VSZ");
		headerNames.add("RSS");
		headerNames.add("TTY");
		headerNames.add("STAT");
		headerNames.add("START");
		headerNames.add("TIME");
		headerNames.add("COMMAND");
		Vector data=this.getData();

		CheckTableModle tableModel=new CheckTableModle(data,headerNames);
		// 开辟新的线程运行实验【防止UI线程阻塞】
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {table.setModel(tableModel);
						table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
						TableColumnModel tcm = table.getColumnModel();
						tcm.getColumn(0).setPreferredWidth(60);
						tcm.getColumn(0).setWidth(60);
						tcm.getColumn(0).setMaxWidth(60);
						//提示
						table.addMouseMotionListener(new MouseAdapter(){
							public void mouseMoved(MouseEvent e) {
								int row=table.rowAtPoint(e.getPoint());
								int col=table.columnAtPoint(e.getPoint());
								if(row>-1 && col==11){
									Object value=table.getValueAt(row, col);
									if(null!=value && !"".equals(value)){
										table.setToolTipText(value.toString());//悬浮显示单元格内容
									}else{
											table.setToolTipText(null);//关闭提示
										}
									}
								}
							});
						tcm.getColumn(11).setPreferredWidth(200);
						tcm.getColumn(11).setWidth(200);
						tcm.getColumn(11).setMaxWidth(200);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initTable1() {
		Vector headerNames=new Vector();
		headerNames.add("全选");
		headerNames.add("正在加载");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");
		Vector data=this.getData();

		CheckTableModle tableModel=new CheckTableModle(data,headerNames);
		Thread mainThread = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {table.setModel(tableModel);
						table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
						TableColumnModel tcm = table.getColumnModel();
						tcm.getColumn(0).setPreferredWidth(60);
						tcm.getColumn(0).setWidth(60);
						tcm.getColumn(0).setMaxWidth(60);
						
						//提示
						table.addMouseMotionListener(new MouseAdapter(){
							public void mouseMoved(MouseEvent e) {
								int row=table.rowAtPoint(e.getPoint());
								int col=table.columnAtPoint(e.getPoint());
								if(row>-1 && col==11){
									Object value=table.getValueAt(row, col);
									if(null!=value && !"".equals(value)){
										table.setToolTipText(value.toString());//悬浮显示单元格内容
									}else{
											table.setToolTipText(null);//关闭提示
										}
									}
								}
							});
						tcm.getColumn(11).setPreferredWidth(200);
						tcm.getColumn(11).setWidth(200);
						tcm.getColumn(11).setMaxWidth(200);
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
	private void getList(){

		String processComString = "ps -aux --sort=-pcpu";
		Main_Process main_process = new Main_Process(multiUserBase);
		exeprocessResultString=main_process.process_execute(processComString, "process");
		processReThread=new Thread(){

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int i = 0; i < 15; i++) {

					if (exeprocessResultString.equals(Config.static_outString_map.get(multiUserBase.getHostName()).getByString("process"))) {
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else {
						exeprocessResultString=Config.static_outString_map.get(multiUserBase.getHostName()).getByString("process");
						processResult=exeprocessResultString.split("\n");
						initTable();
					}
				}
				if (!delprocess&&processLists.isEmpty()) {
					JOptionPane.showMessageDialog(null, "网络错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);

				}
				detection=true;
			}

		};
		processReThread.start();
		processResult=exeprocessResultString.split("\n");
		initTable();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getData(){
		processLists=new ArrayList<ProcessShowObject>();
		processPIDList=new ArrayList<String>();
		Vector processData = new Vector();
		if (processResult!=null) {
			for (int i = 1; i < processResult.length; i++) {
				if (processResult[i].equals("\\s+")) {
					continue;
				}
				if (processResult[i].equals("")) {
					continue;
				}
				if (!processResult[i].subSequence(0, 4).equals("USER")) {
					//	System.out.println(processResult);
					String[] processTempStrings = processResult[i].split("\\s+");
					if (processTempStrings.length<=10) {
						continue;
					}
					String CPU=processTempStrings[2];
					String USER=processTempStrings[0];
					String PID=processTempStrings[1];

					String MEM=processTempStrings[3];
					String VSZ=processTempStrings[4];
					String RSS=processTempStrings[5];
					String TTY=processTempStrings[6];
					String STAT=processTempStrings[7];
					String START=processTempStrings[8];
					String TIME=processTempStrings[9];
					String COMMAND = "";
					for (int j = 10; j < processTempStrings.length; j++) {
						COMMAND+=processTempStrings[j]+" ";
					}
					ProcessShowObject processShowObject = new ProcessShowObject(USER, PID, CPU, MEM, VSZ, RSS, TTY, STAT, START, TIME, COMMAND);
					processPIDList.add(PID);
					processLists.add(processShowObject);
				}

			}
			for (int i = 0; i < processLists.size(); i++) {
				Vector processRowVector=new Vector();
				processRowVector.add(false);
				processRowVector.add(processLists.get(i).getUSER());
				processRowVector.add(processLists.get(i).getPID());
				processRowVector.add(processLists.get(i).getCPU());
				processRowVector.add(processLists.get(i).getMEM());
				processRowVector.add(processLists.get(i).getVSZ());
				processRowVector.add(processLists.get(i).getRSS());
				processRowVector.add(processLists.get(i).getTTY());
				processRowVector.add(processLists.get(i).getSTAT());
				processRowVector.add(processLists.get(i).getSTART());
				processRowVector.add(processLists.get(i).getTIME());
				processRowVector.add(processLists.get(i).getCOMMAND());
				processData.add(processRowVector);
			}
		}

		return processData;
	}

}
