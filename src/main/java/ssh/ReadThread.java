package ssh;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.PUBLIC_MEMBER;



public class ReadThread implements Runnable {
	private InputStream in;
	private PrintStream out;
	private MultiUserBase multiUserBase;
	String usernameString;

	//public static String shell_static_outString="";
	//public static String log_static_outString="";
	//public static String static_outString_once="";
	//public static String file_change_static_outString="";
	//public static String default_static_outString="";
	//设置编码
	private String charset = StandardCharsets.UTF_8.toString();

	//是否停止 flag
	private boolean flag = true;

	// 停止线程
	public void stopThread() {
		flag = false;
	}

	/**
	 * 
	 * @param in  输入流，获取的输入
	 * @param out 输出流
	 */
	public ReadThread(InputStream in, PrintStream out,String usernameString) {
		super();
		this.in = in;
		this.out = out;
		this.usernameString= usernameString;
	}

	public void run() {
		  
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, charset));

			String temp;

			//读取数据
			while ((temp = br.readLine()) != null && flag == true) {
				if (out != null) {
					out.println(temp);
					switch (Config.shellOutObject.getOutType()) {
					case "shell":
						MultiUserOutStringBase shelltempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String shelltempmMultiString=shelltempmMultiUserOutStringBase.getByString("shell");
						shelltempmMultiString=shelltempmMultiString+temp+"\n";
						shelltempmMultiUserOutStringBase.setShell_outString(shelltempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), shelltempmMultiUserOutStringBase);
						break;
					case "log":
						MultiUserOutStringBase logtempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String logtempmMultiString=logtempmMultiUserOutStringBase.getByString("log");
						logtempmMultiString=logtempmMultiString+temp+"\n";
						logtempmMultiUserOutStringBase.setLog_outString(logtempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), logtempmMultiUserOutStringBase);
						break;	
					case "once":
						MultiUserOutStringBase oncetempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String oncetempmMultiString=oncetempmMultiUserOutStringBase.getByString("once");
						oncetempmMultiString=oncetempmMultiString+temp+"\n";
						oncetempmMultiUserOutStringBase.setOnce_outString(oncetempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), oncetempmMultiUserOutStringBase);
						break;
					case "file_change":
						MultiUserOutStringBase file_changetempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String file_changetempmMultiString=file_changetempmMultiUserOutStringBase.getByString("file_change");
						file_changetempmMultiString=file_changetempmMultiString+temp+"\n";
						file_changetempmMultiUserOutStringBase.setFile_change_outString(file_changetempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), file_changetempmMultiUserOutStringBase);
						break;
					case "file":
						MultiUserOutStringBase filetempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String file_tempmMultiString=filetempmMultiUserOutStringBase.getByString("file");
						file_tempmMultiString=file_tempmMultiString+temp+"\n";
						filetempmMultiUserOutStringBase.setFile_outString(file_tempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), filetempmMultiUserOutStringBase);
						break;
					case "virus":
						MultiUserOutStringBase virustempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String virus_tempmMultiString=virustempmMultiUserOutStringBase.getByString("virus");
						virus_tempmMultiString=virus_tempmMultiString+temp+"\n";
						virustempmMultiUserOutStringBase.setVirus_outString(virus_tempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), virustempmMultiUserOutStringBase);
						break;
					case "mining":
						MultiUserOutStringBase miningtempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String mining_tempmMultiString=miningtempmMultiUserOutStringBase.getByString("mining");
						mining_tempmMultiString=mining_tempmMultiString+temp+"\n";
						miningtempmMultiUserOutStringBase.setMining_outString(mining_tempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), miningtempmMultiUserOutStringBase);
						break;
					case "process":
						MultiUserOutStringBase processtempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String process_tempmMultiString=processtempmMultiUserOutStringBase.getByString("process");
						process_tempmMultiString=process_tempmMultiString+temp+"\n";
						processtempmMultiUserOutStringBase.setProcess_outString(process_tempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), processtempmMultiUserOutStringBase);
						break;
					case "proxy":
						MultiUserOutStringBase proxytempmMultiUserOutStringBase = Config.static_outString_map.get(Config.shellOutObject.getUsername());
						String proxy_tempmMultiString=proxytempmMultiUserOutStringBase.getByString("proxy");
						proxy_tempmMultiString=proxy_tempmMultiString+temp+"\n";
						proxytempmMultiUserOutStringBase.setProxy_outString(proxy_tempmMultiString);
						Config.static_outString_map.put(Config.shellOutObject.getUsername(), proxytempmMultiUserOutStringBase);
						break;
					case "no":
						System.out.println("选择输出为no");
						break;
					default:
						System.out.println("默认不输出");
						break;
					}
					
					
					out.flush();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
