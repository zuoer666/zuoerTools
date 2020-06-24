package ssh;

import java.util.List;

public class FileChangeSetResultObject {
	/*
	 *  resultcode
	 * -2 UUID 不正确
	 * -1 UUID 正确
	 * 
	*/
	int resultcode;
	List<String> resultList;
	public int getResultcode() {
		return resultcode;
	}
	public void setResultcode(int resultcode) {
		this.resultcode = resultcode;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	@Override
	public String toString() {
		return "FileChangeResultObject [resultcode=" + resultcode + ", resultList=" + resultList + "]";
	}
	public FileChangeSetResultObject(int resultcode, List<String> resultList) {
		super();
		this.resultcode = resultcode;
		this.resultList = resultList;
	}
	
}
