package cn.com.czcb.model.tft;

import java.util.Arrays;

/**
 * 账户圈存接口响应数据
 * @author Administrator
 *
 */
public class AccountTransferData {
	/**
	 * 中心流水号
	 */
	private String centerFlowNo;
	/**
	 * 圈存指令
	 */
	private String[] apdu;
	/**
	 * 卡余额
	 */
	private Integer balance;
	/**
	 * 圈存日期
	 */
	private String transDate;
	/**
	 * 圈存时间
	 */
	private String transTime;
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
     * Getter method for property <tt>apdu</tt>.
     * 
     * @return property value of apdu
     */
    public String[] getApdu() {
        return apdu;
    }
    /**
     * Setter method for property <tt>apdu</tt>.
     * 
     * @param apdu value to be assigned to property apdu
     */
    public void setApdu(String[] apdu) {
        this.apdu = apdu;
    }
    /**
     * Getter method for property <tt>balance</tt>.
     * 
     * @return property value of balance
     */
    public Integer getBalance() {
        return balance;
    }
    /**
     * Setter method for property <tt>balance</tt>.
     * 
     * @param balance value to be assigned to property balance
     */
    public void setBalance(Integer balance) {
        this.balance = balance;
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
    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AccountTransferData [centerFlowNo=" + centerFlowNo + ", apdu="
               + Arrays.toString(apdu) + ", balance=" + balance + ", transDate=" + transDate
               + ", transTime=" + transTime + "]";
    }

}
