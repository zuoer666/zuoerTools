package ssh;

public class ProxyDetectionObject {
	public ProxyDetectionObject(String hostname, boolean proxy) {
		super();
		this.hostname = hostname;
		this.proxy = proxy;
	}
	String hostname;
	boolean proxy;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProxyDetectionObject [hostname=" + hostname + ", proxy=" + proxy + "]";
	}
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
	 * @return the proxy
	 */
	public boolean isProxy() {
		return proxy;
	}
	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}
}
