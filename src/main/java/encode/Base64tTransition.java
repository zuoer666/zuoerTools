package encode;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.awt.event.ActionEvent;

public class Base64tTransition extends JPanel {

	Base64.Decoder decoder;
	Base64.Encoder encoder;
	String transitionBefore1;
	String transitionBefore2;
	

	/**
	 * Create the frame.
	 */
	public Base64tTransition() {
		
		setSize(1000, 500);
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 57, 943, 187);
		add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(24, 300, 943, 187);
		add(scrollPane_1);
		
		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		JLabel lblBase_1 = new JLabel("正常  ----> Base64");
		lblBase_1.setBounds(43, 26, 130, 15);
		add(lblBase_1);
		
		JLabel lblBase = new   JLabel("Base64 ----> 正常");
		lblBase.setBounds(34, 268, 130, 15);
		add(lblBase);
		
		JButton btnNewButton = new JButton("转换");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] textByte = null;
				 try {
					 transitionBefore1=textArea.getText();
					textByte = textArea.getText().getBytes("UTF-8");
				} catch (UnsupportedEncodingException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
				 textArea.setText(encoder.encodeToString(textByte));
			}
		});
		btnNewButton.setBounds(186, 22, 93, 23);
		add(btnNewButton);
		
		JButton button = new JButton("转换");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//解码
				try {
					transitionBefore2=textArea_1.getText();
					textArea_1.setText(new String(decoder.decode(textArea_1.getText()), "UTF-8"));
				} catch (UnsupportedEncodingException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
			}
		});
		button.setBounds(177, 265, 93, 23);
		add(button);
		
		JButton btnNewButton_1 = new JButton("转换前");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(transitionBefore1);
			}
		});
		btnNewButton_1.setBounds(291, 23, 93, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("转换前");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText(transitionBefore2);
			}
		});
		btnNewButton_2.setBounds(282, 265, 93, 23);
		add(btnNewButton_2);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		decoder = Base64.getDecoder();
		encoder = Base64.getEncoder();
	}
}
