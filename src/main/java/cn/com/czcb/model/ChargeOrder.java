/**
 * 2018/2/27 9:23:30 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/02/27
 * Created by AJiong.
 */
public class ChargeOrder implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -9223372036854775808L;

    // 订单id [主键]
    private String id;
    // 用户id
    private String userId;
    // 充值卡号
    private String cardNo;
    // 充值金额(单位：分)
    private Integer chargeNum;
    // 流水号
    private String serialNum;
    // 支付类型（1：微信支付，2：翼支付）
    private Integer payType;
    // 订单状态,0：未支付；1：已支付，未充值；2：充值成功。
    private String status;
    // 创建时间
    private long createTime;
    // 用户电话号码
    private String phone;
    //微信支付订单号
    private String transactionId;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * 获取订单id [主键]
     * 
     * @return 订单id
     */
    public String getId() {
        return id;
    }

    /** 
     * 设置订单id [主键]
     * 
     * @param id 订单id
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
     * 获取充值卡号
     * 
     * @return 充值卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /** 
     * 设置充值卡号
     * 
     * @param cardNo 充值卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /** 
     * 获取充值金额(单位：分)
     * 
     * @return 充值金额(单位：分)
     */
    public Integer getChargeNum() {
        return chargeNum;
    }

    /** 
     * 设置充值金额(单位：分)
     * 
     * @param chargeNum 充值金额(单位：分)
     */
    public void setChargeNum(Integer chargeNum) {
        this.chargeNum = chargeNum;
    }

    /** 
     * 获取流水号
     * 
     * @return 流水号
     */
    public String getSerialNum() {
        return serialNum;
    }

    /** 
     * 设置流水号
     * 
     * @param serialNum 流水号
     */
    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    /** 
     * 获取支付类型（1：微信支付，2：翼支付）
     * 
     * @return 支付类型（1：微信支付，2：翼支付）
     */
    public Integer getPayType() {
        return payType;
    }

    /** 
     * 设置支付类型（1：微信支付，2：翼支付）
     * 
     * @param payType 支付类型（1：微信支付，2：翼支付）
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /** 
     * 获取订单状态,0：未支付；1：已支付，未充值；2：充值成功。
     * 
     * @return 订单状态,0：未支付；1：已支付，未充值；2：充值成功。
     */
    public String getStatus() {
        return status;
    }

    /** 
     * 设置订单状态,0：未支付；1：已支付，未充值；2：充值成功。
     * 
     * @param status 订单状态,0：未支付；1：已支付，未充值；2：充值成功。
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public long getCreateTime() {
        return createTime;
    }

    /** 
     * 设置创建时间
     * 
     * @param createTime 创建时间
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
    
    

}
