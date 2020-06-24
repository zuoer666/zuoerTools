package ssh;

public class ConfigObject {
	private String webdir;
	private String other;
	
	public String getWebdir() {
		return webdir;
	}
	public void setWebdir(String webdir) {
		this.webdir = webdir;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	@Override
	public String toString() {
		return "ConfigObject [webdir=" + webdir + ", other=" + other + "]";
	}
	public ConfigObject(String webdir, String other) {
		super();
		this.webdir = webdir;
		this.other = other;
	}
	
	
}
