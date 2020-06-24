package ssh;

public class ProxyShowObject {
	public ProxyShowObject(String tcp_udp_type, String ip_port, String pID, String processName) {
		super();
		this.tcp_udp_type = tcp_udp_type;
		this.ip_port = ip_port;
		this.pID = pID;
		this.processName = processName;
	}
	//tcp_udp_type
	//ip_port
	//PID
	private String tcp_udp_type;
	private String ip_port;
	private String pID;
	private String processName;
	/**
	 * @return the tcp_udp_type
	 */
	public String getTcp_udp_type() {
		return tcp_udp_type;
	}
	/**
	 * @param tcp_udp_type the tcp_udp_type to set
	 */
	public void setTcp_udp_type(String tcp_udp_type) {
		this.tcp_udp_type = tcp_udp_type;
	}
	/**
	 * @return the ip_port
	 */
	public String getIp_port() {
		return ip_port;
	}
	/**
	 * @param ip_port the ip_port to set
	 */
	public void setIp_port(String ip_port) {
		this.ip_port = ip_port;
	}
	/**
	 * @return the pID
	 */
	public String getpID() {
		return pID;
	}
	/**
	 * @param pID the pID to set
	 */
	public void setpID(String pID) {
		this.pID = pID;
	}
	/**
	 * @return the processName
	 */
	public String getProcessName() {
		return processName;
	}
	/**
	 * @param processName the processName to set
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProxyShowObject [tcp_udp_type=" + tcp_udp_type + ", ip_port=" + ip_port + ", pID=" + pID
				+ ", processName=" + processName + "]";
	}
	
}
