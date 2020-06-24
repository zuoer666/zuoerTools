package ssh;

import java.util.ArrayList;
import java.util.List;

public class ProxyList {


	
	List<String> strings = new ArrayList<String>();
	public ProxyList() {
		strings.add("gost");
		strings.add("tinyproxy");
		strings.add("dante-server");
		strings.add("squid");
		//strings.add("");
		strings.add("ngrok");
	}
	
	
	/**
	 * @return the strings
	 */
	public List<String> getStrings() {
		return strings;
	}
	/**
	 * @param strings the strings to set
	 */
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}
}
