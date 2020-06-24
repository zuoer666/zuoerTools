package ssh;

public class HostNameObject {
	
	private String hostname;
	private String hostuser;
	private String hostpassword;
	private String hostport;
	private String flag;
	
	public HostNameObject(String hostname, String hostuser, String hostpassword, String hostport, String flag) {
		super();
		this.hostname = hostname;
		this.hostuser = hostuser;
		this.hostpassword = hostpassword;
		this.hostport = hostport;
		this.flag = flag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHostuser() {
		return hostuser;
	}
	public void setHostuser(String hostuser) {
		this.hostuser = hostuser;
	}
	public String getHostpassword() {
		return hostpassword;
	}
	public void setHostpassword(String hostpassword) {
		this.hostpassword = hostpassword;
	}
	public String getHostport() {
		return hostport;
	}
	public void setHostport(String hostport) {
		this.hostport = hostport;
	}
	
}
