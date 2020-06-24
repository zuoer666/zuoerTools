package ssh;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class GetFileChangeHttpService {

	public FileChangeGetResultObject GetFileChange(MultiUserBase multiUserBase) {
		/*
		 * 获取文件时间
		 *  0:uuid失败
			1:成功
			2:有一个或者多个设置不成功
			3:网络错误
		 * */
		BaseRequestObject baseRequestObject = new BaseRequestObject(Config.username, Config.hashUUID, multiUserBase.getHostName());
		System.out.println(JSON.toJSONString(baseRequestObject));

		Base64.Encoder encoder = Base64.getEncoder();
		//编码
		byte[] textByte = null;
		try {
			textByte = JSON.toJSONString(baseRequestObject).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);

		String url=Config.httpPrefix+Config.httpHostname
				+Config.httpPort+Config.httpAddress+"/GetFileChange"
				+Config.httpSuffix+"?getFileChangeJson="+encodedText;
		String resultString=MyHttpUtilBase64.requestByGet(url);
		FileChangeGetResultObject  fileChangegetResultObject = new FileChangeGetResultObject(-2, new ArrayList<FileChangeObjectBase>());
		if (resultString!=null) {
			fileChangegetResultObject =  JSON.parseObject(resultString, FileChangeGetResultObject.class);
			
		}
		System.out.println("请求文件时间请求"+url);
		System.out.println("请求文件重置时间结果："+resultString);
		
		return fileChangegetResultObject;

	}
}
