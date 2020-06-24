package main;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import encode.EncodeMain;
import ssh.Login;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.IllegalFormatCodePointException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class ZuoerToolsMain extends JFrame {

	private JPanel contentPane;
	private static ZuoerToolsMain frame ;
	private static int beforeX;
	private static int beforeY;
	public static Login sshFrame ;
	public static EncodeMain encodeFrame;
	private JButton encodeButton ;
	private JButton sshButton ;
	private JLabel lblNewLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ZuoerToolsMain();
					frame.setLocationRelativeTo(null);
//					encodeFrame = new EncodeMain();
//					encodeFrame.setLocationRelativeTo(null);
//					setMyLocation(encodeFrame.getX(), frame.getY());
//					encodeFrame.toFront();
//					beforeLocation(encodeFrame);
//					encodeFrame.setVisible(true);
					
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ZuoerToolsMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 126, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		encodeButton = new JButton("编码");
		encodeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (encodeFrame==null) {
					encodeFrame = new EncodeMain();
					encodeFrame.setLocationRelativeTo(null);
					encodeFrame.setVisible(true);
					beforeLocation(encodeFrame);

				}else {
					if (encodeFrame.getExtendedState() == JFrame.ICONIFIED)//如果此时窗口状态为最小化
						encodeFrame.setExtendedState(JFrame.NORMAL);//那么首先还原此窗口
					encodeFrame.toFront();
				}
				setMyLocation(encodeFrame.getX(), frame.getY());
			}
		});
		encodeButton.setBounds(6, 56, 117, 29);
		contentPane.add(encodeButton);

		sshButton = new JButton("ssh");
		sshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sshButtonClick();

			}

			
		});
		sshButton.setBounds(6, 101, 117, 29);
		contentPane.add(sshButton);
		
		lblNewLabel = new JLabel("左耳工具集 v 1.0.2",JLabel.CENTER);
		
		lblNewLabel.setBounds(6, 15, 117, 16);
		contentPane.add(lblNewLabel);
		setResizable(false);

	}

	
	private static void beforeLocation(Frame myFrame) {
		// TODO Auto-generated method stub
		beforeX=myFrame.getX();
		beforeY=frame.getY();
	}
	public static void recoverLocationFirst() {
		if (encodeFrame!=null) {
			frame.setLocation(encodeFrame.getX()-frame.getWidth(), beforeY);
		}
		
	}
	public static void recoverLocation() {
		frame.setLocation(beforeX-frame.getWidth(), beforeY);
	}

	public static void setMyLocation(int x,int y) {
		// TODO Auto-generated method stub
		frame.setLocation(x-frame.getWidth(),frame.getY());
	}
	public static void sshButtonClick() {
		// TODO Auto-generated method stub
		if (sshFrame==null) {
			sshFrame = new Login();
			Login.setFrame(sshFrame);
			sshFrame.setLocationRelativeTo(null);
			sshFrame.setVisible(true);
			beforeLocation(sshFrame);
			setMyLocation(sshFrame.getX(), sshFrame.getY());
		}else {
			if (sshFrame.getExtendedState() == JFrame.ICONIFIED)//如果此时窗口状态为最小化
				sshFrame.setExtendedState(JFrame.NORMAL);//那么首先还原此窗口
			sshFrame.toFront();
			if (sshFrame.ghostNameLogin!=null) {
				if (sshFrame.ghostNameLogin.getExtendedState() == JFrame.ICONIFIED)//如果此时窗口状态为最小化
					sshFrame.ghostNameLogin.setExtendedState(JFrame.NORMAL);//那么首先还原此窗口
				sshFrame.ghostNameLogin.toFront();
				if (sshFrame.ghostNameLogin.main_frame!=null) {
					if (sshFrame.ghostNameLogin.main_frame.getExtendedState() == JFrame.ICONIFIED)//如果此时窗口状态为最小化
						sshFrame.ghostNameLogin.main_frame.setExtendedState(JFrame.NORMAL);//那么首先还原此窗口
					sshFrame.ghostNameLogin.main_frame.toFront();
					beforeLocation(	sshFrame.ghostNameLogin.main_frame);
					setMyLocation(sshFrame.ghostNameLogin.main_frame.getX(), sshFrame.ghostNameLogin.main_frame.getY());
				}else {
					beforeLocation(sshFrame.ghostNameLogin);
					setMyLocation(sshFrame.ghostNameLogin.getX(), sshFrame.ghostNameLogin.getY());
				}

			}else {
				beforeLocation(sshFrame);
				setMyLocation(sshFrame.getX(), sshFrame.getY());
			}
			

		}
		
		
	}

	public static int getMyWidth(){
		return frame.getWidth();
	}

}
