package ssh;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.alibaba.fastjson.JSON;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Editor extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	String initString;
	JTextArea textArea;
	MultiUserBase multiUserBase;
	public Editor(String path,String title,String string,MultiUserBase multiUserBase) {
		initString=string;
		this.multiUserBase=multiUserBase;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String textString=textArea.getText();
				if (!initString.equals(textArea.getText())) {
					int saveConfirmDialogCode= JOptionPane.showConfirmDialog(null,"是否保存", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (saveConfirmDialogCode==0) {
						textString.replace("'", "\\'");
						textString.replace("$", "\\$");
						Main_File main_File = new Main_File(multiUserBase);
						String Command="cat <<EOF > "+path+"\n"+textString+"\nEOF";
						
						main_File.file_execute(Command, "once");
					}
				}
				
			}
		});
		setTitle(title);
		//setBounds(420, 320, 806, 459);
		setSize(806, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0,806, 459);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setText(string);
		textArea.setCaretPosition(0);
		scrollPane.setViewportView(textArea);
		setResizable(false);
	}
}
