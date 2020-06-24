package ssh;

public class ShellOutObject {
	private String username;
	private String outType;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public ShellOutObject(String username, String outType) {
		super();
		this.username = username;
		this.outType = outType;
	}
	
}
