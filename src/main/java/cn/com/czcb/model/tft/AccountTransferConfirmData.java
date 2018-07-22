package cn.com.czcb.model.tft;

/**
 * 账户圈存响应数据
 * @author Administrator
 *
 */
public class AccountTransferConfirmData {
	/**
	 * 响应状态码
	 */
	private String code;
	/**
	 * 响应消息
	 */
	private String msg;
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

}
