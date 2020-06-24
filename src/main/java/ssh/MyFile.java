package ssh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class MyFile {

	public static void writeFile(String fileName,String s) {
		// 文件写入操作

		FileOutputStream outputStream = null;
		try {
			File file = new File(fileName);
			file.createNewFile();//创建文件
			outputStream = new FileOutputStream(file);//形参里面可追加true参数，表示在原有文件末尾追加信息
			String data = s;
			outputStream.write(data.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static StringBuffer readFileByBytesNew(String fileName) throws IOException {
		
		String fileContent = null;
		InputStream is = MyFile.class.getResourceAsStream(fileName);
		try {
			 fileContent = IOUtils.toString(is);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("找不到指定的文件，请确认文件路径是否正确");
		}
		return new StringBuffer(fileContent);
	}

	public static StringBuffer readFileByBytesOld(String fileName) throws IOException {

		StringBuffer sb = new StringBuffer();
		try {
			List<String> contents = IOUtils.readLines(MyFile.class.getClassLoader().getResourceAsStream(fileName), Charset.forName("UTF-8"));
			for (int i = 0; i < contents.size(); i++) {
				sb.append(contents.get(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("找不到指定的文件，请确认文件路径是否正确");
		}
		return sb;
	}

	public static StringBuffer readFileByBytes(String fileName) throws IOException {
		File file = new File(fileName);
		InputStream in = null;
		StringBuffer sb = new StringBuffer();


		if (file.isFile() && file.exists()) { //判断文件是否存在
			//System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			// 一次读多个字节
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			in = new FileInputStream(file);
			//MyFile.showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				//  System.out.write(tempbytes, 0, byteread);
				String str = new String(tempbytes, 0, byteread);
				sb.append(str);
			}
		} else {
			System.out.println("找不到指定的文件，请确认文件路径是否正确");
		}
		return sb;
	}
	private static void showAvailableBytes(InputStream in) {

		try {
			System.out.println("当前字节输入流中的字节数为"+in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

