package ssh;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewFile extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	M_File m_File;
	

	/**
	 * Create the frame.
	 */
	public NewFile(M_File m_File) {
		
		this.m_File=m_File;
		//setBounds(620, 470, 417, 188);
		setSize(417, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(141, 19, 232, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("文件或目录名：");
		lblNewLabel.setBounds(30, 24, 99, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("新建文件");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(btnNewButton,"不能为空", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (textField.getText().contains(" ")) {
					JOptionPane.showMessageDialog(btnNewButton,"上传的文件不能有空格", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					return;
				}
				m_File.NewFile(textField.getText());
				m_File.refresh();
				JOptionPane.showMessageDialog(btnNewButton,"新建成功", "温馨提示", JOptionPane.WARNING_MESSAGE);
				textField.setText("");
				
			}
		});
		btnNewButton.setBounds(55, 83, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("新建目录");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(btnNewButton_1,"不能为空", "温馨提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (textField.getText().contains(" ")) {
					JOptionPane.showMessageDialog(btnNewButton_1,"上传的文件不能有空格", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					return;
				}
				if(m_File.NewDir(textField.getText())){
					m_File.refresh();
					textField.setText("");
					JOptionPane.showMessageDialog(btnNewButton_1,"新建成功", "温馨提示", JOptionPane.WARNING_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(btnNewButton_1,"该目录已存在", "温馨提示", JOptionPane.WARNING_MESSAGE);
				}
				
				
			}
		});
		btnNewButton_1.setBounds(229, 83, 117, 29);
		contentPane.add(btnNewButton_1);
		setResizable(false);
	}
}
