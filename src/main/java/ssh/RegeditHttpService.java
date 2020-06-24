package ssh;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
public class RegeditHttpService {
	
	public int httpRegedit(String username,String password) {
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
				+Config.httpPort+Config.httpAddress+"/Register"
				+Config.httpSuffix+"?registerJson="+encodedText;
		String result=MyHttpUtilBase64.requestByGet(url);
		System.out.println(url);
		System.out.println(result);
		if (result.equals("success")) {
			return 1;
		}else if (result.equals("failed")) {
			return 0;
		}else{
			return 2;
		}

	}
}
