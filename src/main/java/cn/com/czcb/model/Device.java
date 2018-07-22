/**
 * 2018/5/7 星期一 14:47:50 Cron created.
 */

package cn.com.czcb.model;


/**
 * 2018/05/07
 * Created by Cron.
 */
public class Device implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -8989801848768163840L;

    // 唯一主键 [主键]
    private String id;
    // 匹配码
    private String matchCode;
    // 设备名称
    private String deviceName;
    // 卡号
    private String cardNo;
    // 匹配时间
    private Long matchTime;

    /** 
     * 获取唯一主键 [主键]
     * 
     * @return 唯一主键
     */
    public String getId() {
        return id;
    }

    /** 
     * 设置唯一主键 [主键]
     * 
     * @param id 唯一主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 
     * 获取匹配码
     * 
     * @return 匹配码
     */
    public String getMatchCode() {
        return matchCode;
    }

    /** 
     * 设置匹配码
     * 
     * @param matchCode 匹配码
     */
    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    /** 
     * 获取设备名称
     * 
     * @return 设备名称
     */
    public String getDeviceName() {
        return deviceName;
    }

    /** 
     * 设置设备名称
     * 
     * @param deviceName 设备名称
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /** 
     * 获取卡号
     * 
     * @return 卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /** 
     * 设置卡号
     * 
     * @param cardNo 卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /** 
     * 获取匹配时间
     * 
     * @return 匹配时间
     */
    public Long getMatchTime() {
        return matchTime;
    }

    /** 
     * 设置匹配时间
     * 
     * @param matchTime 匹配时间
     */
    public void setMatchTime(Long matchTime) {
        this.matchTime = matchTime;
    }

	@Override
	public String toString() {
		return "Device [id=" + id + ", matchCode=" + matchCode + ", deviceName=" + deviceName + ", cardNo=" + cardNo
				+ ", matchTime=" + matchTime + "]";
	}

}
