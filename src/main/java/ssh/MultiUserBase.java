package ssh;

public class MultiUserBase {
	public MultiUserBase(String hostname, String username, String passwordString, String port, Ssh ssh) {
		super();
		this.hostname = hostname;
		this.username = username;
		this.passwordString = passwordString;
		this.port = port;
		this.ssh = ssh;
	}

	private String hostname,username,passwordString,port;
	private Ssh ssh;
	
	
	
	
	
	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the passwordString
	 */
	public String getPasswordString() {
		return passwordString;
	}

	/**
	 * @param passwordString the passwordString to set
	 */
	public void setPasswordString(String passwordString) {
		this.passwordString = passwordString;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	public Ssh getSsh() {
		return ssh;
	}

	public void setSsh(Ssh ssh) {
		this.ssh = ssh;
	}

	public String getHostName() {
		return hostname;
	}

	public void setHostName(String hostname) {
		this.hostname = hostname;
	}

	public MultiUserBase(String hostname, Ssh ssh) {
		super();
		this.hostname = hostname;
		this.ssh = ssh;
	}

	@Override
	public String toString() {
		return "MultiUserBase [hostname=" + hostname + ", ssh=" + ssh + "]";
	}
	
	
}
