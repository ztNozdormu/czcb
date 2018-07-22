package cn.com.czcb.pub;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * el 扩展方法
 * @author Administrator
 *
 */
public class EL_Extends {
	public static final  DateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 毫秒数日期格式化
	 * @param time
	 * @return
	 */
	public static String formatDefaultDate(long time){
		return defaultDateFormat.format(time);
	}

	/**
	 * 毫秒数日期格式化
	 * @param time
	 * @param formatStr
	 * @return
	 */
	public static String formatDate(long time,String formatStr){
		DateFormat format = new SimpleDateFormat();
		if(ObjectUtils.isEmpty(formatStr)){
			format = defaultDateFormat;
		}else{
			format = new SimpleDateFormat(formatStr);
		}
		return format.format(time);
	}
}
