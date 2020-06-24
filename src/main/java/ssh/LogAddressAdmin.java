package ssh;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogAddressAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	String[] values;   //刷新list
	String[] setModelstring;
	static LogAddressAdmin frame;
	Log log;
	
	

	/**
	 * Create the frame.
	 */
	public LogAddressAdmin(Log log) {
		this.log=log;
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				String fileName = Config.logaddressfileName;
				MyJson myJson = new MyJson();
				MyFile myFile = new MyFile();
				StringBuffer readBuffer = null;
				try {
					readBuffer = myFile.readFileByBytes(fileName);
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				String[] setModelstring= myJson.JsontoStringarray1(String.valueOf(readBuffer));
				
				log.logcomboBox.setModel(new DefaultComboBoxModel(setModelstring));
				
			}
		});

		//setBounds(620, 300, 645, 378);
		setSize(645, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JList list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			@Override
			public void mousePressed(MouseEvent e) {

				String tempString = (String) list.getSelectedValue();
				//System.out.println(tempString);
				textField.setText(tempString);
			}
		});

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
		values= myJson.JsontoStringarray1(String.valueOf(readBuffer));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(45, 74, 547, 253);
		contentPane.add(list);

		textField = new JTextField();
		textField.setBounds(45, 25, 345, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("添加");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string= textField.getText();
				if (string.equals("")|string==null) {
					return;
				}else{

					String fileName = Config.logaddressfileName;
					setModelstring = new String[list.getModel().getSize()+1];	
					for (int i = 0; i < list.getModel().getSize();i++) {
						setModelstring[i]=(String) list.getModel().getElementAt(i);
					}
					setModelstring[list.getModel().getSize()]=string;
					values=setModelstring;
					MyJson myJson = new MyJson();
					String s= myJson.stringarraytoJson1(setModelstring);
					myFile.writeFile(fileName,s);

					list.setModel(new AbstractListModel() {
						public int getSize() {
							return values.length;
						}
						public Object getElementAt(int index) {
							return values[index];
						}
					});
				}
			}
		});
		button.setBounds(402, 25, 94, 29);
		contentPane.add(button);

		JButton button_1 = new JButton("删除");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//删除选定行
				int i=list.getSelectedIndex();
				ArrayList<String> arrayList = new ArrayList<String>();
				MyArrayList myArrayList = new MyArrayList();
				arrayList=myArrayList.fromStringarray(values);
				arrayList.remove(i);
				values=myArrayList.toStringArray(arrayList);

				//保存
				String s= myJson.stringarraytoJson1(values);
				myFile.writeFile(fileName,s);

				//刷新
				list.setModel(new AbstractListModel() {
					public int getSize() {
						return values.length;
					}
					public Object getElementAt(int index) {
						return values[index];
					}
				});
			}
		});
		button_1.setBounds(504, 25, 94, 29);
		contentPane.add(button_1);
		setResizable(false);
	}
}
