package cn.com.czcb.pub.tft;

/**
 * 消息状态类
 * @author Administrator
 *
 */
public class MsgStatus {
	
	/**
	 * 给app返回消息·成功
	 */
	public static final String SUCCESS = "00";
	/**
	 * 给app返回消息·失败
	 */
	public static final String FAIL = "01";
	/**
	 * 用户未注册
	 */
	public static final String UNREGISTERED = "02";
	/**
	 * 用户已注册
	 */
	public static final String REGISTERED = "03";
	/**
	 * 用户·启用
	 */
	public static final String ON = "1";
	/**
	 * 用户·停用
	 */
	public static final String OFF = "0";
}
