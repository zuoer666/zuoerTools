package ssh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


public class Main_Process {
	MultiUserBase multiUserBase;

	public Main_Process(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	public String process_execute(String Command,String flagString){

		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
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
						Thread.sleep(Config.SHaveNewSleepTime);
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

}
