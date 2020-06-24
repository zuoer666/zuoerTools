package ssh;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;


public class Log extends JPanel {
	private JTextArea textArea;
	public static JComboBox logcomboBox;
	String log_out;
	String tempString;
	MultiUserBase multiUserBase;
	String cat_result;
	LogAddressAdmin frame;
	Log log;
	/**
	 * @return the log
	 */
	public Log getLog() {
		return log;
	}
	/**
	 * @param log the log to set
	 */
	public void setLog(Log log) {
		this.log = log;
	}
	/**
	 * Create the panel.
	 */
	public Log(MultiUserBase multiUserBase) {
		this.multiUserBase = multiUserBase;
		setLayout(null);
		setBounds(0, 55, 999, 381);
		JLabel lblNewLabel = new JLabel("选择日志地址");
		lblNewLabel.setBounds(36, 28, 78, 16);
		add(lblNewLabel);



		textArea = new JTextArea();
		textArea.setBounds(30, 56, 939, 298);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 56, 939, 298);
		scrollPane.add(textArea);
		scrollPane.setViewportView(textArea);
		add(scrollPane);
		Main_Log main_log = new Main_Log(multiUserBase);
		JButton btnNewButton = new JButton("查看");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.static_outString_map.get(multiUserBase.getHostName()).setLog_outString("");
				String logAddressString= (String) logcomboBox.getSelectedItem();
				log_out = main_log.log_execute("cat "+logAddressString,"log");
				
				new Thread(){

					/* (non-Javadoc)
					 * @see java.lang.Thread#run()
					 */
					@Override
					public void run() {
						// TODO Auto-generated method stub
						for (int i = 0; i < Config.SDynamicDisplayNumber; i++) {
							if (log_out.equals(Config.static_outString_map.get(multiUserBase.getHostName()).getByString("log"))) {
								try {
									Thread.sleep(Config.SDynamicDisplayTime);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}else {
								log_out=Config.static_outString_map.get(multiUserBase.getHostName()).getByString("log");
								setText();
							}
						}
					}}.start();
				setText();
				
				
			}
		});
		btnNewButton.setBounds(606, 23, 117, 29);
		add(btnNewButton);

		//保存有问题 " 问题
//		JButton button = new JButton("保存");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println();
//				log_out = main_log.log_execute("echo -e "+'"'+textArea.getText()+'"'+" > "+(String) logcomboBox.getSelectedItem(),"once");
//			}
//		});
//		button.setBounds(852, 23, 117, 29);
//		add(button);

		logcomboBox = new JComboBox();

		String fileName = Config.logaddressfileName;
		MyJson myJson = new MyJson();
		MyFile myFile = new MyFile();
		StringBuffer readBuffer = null;
		try {
			readBuffer = myFile.readFileByBytes(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] setModelstring= myJson.JsontoStringarray1(String.valueOf(readBuffer));

		logcomboBox.setModel(new DefaultComboBoxModel(setModelstring));
		logcomboBox.setBounds(152, 24, 442, 27);
		add(logcomboBox);

		JButton btnNewButton_1 = new JButton("编辑地址");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame = new LogAddressAdmin(log);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(842, 23, 117, 29);
		add(btnNewButton_1);
		
		JButton button = new JButton("清空");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String logAddressString= (String) logcomboBox.getSelectedItem();
				log_out = main_log.log_execute("true > "+logAddressString,"log");
				btnNewButton.doClick();
			}
		});
		button.setBounds(726, 23, 117, 29);
		add(button);



	}
	protected void setText() {
		// TODO Auto-generated method stub
		//String[] strings=log_out.replace("\n\n", "\n").split("\n");
		String[] strings=log_out.split("\n");
		cat_result=new MyJson().stringarraytoJson2(strings,1);
		textArea.setText(cat_result);
		textArea.setCaretPosition(textArea.getText().length());
	}
}
