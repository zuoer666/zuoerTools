package encode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import main.ZuoerToolsMain;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class EncodeMain extends JFrame {

	private JPanel contentPane;

	

	/**
	 * Create the frame.
	 */
	public EncodeMain() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				ZuoerToolsMain.recoverLocation();	
				ZuoerToolsMain.encodeFrame=null;
			}

			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				ZuoerToolsMain.recoverLocation();
				ZuoerToolsMain.encodeFrame=null;
			}
			
		});
		
		setSize(1000, 570);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("左耳渗透工具集");
		JTabbedPane tabbedPane=new JTabbedPane();
		tabbedPane.setSize(1000, 550);
		//  ImageIcon icon=createImageIcon("tab.jp1g");
		JComponent panel1= new UrlEncode();
		tabbedPane.addTab("URL编码",null, panel1,"");
		tabbedPane.setMnemonicAt(0,KeyEvent.VK_F1);
		
		JComponent panel2=new Base64tTransition();
		tabbedPane.addTab("Base64编码",null,panel2,"");
		tabbedPane.setMnemonicAt(1,KeyEvent.VK_F2);

		JComponent panel3=new HtmlEncode();
		tabbedPane.addTab("Html编码",null,panel3,"");
		tabbedPane.setMnemonicAt(2,KeyEvent.VK_F3);
		contentPane.add(tabbedPane);
		setResizable(false);
		

	}
}
