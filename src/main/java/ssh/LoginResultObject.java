package ssh;

public class LoginResultObject {
	private int code=0;
	private String hashUUid="";
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getHashUUid() {
		return hashUUid;
	}
	public void setHashUUid(String hashUUid) {
		this.hashUUid = hashUUid;
	}
	public LoginResultObject(int code, String hashUUid) {
		super();
		this.code = code;
		this.hashUUid = hashUUid;
	}
			
}
