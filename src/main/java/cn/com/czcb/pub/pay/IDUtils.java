package cn.com.czcb.pub.pay;

import org.junit.Test;

import cn.com.czcb.pub.ObjectUtils;


public class IDUtils {
	/**
	 * 生成订单id
	 * @return
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
	 * @return
	 */
	public static String generateSerialNum(String orderId){
		//生成一个1位数的随机数
		long random = Math.round((Math.random() * 9 + 1));
		return orderId + random;
	}
	
	@Test
	public void test1(){
		System.out.println(generateOrderId());
		System.out.println(generateSerialNum(generateOrderId()));
	}
}
