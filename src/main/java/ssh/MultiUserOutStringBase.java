package ssh;

public class MultiUserOutStringBase {
	public MultiUserOutStringBase() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MultiUserOutStringBase(String once_outString, String no_outString, String shell_outString,
			String log_outString, String file_change_outString, String file_outString, String virus_outString,
			String proxy_outString, String mining_outString, String process_outString) {
		super();
		this.once_outString = once_outString;
		this.no_outString = no_outString;
		this.shell_outString = shell_outString;
		this.log_outString = log_outString;
		this.file_change_outString = file_change_outString;
		this.file_outString = file_outString;
		this.virus_outString = virus_outString;
		this.proxy_outString = proxy_outString;
		this.mining_outString = mining_outString;
		this.process_outString = process_outString;
	}
	

	private String once_outString="";
	private String no_outString="";
	private String shell_outString="";
	private String log_outString="";
	private String file_change_outString="";
	private String file_outString="";
	private String virus_outString="";
	private String proxy_outString="";
	private String mining_outString="";
	private String process_outString="";
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String getByString(String string) {
		String rString="";
		switch (string) {
		case "shell":
			rString = shell_outString;
			break;
		case "log":
			rString = log_outString;
			break;
		case "once":
			rString = once_outString;
			break;
		case "file_change":
			rString = file_change_outString;
			break;
		case "file":
			rString = file_outString;
			break;
		case "virus":
			rString = virus_outString;
			break;
		case "proxy":
			rString = proxy_outString;
			break;
		case "mining":
			rString = mining_outString;
			break;
		case "process":
			rString = process_outString;
			break;
		case "no_outString":
			rString = no_outString;
			break;
		default:
			rString = once_outString;
			break;
		}
		return rString;

	}

	/**
	 * @param once_outString the once_outString to set
	 */
	public void setOnce_outString(String once_outString) {
		this.once_outString = once_outString;
	}

	/**
	 * @param no_outString the no_outString to set
	 */
	public void setNo_outString(String no_outString) {
		this.no_outString = no_outString;
	}

	/**
	 * @param shell_outString the shell_outString to set
	 */
	public void setShell_outString(String shell_outString) {
		this.shell_outString = shell_outString;
	}

	/**
	 * @param log_outString the log_outString to set
	 */
	public void setLog_outString(String log_outString) {
		this.log_outString = log_outString;
	}

	/**
	 * @param file_change_outString the file_change_outString to set
	 */
	public void setFile_change_outString(String file_change_outString) {
		this.file_change_outString = file_change_outString;
	}

	/**
	 * @param file_outString the file_outString to set
	 */
	public void setFile_outString(String file_outString) {
		this.file_outString = file_outString;
	}

	/**
	 * @param virus_outString the virus_outString to set
	 */
	public void setVirus_outString(String virus_outString) {
		this.virus_outString = virus_outString;
	}

	/**
	 * @param proxy_outString the proxy_outString to set
	 */
	public void setProxy_outString(String proxy_outString) {
		this.proxy_outString = proxy_outString;
	}

	/**
	 * @param minming_outString the minming_outString to set
	 */
	public void setMining_outString(String mining_outString) {
		this.mining_outString = mining_outString;
	}

	/**
	 * @param process_outString the process_outString to set
	 */
	public void setProcess_outString(String process_outString) {
		this.process_outString = process_outString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MultiUserOutStringBase [once_outString=" + once_outString + ", no_outString=" + no_outString
				+ ", shell_outString=" + shell_outString + ", log_outString=" + log_outString
				+ ", file_change_outString=" + file_change_outString + ", file_outString=" + file_outString
				+ ", virus_outString=" + virus_outString + ", proxy_outString=" + proxy_outString
				+ ", mining_outString=" + mining_outString + ", process_outString=" + process_outString + "]";
	}
	



}
