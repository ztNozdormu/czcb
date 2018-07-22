package cn.com.czcb.pub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	private static HttpClient client = HttpClients.custom().build();
	
	public static String doGet(String url, Map<String, Object> params) throws ClientProtocolException, IOException {
		StringBuffer urlSB = appendGetParams(url, params);
		HttpGet get = new HttpGet(urlSB.toString());
		HttpResponse response = client.execute(get);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		ObjectUtils.logInfo(result);
		return result;
	}

	public static String doPost(String url, Map<String, String> params, Map<String, String> headers)
			throws ClientProtocolException, IOException {

		HttpPost post = new HttpPost(url);
		if (!headers.isEmpty()) {
			for (Entry<String, String> header : headers.entrySet()) {
				post.addHeader(header.getKey(), header.getValue());
			}
		}
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			paramPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
		post.setEntity(formEntity);
		HttpResponse response = client.execute(post);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(result);
		return result;
	}

	/**
	 * 
	 * 描述：http post请求 (String url, Map<String, String> params)
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author fengshengliang 2017年3月21日 下午6:37:01
	 * @version 1.0
	 */
	public static String doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			paramPairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
		post.setEntity(formEntity);
		HttpResponse response = HttpClients.custom().build().execute(post);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		return result;
	}
	
    public static String doPost(String url,String params) throws ClientProtocolException, IOException{
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(params, "UTF-8"));
		HttpResponse response = client.execute(post);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(result);
		return result;
	}
	/**
	 * 将请求参数拼接到地址上
	 * @param url 请求路径
	 * @param params 参数
	 * @return
	 */
	private static StringBuffer appendGetParams(String url, Map<String, Object> params) {
		StringBuffer urlSB = new StringBuffer(url);
		if(params==null||params.isEmpty()){
			return urlSB;
		}
		if(url.contains("?")){
			urlSB.append("&");
		}else{
			urlSB.append("?");
		}
		for(Entry<String,Object> param:params.entrySet()){
			urlSB.append(param.getKey());
			urlSB.append("=");
			urlSB.append(param.getValue());
			urlSB.append("&");
		}
		urlSB.deleteCharAt(urlSB.length()-1);
		return urlSB;
	}
}
