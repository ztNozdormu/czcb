package cn.com.czcb.dto;

/**
 * Created by wenjun on 2018/4/12.
 */
public class ChargeDto {

    /** 用户电话号码 */
    private String phone;
    /** 充值卡号 */
    private String cardNo;
    /** 充值金额 */
    private Integer chargeNum;
    /** 创建时间 */
    private Long createTime;
    /** 充值类型 */
    private Integer payType;
    /** 微信支付订单号 */
    private String transactionId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
