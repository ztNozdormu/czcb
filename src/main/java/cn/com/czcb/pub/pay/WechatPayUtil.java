package cn.com.czcb.pub.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.junit.Test;

import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.Md5Utils;
import cn.com.czcb.pub.ObjectUtils;

/**
 * 
 * @author AJiong
 * @version $Id: IDUtils.java, v 0.1 2018年1月29日 上午10:17:12 Ajiong Exp $
 */
public class WechatPayUtil {
	/**
	 * 生成订单id
	 * @return OrderId
	 */
	public static String generateOrderId(){
		//当前时间毫秒值
		long mins = System.currentTimeMillis();
		String nowTime = ObjectUtils.formatDate(mins, "yyyyMMddhhmmssSSS");
		//生成一个3位数的随机数
		long random = Math.round((Math.random() * 900 + 100));
		return nowTime + random + "";
	}
	/**
	 * 根据订单号生成订单流水号
	 * @param orderId 
	 * @return SerialNum
	 */
	public static String generateSerialNum(String orderId){
		//生成一个1位数的随机数
		long random = Math.round((Math.random() * 9 + 1));
		return orderId + random;
	}
	/**
	 * 生成随机字符串
	 * @param length 长度
	 * @return Noncestr
	 */
	public static String createNoncestr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < length; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
    }
	/**
     * @Description：sign签名
     * @param parameters 请求参数
     * @return
     */
    @SuppressWarnings({ "javadoc", "rawtypes" })
    public static String createSign(SortedMap<String,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + InitConfig.getApiKey());
        String sign = Md5Utils.md5(sb.toString()).toUpperCase();
        return sign;
    }
    
    /** 
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。 
     * @param packageParams 
     * @return boolean 
     */  
    public static boolean isTenpaySign(SortedMap<String, Object> packageParams) {  
        StringBuffer sb = new StringBuffer();  
        Set<Entry<String, Object>> es = packageParams.entrySet();  
        Iterator<Entry<String, Object>> it = es.iterator();  
        while(it.hasNext()) {  
            Entry<String, Object> entry = it.next();  
            String k = (String)entry.getKey();  
            String v = (String)entry.getValue();  
            if(!"sign".equals(k) && null != v && !"".equals(v)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  

        sb.append("key=" + InitConfig.getApiKey());  

        //算出摘要  
        String mysign = Md5Utils.md5(sb.toString()).toLowerCase();  
        String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();  

        //System.out.println(tenpaySign + "    " + mysign);  
        return tenpaySign.equals(mysign);  
    }
	/**
	 * 
	 */
	@Test
	public void test1(){
		System.out.println(generateOrderId());
		System.out.println(generateSerialNum(generateOrderId()));
	}
}
