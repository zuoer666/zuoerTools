package ssh;



public class FileChangeObjectBase {
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		FileChangeObjectBase tempBase  = (FileChangeObjectBase) obj;
		if (hostname.equals(tempBase.getHostname())&&pwd_directory.equals(tempBase.getPwd_directory())&&have_directory.equals(tempBase.getHave_directory())&&file_name.equals(tempBase.getFile_name())&&file_time.equals(tempBase.getFile_time())) {
			return true;
		}
		return  false;
	}
	private String hostname,pwd_directory,have_directory,file_name,file_time;
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPwd_directory() {
		return pwd_directory;
	}
	public void setPwd_directory(String pwd_directory) {
		this.pwd_directory = pwd_directory;
	}
	public String getHave_directory() {
		return have_directory;
	}
	public void setHave_directory(String have_directory) {
		this.have_directory = have_directory;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_time() {
		return file_time;
	}
	public void setFile_time(String file_time) {
		this.file_time = file_time;
	}
	@Override
	public String toString() {
		return "FileChangeObjectBase [hostname=" + hostname + ", pwd_directory=" + pwd_directory + ", have_directory="
				+ have_directory + ", file_name=" + file_name + ", file_time=" + file_time + "]";
	}
	public FileChangeObjectBase(String hostname, String pwd_directory, String have_directory, String file_name,
			String file_time) {
		super();
		this.hostname = hostname;
		this.pwd_directory = pwd_directory;
		this.have_directory = have_directory;
		this.file_name = file_name;
		this.file_time = file_time;
	}
	
	
	

	
}
