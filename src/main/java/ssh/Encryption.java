package ssh;

public class Encryption {
	public static String encryption(String encrypted) {
		char[] charEncrypted = encrypted.toCharArray();
		//使用for循环给字符数组加密
		for(int i=0;i<charEncrypted.length;i++){
			charEncrypted[i] = (char)(charEncrypted[i]^1821431);
		}
		return new String(charEncrypted);
	}
}
