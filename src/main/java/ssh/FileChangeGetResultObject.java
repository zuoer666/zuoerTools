package ssh;

import java.util.List;

public class FileChangeGetResultObject {
	int resultcode;
	List<FileChangeObjectBase> list;
	public int getResultcode() {
		return resultcode;
	}
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}
	public List<FileChangeObjectBase> getList() {
		return list;
	}
	public void setList(List<FileChangeObjectBase> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "FileChangeGetResultObject [resultcode=" + resultcode + ", list=" + list + "]";
	}
	public FileChangeGetResultObject(int resultcode, List<FileChangeObjectBase> list) {
		super();
		this.resultcode = resultcode;
		this.list = list;
	}
	
}
