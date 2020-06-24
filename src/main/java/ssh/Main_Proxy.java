package ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;


public class Main_Proxy {
	MultiUserBase multiUserBase;

	public Main_Proxy(MultiUserBase multiUserBase) {
		super();
		this.multiUserBase = multiUserBase;
	}
	
	
	public boolean ProxyDetection(String portString){
		String hostName=multiUserBase.getHostName();
		int port=Integer.valueOf(portString);
		URL url = null;
		try {
			url = new URL("http://www.baidu.com");
		} catch (MalformedURLException e) {
			System.out.println("url invalidate");
		}
		InetSocketAddress addr = null;
		addr = new InetSocketAddress(hostName, port);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
		InputStream in = null;
		try {
			URLConnection conn = url.openConnection(proxy);
			conn.setConnectTimeout(2000);
			in = conn.getInputStream();
		} catch (Exception e) {
			System.out.println("hostName:port " + hostName+":"+port + " is not aviable");//异常IP
			return false;
		}
		String s = convertStreamToString(in);
		System.out.println(s);
		// System.out.println(s);
		if (s.indexOf("baidu") > 0) {//有效IP
			System.out.println(hostName + ":"+port+ " is ok");
			return true;
		}
		return false;
	}


	public static String convertStreamToString(InputStream is) {
		if (is == null)
			return "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
	
	public String proxy_execute(String Command,String flagString){
		if (flagString.equals("once")) {
			Config.static_outString_map.get(multiUserBase.getHostName()).setOnce_outString("");
		}
		Config.shellOutObject=new ShellOutObject(multiUserBase.getHostName(),flagString);
		
		String tempResultCompare = Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
		multiUserBase.getSsh().Command(Command);
		int sleepTimes=0;
		while (true) {
			try {
				Thread.sleep(Config.SSleepTime);
				sleepTimes++;
				if (sleepTimes>Config.SSleepTimesMaxNumber) {
					JOptionPane.showMessageDialog(null,"请求超时","温馨提示", JOptionPane.WARNING_MESSAGE);
					return "";
				}
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String newtempResult=Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
			if (!tempResultCompare.equals(newtempResult)) {
				while (true) {
					try {
						Thread.sleep(Config.SHaveNewSleepTime);
						String newnewtempResult=Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
						if (newnewtempResult.equals(newtempResult)) {
							//获取返回值后，没有新的返回值。
							return Config.static_outString_map.get(multiUserBase.getHostName()).getByString(flagString);
						}else {
							newtempResult=newnewtempResult;
							//获取返回值后，有新的返回值。
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
	}

}
