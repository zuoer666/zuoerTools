package ssh;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Mining extends JPanel {
	MultiUserBase multiUserBase;
	private JTable table;
	JLabel label;
	Thread miningReThread;
	String exeminingResultString;
	String[] miningResult;
	Boolean delmining=false;
	List<String> miningSelectList;
	List<String> miningSelectListTemp;
	List<String> miningPIDList;
	List<MiningShowObject> miningLists;
	JButton btnNewButton;
	Boolean detection=true;
	Boolean delete=true;
	Boolean miningTest=false;
	/**
	 * Create the panel.
	 */
	public Mining(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		miningReThread=new Thread(){};
		JButton button = new JButton("挖矿检测");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (detection) {
					detection=false;
					delmining=false;
					miningReThread.stop();
					Config.static_outString_map.get(multiUserBase.getHostName()).setMining_outString("");
					MyWait();
					MiningDetection();
				}else {
					//连续点击
					JOptionPane.showMessageDialog(null, "正在检测，无需多次点击！", "温馨提示", JOptionPane.WARNING_MESSAGE);

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
		btnNewButton = new JButton("一键删除");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (delete) {
					delete=false;
					miningReThread.stop();

					Config.static_outString_map.get(multiUserBase.getHostName()).setMining_outString("");
					miningSelectList=new ArrayList<String>();
					for (int i = 0; i < table.getModel().getRowCount() ; i++) {
						if ((boolean) table.getValueAt(i, 0)) {
							miningSelectList.add((String) table.getValueAt(i, 2));
						}

					}
					delmining=true;
					if (miningSelectList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "请先选中要清除到挖矿程序！", "温馨提示", JOptionPane.WARNING_MESSAGE);
						delete=true;
						return;
					}


					String miningCommandString="";
					for (int j = 0; j < miningSelectList.size(); j++) {
						//System.out.println(selectList.get(j));
						String miningTmp = miningSelectList.get(j);

						miningCommandString+="if readlink -f /proc/"+miningTmp+"/cwd  \"/usr/bin\"; then\n";
						miningCommandString+="rm -rf $(readlink -f /proc/"+miningTmp+"/exe\n);";
						miningCommandString+="kill "+miningTmp+";\n";
						miningCommandString+="else\n";
						miningCommandString+="rm -rf $(readlink -f /proc/"+miningTmp+"/exe);\n";
						miningCommandString+="rm -rf $(readlink -f /proc/"+miningTmp+"/cwd/*);\n";
						miningCommandString+="kill "+miningTmp+";\n";
						miningCommandString+="fi\n";
						miningCommandString+="\n";
					}

					Main_Mining main_mining = new Main_Mining(multiUserBase);
					String miningCommandString2=miningCommandString;
					System.out.println(miningCommandString);
					//String miningCommandString2="";
					new Thread(){

						/* (non-Javadoc)
						 * @see java.lang.Thread#run()
						 */
						@Override
						public void run() {
							// TODO Auto-generated method stub

							miningSelectListTemp=miningSelectList;
							exeminingResultString=main_mining.mining_execute(miningCommandString2, "mining");


							try {

								Thread.sleep(1000);
								Config.static_outString_map.get(multiUserBase.getHostName()).setMining_outString("");
								getList();
								Thread.sleep(1000);
								if (miningSelectListTemp!=null) {

									if (!miningPIDList.containsAll(miningSelectListTemp)) {
										JOptionPane.showMessageDialog(null, "删除成功！", "温馨提示", JOptionPane.WARNING_MESSAGE);

									}else {
										JOptionPane.showMessageDialog(null, "删除失败，请切换权限更高用户！", "温馨提示", JOptionPane.WARNING_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "正在删除，无需多次点击点击！", "温馨提示", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(160, 19, 117, 29);
		btnNewButton.setVisible(false);
		add(btnNewButton);
	}
	protected void MiningDetection() {
		new Thread(
				new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						initTable1();
						getList();
					}
				}
				).start();
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
		        		tcm.setColumnSelectionAllowed(true);
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
						detection=true;
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
		headerNames.add("正在检测");
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
		        		tcm.setColumnSelectionAllowed(true);
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

		String miningComString = "ps -aux --sort=-pcpu|head -10";
		Main_Mining main_mining = new Main_Mining(multiUserBase);
		exeminingResultString=main_mining.mining_execute(miningComString, "mining");
		miningReThread=new Thread(){

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int i = 0; i < 15; i++) {

					if (exeminingResultString.equals(Config.static_outString_map.get(multiUserBase.getHostName()).getByString("mining"))) {
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else {
						exeminingResultString=Config.static_outString_map.get(multiUserBase.getHostName()).getByString("mining");
						miningResult=exeminingResultString.split("\n");
						initTable();
					}
				}
				MyStopWait();
				while (true) {
					try {
						Thread.sleep(500);
						if (miningTest&&!delmining&&miningLists.isEmpty()) {
							successTip();
							//MyStopWait();
							detection=true;
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				detection=true;
			}

		};
		miningReThread.start();
		miningResult=exeminingResultString.split("\n");
		initTable();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getData(){
		miningLists=new ArrayList<MiningShowObject>();
		miningPIDList=new ArrayList<String>();
		Vector miningData = new Vector();
		if (miningResult!=null) {
			for (int i = 1; i < miningResult.length; i++) {
				if (!miningResult[i].subSequence(0, 4).equals("USER")&&!miningResult[i].contains("Warning:")) {
				//	System.out.println(miningResult);
					String[] miningTempStrings = miningResult[i].split("\\s+");
					String CPU=miningTempStrings[2];
					double cpuDouble = Double.valueOf(CPU);
					if (cpuDouble<20) {
						continue;
					}
					String USER=miningTempStrings[0];
					String PID=miningTempStrings[1];

					String MEM=miningTempStrings[3];
					String VSZ=miningTempStrings[4];
					String RSS=miningTempStrings[5];
					String TTY=miningTempStrings[6];
					String STAT=miningTempStrings[7];
					String START=miningTempStrings[8];
					String TIME=miningTempStrings[9];
					String COMMAND = "";
					for (int j = 10; j < miningTempStrings.length; j++) {
						COMMAND+=miningTempStrings[j]+" ";
					}
					MiningShowObject miningShowObject = new MiningShowObject(USER, PID, CPU, MEM, VSZ, RSS, TTY, STAT, START, TIME, COMMAND);
					miningPIDList.add(PID);
					miningLists.add(miningShowObject);
				}

			}
			for (int i = 0; i < miningLists.size(); i++) {
				Vector miningRowVector=new Vector();
				miningRowVector.add(false);
				miningRowVector.add(miningLists.get(i).getUSER());
				miningRowVector.add(miningLists.get(i).getPID());
				miningRowVector.add(miningLists.get(i).getCPU());
				miningRowVector.add(miningLists.get(i).getMEM());
				miningRowVector.add(miningLists.get(i).getVSZ());
				miningRowVector.add(miningLists.get(i).getRSS());
				miningRowVector.add(miningLists.get(i).getTTY());
				miningRowVector.add(miningLists.get(i).getSTAT());
				miningRowVector.add(miningLists.get(i).getSTART());
				miningRowVector.add(miningLists.get(i).getTIME());
				miningRowVector.add(miningLists.get(i).getCOMMAND());
				miningData.add(miningRowVector);
			}
		}
		miningTest=true;
		return miningData;
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
}