package ssh;

import javax.swing.JOptionPane;

public class Cat {

	MultiUserBase multiUserBase;
	
	public Cat(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	public String cat_execute(String Command,String flagString){
		
		Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),"once");
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
