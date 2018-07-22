/**
 * 2018/5/7 星期一 14:47:51 Cron created.
 */

package cn.com.czcb.model;


/**
 * 用户设备连接信息中间表
 * 2018/05/07
 * Created by Cron.
 */
public class UserDevice implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -9223372036854775808L;

    // 唯一主键 [主键]
    private String id;
    // 用户id
    private String userId;
    // 设备id
    private String deviceId;
    // 自动连接标签（自动连接：1，非默认：0）
    private String tag = "0";

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
     * 获取用户id
     * 
     * @return 用户id
     */
    public String getUserId() {
        return userId;
    }

    /** 
     * 设置用户id
     * 
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /** 
     * 获取设备id
     * 
     * @return 设备id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /** 
     * 设置设备id
     * 
     * @param deviceId 设备id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /** 
     * 获取自动标签
     * 
     * @return 自动标签
     */
	public String getTag() {
		return tag;
	}
	
	/** 
     * 设置自动标签
     * 
     * @param tag 自动标签
     */
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "UserDevice [id=" + id + ", userId=" + userId + ", deviceId=" + deviceId + ", tag=" + tag + "]";
	}
    
}
