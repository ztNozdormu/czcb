package cn.com.czcb.pub.tft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.center.RSAEncrypt;

/**
 * 
 * HttpUtil
 *
 * @author: 杨永川
 * @version: 1.0, 2015年10月28日
 */
public class HttpUtil {
    /**  */
    private static final Logger LOG               = Logger.getLogger(HttpUtil.class);
    /**  */
    public static final Integer MAX_IDLE_TIME_OUT = Integer.valueOf(60000);
    /**  */
    private static final String URL_ENCODE        = "utf-8";
    /**  */
    public static HttpClient    httpClient;

    static {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.closeIdleConnections(MAX_IDLE_TIME_OUT.intValue());
        httpClient = new HttpClient(connectionManager);

    }

    /**
     * 
     * Http请求 请求方式get
     *
     * @param url
     * @return string
     */
    public static String get(String url) {
        LOG.info("请求url:" + url);
        GetMethod get = new GetMethod(url);
        try {
            httpClient.executeMethod(get);
            return streamToString(get.getResponseBodyAsStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            get.releaseConnection();
        }
    }

    /**
     * 
     * Http请求 请求方式get
     *
     * @param url
     * @param params
     * @return string
     */
    public static String get(String url, Map<String, String> params) {
        StringBuffer urlSB = appendGetParams(url, params);
        return get(urlSB.toString());
    }

    /**
     * 
     * 拼接参数
     *
     * @param url
     * @param params
     * @return string
     */
    private static StringBuffer appendGetParams(String url, Map<String, String> params) {
        StringBuffer urlSB = new StringBuffer(url);
        if (params == null || params.isEmpty()) {
            return urlSB;
        }
        if (url.contains("?")) {
            urlSB.append("&");
        } else {
            urlSB.append("?");
        }
        for (Entry<String, String> param : params.entrySet()) {
            urlSB.append(param.getKey());
            urlSB.append("=");
            urlSB.append(param.getValue());
            urlSB.append("&");
        }
        urlSB.deleteCharAt(urlSB.length() - 1);
        return urlSB;
    }

    /**
     * 
     * Http请求 请求方式post
     *
     * @param url
     * @param content
     * @param sign 
     * @return string
     */
    public static String tftPost(String url, String content, String sign) {
        return tftPost(url, content, "application/json", "utf-8", sign);
    }
    /**
     *
     * Http请求 请求方式post
     *
     * @param url
     * @param content
     * @param sign
     * @return string
     */
    public static String post(String url, String content,String contentType) {
        return post(url, content, contentType, "utf-8");
    }

    /**
     * 
     * Http请求 请求方式post
     *
     * @param url
     * @param content
     * @param contentType
     * @param charset
     * @param sign 
     * @return string
     */
    public static String tftPost(String url, String content, String contentType, String charset,
                              String sign) {
        HttpPost post = new HttpPost(url);
        try {
            //采用绕过验证的方式处理https请求  
            SSLContext sslcontext = createIgnoreVerifySSL();
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext,
                NoopHostnameVerifier.INSTANCE);
            // 设置协议http和https对应的处理socket链接工厂的对象  
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", ssf)
                .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
            HttpClients.custom().setConnectionManager(connManager);

            //创建自定义的httpclient对象  
            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
                .build();
            StringEntity stringEntity = new StringEntity(content);
            stringEntity.setContentType(contentType);
            stringEntity.setContentEncoding(charset);
            post.setEntity(stringEntity);
            post.addHeader("third_channel_code", InitConfig.getThirdChannelCode());
            post.addHeader("sign", sign);
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            HttpEntity httpEntity = httpResponse.getEntity();
            String responseSign = httpResponse.getFirstHeader("sign").getValue();
            String resultInfo = streamToString(httpEntity.getContent());
            boolean result = SignUtil.verify256(resultInfo,
                SignUtil.decodeBase64(responseSign.getBytes()),
                RSAEncrypt.loadPublicKeyByStr(InitConfig.getTftPublicKey()));
            if (result) {
                return resultInfo;
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            post.releaseConnection();
        }
    }

	/**
	 *
	 * Http请求 请求方式post
	 *
	 * @param url
	 * @param content
	 * @param contentType
	 * @param charset
	 * @param sign
	 * @return string
	 */
	public static String post(String url, String content, String contentType, String charset) {
		HttpPost post = new HttpPost(url);
		try {
			//采用绕过验证的方式处理https请求
			SSLContext sslcontext = createIgnoreVerifySSL();
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext,
					NoopHostnameVerifier.INSTANCE);
			// 设置协议http和https对应的处理socket链接工厂的对象
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
					.<ConnectionSocketFactory> create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", ssf)
					.build();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			HttpClients.custom().setConnectionManager(connManager);

			//创建自定义的httpclient对象
			CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager)
					.build();
			StringEntity stringEntity = new StringEntity(content);
			stringEntity.setContentType(contentType);
			stringEntity.setContentEncoding(charset);
			post.setEntity(stringEntity);
			CloseableHttpResponse httpResponse = httpClient.execute(post);
			HttpEntity httpEntity = httpResponse.getEntity();
			String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			return responseStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			post.releaseConnection();
		}
	}

    /**
     * 
     * @param request
     * @param charSet
     * @return map
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> packParamsFromRequest(HttpServletRequest request,
                                                            String charSet) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map<?, ?> requestParams = request.getParameterMap();
        for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                    : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), charSet);
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * 
     * Http 请求获取html页面源码
     *
     * @param urlStr
     * @return string
     */
    public static String getHtmlContent(String urlStr) {
        StringBuffer contentBuffer = new StringBuffer();
        int responseCode = -1;
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlStr);
            con = (HttpURLConnection) url.openConnection();
            // IE代理进行下载
            con.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            // 获得网页返回信息码
            responseCode = con.getResponseCode();
            if (responseCode == -1) {
                System.out.println(url.toString() + " : connection is failure...");
                con.disconnect();
                return null;
            }
            // 请求失败
            if (responseCode >= 400) {
                System.out.println("请求失败:get response code: " + responseCode);
                con.disconnect();
                return null;
            }

            InputStream inStr = con.getInputStream();
            InputStreamReader istreamReader = new InputStreamReader(inStr, URL_ENCODE);
            BufferedReader buffStr = new BufferedReader(istreamReader);
            String str = null;
            while ((str = buffStr.readLine()) != null) {
                contentBuffer.append(str);
            }
            inStr.close();
        } catch (Exception e) {
            contentBuffer = new StringBuffer();
            contentBuffer.append("请求失败,error:" + e.getMessage());
        } finally {
            con.disconnect();
        }
        return contentBuffer.toString();
    }

    /**
     * 
     * 输入流中获取返回数据
     *
     * @param is
     * @return string
     */
    public static String streamToString(InputStream is) {
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            isr = new InputStreamReader(is, "UTF-8");
            sb = new StringBuffer();
            br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }

    /** 
     * 绕过验证 
     *   
     * @return SSLContext
     * @throws NoSuchAlgorithmException  
     * @throws KeyManagementException  
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException,
                                                     KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法  
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }
}
