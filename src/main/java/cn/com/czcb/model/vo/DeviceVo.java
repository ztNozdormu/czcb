package cn.com.czcb.model.vo;

/**
 * 说明 用户我的设备vo
 * 
 * @author Cron
 * @date 2018年5月8日 下午1:42:28
 */
public class DeviceVo {

	// 用户id
	private String userId;
	// 设备id
	private String deviceId;
	// 自动连接标记
	private String tag;
	// 匹配码
	private String matchCode;
	// 设备名称
	private String deviceName;
	// 卡号
	private String cardNo;
	// 匹配时间
	private Long matchTime;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getMatchCode() {
		return matchCode;
	}
	
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public Long getMatchTime() {
		return matchTime;
	}
	
	public void setMatchTime(Long matchTime) {
		this.matchTime = matchTime;
	}
	
}
