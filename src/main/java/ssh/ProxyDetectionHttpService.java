package ssh;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ProxyDetectionHttpService {

	public List<ProxyDetectionObject>  ProxyDetection(List<ProxyDetectionRequestObject> proxyDetectionRequestObjects) {
		
		List<ProxyDetectionObject> listResult= new ArrayList<ProxyDetectionObject>();
		System.out.println(JSON.toJSONString(proxyDetectionRequestObjects));

		Base64.Encoder encoder = Base64.getEncoder();
		// 编码
		byte[] textByte = null;
		try {
			textByte = JSON.toJSONString(proxyDetectionRequestObjects).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);

		String url = Config.httpPrefix + Config.httpHostname + Config.httpPort + Config.httpAddress
				+ "/ProxyDetection" + Config.httpSuffix + "?ProxyDetectionJson=" + encodedText;
		JSONObject resultJson = MyHttpUtilBase64.requestByGetJson(url);
		if (resultJson == null) {
			return new ArrayList<ProxyDetectionObject>();
		}
			listResult = JSON.parseArray(resultJson.toJSONString(), ProxyDetectionObject.class);
		System.out.println("重置文件时间请求" + url);
		System.out.println("重置文件时间结果：" + listResult);
		
		return listResult;
	}
}
