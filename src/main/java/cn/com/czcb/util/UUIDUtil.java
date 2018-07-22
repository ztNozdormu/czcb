package cn.com.czcb.util;

import java.util.UUID;

/**
 * 说明 UUID工具类
 * 
 * @author Cron
 * @date 2018年4月16日 下午4:53:32
 */
public class UUIDUtil {

	/**
	 * uuid
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
