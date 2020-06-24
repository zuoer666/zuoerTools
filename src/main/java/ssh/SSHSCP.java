package ssh;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.swing.JOptionPane;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SSHSCP {
	private Logger log = LoggerFactory.getLogger(getClass());

	private Connection connection;

	public void initSession(String hostName, String userName, String passwd,int port) throws IOException {
		connection = new Connection(hostName,port);
		connection.connect();

		boolean authenticateWithPassword = connection.authenticateWithPassword(userName, passwd);
		if (!authenticateWithPassword) {
			throw new RuntimeException("Authentication failed. Please check hostName, userName and passwd");
		}
	}

	/**
	 * Why can't I execute several commands in one single session?
	 * 
	 * If you use Session.execCommand(), then you indeed can only execute only one command per session. This is not a restriction of the library, but rather an enforcement by the underlying SSH-2 protocol (a Session object models the underlying SSH-2 session).
	 * 
	 * There are several solutions:
	 * 
	 * Simple: Execute several commands in one batch, e.g., something like Session.execCommand("echo Hello && echo again").
	 * Simple: The intended way: simply open a new session for each command - once you have opened a connection, you can ask for as many sessions as you want, they are only a "virtual" construct.
	 * Advanced: Don't use Session.execCommand(), but rather aquire a shell with Session.startShell().
	 * 
	 * @param command
	 * @return
	 * @throws IOException
	 */

	public String execCommand(String command) throws IOException {
		Session session = connection.openSession();
		session.execCommand(command);
		InputStream streamGobbler = new StreamGobbler(session.getStdout());

		String result = IOUtils.toString(streamGobbler, StandardCharsets.UTF_8);

		session.waitForCondition(ChannelCondition.EXIT_SIGNAL, Long.MAX_VALUE);

		if (session.getExitStatus().intValue() == 0) {
			log.info("execCommand: {} success ", command);
		} else {
			log.error("execCommand : {} fail", command);
		}

		IOUtils.closeQuietly(streamGobbler);
		session.close();
		return result;
	}
	Session session;
	public String execCommands(String command) throws IOException {
		 session = connection.openSession();
		session.execCommand(command);
		InputStream streamGobbler = new StreamGobbler(session.getStdout());

		String result = IOUtils.toString(streamGobbler, StandardCharsets.UTF_8);

		session.waitForCondition(ChannelCondition.EXIT_SIGNAL, Long.MAX_VALUE);

		if (session.getExitStatus().intValue() == 0) {
			log.info("execCommand: {} success ", command);
		} else {
			log.error("execCommand : {} fail", command);
		}

		IOUtils.closeQuietly(streamGobbler);
		
		return result;
	}

	/**
	 * 远程传输单个文件
	 * 
	 * @param localFile
	 * @param remoteTargetDirectory
	 * @throws IOException
	 */

	public Boolean transferFile(String localFile, String remoteTargetDirectory) {
		//		File file = new File(localFile);
		//		if (file.isDirectory()) {
		//			throw new RuntimeException(localFile + "  is not a file");
		//		}
		//		String fileName = file.getName();
		//		execCommand("cd " + remoteTargetDirectory + ";rm " + fileName + "; touch " + fileName);

		//SCPClient sCPClient = connection.createSCPClient();
		//		SCPOutputStream scpOutputStream = sCPClient.put(fileName, file.length(), remoteTargetDirectory, "0600");
		//
		//		String content = IOUtils.toString(new FileInputStream(file));
		//		scpOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
		//		scpOutputStream.flush();
		//		scpOutputStream.close();

		try {
			SCPClient client = new SCPClient(connection);
			client.put(localFile, remoteTargetDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection.close();
			return false;
		} 
		connection.close();
		return true;
	}
	
	public Boolean transferFiles(String localFile, String remoteTargetDirectory) {
		try {
			SCPClient client = new SCPClient(connection);
			client.put(localFile, remoteTargetDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return true;
	}

	/**
	 * 传输整个目录
	 * 
	 * @param localFile
	 * @param remoteTargetDirectory
	 * @throws IOException
	 */
	Boolean  transferDirectorySuccessBoolean = true;
	public Boolean transferDirectory(String localDirectory, String remoteTargetDirectory) {
		File dir = new File(localDirectory);
		if (!dir.isDirectory()) {
			throw new RuntimeException(localDirectory + " is not directory");
		}

		String[] files = dir.list();
		try {
			for (String file : files) {
				if (file.startsWith(".")) {
					continue;
				}
				if (file.contains(" ")) {
					JOptionPane.showMessageDialog(null,localDirectory+"/"+file+"这个文件上传失败，因为文件名不能有空格", "温馨提示", JOptionPane.WARNING_MESSAGE);	
					continue;

				}
				String fullName = localDirectory + "/" + file;
				if (new File(fullName).isDirectory()) {
					String rdir = remoteTargetDirectory + "/" + file;

					execCommands("mkdir -p " + remoteTargetDirectory + "/" + file);

					if (!transferDirectory(fullName, rdir)) {
						transferDirectorySuccessBoolean=false;
					}
				} else {
					transferFiles(fullName, remoteTargetDirectory);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			transferDirectorySuccessBoolean=false;
			return transferDirectorySuccessBoolean;
		}
		return transferDirectorySuccessBoolean;

	}
	

	/**
	 * download
	 * @param remoteFile 服务器上的文件名
	 * @param remoteTargetDirectory 服务器上文件的所在路径
	 * @param newPath 下载文件的路径
	 */

	public Boolean downloadFile(String remoteFile, String remoteTargetDirectory,String newPath){

		try {
			SCPClient scpClient = connection.createSCPClient();
			scpClient.get(remoteTargetDirectory+"/"+remoteFile, newPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection.close();
			return false;
		}
		connection.close();
		return true;

	}


	public void close() {
		if (session!=null) {
			session.close();
		}
		connection.close();
	}



}
