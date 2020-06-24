package ssh;

public class BaseRequestObject {
	private String username,hashUUID,hostname;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashUUID() {
		return hashUUID;
	}

	public void setHashUUID(String hashUUID) {
		this.hashUUID = hashUUID;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	@Override
	public String toString() {
		return "BaseRequestObject [username=" + username + ", hashUUID=" + hashUUID + ", hostname=" + hostname + "]";
	}

	public BaseRequestObject(String username, String hashUUID, String hostname) {
		super();
		this.username = username;
		this.hashUUID = hashUUID;
		this.hostname = hostname;
	}

	
	
}
