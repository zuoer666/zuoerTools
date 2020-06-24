package ssh;

import java.util.ArrayList;

public class MyArrayList {
	public String[] toStringArray(ArrayList<String> arrayList) {

		String [] resiltStrings= new String[arrayList.size()];
		for (int i = 0; i < resiltStrings.length; i++) {
			resiltStrings[i]=arrayList.get(i);
		}
		return resiltStrings;
	}
	
	public ArrayList<String> fromStringarray(String[] strings) {

		ArrayList<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < strings.length; i++) {
			arrayList.add(strings[i]);
		}
		return arrayList;
	}
	
	



}
