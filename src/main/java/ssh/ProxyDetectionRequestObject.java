package ssh;

import java.util.List;

public class ProxyDetectionRequestObject {
	public ProxyDetectionRequestObject(String hostname, List<String> port) {
		super();
		this.hostname = hostname;
		this.port = port;
	}
	private String hostname;
	private List<String> port;
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
	 * @return the port
	 */
	public List<String> getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(List<String> port) {
		this.port = port;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProxyDetectionObject [hostname=" + hostname + ", port=" + port + "]";
	}
	
}
