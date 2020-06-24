package ssh;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class SetFileChangeHttpService {

	public int SetFileChangeHttp(MultiUserBase multiUserBase,List<FileChangeObjectBase> fileChangeObjectBases) {
		/*
		 * {"resultList":["success","success"],"resultcode":-1} 
		0:uuid失败
		1:成功
		2:有一个或者多个设置不成功
		3:网络错误

		 */
		BaseRequestObject baseRequestObject = new BaseRequestObject(Config.username, Config.hashUUID, multiUserBase.getHostName());

		FileChangeObject fileChangeObject = new FileChangeObject(baseRequestObject,fileChangeObjectBases);
		System.out.println(JSON.toJSONString(fileChangeObject));

		Base64.Encoder encoder = Base64.getEncoder();
		//编码
		byte[] textByte = null;
		try {
			textByte = JSON.toJSONString(fileChangeObject).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);

//		String url=Config.httpPrefix+Config.httpHostname
//				+Config.httpPort+Config.httpAddress+"/SetFileChange"
//				+Config.httpSuffix+"?filechangejson="+encodedText;
		//JSONObject resultJson=MyHttpUtilBase64.requestByGetJson(url);
		String url=Config.httpPrefix+Config.httpHostname
				+Config.httpPort+Config.httpAddress+"/SetFileChange"
				+Config.httpSuffix;
		JSONObject resultJson=MyHttpUtilBase64.requestByPostJson(url, encodedText);
		if (resultJson==null) {
			return 3;
		}
		FileChangeSetResultObject fileChangeResultObject =  resultJson.toJavaObject(FileChangeSetResultObject.class);
		System.out.println("重置文件时间请求"+url);
		System.out.println("重置文件时间结果："+fileChangeResultObject);
		if (fileChangeResultObject.getResultcode()==-1) {
			//成功 
			boolean flag = true;
			for (int i = 0; i < fileChangeResultObject.getResultList().size(); i++) {
				if(fileChangeResultObject.getResultList().get(i).equals("failed")){
					flag=false;
				}
			}
			if (flag) {
				return 1;
			}else{
				return 2;
			}
		}else {
			return 0;
		}



	}
}
