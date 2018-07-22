package cn.com.czcb.model.tft;

/**
 * 天府通返回参数
 * @author Administrator
 *
 * @param <T>
 */
public class ResultInfo<T> {
	/**
	 * 响应状态码
	 */
	private String code;
	/**
	 * 响应消息
	 */
	private String msg;
	/**
	 * 响应数据
	 */
	private T data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
