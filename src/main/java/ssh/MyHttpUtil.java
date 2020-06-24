package ssh;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;



public class MyHttpUtil {

	
	//  private static final String DOMAIN = GGConfigurer.get("api.domain");
	/**
	 * 
	 * 方法描述:POST请求用户API
	 *
	 * @param url
	 * @param jsonParam
	 * @return
	 * 
	 * @date 2018年5月28日 下午1:37:11
	 */
	public static JSONObject requestByPostJson(String url, JSONObject jsonParam) {  
		// post请求返回结果  
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		JSONObject jsonResult = null;  
		HttpPost httpPost = new HttpPost(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		httpPost.setConfig(requestConfig);  
		try {  
			if (null != jsonParam) {  
				// 解决中文乱码问题  
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");  
				entity.setContentEncoding("UTF-8");  
				entity.setContentType("application/json");  
				httpPost.setEntity(entity);  
			}  
			CloseableHttpResponse result = httpClient.execute(httpPost);  
			//请求发送成功，并得到响应  
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				try {  
					//读取服务器返回过来的json字符串数据   
					String str = EntityUtils.toString(result.getEntity(), "utf-8");  
					//把json字符串转换成json对象   
					jsonResult = JSONObject.parseObject(str);  
				} catch (Exception e) {  
					System.out.println("post请求提交失败:" + url);
				}  
			}  
		} catch (IOException e) {  
			System.out.println("post请求提交失败:" + url);
		} finally {  
			httpPost.releaseConnection();  
		}  
		return jsonResult;  
	}  
	
	public static JSONObject requestByPostJson(String url, String date) {  
		// post请求返回结果  
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		JSONObject jsonResult = null;  
		HttpPost httpPost = new HttpPost(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		httpPost.setConfig(requestConfig);  
		try {  
			if (null != date) {  
				// 解决中文乱码问题  
				StringEntity entity = new StringEntity(date, "utf-8");  
				entity.setContentEncoding("UTF-8");  
				entity.setContentType("application/json");  
				httpPost.setEntity(entity);  
			}  
			CloseableHttpResponse result = httpClient.execute(httpPost);  
			//请求发送成功，并得到响应  
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				try {  
					//读取服务器返回过来的json字符串数据   
					String str = EntityUtils.toString(result.getEntity(), "utf-8");  
					//把json字符串转换成json对象   
					jsonResult = JSONObject.parseObject(str);  
				} catch (Exception e) {  
					System.out.println("post请求提交失败:" + url);
				}  
			}  
		} catch (IOException e) {  
			System.out.println("post请求提交失败:" + url);
		} finally {  
			httpPost.releaseConnection();  
		}  
		return jsonResult;  
	}  
	
	public static String requestByPost(String url, JSONObject jsonParam) {  
		// post请求返回结果  
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		String str="";
		HttpPost httpPost = new HttpPost(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		httpPost.setConfig(requestConfig);  
		try {  
			if (null != jsonParam) {  
				// 解决中文乱码问题  
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");  
				entity.setContentEncoding("UTF-8");  
				entity.setContentType("application/json");  
				httpPost.setEntity(entity);  
			}  
			CloseableHttpResponse result = httpClient.execute(httpPost);  
			//请求发送成功，并得到响应  
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				try {  
					//读取服务器返回过来的json字符串数据   
					 str = EntityUtils.toString(result.getEntity(), "utf-8");  
					
				} catch (Exception e) {  
					System.out.println("post请求提交失败:" + url);
				}  
			}  
		} catch (IOException e) {  
			System.out.println("post请求提交失败:" + url);
		} finally {  
			httpPost.releaseConnection();  
		}  
		return str;  
	}  
	public static String requestByPost(String url, String date) {  
		// post请求返回结果  
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		String str=""; 
		HttpPost httpPost = new HttpPost(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		httpPost.setConfig(requestConfig);  
		try {  
			if (null != date) {  
				// 解决中文乱码问题  
				StringEntity entity = new StringEntity(date, "utf-8");  
				entity.setContentEncoding("UTF-8");  
				entity.setContentType("application/json");  
				httpPost.setEntity(entity);  
			}  
			CloseableHttpResponse result = httpClient.execute(httpPost);  
			//请求发送成功，并得到响应  
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				try {  
					//读取服务器返回过来的json字符串数据   
					 str = EntityUtils.toString(result.getEntity(), "utf-8");  
					
				} catch (Exception e) {  
					System.out.println("post请求提交失败:" + url);
				}  
			}  
		} catch (IOException e) {  
			System.out.println("post请求提交失败:" + url);
		} finally {  
			httpPost.releaseConnection();  
		}  
		return str;  
	}  
	
	
	/**
	 * 
	 * 方法描述 GET请求用户API
	 *
	 * @param url
	 * @return
	 * 
	 * @date 2018年5月28日 下午1:37:59
	 */
	public static JSONObject requestByGetJson(String url) {  
		// get请求返回结果  
		JSONObject jsonResult = null;  
		CloseableHttpClient client = HttpClients.createDefault();  
		// 发送get请求  
		HttpGet request = new HttpGet(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		request.setConfig(requestConfig);  
		try {  
			CloseableHttpResponse response = client.execute(request);  
			//请求发送成功，并得到响应  
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				//读取服务器返回过来的json字符串数据  
				HttpEntity entity = response.getEntity();  
				String strResult = EntityUtils.toString(entity, "utf-8");  
				//把json字符串转换成json对象  
				jsonResult = JSONObject.parseObject(strResult);  
			} else {  
				System.out.println("GET请求提交失败:" + url); 
			}  
		} catch (IOException e) {  
			System.out.println("GET请求提交失败:" + url);
		} finally {  
			request.releaseConnection();  
		}  
		return jsonResult ;  
	}  
	/**
	 * 
	 * 方法描述 GET请求用户API
	 *
	 * @param url
	 * @return
	 * 
	 * @date 2018年5月28日 下午1:37:59
	 */
	public static String requestByGet(String url) {  
		// get请求返回结果 
		String strResult="";

		CloseableHttpClient client = HttpClients.createDefault();  
		// 发送get请求  
		HttpGet request = new HttpGet(url);  
		// 设置请求和传输超时时间  
		RequestConfig requestConfig = RequestConfig.custom()  
				.setSocketTimeout(2000).setConnectTimeout(2000).build();  
		request.setConfig(requestConfig);  
		try {  
			CloseableHttpResponse response = client.execute(request);  
			//请求发送成功，并得到响应  
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
				//读取服务器返回过来的json字符串数据  
				HttpEntity entity = response.getEntity();  
				strResult = EntityUtils.toString(entity, "utf-8");  

			} else {  
				System.out.println("GET请求提交失败:" + url); 
			}  
		} catch (IOException e) {  
			System.out.println("GET请求提交失败:" + url);
		} finally {  
			request.releaseConnection();  
		}  
		return strResult ;  
	}  
}
