package cn.com.czcb.model.tft;

/**
 * 申请开卡响应数据
 * @author Administrator
 *
 */
public class OpenCardApplyData {
	/**
	 * 申请状态
	 */
	private String status;
	/**
	 * 卡号
	 */
	private String cardNo;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
