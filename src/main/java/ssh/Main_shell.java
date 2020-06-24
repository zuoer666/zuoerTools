package ssh;

import javax.swing.JOptionPane;


public class Main_shell {

	MultiUserBase multiUserBase;

	public Main_shell(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}


	public void shell_execute(String Command,String flagString){

		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
		Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),flagString);
		String tempResultCompare = Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);

		if (Command.contains("nohup")) {
			Ssh ssh= new Ssh(multiUserBase.getHostName(),multiUserBase.getUsername(),multiUserBase.getPasswordString(),multiUserBase.getPort());
			ssh.ssh_login();
			ssh.Command(Command);
		}else {
			multiUserBase.getSsh().Command(Command);
		}
	}

	public void shell_execute(int Command,String flagString){
		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
		Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),flagString);
		String tempResultCompare = Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
		multiUserBase.getSsh().Command(Command);
	}
	
}
