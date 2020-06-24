package ssh;

public class FileShowObject {
	/*
	 * 权限 文件名 用户 组 硬连接数 大小 时间
	 * */
	
	String file_permission;
	String file_name;
	String file_user;
	String file_group;
	String file_count;
	String size;
	String time;
	boolean is_dir;
	public String getFile_permission() {
		return file_permission;
	}
	public void setFile_permission(String file_permission) {
		this.file_permission = file_permission;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_user() {
		return file_user;
	}
	public void setFile_user(String file_user) {
		this.file_user = file_user;
	}
	public String getFile_group() {
		return file_group;
	}
	public void setFile_group(String file_group) {
		this.file_group = file_group;
	}
	public String getFile_count() {
		return file_count;
	}
	public void setFile_count(String file_count) {
		this.file_count = file_count;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isIs_dir() {
		return is_dir;
	}
	public void setIs_dir(boolean is_dir) {
		this.is_dir = is_dir;
	}
	@Override
	public String toString() {
		return "FileShowObject [file_permission=" + file_permission + ", file_name=" + file_name + ", file_user="
				+ file_user + ", file_group=" + file_group + ", file_count=" + file_count + ", size=" + size + ", time="
				+ time + ", is_dir=" + is_dir + "]";
	}
	public FileShowObject(String file_permission, String file_name, String file_user, String file_group, String file_count,
			String size, String time, boolean is_dir) {
		super();
		this.file_permission = file_permission;
		this.file_name = file_name;
		this.file_user = file_user;
		this.file_group = file_group;
		this.file_count = file_count;
		this.size = size;
		this.time = time;
		this.is_dir = is_dir;
	}
	
	
	
	
}
