package cn.com.czcb.model.tft;

/**
 * 账户圈存确认
 * @author Administrator
 *
 */
public class AccountTransferConfirm {
	/**
	 * 天府通卡号
	 */
	private String cardNo;
	/**
	 * 交易金额
	 */
	private Integer amount;
	/**
	 * 圈存请求的时候生成的中心流水号
	 */
	private String centerFlowNo;
	/**
	 * 圈存申请中的订单号
	 */
	private String orderNo;
	/**
	 * 卡片余额
	 */
	private Integer cardBalance;
	/**
	 * 脚本执行结果
	 */
	private String[] rapdu;
	/**
	 * 请求日期
	 */
	private String transDate;
	/**
	 * 请求时间
	 */
	private String transTime;
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
     * Getter method for property <tt>centerFlowNo</tt>.
     * 
     * @return property value of centerFlowNo
     */
    public String getCenterFlowNo() {
        return centerFlowNo;
    }
    /**
     * Setter method for property <tt>centerFlowNo</tt>.
     * 
     * @param centerFlowNo value to be assigned to property centerFlowNo
     */
    public void setCenterFlowNo(String centerFlowNo) {
        this.centerFlowNo = centerFlowNo;
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
     * Getter method for property <tt>rapdu</tt>.
     * 
     * @return property value of rapdu
     */
    public String[] getRapdu() {
        return rapdu;
    }
    /**
     * Setter method for property <tt>rapdu</tt>.
     * 
     * @param rapdu value to be assigned to property rapdu
     */
    public void setRapdu(String[] rapdu) {
        this.rapdu = rapdu;
    }
    /**
     * Getter method for property <tt>transDate</tt>.
     * 
     * @return property value of transDate
     */
    public String getTransDate() {
        return transDate;
    }
    /**
     * Setter method for property <tt>transDate</tt>.
     * 
     * @param transDate value to be assigned to property transDate
     */
    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }
    /**
     * Getter method for property <tt>transTime</tt>.
     * 
     * @return property value of transTime
     */
    public String getTransTime() {
        return transTime;
    }
    /**
     * Setter method for property <tt>transTime</tt>.
     * 
     * @param transTime value to be assigned to property transTime
     */
    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

}
