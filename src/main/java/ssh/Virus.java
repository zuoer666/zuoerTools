package ssh;

import javax.swing.JPanel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.lang3.StringEscapeUtils;

import com.alibaba.fastjson.JSON;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Virus extends JPanel {
	MultiUserBase multiUserBase;
	private JTable table;
	JLabel label;
	Thread ls_alThread;
	String exevirusResultString;
	String[] virusResult;
	Boolean delVirus=false;
	List<String> virusSelectList;
	List<String> virusSelectListTemp;
	List<String> virusLists;
	Boolean detection=true;
	Boolean delete=true;
	JButton btnNewButton;
	//JPanel jPanel;
	//检测木马是否完事
	Boolean virusTest=false;
	Editor editor ;
	/**
	 * Create the panel.
	 */
	public Virus(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		ls_alThread=new Thread(){};
		JButton button = new JButton("木马检测");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (detection) {
					delVirus=false;
					detection=false;
					ls_alThread.stop();
					Config.static_outString_map.get(multiUserBase.getHostName()).setVirus_outString("");
					MyWait();
					trojanDetection();
				}else {
					//连续点击
					JOptionPane.showMessageDialog(null, "正在检测，无需多次点击！", "温馨提示", JOptionPane.WARNING_MESSAGE);

				}

			}
		});

		button.setBounds(31, 19, 117, 29);
		add(button);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 60, 856, 284);
		add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clickTimes = e.getClickCount();
				if (clickTimes == 2) {
					//文件管理器table被双击
					String filename = table.getValueAt(table.getSelectedRow(),1).toString();
					String catString =  catFile(filename);
					editor = new Editor(filename,filename,catString,multiUserBase);
					editor.setLocationRelativeTo(null);
					editor.setVisible(true);


				}
			}
		});

		scrollPane.setViewportView(table);
		btnNewButton = new JButton("一键删除");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (delete) {
					delete=false;
					
					ls_alThread.stop();
					Config.static_outString_map.get(multiUserBase.getHostName()).setVirus_outString("");
					virusSelectList=new ArrayList<String>();
					for (int i = 0; i < table.getModel().getRowCount() ; i++) {
						if ((boolean) table.getValueAt(i, 0)) {
							virusSelectList.add((String) table.getValueAt(i, 1));
						}

					}
//					if (!delVirus) {
//						JOptionPane.showMessageDialog(null, "请先检测！", "温馨提示", JOptionPane.WARNING_MESSAGE);
//						delete=true;
//						delVirus=true;
//						return;
//					}
					delVirus=true;
					if (virusSelectList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "请先选中要清除到木马！", "温馨提示", JOptionPane.WARNING_MESSAGE);
						delete=true;
						return;
					}


					String virusCommandString="";
					for (int j = 0; j < virusSelectList.size(); j++) {
						//System.out.println(selectList.get(j));
						String virusTmp = virusSelectList.get(j);
						//virusCommandString+="chmod 000 "+virusTmp+";chattr +i "+virusTmp+";rm "+virusTmp+";";
						virusCommandString+="rm -rf "+virusTmp+";";
					}

					Main_virus main_virus = new Main_virus(multiUserBase);
					String virusCommandString2=virusCommandString.substring(0, virusCommandString.length()-1);
					//String virusCommandString2="";
					System.out.println(virusCommandString2);
					new Thread(){

						/* (non-Javadoc)
						 * @see java.lang.Thread#run()
						 */
						@Override
						public void run() {
							// TODO Auto-generated method stub

							virusSelectListTemp=virusSelectList;
							exevirusResultString=main_virus.virus_execute(virusCommandString2, "virus");
							try {
								Thread.sleep(1000);
								Config.static_outString_map.get(multiUserBase.getHostName()).setVirus_outString("");
								getList();
								Thread.sleep(3000);
								if (virusSelectListTemp!=null) {
									if (!virusLists.containsAll(virusSelectListTemp)) {
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
	protected void trojanDetection() {
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
		headerNames.add("已检测到的木马");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initTable1() {
		Vector headerNames=new Vector();
		headerNames.add("全选");
		headerNames.add("正在检测");
		Vector data=new Vector();

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
		StringBuffer readBuffer = null;
		String virusComString = null;
		try {
			readBuffer = MyFile.readFileByBytes(Config.SVirusFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取"+Config.SVirusFileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);

		}
		try {
			virusComString=String.valueOf(readBuffer);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "解析"+Config.SVirusFileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);
		}
		Main_virus main_virus = new Main_virus(multiUserBase);
		exevirusResultString=main_virus.virus_execute(virusComString, "virus");
		ls_alThread=new Thread(){

			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int i = 0; i < 30; i++) {

					if (exevirusResultString.equals(Config.static_outString_map.get(multiUserBase.getHostName()).getByString("virus"))) {
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}else {
						exevirusResultString=Config.static_outString_map.get(multiUserBase.getHostName()).getByString("virus");
						virusResult=exevirusResultString.split("\n");
						initTable();

					}
				}
				MyStopWait();
				while (true) {
					try {
						Thread.sleep(500);
						if (virusTest&&!delVirus&&virusLists.isEmpty()) {
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
		ls_alThread.start();
		virusResult=exevirusResultString.split("\n");
		initTable();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getData(){
		virusLists=new ArrayList<String>();
		Vector virusData = new Vector();
		if (virusResult!=null) {
			for (int i = 1; i < virusResult.length; i++) {
				if (!(virusResult[i].length()>=1)) {
					continue;
				}
				if (!virusResult[i].subSequence(0, 1).equals("/")) {
					continue;
				}
				virusLists.add(virusResult[i]);

			}
			for (int i = 0; i < virusLists.size(); i++) {
				Vector virusRowVector=new Vector();
				virusRowVector.add(false);
				virusRowVector.add(virusLists.get(i));
				virusData.add(virusRowVector);
			}
		}
		virusTest=true;
		return virusData;
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
