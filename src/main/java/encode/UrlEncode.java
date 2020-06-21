package encode;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class UrlEncode extends JPanel {

	/**
	 * Create the panel.
	 */
	public UrlEncode() {
		setLayout(null);
		setSize(1000, 500);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 107, 450, 375);
		add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(516, 107, 450, 375);
		add(scrollPane_1);

		JTextArea textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);

		JButton btnNewButton = new JButton("编码");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText(encode(textArea.getText()));

			}
		});
		btnNewButton.setBounds(33, 51, 117, 29);
		add(btnNewButton);

		JButton btnNewButton_1 = new JButton("解码");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(decode(textArea_1.getText()));
			}
		});
		btnNewButton_1.setBounds(169, 51, 117, 29);
		add(btnNewButton_1);

		JButton button = new JButton("完全解码");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(decodeaAll(textArea_1.getText()));
			}
		});
		button.setBounds(305, 51, 117, 29);
		add(button);

		JLabel label = new JLabel("正常：");
		label.setBounds(33, 92, 61, 16);
		add(label);

		JLabel label_1 = new JLabel("编码：");
		label_1.setBounds(516, 92, 61, 16);
		add(label_1);

	}
	public static String decode(String url)   
	{   
		try {   
			String decodeURL=url;   
			decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );   
			return decodeURL;   
		} catch (UnsupportedEncodingException e) {   
			return "Issue while decoding" +e.getMessage();   
		}   
	}   

	public static String decodeaAll(String url)   
	{   
		try {   
			String prevURL="";   
			String decodeURL=url;   
			while(!prevURL.equals(decodeURL))   
			{   
				prevURL=decodeURL;   
				decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );   
			}   
			return decodeURL;   
		} catch (UnsupportedEncodingException e) {   
			return "Issue while decoding" +e.getMessage();   
		}   
	}   

	public static String encode(String url)   
	{   
		try {   
			String encodeURL=URLEncoder.encode( url, "UTF-8" );   
			return encodeURL;   
		} catch (UnsupportedEncodingException e) {   
			return "Issue while encoding" +e.getMessage();   
		}   
	}   
}
