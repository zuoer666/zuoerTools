package ssh;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.alibaba.fastjson.JSONObject;
public class LoginHttpService {

	public int httpLogin(String username,String password) {
		/*
		0:失败
		1:成功
		2:网络错误
		 */
		String date="{'username':'"+username+"','password':'"+password+"'}";
		Base64.Encoder encoder = Base64.getEncoder();
		//编码
		byte[] textByte = null;
		try {
			textByte = date.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String encodedText = encoder.encodeToString(textByte);

		String url=Config.httpPrefix+Config.httpHostname
				+Config.httpPort+Config.httpAddress+"/Login"
				+Config.httpSuffix+"?loginJson="+encodedText;
		JSONObject resultJson=MyHttpUtilBase64.requestByGetJson(url);
		if (resultJson==null) {
			return 3;
		}
		LoginResultObject loginResultObject = resultJson.toJavaObject(LoginResultObject.class);
		Config.hashUUID=loginResultObject.getHashUUid();
		Config.username=username;
		System.out.println(url);
		System.out.println(loginResultObject.getCode());
		System.out.println(Config.hashUUID);

		if (loginResultObject.getCode()==1) {
			return 1;
		}else {
			return 2;
		}

	}
}
