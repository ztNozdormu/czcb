package cn.com.czcb.pub;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;






public class ObjectUtils {
	private static final String dateFormatStr = "yyyy-MM-dd HH:mm:ss";
	private static final String dayFormatStr = "yyyy-MM-dd";
	private static final String configDateFormatStr = "yyyy-MM-dd HH:mm";
	private static final String recordDateFormatStr = "yyyy.M.d";
	private static final String commentDateFormatStr ="MM月dd日 HH:mm";
	private static final String timeCodeDateFormatStr = "yyyyMMddHHmmssSSS";
	private static final String code_number_format_Str = "0000";  
	private static final Pattern mobile_pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");  
	private static final PrintStream INFO_LOGGER = System.out; 
	private static final PrintStream ERROR_LOGGER = System.err;
	public static final long MAX_TIME = 3000000000000L; 
	public static final long DAY_MILLISECOND = 86400000L;
	public static final long WEEK_MILLISECOND = 604800000L;
	public static boolean isDebug = true;
	/** 
     * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700 
     * **/  
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|8[019])\\d{8}$)|(^1700\\d{7}$)";  
  
    /** 
     * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709 
     * **/  
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";  
  
    /** 
     * 中国移动号码格式验证 
     * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184 
     * ,187,188,147,178,1705 
     * **/  
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";
	public synchronized static void logInfo(String info){
		if(!isDebug){
			return;
		}
		INFO_LOGGER.println(info);
	} 
	
	public synchronized static void logError(String errorMsg,Throwable... exceptions){
		if(!isDebug){
			return;
		}
		ERROR_LOGGER.println(errorMsg);
		if(exceptions==null){
			return;
		}
		for(Throwable t:exceptions){
			t.printStackTrace();
		}
	}
	
	public static boolean isEmpty(String str){
		return str==null||"".equals(str.trim());
	}

	public static boolean isEmpty(String... strs){
		boolean isEmpty = strs==null||strs.length<1;
		if(!isEmpty){
			for(String str:strs){
				isEmpty = isEmpty(str);
				if(isEmpty){
					break;
				}
			}
		}
		return isEmpty;
	}

	public static <T>  boolean isEmpty(T[] array){
		return array==null||array.length<=0;
	}
	
	public static <T> boolean isEmpty(Collection<T> collection){
		return collection==null||collection.isEmpty();
	}

	public static <T,K> boolean isEmpty(Map<T,K> map){
		return map==null||map.isEmpty();
	}
	
	public static <T> boolean isEmpty(Enumeration<T> enums){
		return enums==null||!enums.hasMoreElements();
	}
	
	/**
	 * 将yyyy-MM-dd HH:mm:ss转换为date对象
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr) throws ParseException{
		return new SimpleDateFormat(dateFormatStr).parse(dateStr);
	}
	/**
	 * 将yyyy-MM-dd转换为date对象
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateByDay(String dateStr) throws ParseException{
		return new SimpleDateFormat(dayFormatStr).parse(dateStr);
	}
	public static String formatDate(Date date){
		if(date == null){
			return "";
		}
		return new SimpleDateFormat(dateFormatStr).format(date);
	}

	public static String formatCodeNumber(Number codeNum){
		if(codeNum == null){
			codeNum = 0;
		}
		return new DecimalFormat(code_number_format_Str).format(codeNum);
	}

	public static String formatConfigDate(Date date){
		if(date == null){
			return "";
		}
		return new SimpleDateFormat(configDateFormatStr).format(date);
	}

	/**
	 * @param configDateStr 格式为 “2016-2-26 14:15”
	 * @return
	 */
	public static Date parseConfigDate(String configDateStr){
		Date result = null;
		if(ObjectUtils.isEmpty(configDateStr)){
			return result;
		}
		try {
			result = new SimpleDateFormat(configDateFormatStr).parse(configDateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将毫秒值转化为yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static String formatDate(long time){
		return new SimpleDateFormat(dateFormatStr).format(new Date(time));
	}
	
	public static String formatDate(long time,String format){
        return new SimpleDateFormat(format).format(new Date(time));
    }
	/**
	 * 将毫秒值转化为YYYY-MM-DD
	 * @param time
	 * @return
	 */
	public static String formatDay(long time){
		return new SimpleDateFormat(dayFormatStr).format(new Date(time));
	}

	public static String formatTimeCodeDate(long time){
		return new SimpleDateFormat(timeCodeDateFormatStr).format(new Date(time))+(new Random()).nextInt(9);
	}
	
	public static String formatRecordDate(Date date){
		if(date == null){
			return "";
		}
		return new SimpleDateFormat(recordDateFormatStr).format(date);
	}

	public static String formatCommentDate(Date date){
		if(date == null){
			return "";
		}
		return  new SimpleDateFormat(commentDateFormatStr).format(date);
	}

	public static String formatRecordDate(long time){
		return new SimpleDateFormat(recordDateFormatStr).format(new Date(time));
	}
	
	public static boolean isMobile(String mobiles){  
		if(isEmpty(mobiles)){
			return false;
		}
		Matcher m = mobile_pattern.matcher(mobiles);  
		return m.matches();
	}
	
	public static String customDecp(String encp) {
		char[] cp = new char[encp.length()];
		int index = 0;
		for(char c : encp.toCharArray()){
			cp[index] = (char) ((c-92)/2+45);
			index++;
		}
		final String result = new String(cp);
		return result;
	}

	public static String customEncp(String str) {
		char[] cp = new char[str.length()];
		int index = 0;
		for(char c:str.toCharArray()){
			cp[index] = (char) (92+(c - 45)*2);
			index++;
		}
		final String result = new String(cp);
		return result;
	}
	
	public static int parseStringToInt(String str){
		int result = 0;
		try {
			result = Integer.valueOf(str);
		} catch (Exception e) {
		}
		return result;
	}

	public static int parseStringToInt(String str,int def){
		int result = def;
		try {
			result = Integer.valueOf(str);
		} catch (Exception e) {
		}
		return result;
	}
	
	public static double parseStringToDouble(String str){
		return parseStringToDouble(str,0);
	}

	public static double parseStringToDouble(String str,double def){
		double result = def;
		try {
			result = new BigDecimal(str).doubleValue();
		} catch (Exception e) {
		}
		return result;
	}
	
	/**
	 * 是否是明天之前（不包含明天）
	 * @param mills
	 * @return
	 */
	public static boolean isNotAfterToday(long mills){
		boolean used = true;
		if(mills<=0){
			used = false;
		}else{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			//System.out.println(ObjectUtils.formatDate(cal.getTime()));
			long todayBegain = cal.getTimeInMillis();
			if(mills<todayBegain){
				used = false;
			}
		}
		return used;
	}

	/**
	 * 该日结束时间
	 * @param mills
	 * @return
	 */
	public static long dayMaxMills(long mills){
		if(mills<0){
			mills =0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//System.out.println(ObjectUtils.formatDate(cal.getTime()));
		long todayBegain = cal.getTimeInMillis();
		//long inteval = 24*60*60*1000-1;
		long inteval = 86399999L;
		return todayBegain+inteval;
	}

	/**
	 * 该日开始时间（不包含前一天的时间）
	 * @param mills
	 * @return
	 */
	public static long dayMinMills(long mills){
		if(mills<0){
			mills =0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//System.out.println(ObjectUtils.formatDate(cal.getTime()));
		long todayBegain = cal.getTimeInMillis();
		return todayBegain;
	}

	/**
	 * 小时段开始时间
	 * @param mills
	 * @return
	 */
	public static long hourMinMills(long mills){
		if(mills<0){
			mills =0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//System.out.println(ObjectUtils.formatDate(cal.getTime()));
		long hourBegin = cal.getTimeInMillis();
		return hourBegin;
	}

	/**
	 * 小时段开始时间
	 * @param mills
	 * @return
	 */
	public static long hourMaxMills(long mills){
		if(mills<0){
			mills =0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(mills);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//System.out.println(ObjectUtils.formatDate(cal.getTime()));
		long hourBegin = cal.getTimeInMillis();
		long intevel = 3599999;// 60*60*1000-1;
		return hourBegin+intevel;
	}
	
	/**
	 * 明天之前（不包含明天）
	 * @return
	 */
	public static long todayMaxMills(){
		return dayMaxMills(System.currentTimeMillis());
	}

	/**
	 * 今天初始时间（不包含昨天）
	 * @return
	 */
	public static long todayMinMills(){
		return dayMinMills(System.currentTimeMillis());
	}
	
	/**
	 * 当前小时段初始时间
	 * @return
	 */
	public static long currentHourMinMills(){
		return hourMinMills(System.currentTimeMillis());
	}

	/**
	 * 当前小时段结束时间
	 * @return
	 */
	public static long currentHourMaxMills(){
		return hourMaxMills(System.currentTimeMillis());
	}
	/**
	 * 判断上一次上传时间是不是昨天
	 * @param lastUpload
	 * @return
	 */
	public static boolean isYesterday(long lastUpload,long uploadTime){
		//当前时间的昨天最小的毫秒值
		Long yesterdayMinMILLISECOND = dayMinMills(uploadTime - DAY_MILLISECOND) ;
		if (dayMinMills(lastUpload) == yesterdayMinMILLISECOND) {
			return true;
		}
		return false;
	}
	/**
	 * 判断当前时间是否是星期天或者(7月24日)
	 * @return
	 */
	public static boolean isGameTime(String rule){
		boolean isGameTime = false;
		if ("2".equals(rule)) {
			//每周日开奖
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			isGameTime = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
			//isGameTime = cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
		}
		if ("3".equals(rule)) {
			//2017年7月24日
			Calendar cal_7 = Calendar.getInstance();
			cal_7.set(Calendar.YEAR, 2017);
			cal_7.set(Calendar.MONTH, Calendar.JULY);
			cal_7.set(Calendar.DAY_OF_MONTH, 24);
			cal_7.set(Calendar.HOUR_OF_DAY, 0);
			cal_7.set(Calendar.MINUTE, 0);
			cal_7.set(Calendar.SECOND, 0);
			cal_7.set(Calendar.MILLISECOND, 0);
			//今天时间
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 0);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.SECOND, 0);
			today.set(Calendar.MILLISECOND, 0);
			if (cal_7.getTimeInMillis()==today.getTimeInMillis()) {
				isGameTime = true;
			}
		}
		return isGameTime;
	}
	/**
     * 根据抽奖规则获取下一次开奖时间
     * @param rule
     * @return
     */
	public static long getGameTime(String rule) {
		long gameTime = 0L;
		if ("2".equals(rule)) {
			//获取下一个星期天的时间
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			//cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			gameTime = cal.getTimeInMillis() + WEEK_MILLISECOND;
		}
		if ("3".equals(rule)) {
			//2017年7月24日
			Calendar cal_7 = Calendar.getInstance();
			cal_7.set(Calendar.YEAR, 2017);
			cal_7.set(Calendar.MONTH, Calendar.JULY);
			cal_7.set(Calendar.DAY_OF_MONTH, 24);
			cal_7.set(Calendar.HOUR_OF_DAY, 23);
			cal_7.set(Calendar.MINUTE, 59);
			cal_7.set(Calendar.SECOND, 59);
			cal_7.set(Calendar.MILLISECOND, 999);
			gameTime = cal_7.getTimeInMillis();
		}
		return gameTime;
	}
	/**
	 * 根据文件名返回扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtendName(String fileName){
		if(isEmpty(fileName)){
			return fileName;
		}
		String[] sp = fileName.split("\\.");
		return sp[sp.length-1];
	}
	
	public static <T> void setObjectProperty(T obj,String fieldName,Object value) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		PropertyDescriptor pDescriptor = new PropertyDescriptor(fieldName, obj.getClass());
		pDescriptor.getWriteMethod().invoke(obj, value);
	}
	
	/** 
     * 剔除emoji表情 
     *  
     * @param str 
     * @return 过滤后的字符串 
     */  
    public static String filterEmoji(String str) {  
        if(isEmpty(str)){  
        	return str;  
        }else{  
        	return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");  
        }  
    }  

    /** 
     * 将每个emoji表情替换成replaceCh
     *  
     * @param str 
     * @return 过滤后的字符串 
     */  
    public static String filterEmoji(String str,String replaceCh) {  
    	if(isEmpty(str)){  
    		return str;  
    	}else if(isEmpty(replaceCh)){
    		return filterEmoji(str);
    	}else{
    		return str.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", replaceCh);  
    	}  
    }  
    
    /**
     * 获取三者中最小值
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static int getMin(int a,int b,int c){
    	return a<b?(a<c?a:c):(b<c?b:c);
    }
    
    /**
     * 生成随机兑换码
     * @return
     */
    public static String generateGiftCode(){
    	return EncpUtils.encryptMD5(UUID.randomUUID().toString()+System.currentTimeMillis()).substring(0,12);
    }
    
    /** 
     * 获取当前网络ip 
     * @param request 
     * @return 
     */  
    public static String getIpAddr(HttpServletRequest request){
    	String ipAddress = null;
    	try {
    		ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
            	ipAddress = request.getHeader("X-ng-Real-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
    	} catch (Exception e) {
			// TODO: handle exception
		}
    	return ipAddress;   
    }
    /**
     * 将数组进行反向排序
     * @param idsArr
     * @return
     */
	public static String[] reverseArray(String[] idsArr) {
		String[] arr = new String[idsArr.length];
		for (int i = 0; i < idsArr.length; i++) {
			arr[i] = idsArr[idsArr.length - i - 1];
		}
		return arr;
	}
	/** 
     * 验证【电信】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaTelecomPhoneNum(String str) {  
  
        return str == null || "".equals(str.trim()) ? false : match(  
                CHINA_TELECOM_PATTERN, str);  
    }  
  
    /** 
     * 验证【联通】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaUnicomPhoneNum(String str) {  
  
        return str == null || "".equals(str.trim()) ? false : match(  
                CHINA_UNICOM_PATTERN, str);  
    }  
  
    /** 
     * 验证【移动】手机号码的格式 
     *  
     * @author LinBilin 
     * @param str 
     *            校验手机字符串 
     * @return 返回true,否则为false 
     */  
    public static boolean isChinaMobilePhoneNum(String str) {  
  
        return str == null || "".equals(str.trim()) ? false : match(  
                CHINA_MOBILE_PATTERN, str);  
    }
    /** 
     * 执行正则表达式 
     *  
     * @param pat 
     *            表达式 
     * @param str 
     *            待验证字符串 
     * @return 返回true,否则为false 
     */  
    private static boolean match(String pat, String str) {  
        Pattern pattern = Pattern.compile(pat);  
        Matcher match = pattern.matcher(str);  
        return match.find();  
    }
    
    /**
     * 判断是哪个运营商
     * @param phone
     * @return
     */
    public static String isWhichOperator(String phone){
    	String famPhoneState = null;
    	if (isChinaTelecomPhoneNum(phone)) {
			famPhoneState = "电信";
		}else if (isChinaUnicomPhoneNum(phone)) {
			famPhoneState = "联通";
		}else if (isChinaMobilePhoneNum(phone)) {
			famPhoneState = "移动";
		}else {
			famPhoneState = "未知";
		}
    	return famPhoneState;
    }

    /**
     * 将数组转换为字符串（逗号分隔）
     * @param array
     * @return String
     */
    public static String arrayToString(String[] array) {
        return StringUtils.join(Arrays.asList(array), ",");
    }
}
