package ssh;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.ConnectionInfo;
import ch.ethz.ssh2.Session;

public class Ssh {

	private MultiUserBase multiUserBase;
	Session session;
	public Connection conn;
	ReadThread is;
	ReadThread error;
	BufferedWriter out;
	public String hostname;
	public String username;
	public String passwordString;
	public String port;

	//	public Ssh(MultiUserBase multiUserBase) {
	//		super();
	//		this.multiUserBase=multiUserBase;
	//		// TODO Auto-generated constructor stub
	//	}

	public Ssh(String hostname, String username, String passwordString, String port) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.passwordString = passwordString;
		this.port = port;
	}

	public boolean ssh_login() {
		// TODO Auto-generated constructor stub
		
		boolean isAuthenticate=false;
		Config.sshCode=-1;
		try {

			session = null;
			conn = null;

			System.out.println("connection "+hostname+" start...");

			// 建立连接
			conn = new Connection(hostname, Integer.valueOf(port));
			
			// 连接
			try {
				ConnectionInfo info = conn.connect();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Config.sshCode=1;
				return false;
			}


			//授权
			try {
				isAuthenticate = conn.authenticateWithPassword(username, passwordString);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				Config.sshCode=2;
				return false;
			}

			if(isAuthenticate){

				//打开连接
				session = conn.openSession();
				//打开bash
				session.requestPTY("bash");		
				session.startShell();
				
				//启动多线程，来获取我们运行的结果
				//第一个参数  输入流
				//第二个参数  输出流，这个直接输出的是控制台
				is = new ReadThread(session.getStdout(), new PrintStream(System.out),username);
				new Thread(is).start();

				//启动线程2，我
				error = new ReadThread(session.getStderr(), new PrintStream(System.out),username);
				new Thread(error).start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isAuthenticate;
	}

	public void Command(String commondStr) {

		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(session.getStdin(), StandardCharsets.UTF_8.toString()));
			out.write(commondStr + "\n");
			try {
				out.flush();
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null,"连接超时，请重新登录", "温馨提示", JOptionPane.WARNING_MESSAGE);	
			}
			





		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

	public void Command(int commondStr) {

		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(session.getStdin(), StandardCharsets.UTF_8.toString()));
			out.write(commondStr);
			out.flush();





		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}





	}

	public void exit(){
		is.stopThread();
		error.stopThread();
		session.close();
		conn.close();
	}





}
