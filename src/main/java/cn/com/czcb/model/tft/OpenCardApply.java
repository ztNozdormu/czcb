package cn.com.czcb.model.tft;

/**
 * 开卡申请
 * @author Administrator
 *
 */
public class OpenCardApply {
	/**
	 * 申请流水号
	 */
	private String id;
	/**
	 * 第三方账号
	 */
	private String userId;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 安全模块标识
	 */
	private String seId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSeId() {
		return seId;
	}
	public void setSeId(String seId) {
		this.seId = seId;
	}
	

}
