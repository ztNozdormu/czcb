package cn.com.czcb.dto;

/**
 * Created by wenjun on 2018/5/7.
 */
public class UserInfoDto {

    private String userId;

    private String nickName;

    private String openId;

    private Long createTime;

    private Integer deviceCount;

    private String deviceName;

    private String cardNo;

    private Long matchTime;

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", openId='" + openId + '\'' +
                ", createTime=" + createTime +
                ", deviceCount=" + deviceCount +
                ", deviceName='" + deviceName + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", matchTime=" + matchTime +
                '}';
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
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
