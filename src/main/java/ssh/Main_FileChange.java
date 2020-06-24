package ssh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


public class Main_FileChange {
	MultiUserBase multiUserBase;

	public Main_FileChange(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	public String file_change_execute(String Command,String flagString){
		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
		Config.static_outString_map.get(multiUserBase.getHostName()).setFile_change_outString("");
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
	
	 public  List<FileChangeObjectBase> getSame(List<FileChangeObjectBase> list1, List<FileChangeObjectBase> list2) {  
	        List<FileChangeObjectBase> same = new ArrayList<FileChangeObjectBase>();  
	        for (FileChangeObjectBase fileChangeObjectBase : list1) {  
		            if (isExits(fileChangeObjectBase,list2)) {  
		            	same.add(fileChangeObjectBase);  
		            }  
	        }  
	        return same;  
	    }  
	 
	 public  List<FileChangeObjectBase> getsamename(List<FileChangeObjectBase> list1, List<FileChangeObjectBase> list2) {  
	        List<FileChangeObjectBase> same = new ArrayList<FileChangeObjectBase>();  
	        for (FileChangeObjectBase fileChangeObjectBase : list1) {  
	        	for (FileChangeObjectBase fileChangeObjectBase2 : list2) {  
		            if (fileChangeObjectBase.getFile_name().equals(fileChangeObjectBase2.getFile_name())&&fileChangeObjectBase.getPwd_directory().equals(fileChangeObjectBase2.getPwd_directory())) {
						same.add(fileChangeObjectBase);
					}
	        }  
	        }  
	        return same;  
	    }  
	 
	public boolean isExits(FileChangeObjectBase fileChangeObjectBase,List<FileChangeObjectBase> same) {
		/*
		 *  ture : 存在
		 *  false ： 不存在
		 * */
		boolean ret = false;
		
		for (FileChangeObjectBase fileChangeObjectBase2 : same) {
			if (fileChangeObjectBase2.equals(fileChangeObjectBase)) {
				ret = true;	
			}	
		}
		
		return ret;
		
		
	}
	public boolean isExitsByName(FileChangeObjectBase fileChangeObjectBase,List<FileChangeObjectBase> same) {
		/*
		 *  ture : 存在
		 *  false ： 不存在
		 * */
		boolean ret = false;
		
		for (FileChangeObjectBase fileChangeObjectBase2 : same) {
			if (fileChangeObjectBase.getFile_name().equals(fileChangeObjectBase2.getFile_name())&&fileChangeObjectBase.getPwd_directory().equals(fileChangeObjectBase2.getPwd_directory())) {
				ret = true;	
			}	
		}
		
		return ret;
		
		
	}
	public List<FileChangeObjectBase> delSame(List<FileChangeObjectBase> list,List<FileChangeObjectBase> same)  {
		List<FileChangeObjectBase> result = new ArrayList<FileChangeObjectBase>();
		
		for (FileChangeObjectBase temp : list) {
				if (!isExits(temp,same)) {
					result.add(temp);
				}
		}
		
		return result;
		
	}
	
	public List<FileChangeObjectBase> delSameByName(List<FileChangeObjectBase> list,List<FileChangeObjectBase> same)  {
		List<FileChangeObjectBase> result = new ArrayList<FileChangeObjectBase>();
		
		for (FileChangeObjectBase temp : list) {
				if (!isExitsByName(temp,same)) {
					result.add(temp);
				}
		}
		
		return result;
		
	}
	

}
