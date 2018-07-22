/**
 * 2018/3/1 17:35:03 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/03/01
 * Created by AJiong.
 */
public class ChargeRecord implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -3229195328070407168L;

    //  [主键]
    private String id;
    // 智能卡号
    private String cardNo;
    // 订单编号
    private String orderId;
    // 卡余额(单位：分)
    private Integer cardBalance;
    // 圈存初始化指令
    private String initRapdu;
    // 交易标志 1：正常交易，2：冲正交易
    private Integer transMark;
    // 中心流水号
    private String centerFlowNo;
    // 圈存指令
    private String rapdu;
    // 圈存日期(YYYYMMDD)
    private String transDate;
    // 圈存时间(HHMMSS)
    private String transTime;
    // 是否已确认
    private Boolean confirmed;
    // 最后一次圈存时间
    private Long lastTime;
    // 创建时间
    private Long createTime;

    /** 
     * 获取 [主键]
     * 
     * @return 
     */
    public String getId() {
        return id;
    }

    /** 
     * 设置 [主键]
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 
     * 获取智能卡号
     * 
     * @return 智能卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /** 
     * 设置智能卡号
     * 
     * @param cardNo 智能卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /** 
     * 获取订单编号
     * 
     * @return 订单编号
     */
    public String getOrderId() {
        return orderId;
    }

    /** 
     * 设置订单编号
     * 
     * @param orderId 订单编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /** 
     * 获取卡余额(单位：分)
     * 
     * @return 卡余额(单位：分)
     */
    public Integer getCardBalance() {
        return cardBalance;
    }

    /** 
     * 设置卡余额(单位：分)
     * 
     * @param cardBalance 卡余额(单位：分)
     */
    public void setCardBalance(Integer cardBalance) {
        this.cardBalance = cardBalance;
    }

    /** 
     * 获取圈存初始化指令
     * 
     * @return 圈存初始化指令
     */
    public String getInitRapdu() {
        return initRapdu;
    }

    /** 
     * 设置圈存初始化指令
     * 
     * @param initRapdu 圈存初始化指令
     */
    public void setInitRapdu(String initRapdu) {
        this.initRapdu = initRapdu;
    }

    /** 
     * 获取交易标志 1：正常交易，2：冲正交易
     * 
     * @return 交易标志 1：正常交易，2：冲正交易
     */
    public Integer getTransMark() {
        return transMark;
    }

    /** 
     * 设置交易标志 1：正常交易，2：冲正交易
     * 
     * @param transMark 交易标志 1：正常交易，2：冲正交易
     */
    public void setTransMark(Integer transMark) {
        this.transMark = transMark;
    }

    /** 
     * 获取中心流水号
     * 
     * @return 中心流水号
     */
    public String getCenterFlowNo() {
        return centerFlowNo;
    }

    /** 
     * 设置中心流水号
     * 
     * @param centerFlowNo 中心流水号
     */
    public void setCenterFlowNo(String centerFlowNo) {
        this.centerFlowNo = centerFlowNo;
    }

    /** 
     * 获取圈存指令
     * 
     * @return 圈存指令
     */
    public String getRapdu() {
        return rapdu;
    }

    /** 
     * 设置圈存指令
     * 
     * @param rapdu 圈存指令
     */
    public void setRapdu(String rapdu) {
        this.rapdu = rapdu;
    }

    /** 
     * 获取圈存日期(YYYYMMDD)
     * 
     * @return 圈存日期(YYYYMMDD)
     */
    public String getTransDate() {
        return transDate;
    }

    /** 
     * 设置圈存日期(YYYYMMDD)
     * 
     * @param transDate 圈存日期(YYYYMMDD)
     */
    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    /** 
     * 获取圈存时间(HHMMSS)
     * 
     * @return 圈存时间(HHMMSS)
     */
    public String getTransTime() {
        return transTime;
    }

    /** 
     * 设置圈存时间(HHMMSS)
     * 
     * @param transTime 圈存时间(HHMMSS)
     */
    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    /** 
     * 获取是否已确认
     * 
     * @return 是否已确认
     */
    public Boolean isConfirmed() {
        return confirmed;
    }

    /** 
     * 设置是否已确认
     * 
     * @param confirmed 是否已确认
     */
    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    /** 
     * 获取最后一次圈存时间
     * 
     * @return 最后一次圈存时间
     */
    public Long getLastTime() {
        return lastTime;
    }

    /** 
     * 设置最后一次圈存时间
     * 
     * @param lastTime 最后一次圈存时间
     */
    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    /** 
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /** 
     * 设置创建时间
     * 
     * @param createTime 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
