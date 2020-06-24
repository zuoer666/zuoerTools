package ssh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
	
	static{
		System.out.println(System.getProperty("user.dir"));
	}
	public static String file = "./myfile/";
	public static String logaddressfileName = file+"logaddress.json";
	public static String hostmessagefileName = file+"hostmessage.json";
	public static String userfileName = file+"user.json";
	public static String configfileName = file+"config.json";
	public static String commonCommandfileName = file+"commonCommands.json";
	public static String SVirusFileName = file+"virus.zuoer";
	//public static String shellout = "shell";
	public static ShellOutObject shellOutObject;
	public static ShellOutObject tempshellOutObject;
	//用于接收命令回显
	public static Map<String,MultiUserOutStringBase> static_outString_map = new HashMap<String, MultiUserOutStringBase>();
	public static Map<String,MultiUserOutStringBase> tempstatic_outString_map = new HashMap<String, MultiUserOutStringBase>();
	//用于 web目录地址保存
	public static Map<String,ConfigObject> static_config_map = new HashMap<String,ConfigObject>();
	//public static String CurrentUser;
	//public static Ssh Currentssh;
	public static String httpPrefix = "http://";
	public static String httpHostname = "www.zuoer.xin";
	public static String httpPort = ":8080";
	public static String httpAddress = "/LinuxSRCAssistantService";
	public static String httpSuffix = ".zuoer";
	public static String hashUUID = "";
	public static String username = "";
	//2:授权失败  1:hostname或端口不可达 -1:账号密码错误
	public static int sshCode = -1;
	public static int SServiceTimeOut = 3000;   
	//每次等待返回结果时间
	public static int SSleepTime=50;
	//等待返回结果超时次数
	public static int SSleepTimesMaxNumber = 30;   
	//是否还有值等待时间
	public static int SHaveNewSleepTime = 200;   
	public static int SHaveNewPwdSleepTime = 200;  
	
	//动态显示
	public static int SDynamicDisplayNumber=120;
	public static int SDynamicDisplayTime=120;
	
	
}
