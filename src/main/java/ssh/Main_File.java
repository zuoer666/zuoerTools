package ssh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


public class Main_File {
	MultiUserBase multiUserBase;
	String pwdString="";
	Main_File main_File;
	public Main_File(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	public String file_execute(String Command,String flagString){
		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
		int HaveNewSleepTime =Config.SHaveNewSleepTime;
		if (Command.equals("pwd")) {
			HaveNewSleepTime=Config.SHaveNewPwdSleepTime;
		}
		if (Command.split("\n")[0].equals("cd")) {
			multiUserBase.getSsh().Command(Command);
			return "";
		}
		//StaticFile.static_outString_map.get(multiUserBase.getHostName()).setFile_change_outString("");
		Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),flagString);
		String tempResultCompare = Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
		multiUserBase.getSsh().Command(Command);
		int sleepTimes=0;
		while (true) {
			try {
				Thread.sleep(Config.SSleepTime);
				sleepTimes++;
				if (sleepTimes>Config.SSleepTimesMaxNumber) {
					JOptionPane.showMessageDialog(null,"请求超时","温馨提示", JOptionPane.WARNING_MESSAGE);
					return "";
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String newtempResult=Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
			if (!tempResultCompare.equals(newtempResult)) {
				while (true) {
					try {
						Thread.sleep(HaveNewSleepTime);
						String newnewtempResult=Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
						if (newnewtempResult.equals(newtempResult)) {
							//获取返回值后，没有新的返回值。
							return Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
						}else {
							newtempResult=newnewtempResult;
							//获取返回值后，有新的返回值。
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}

	public String pwd() {
		return file_execute("pwd","once");
	}
	public String cd__(String s) {
		file_execute("cd "+s,"once");
		return cd2();
	}

	public String cd2() {
		String pwdString = pwd();
		if(pwdString.contains("\n")) {
			return pwdString.split("\n")[1];
		}
		String tempString=pwd();
		String[] tempStrings=tempString.split("\n");
		if (tempStrings.length>1) {
			if (tempStrings[1].substring(0, 1).equals("/")) {
				return tempStrings[1];
			}else {
				return cd2();
			}
			
		}else {
			return cd2();
		}

	}

}
