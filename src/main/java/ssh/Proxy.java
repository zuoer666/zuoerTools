package ssh;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumnModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertFalse;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class Proxy extends JPanel {
	MultiUserBase multiUserBase;
	private JTable table;
	JLabel label;
	String exeProxyResultString;
	String[] ProxyResult;
	Boolean delProxy=false;
	List<String> ProxySelectList;
	List<String> ProxySelectListTemp;
	List<String> ProxyPIDList;
	List<ProxyShowObject> ProxyLists;
	Boolean detection=true;
	Boolean delete=true;
	JButton btnNewButton;
	Main_Proxy main_Proxy;


	/**
	 * Create the panel.
	 */
	public Proxy(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		JButton button = new JButton("代理检测");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (detection) {
					detection=false;
					delProxy=false;
					Config.static_outString_map.get(multiUserBase.getHostName()).setProxy_outString("");
					MyWait();
					ProxyDetection();
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
					delProxy=true;
					Config.static_outString_map.get(multiUserBase.getHostName()).setProxy_outString("");
					ProxySelectList=new ArrayList<String>();
					for (int i = 0; i < table.getModel().getRowCount() ; i++) {
						if ((boolean) table.getValueAt(i, 0)) {
							ProxySelectList.add((String) table.getValueAt(i, 4));
						}

					}
					if (ProxySelectList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "请先选中要清除到代理程序！", "温馨提示", JOptionPane.WARNING_MESSAGE);
						delete=true;
						return;
					}


					String ProxyCommandString="";
					for (int j = 0; j < ProxySelectList.size(); j++) {
						//System.out.println(selectList.get(j));
						String ProxyTmp = ProxySelectList.get(j);

						ProxyCommandString+="if readlink -f /proc/"+ProxyTmp+"/cwd  \"/usr/bin\"; then\n";
						ProxyCommandString+="rm -rf $(readlink -f /proc/"+ProxyTmp+"/exe);\n";
						ProxyCommandString+="kill "+ProxyTmp+";\n";
						ProxyCommandString+="else\n";
						ProxyCommandString+="rm -rf $(readlink -f /proc/"+ProxyTmp+"/exe);\n";
						ProxyCommandString+="rm -rf $(readlink -f /proc/"+ProxyTmp+"/cwd/*);\n";
						ProxyCommandString+="kill "+ProxyTmp+";\n";
						ProxyCommandString+="fi\n";
						ProxyCommandString+="\n";


					}

					Main_Proxy main_Proxy = new Main_Proxy(multiUserBase);
					System.out.println(ProxyCommandString);
					String ProxyCommandString2=ProxyCommandString;
					new Thread(){

						/* (non-Javadoc)
						 * @see java.lang.Thread#run()
						 */
						@Override
						public void run() {
							// TODO Auto-generated method stub

							ProxySelectListTemp=ProxySelectList;
							exeProxyResultString=main_Proxy.proxy_execute(ProxyCommandString2, "proxy");
							try {
								Thread.sleep(2000);
								Config.static_outString_map.get(multiUserBase.getHostName()).setProxy_outString("");
								getList();
								Thread.sleep(1000);
								if (ProxySelectListTemp!=null) {
									if (!ProxyPIDList.containsAll(ProxySelectListTemp)) {
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
	protected void ProxyDetection() {
		MyWait();

		new Thread(new Runnable() {
			public void run() {
				initTable();
				getList();
			}
		}).start();
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initTable() {
		btnNewButton.setVisible(true);
		Vector headerNames=new Vector();
		
		headerNames.add("全选");
		headerNames.add("正在检测");
		headerNames.add("");
		headerNames.add("");
		headerNames.add("");

		Vector data=new Vector();

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

						}
					});

				} catch (Exception e) {

					e.printStackTrace();
					System.exit(1);
				}
				MyStopWait();
			}
		});
		mainThread.start();



	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initTable1() {

		Vector headerNames=new Vector();
		headerNames.add("全选");
		headerNames.add("NAME");
		headerNames.add("PORT");
		headerNames.add("TYPE");
		headerNames.add("PID");
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

		String ProxyComString = "netstat -anlptu|grep LISTEN|awk '{print $1,$4,$7}'";
		main_Proxy = new Main_Proxy(multiUserBase);
		exeProxyResultString=main_Proxy.proxy_execute(ProxyComString, "proxy");
		ProxyResult=exeProxyResultString.split("\n");
		if (ProxyResult.length<2) {
			getList();
		}else {
			initTable1();
		}
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Vector getData(){
		ProxyLists=new ArrayList<ProxyShowObject>();
		ProxyPIDList=new ArrayList<String>();
		Vector ProxyData = new Vector();
		//检测代理的端口
		List<ProxyDetectionRequestObject> proxyDetectionRequestObjects;
		ProxyList proxyList = new ProxyList();
		if (ProxyResult!=null) {
			System.out.println( ProxyResult.length+" 0000000000000000000000000001");
			for (int i = 1; i < ProxyResult.length; i++) {
				if (ProxyResult[i].contains("{print $1,$4,$7}")) {
					System.out.println("包含了");
					continue;
				}
				if (ProxyResult[i].contains("tcp")||ProxyResult[i].contains("udp")) {
					System.out.println( ProxyResult[i]+" pppp");
					//if (!ProxyResult[i].subSequence(0, 4).equals("USER")) {
					//	System.out.println(ProxyResult);
					String[] ProxyTempStrings = ProxyResult[i].split("\\s+");
					String tcp_udp_type=ProxyTempStrings[0];
					String ip_port=ProxyTempStrings[1];
					String proxtTemp=ProxyTempStrings[2];
					System.out.println(proxtTemp+" 0000000");
					String pID="";
					String processName="";
					if (proxtTemp.contains("/")) {
						pID=proxtTemp.split("/")[0];
						processName=proxtTemp.split("/")[1];
					}
					ProxyShowObject ProxyShowObject = new ProxyShowObject(tcp_udp_type, ip_port, pID, processName);
					String[] ProxyPortString=ip_port.split(":");
					String portString =ProxyPortString[ProxyPortString.length-1];
					System.out.println(portString);

					//一定为代理
					if (proxyList.getStrings().contains(processName)) {
						ProxyPIDList.add(pID);
						ProxyLists.add(ProxyShowObject);
						continue;
					}

					//验证是否有流量转发
					if (main_Proxy.ProxyDetection(portString)) {
						ProxyPIDList.add(pID);
						ProxyLists.add(ProxyShowObject);
						continue;
					}
				}

			}
			for (int i = 0; i < ProxyLists.size(); i++) {
				Vector ProxyRowVector=new Vector();
				ProxyRowVector.add(false);
				ProxyRowVector.add(ProxyLists.get(i).getProcessName());
				ProxyRowVector.add(ProxyLists.get(i).getIp_port());
				ProxyRowVector.add(ProxyLists.get(i).getTcp_udp_type());
				ProxyRowVector.add(ProxyLists.get(i).getpID());

				ProxyData.add(ProxyRowVector);
			}
			
			MyStopWait();
			System.out.println(ProxyLists);
			if (!delProxy&&ProxyLists.isEmpty()) {
				successTip();
			}
			detection=true;
			System.out.println( ProxyResult.length+" 0000000000000000000000000002");
		}

		return ProxyData;
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
