package ssh;

public class MyJson {
	
	public String stringarraytoJson(String[]  ss){
		String result="{";
		
		for (int i = 0; i < ss.length; i++) {
			result+='"';
			result+=i+1;
			result+='"';
			result+=':';
			result+='"';
			result+=ss[i];
			result+='"';
			result+=',';
		}
		result=result.substring(0, result.length()-1);
		result+="}";
		return result;
		
	}
	public String stringarraytoJson1(String[]  ss){
		String result="{";
		
		for (int i = 0; i < ss.length; i++) {
			result+='"';
			result+=ss[i];
			result+='"';
			result+=',';
		}
		result=result.substring(0, result.length()-1);
		result+="}";
		return result;
		
	}
	public String stringarraytoJson2(String[]  ss,int i_values){
		String result="";
		for (int i = i_values; i < ss.length; i++) {
			
			result+=ss[i]+"\n";
			
		}
		
		return result;
		
	}
	
	public String[] JsontoStringarray1(String  ss){
		String[] resultStrings =ss.split(",");
		resultStrings[0]=resultStrings[0].substring(2, resultStrings[0].length()-1);
		System.out.println(resultStrings[0]);
		for (int i = 1; i < resultStrings.length-1; i++) {
			resultStrings[i]=resultStrings[i].substring(1, resultStrings[i].length()-1);
		}
		resultStrings[resultStrings.length-1]=resultStrings[resultStrings.length-1].substring(1, resultStrings[resultStrings.length-1].length()-2);
		return resultStrings;
	}
	
//	public String jsonGetValue(String  ss,String key){
//		String[] resultStrings =ss.split(",");
//		String result="";
//		resultStrings[0]=resultStrings[0].substring(2, resultStrings[0].length()-1);
//		System.out.println(resultStrings[0]);
//		for (int i = 1; i < resultStrings.length-1; i++) {
//			resultStrings[i]=resultStrings[i].substring(1, resultStrings[i].length()-1);
//		}
//		resultStrings[resultStrings.length-1]=resultStrings[resultStrings.length-1].substring(1, resultStrings[resultStrings.length-1].length()-2);
//		return result;
//	}
}
