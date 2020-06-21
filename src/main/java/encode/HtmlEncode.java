package encode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlEncode extends JPanel {

	/**
	 * Create the panel.
	 */
	public HtmlEncode() {
		setLayout(null);
		setSize(1000, 500);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 107, 450, 300);
		add(scrollPane);

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(516, 107, 450, 300);
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
	public static String decode(String html)   
	{   

		String decodeHtml=html;  
		
		decodeHtml=StringEscapeUtils.unescapeHtml4(decodeHtml);
		return decodeHtml;   
	}   

	public static String decodeaAll(String html)   
	{   
		String prevHtml="";   
		String decodeHtml=html;   
		while(!prevHtml.equals(decodeHtml))   
		{   
			prevHtml=decodeHtml;   
			decodeHtml=StringEscapeUtils.unescapeHtml4(decodeHtml);   
		}   
		return decodeHtml;   
	}   

	public static String encode(String html)   
	{   
		String encodeHtml=StringEscapeUtils.escapeHtml4(html);
		return encodeHtml;   
	}   
}
