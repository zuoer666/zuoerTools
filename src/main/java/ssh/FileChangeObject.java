package ssh;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"baseRequestObject","fileChangeObjectBaseList"})
public class FileChangeObject {
	
	//@JSONField(ordinal = 0)
	private BaseRequestObject baseRequestObject;
	//@JSONField(ordinal = 1)
	private List<FileChangeObjectBase> fileChangeObjectBaseList;
	

	public BaseRequestObject getBaseRequestObject() {
		return baseRequestObject;
	}
	public void setBaseRequestObject(BaseRequestObject baseRequestObject) {
		this.baseRequestObject = baseRequestObject;
	}
	public List<FileChangeObjectBase> getFileChangeObjectBaseList() {
		return fileChangeObjectBaseList;
	}
	public void setFileChangeObjectBaseList(List<FileChangeObjectBase> fileChangeObjectBaseList) {
		this.fileChangeObjectBaseList = fileChangeObjectBaseList;
	}
	
	@Override
	public String toString() {
		return "FileChangeObject [baseRequestObject=" + baseRequestObject + ", fileChangeObjectBaseList="
				+ fileChangeObjectBaseList + "]";
	}
	public FileChangeObject(BaseRequestObject baseRequestObject,List<FileChangeObjectBase> fileChangeObjectBaseList) {
		super();
		this.baseRequestObject = baseRequestObject;
		this.fileChangeObjectBaseList = fileChangeObjectBaseList;
	}
	
	
	
}
