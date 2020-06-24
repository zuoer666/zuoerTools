package ssh;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.alibaba.fastjson.JSON;

import main.ZuoerToolsMain;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CommonCommands extends JFrame {

	private JPanel contentPane;
	private JTable table;
	CommonCommands frame;
	List<CommonCommandsObject> list = new ArrayList<CommonCommandsObject>();
	Object[][] objects ;
	Object[][] tempobjects ;
	DefaultTableModel defaultTableModel;
	//选中行和列
	int currentRow;
	int column;
	int click_2;
	
	Shell myShell;
	int beforeX;
	int beforeY;
	Main parentMainFrame;
	boolean editor=false;
	/**
	 * Create the frame.
	 */
	public CommonCommands(Shell myShell) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parentMainFrame.setLocation(beforeX, beforeY);
				ZuoerToolsMain.setMyLocation(parentMainFrame.getX(), parentMainFrame.getY());
				myShell.commands=null;
			}
		});
		this.myShell=myShell;

		parentMainFrame = (Main) SwingUtilities.getWindowAncestor(myShell);
		beforeX=parentMainFrame.getX();
		beforeY=parentMainFrame.getY();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		double xx=(screen.getWidth()-parentMainFrame.getWidth()-450-ZuoerToolsMain.getMyWidth())/2;
		System.out.println(screen.getWidth());
		System.out.println(xx);
		parentMainFrame.setLocation((int) (Math.round(xx))+ZuoerToolsMain.getMyWidth(), parentMainFrame.getY());
		ZuoerToolsMain.setMyLocation(parentMainFrame.getX(), parentMainFrame.getY());
		setTitle("Linux应急响应助手 - 常用命令");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(parentMainFrame.getX()+parentMainFrame.getWidth(), parentMainFrame.getY(), 450, parentMainFrame.getHeight());
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 65, 414, 306);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentRow=table.getSelectedRow();
				column=table.getSelectedColumn();
				int clickTimes = e.getClickCount();
				click_2=clickTimes;
			    if (clickTimes == 2) {
			    //执行命令
			    	Thread thread=new Thread(){
						@Override
						public void run() {
							// TODO Auto-generated method stub
							//super.run();
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (click_2==2) {
								System.out.println("执行命令:"+table.getValueAt(currentRow, 0));
								myShell.exe(table.getValueAt(currentRow, 0).toString());
							}
						}
			    		
			    	};
			    	thread.start();
			    }
			    
			    //是否可以编辑
			    if (clickTimes == 2) {
			    	editor=true;
			    }
			}
			
			
		});
		//在焦点丢失时终止编辑
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null},
				},
				new String[] {
						"\u547D\u4EE4", "\u5907\u6CE8"
				}
				));
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savaFile();
			}
		});
		btnNewButton.setBounds(331, 26, 93, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("添加");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addObjects();
			}
		});
		btnNewButton_1.setBounds(10, 26, 93, 23);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("删除");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if (row==-1) {
					JOptionPane.showMessageDialog(frame, "请选择要删除的命令！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				delObjects(row);

			}
		});
		btnNewButton_2.setBounds(126, 26, 93, 23);
		contentPane.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("上移");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if (row==-1) {
					JOptionPane.showMessageDialog(frame, "请选择要移动的命令！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				moveUp(row);
			}
		});
		btnNewButton_3.setBounds(10, 381, 93, 23);
		contentPane.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("下移");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				if (row==-1) {
					JOptionPane.showMessageDialog(frame, "请选择要移动的命令！", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				moveDown(row);
			}
		});
		btnNewButton_4.setBounds(126, 381, 93, 23);
		contentPane.add(btnNewButton_4);
		init();
		setResizable(false);
	}
	
	protected void moveUp(int row) {
		if (row==0) {
			return;
		}
		// TODO Auto-generated method stub
		Object[] tempObject= objects[row-1];
		objects[row-1]=objects[row];
		objects[row]=tempObject;
		refresh();
		currentRow--;
		table.setRowSelectionInterval(currentRow,currentRow);
	}
	protected void moveDown(int row) {
		// TODO Auto-generated method stub
		if (row==objects.length-1) {
			return;
		}
		Object[] tempObject= objects[row+1];
		objects[row+1]=objects[row];
		objects[row]=tempObject;
		refresh();
		currentRow++;
		table.setRowSelectionInterval(currentRow,currentRow);
	}
	protected void delObjects(int row) {
		tempobjects=objects;
		objects=new Object[tempobjects.length-1][2];
		for (int i = 0; i < row; i++) {
			objects[i]=tempobjects[i];
		}
		for (int i = row; i < objects.length; i++) {
			objects[i]=tempobjects[i+1];
		}
		refresh();
	}
	protected void addObjects() {
		// TODO Auto-generated method stub
		tempobjects=objects;
		objects=new Object[tempobjects.length+1][2];
		for (int i = 0; i < tempobjects.length; i++) {
			objects[i]=tempobjects[i];
		}
		objects[tempobjects.length][0]="命令";
		objects[tempobjects.length][1]="备注";
		refresh();
	}



	protected void savaFile() {
		// TODO Auto-generated method stub
		list = new ArrayList<CommonCommandsObject>();
		for (int i = 0; i < objects.length; i++) {
			list.add(new CommonCommandsObject(table.getValueAt(i, 0).toString(), table.getValueAt(i, 1).toString()));
		}
		String s=JSON.toJSONString(list);
		MyFile.writeFile(Config.commonCommandfileName,s);
	}

	private void init() {
		// TODO Auto-generated method stub

		ReadFile();
		objects = new Object[list.size()][2];
		for (int i = 0; i < objects.length; i++) {
			objects[i][0]=list.get(i).getCommand();
			objects[i][1]=list.get(i).getRemark();
		}
		refresh();

	}

	private void refresh() {
		// TODO Auto-generated method stub
		defaultTableModel=new DefaultTableModel(
				objects,
				new String[] {
						"\u547D\u4EE4", "\u5907\u6CE8"
				}
				){
			/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				if (editor) {
					click_2=3;
					editor=false;
					return true;
				}
				return false;
			}
		};
		table.setModel(defaultTableModel);
		defaultTableModel.addTableModelListener(new TableModelListener(){

            @Override
            public void tableChanged(TableModelEvent arg0) {
                int row = arg0.getFirstRow();
                 column = arg0.getColumn();
                objects[row][0] =defaultTableModel.getValueAt(row, 0);
                objects[row][1] =defaultTableModel.getValueAt(row, 1);
            }
        });
	}

	private void ReadFile() {
		// TODO Auto-generated method stub
		list = new ArrayList<CommonCommandsObject>();
		StringBuffer readBuffer = null;
		try {
			readBuffer = MyFile.readFileByBytes(Config.commonCommandfileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "获取"+Config.commonCommandfileName+"错误！", "温馨提示", JOptionPane.WARNING_MESSAGE);

		}
		try {
			list = JSON.parseArray(String.valueOf(readBuffer), CommonCommandsObject.class);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (list.isEmpty()) {
			list = new ArrayList<CommonCommandsObject>();
			list.add(new CommonCommandsObject("last", "last"));
			list.add(new CommonCommandsObject("last1", "last1"));
			list.add(new CommonCommandsObject("last2", "last2"));
			list.add(new CommonCommandsObject("last3", "last3"));
			String s=JSON.toJSONString(list);
			MyFile.writeFile(Config.commonCommandfileName,s);
			ReadFile();
		}
		objects = new Object[list.size()][2];
		for (int i = 0; i < objects.length; i++) {
			objects[i][0]=list.get(i).getCommand();
			objects[i][1]=list.get(i).getRemark();
		}
		refresh();
	}

}
