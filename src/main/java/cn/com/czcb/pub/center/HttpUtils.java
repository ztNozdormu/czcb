package cn.com.czcb.pub.center;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;

import com.alibaba.fastjson.JSON;


public class HttpUtils {
	// utf-8字符编码
	public static final String CHARSET_UTF_8 = "utf-8";

	// HTTP内容类型。
	public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

	// HTTP内容类型。相当于form表单的形式，提交数据
	public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
	
	private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
	// 连接管理器
	private static PoolingHttpClientConnectionManager pool;

	// 请求配置
	private static RequestConfig requestConfig;
	
	//TODO 加密密钥需要修改
	private static String ENCODE_PUBLIC_KEY = AppConstants.ENCODE_PUBLIC_KEY;

	static {

		try {
			
			
			
			// System.out.println("初始化HttpClientTest~~~开始");
			SSLContextBuilder builder = new SSLContextBuilder();
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
			// 配置同时支持 HTTP 和 HTPPS
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
			// 初始化连接管理器
			pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			// 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
			pool.setMaxTotal(200);
			// 设置最大路由
			pool.setDefaultMaxPerRoute(2);
			// 根据默认超时限制初始化requestConfig
			int socketTimeout = 10000;
			int connectTimeout = 10000;
			int connectionRequestTimeout = 10000;
			requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
					.setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

			// System.out.println("初始化HttpClientTest~~~结束");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		// 设置请求超时时间
		requestConfig = RequestConfig.custom().setSocketTimeout(50000).setConnectTimeout(50000)
				.setConnectionRequestTimeout(50000).build();
	}

	public static CloseableHttpClient getHttpClient() {

		CloseableHttpClient httpClient = HttpClients.custom()
				// 设置连接池管理
				.setConnectionManager(pool)
				// 设置请求配置
				.setDefaultRequestConfig(requestConfig)
				// 设置重试次数
				 .setRetryHandler((exception, executionCount, context) -> {
				        if (executionCount > 3) {				            
				            return false;
				        }
				        if (exception instanceof NoHttpResponseException     //NoHttpResponseException 重试
				                || exception instanceof ConnectTimeoutException //连接超时重试
//				              || exception instanceof SocketTimeoutException    //响应超时不重试，避免造成业务数据不一致
				                ) {
				            return true;
				        }
				        return false;
				    }).build();

		return httpClient;
	}

	/**
	 * 发送Post请求
	 * 
	 * @param httpPost
	 * @return
	 */
	private static String sendHttpPost(HttpPost httpPost) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		String responseContent = null;
		String resultInfo = null;
		Long time = System.currentTimeMillis();
		try {
			// 创建默认的httpClient实例.
			httpClient = getHttpClient();
			addPostHeadler(httpPost, time);
			// 配置请求信息
			httpPost.setConfig(requestConfig);
			// 得到响应
			response = httpClient.execute(httpPost);
			// 得到响应实例
			HttpEntity entity = response.getEntity();
			//响应token密文
			String token = response.getFirstHeader(AppConstants.AUTH_KEY).getValue();
			//响应时间戳密文
			String timeRes = response.getFirstHeader(AppConstants.TIME_KEY).getValue();
			//解密token值
			String tokenInfo = new String((RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64.decode(token))));
			//解密时间
			String timeResInfo = new String((RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64.decode(timeRes))));
			//规则校验
			if (tokenInfo.trim().equals(AppConstants.UKER_KEY+timeResInfo+AppConstants.TOKEN_PWD)) {
			InputStream is = entity.getContent();
			// 读取输入流，即返回文本内容
			StringBuffer sbResult = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				sbResult.append(line);
			}
			//响应明文
			resultInfo = new String((RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64.decode(sbResult.toString()))));
			System.out.println(resultInfo);
			// 判断响应状态
			if (response.getStatusLine().getStatusCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ response.getStatusLine().getStatusCode());
			}
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				responseContent = resultInfo;
			}
			}else {
				throw new Exception("验证失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 添加http请求headler
	 * @param httpPost
	 * @param time
	 * @throws Exception
	 */
	public static void addPostHeadler(HttpPost httpPost, Long time)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(AppConstants.UKER_KEY).append(time).append(AppConstants.TOKEN_PWD);
		httpPost.addHeader(AppConstants.AUTH_KEY, 
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), (sb.toString()).getBytes()))));
		httpPost.addHeader(AppConstants.USER_KEY, 
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY),(AppConstants.UKER_KEY).getBytes()))));
		httpPost.addHeader(AppConstants.TIME_KEY,
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY),(String.valueOf(time)).getBytes()))));
	}

	/**
	 * 发送Get请求
	 * 
	 * @param httpGet
	 * @return
	 */
	private static String sendHttpGet(HttpGet httpGet) {

		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		// 响应内容
		String responseContent = null;
		String resultInfo = "";
		Long time = System.currentTimeMillis();
		try {
			// 创建默认的httpClient实例.
			httpClient = getHttpClient();
			// 配置请求信息
			httpGet.setConfig(requestConfig);
			// 执行请求
			addHeadler(httpGet, time);
			response = httpClient.execute(httpGet);
			// 得到响应实例
			HttpEntity entity = response.getEntity();
			// 将H中返回实体转化为输入流
			InputStream is = entity.getContent();
			// 读取输入流，即返回文本内容
			StringBuffer sbResult = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				sbResult.append(line);
			}
			resultInfo = new String((RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64.decode(sbResult.toString()))));
			System.out.println(resultInfo);
			// 可以获得响应头
			String token = response.getFirstHeader(AppConstants.AUTH_KEY)
					.getValue();
			String timeRes = response.getFirstHeader(AppConstants.TIME_KEY)
					.getValue();
			String tokenInfo = new String((RSAEncrypt.decrypt(RSAEncrypt
					.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64.decode(token))));
			String timeResInfo = new String(
					(RSAEncrypt.decrypt(RSAEncrypt
							.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), Base64
							.decode(timeRes))));
			if (tokenInfo.trim().equals(
					AppConstants.UKER_KEY + timeResInfo
							+ AppConstants.TOKEN_PWD)) {
				// 得到响应类型
				// System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());
				// 判断响应状态
				if (response.getStatusLine().getStatusCode() >= 300) {
					throw new Exception(
							"HTTP Request is not success, Response code is "
									+ response.getStatusLine().getStatusCode());
				}
				if (HttpStatus.SC_OK == response.getStatusLine()
						.getStatusCode()) {
					responseContent = resultInfo;
				}
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
	}

	/**
	 * 设置http请求headler
	 * @param httpGet
	 * @param time
	 * @throws Exception
	 */
	public static void addHeadler(HttpGet httpGet, Long time) throws Exception {
		StringBuffer sb = new StringBuffer();
		//将请求参数按约定顺序拼接，并加密
		sb.append(AppConstants.UKER_KEY).append(time).append(AppConstants.TOKEN_PWD);
		httpGet.addHeader(AppConstants.AUTH_KEY, 
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), (sb.toString()).getBytes()))));
		httpGet.addHeader(AppConstants.USER_KEY, 
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY),(AppConstants.UKER_KEY).getBytes()))));
		httpGet.addHeader(AppConstants.TIME_KEY,
		Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY),(String.valueOf(time)).getBytes()))));
	}
	
	/**
	 * 发送 post请求
	 * 
	 * @param httpUrl
	 *            地址
	 */
	public static String sendHttpPost(String httpUrl) {
		// 创建httpPost
		HttpPost httpPost = new HttpPost(httpUrl);
		return sendHttpPost(httpPost);
	}

	/**
	 * 发送 get请求
	 * 
	 * @param httpUrl
	 * @throws Exception 
	 */
	public static String sendHttpGet(String httpUrl,Map<String, String> map) throws Exception {
		String newHttpUrl = httpUrl+"?";
		String sign = "";
		try {
			if(map!=null){
				//循环map，并对value加密、拼接
				for(String key : map.keySet()){
					if(map.size()>1){
					   sign = Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), (map.get(key)).getBytes())));
				       newHttpUrl+= key+"="+sign+"&";
					}else {
					   sign = Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), (map.get(key)).getBytes())));
					   newHttpUrl+= key+"="+sign;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newHttpUrl = newHttpUrl.substring(0, newHttpUrl.length()-1);
		HttpGet httpGet = new HttpGet(newHttpUrl);
		return sendHttpGet(httpGet);
	}

	/**
	 * 发送 post请求 发送json数据
	 * 
	 * @param httpUrl
	 *            地址
	 * @param paramsJson
	 *            参数(格式 json)
	 * 
	 */
	public static String sendHttpPostJson(String httpUrl, String paramsJson) {
		HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
		try {
			// 设置参数
			if (paramsJson != null && paramsJson.trim().length() > 0) {
				//对参数进行RSA加密，并传递
				String str = Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(ENCODE_PUBLIC_KEY), paramsJson.getBytes("utf-8"))));
				StringEntity stringEntity = new StringEntity(str, "UTF-8");
				httpPost.setHeader("Content-Type", CONTENT_TYPE_JSON_URL);
				httpPost.setEntity(stringEntity);
				System.out.println("密文："+str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sendHttpPost(httpPost);
	}

	/**
	 * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
	 * 
	 * @param parameterMap
	 *            需要转化的键值对集合
	 * @return 字符串
	 */
	public static String convertStringParamter(Map parameterMap) {
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}
				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		return parameterBuffer.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @param json
	 * @throws Exception
	 */
	public static void httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, CHARSET_UTF_8);
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        
        StringEntity se = new StringEntity(encoderJson);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        CloseableHttpResponse response = httpClient.execute(httpPost);
    }
	
	
	public static void main(String[] args){
       try {
		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", "QD657549");
		map.put("appNumber", "APP630773");
//		map.put("keyValue",Base64.encode((RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(en_publickey), map.getBytes()))) );
		map.put("offRampWay", "H5");
		map.put("accountId", "13001");
//		map.put("id", "13001");
//		map.put("id", "13001");
//		map.put("id", "13001");
//		map.put("id", "13001");
		String sign = JSON.toJSONString(map);
//		addPostHeadler(httpPost, new Date().getTime());
		sendHttpPostJson("http://14968yi645.iok.la/UKer-as/channelInformationManagement/getChannelAuthorization.do", sign);
//    	sendHttpGet("http://14968yi645.iok.la/UKer-as/channelInformationManagement/getChannelAuthorization.do",map);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
