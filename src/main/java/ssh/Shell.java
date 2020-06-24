package ssh;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.omg.CORBA.PUBLIC_MEMBER;

import main.ZuoerToolsMain;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Shell extends JPanel {
	public MultiUserBase multiUserBase;

	 JTextField shell_textField;
	//在textArea里输出的结果
	String shell_out;
	Main_shell main_shell;
	//动态输出
	Thread thread;
	String tempString;
	JTextArea shell_textArea ;
	String setString;
	//命令
	String shellString;
	JButton shell_execute;
	List<String> comStrings=new ArrayList<String>();
	int cimInt=0;
	int tempcimInt=0;
	Shell myShell;
	Boolean iscat;
	CommonCommands commands;
	public Shell getMyShell() {
		return myShell;
	}
	
	/**
	 * @param myShell the myShell to set
	 */
	public void setMyShell(Shell myShell) {
		this.myShell = myShell;
	}

	/**
	 * Create the panel.
	 */
	public Shell(MultiUserBase multiUserBase) {

		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);

		JLabel lblNewLabel = new JLabel("命令：");
		lblNewLabel.setBounds(63, 328, 61, 16);
		this.add(lblNewLabel);

		shell_textField = new JTextField();

		shell_textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if (key==KeyEvent.VK_ENTER) {
					shell_execute.doClick();
				}

			}
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==KeyEvent.VK_TAB){//按下TAB
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							exe(9);
							System.out.println("TAB");
						}
					}).start();
				}
				if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_C){//同时按下ctrl+c
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							exe(3);
							System.out.println("ctrl+c");
						}
					}).start();
				}
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){//esc
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							exe(27);
							System.out.println("ESC");
						}
					}).start();
				}
				
				if(e.getKeyCode()==KeyEvent.VK_UP){//按上
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							System.out.println("上");
							if (tempcimInt==0) {
								return;
							}
							shell_textField.setText(comStrings.get(--tempcimInt));
							shell_textField.setCaretPosition(shell_textField.getText().length()); 
						}
					}).start();
				}
				
				if(e.getKeyCode()==KeyEvent.VK_DOWN){//按下
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							System.out.println("下");
							if (tempcimInt>=cimInt-1) {
								return;
							}
							shell_textField.setText(comStrings.get(++tempcimInt));
							shell_textField.setCaretPosition(shell_textField.getText().length()); 
						}
					}).start();
				}
				
			}
		});
		shell_textField.setBounds(136, 323, 592, 26);
		this.add(shell_textField);
		shell_textField.setColumns(10);
		main_shell= new Main_shell(multiUserBase);

		shell_execute = new JButton("执行");
		thread = new Thread(){
			public void run() {

			}
		};
		thread.start();
		shell_execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						exe(shell_textField.getText());
					}
				}).start();

			}

		});
		shell_execute.setBounds(740, 323, 117, 29);
		this.add(shell_execute);

		shell_textArea = new JTextArea();
		//add(shell_textArea);
		shell_textArea.setBounds(53, 84, 901, 244);
		setString = Config.static_outString_map.get(multiUserBase.getHostName()).getByString("shell");
		shell_textArea.setText(setString);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(53, 20, 936, 293);
		scrollPane.add(shell_textArea);
		scrollPane.setViewportView(shell_textArea);
		add(scrollPane);

		JButton button = new JButton("常用命令");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (commands==null) {
					commands = new CommonCommands(myShell);
					commands.setLocationRelativeTo(null);
					commands.setLocation(SwingUtilities.getWindowAncestor(myShell).getX()+SwingUtilities.getWindowAncestor(myShell).getWidth(), SwingUtilities.getWindowAncestor(myShell).getY());
					
					commands.setVisible(true);
				}
				
			}
		});
		button.setBounds(872, 325, 117, 29);
		add(button);





	}
	public void exe(String  shellString) {
		comStrings.add(shellString);
		cimInt++;
		tempcimInt=cimInt;
		if (shellString.length()==5&&shellString.equals("clear")) {
			shell_textField.setText("");
			shell_textArea.setText("");
			Config.static_outString_map.get(multiUserBase.getHostName()).setShell_outString("");
			return;
		}
		shell_textField.setText("");
		if (shellString.length()>4&&shellString.substring(0, 4).equals("cat ")) {
			String catshellString=shell_textArea.getText();	
			Cat cat = new Cat(multiUserBase);
			String resultstring = cat.cat_execute(shellString,"once");
			shell_textArea.setText(catshellString+resultstring);
			shell_textArea.setCaretPosition(shell_textArea.getText().length());
			MultiUserOutStringBase shelltempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
			shelltempmMultiUserOutStringBase.setShell_outString(catshellString+resultstring);
			Config.static_outString_map.put(Config.shellOutObject.getUsername(), shelltempmMultiUserOutStringBase);
			shell_textField.setText("");
			return;
		}
		// TODO Auto-generated method stub
		thread.stop();
		tempString=setString;
		main_shell.shell_execute(shellString,"shell");

		//动态刷新结果
		thread = new Thread(){
			public void run() {
				//System.out.println("");
				for (int i = 0; i < Config.SDynamicDisplayNumber; i++) {
					if (tempString==Config.static_outString_map.get(multiUserBase.getHostName()).getByString("shell")) {
						setString = Config.static_outString_map.get(multiUserBase.getHostName()).getByString("shell");
						tempString=setString;
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						setString = Config.static_outString_map.get(multiUserBase.getHostName()).getByString("shell");
						tempString=setString;
						Thread mainThread = new Thread(new Runnable() {
							@Override
							public void run() {
								try {
									// 不要在UI线程外更新操作UI，这里SwingUtilities会找到UI线程并执行更新UI操作
									SwingUtilities.invokeLater(new Runnable() {
										@Override
										public void run() {
											shell_textArea.setText(setString);
											shell_textArea.setCaretPosition(shell_textArea.getText().length());
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
				}
			}
		};
		thread.start();

		//System.out.println(ReadThread.shell_static_outString);

	}
	public void exe(int  code) {
		shell_textField.setText("");
		// TODO Auto-generated method stub
		thread.stop();
		tempString=setString;
		main_shell.shell_execute(code,"shell");

		//动态刷新结果
		thread = new Thread(){
			public void run() {

				for (int i = 0; i < Config.SDynamicDisplayNumber; i++) {
					if (tempString==setString) {
						try {
							Thread.sleep(Config.SDynamicDisplayTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						setString = Config.static_outString_map.get(multiUserBase.getHostName()).getByString("shell");

					}else {
						tempString=setString;
						shell_textArea.setText(setString);
						shell_textArea.setCaretPosition(shell_textArea.getText().length());
					}
				}
			}
		};
		thread.start();

		//System.out.println(ReadThread.shell_static_outString);

	}
}
