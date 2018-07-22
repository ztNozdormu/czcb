/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.pub;

import com.alibaba.fastjson.JSON;

import cn.com.czcb.pub.pay.XMLUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信小程序工具类
 * @author AJiong
 * @version $Id: WechatUtil.java, v 0.1 2018年1月25日 下午1:43:40 Ajiong Exp $
 */
public class WechatUtil {
	/**
	 * 日志对象
	 */
	private static Logger     logger = LoggerFactory.getLogger(WechatUtil.class);
    /** client */
    private static HttpClient client = HttpClients.custom().build();
    
    /**
     * 根据code获取用户session_key和openid
     * @param code
     * @return String
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String getOpenIdAndSessionKey(String code) throws ClientProtocolException, IOException{
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String,String> params = new HashMap<String, String>();
        params.put("appid", InitConfig.getAppid());
        params.put("secret", InitConfig.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        return doGet(url, params);
    }
    
    /**
     * 微信支付下单
     * @param parameters
     * @return string
     * @throws Exception 
     */
    public static String createWechatPayOrder(SortedMap<String,Object> parameters) throws Exception{
        String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String requestXml = XMLUtil.getRequestXml(parameters);
	    logger.info("微信支付下单接口请求参数,requestXml: {}", requestXml);
        requestXml = new String(requestXml.toString().getBytes(), "ISO8859-1");
        return sendXMLDataByPost(url, requestXml);
    }
    /**
     * get请求
     * @param url
     * @param params
     * @return 返回结果
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url,Map<String,String> params) throws ClientProtocolException, IOException{
        StringBuffer urlSB = appendGetParams(url, params);
        HttpGet get = new HttpGet(urlSB.toString());
        HttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        ObjectUtils.logInfo(result);
        return result;
    }
    
    /**
     * 拼接请求参数
     * @param url
     * @param params
     * @return 请求参数
     */
    private static StringBuffer appendGetParams(String url, Map<String, String> params) {
        StringBuffer urlSB = new StringBuffer(url   );
        if(params==null||params.isEmpty()){
            return urlSB;
        }
        if(url.contains("?")){
            urlSB.append("&");
        }else{
            urlSB.append("?");
        }
        for(Entry<String,String> param:params.entrySet()){
            urlSB.append(param.getKey());
            urlSB.append("=");
            urlSB.append(param.getValue());
            urlSB.append("&");
        }
        urlSB.deleteCharAt(urlSB.length()-1);
        return urlSB;
    }
    
    /**
     *  使用POST方法发送XML数据  
     * @param url
     * @param xmlData
     * @return String
     * @throws Exception
     */
    public static String sendXMLDataByPost(String url, String xmlData) throws Exception {
	    if (client == null){
		    client = HttpClients.createDefault();
	    }
	    HttpPost post = new HttpPost(url);
	    StringEntity stringEntity = new StringEntity(xmlData);
	    stringEntity.setContentType("application/xml");
	    stringEntity.setContentEncoding("UTF-8");
	    post.setEntity(stringEntity);
	    HttpResponse response = client.execute(post);
	    System.out.println(response.toString());
	    HttpEntity entity = response.getEntity();
	    String result = EntityUtils.toString(entity, "UTF-8");
	    return result;
    }
}
