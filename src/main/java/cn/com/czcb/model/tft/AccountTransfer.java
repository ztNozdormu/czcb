package cn.com.czcb.model.tft;

/**
 * 账户圈存
 * @author Administrator
 *
 */
public class AccountTransfer {
	/**
	 * 天府通卡号
	 */
	private String cardNo;
	/**
	 * 第三方账号
	 */
	private String userId;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 交易金额
	 */
	private Integer amount;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 卡余额
	 */
	private Integer cardBalance;
	/**
	 * 圈存初始化指令
	 */
	private String initRapdu;
	/**
	 * 交易标志
	 */
	private Integer transMark;
    /**
     * Getter method for property <tt>cardNo</tt>.
     * 
     * @return property value of cardNo
     */
    public String getCardNo() {
        return cardNo;
    }
    /**
     * Setter method for property <tt>cardNo</tt>.
     * 
     * @param cardNo value to be assigned to property cardNo
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Getter method for property <tt>phone</tt>.
     * 
     * @return property value of phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Setter method for property <tt>phone</tt>.
     * 
     * @param phone value to be assigned to property phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Getter method for property <tt>amount</tt>.
     * 
     * @return property value of amount
     */
    public Integer getAmount() {
        return amount;
    }
    /**
     * Setter method for property <tt>amount</tt>.
     * 
     * @param amount value to be assigned to property amount
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    /**
     * Getter method for property <tt>orderNo</tt>.
     * 
     * @return property value of orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }
    /**
     * Setter method for property <tt>orderNo</tt>.
     * 
     * @param orderNo value to be assigned to property orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    /**
     * Getter method for property <tt>cardBalance</tt>.
     * 
     * @return property value of cardBalance
     */
    public Integer getCardBalance() {
        return cardBalance;
    }
    /**
     * Setter method for property <tt>cardBalance</tt>.
     * 
     * @param cardBalance value to be assigned to property cardBalance
     */
    public void setCardBalance(Integer cardBalance) {
        this.cardBalance = cardBalance;
    }
    /**
     * Getter method for property <tt>initRapdu</tt>.
     * 
     * @return property value of initRapdu
     */
    public String getInitRapdu() {
        return initRapdu;
    }
    /**
     * Setter method for property <tt>initRapdu</tt>.
     * 
     * @param initRapdu value to be assigned to property initRapdu
     */
    public void setInitRapdu(String initRapdu) {
        this.initRapdu = initRapdu;
    }
    /**
     * Getter method for property <tt>transMark</tt>.
     * 
     * @return property value of transMark
     */
    public Integer getTransMark() {
        return transMark;
    }
    /**
     * Setter method for property <tt>transMark</tt>.
     * 
     * @param transMark value to be assigned to property transMark
     */
    public void setTransMark(Integer transMark) {
        this.transMark = transMark;
    }

}
