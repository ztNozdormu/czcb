/**
 * 2018/1/25 15:06:03 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/01/25
 * Created by AJiong.
 */
public class BestPayRecord implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -2929887698464974848L;

    // 唯一主键 [主键]
    private String id;
    // 订单id
    private String orderId;
    // 翼支付网关平台交易流水号
    private String uptranSeq;
    // 商户号
    private String merchantId;
    // 翼支付网关平台交易日期
    private String tranDate;
    // 处理结果解释码
    private String retnInfo;
    // 处理结果码
    private String retnCode;
    // 订单请求交易流水号
    private String orderReqTranSeq;
    // 订单总金额
    private String orderAmount;
    // 产品金额
    private String productAmount;
    // 附加金额
    private String attachAmount;
    // 币种
    private String curType;
    // 加密方式
    private String encodeType;
    // 银行编码
    private String bankId;
    // 商户附加信息
    private String attach;
    // 网关平台请求交易流水号
    private String upReqTranSeq;
    // 银行流水号
    private String upBankTranSeq;
    // 产品号码
    private String productNo;
    // 数字签名
    private String signInfo;

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
     * 获取订单id
     * 
     * @return 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /** 
     * 设置订单id
     * 
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /** 
     * 获取翼支付网关平台交易流水号
     * 
     * @return 翼支付网关平台交易流水号
     */
    public String getUptranSeq() {
        return uptranSeq;
    }

    /** 
     * 设置翼支付网关平台交易流水号
     * 
     * @param uptranSeq 翼支付网关平台交易流水号
     */
    public void setUptranSeq(String uptranSeq) {
        this.uptranSeq = uptranSeq;
    }

    /** 
     * 获取商户号
     * 
     * @return 商户号
     */
    public String getMerchantId() {
        return merchantId;
    }

    /** 
     * 设置商户号
     * 
     * @param merchantId 商户号
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /** 
     * 获取翼支付网关平台交易日期
     * 
     * @return 翼支付网关平台交易日期
     */
    public String getTranDate() {
        return tranDate;
    }

    /** 
     * 设置翼支付网关平台交易日期
     * 
     * @param tranDate 翼支付网关平台交易日期
     */
    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    /** 
     * 获取处理结果解释码
     * 
     * @return 处理结果解释码
     */
    public String getRetnInfo() {
        return retnInfo;
    }

    /** 
     * 设置处理结果解释码
     * 
     * @param retnInfo 处理结果解释码
     */
    public void setRetnInfo(String retnInfo) {
        this.retnInfo = retnInfo;
    }

    /** 
     * 获取处理结果码
     * 
     * @return 处理结果码
     */
    public String getRetnCode() {
        return retnCode;
    }

    /** 
     * 设置处理结果码
     * 
     * @param retnCode 处理结果码
     */
    public void setRetnCode(String retnCode) {
        this.retnCode = retnCode;
    }

    /** 
     * 获取订单请求交易流水号
     * 
     * @return 订单请求交易流水号
     */
    public String getOrderReqTranSeq() {
        return orderReqTranSeq;
    }

    /** 
     * 设置订单请求交易流水号
     * 
     * @param orderReqTranSeq 订单请求交易流水号
     */
    public void setOrderReqTranSeq(String orderReqTranSeq) {
        this.orderReqTranSeq = orderReqTranSeq;
    }

    /** 
     * 获取订单总金额
     * 
     * @return 订单总金额
     */
    public String getOrderAmount() {
        return orderAmount;
    }

    /** 
     * 设置订单总金额
     * 
     * @param orderAmount 订单总金额
     */
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    /** 
     * 获取产品金额
     * 
     * @return 产品金额
     */
    public String getProductAmount() {
        return productAmount;
    }

    /** 
     * 设置产品金额
     * 
     * @param productAmount 产品金额
     */
    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    /** 
     * 获取附加金额
     * 
     * @return 附加金额
     */
    public String getAttachAmount() {
        return attachAmount;
    }

    /** 
     * 设置附加金额
     * 
     * @param attachAmount 附加金额
     */
    public void setAttachAmount(String attachAmount) {
        this.attachAmount = attachAmount;
    }

    /** 
     * 获取币种
     * 
     * @return 币种
     */
    public String getCurType() {
        return curType;
    }

    /** 
     * 设置币种
     * 
     * @param curType 币种
     */
    public void setCurType(String curType) {
        this.curType = curType;
    }

    /** 
     * 获取加密方式
     * 
     * @return 加密方式
     */
    public String getEncodeType() {
        return encodeType;
    }

    /** 
     * 设置加密方式
     * 
     * @param encodeType 加密方式
     */
    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    /** 
     * 获取银行编码
     * 
     * @return 银行编码
     */
    public String getBankId() {
        return bankId;
    }

    /** 
     * 设置银行编码
     * 
     * @param bankId 银行编码
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /** 
     * 获取商户附加信息
     * 
     * @return 商户附加信息
     */
    public String getAttach() {
        return attach;
    }

    /** 
     * 设置商户附加信息
     * 
     * @param attach 商户附加信息
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    /** 
     * 获取网关平台请求交易流水号
     * 
     * @return 网关平台请求交易流水号
     */
    public String getUpReqTranSeq() {
        return upReqTranSeq;
    }

    /** 
     * 设置网关平台请求交易流水号
     * 
     * @param upReqTranSeq 网关平台请求交易流水号
     */
    public void setUpReqTranSeq(String upReqTranSeq) {
        this.upReqTranSeq = upReqTranSeq;
    }

    /** 
     * 获取银行流水号
     * 
     * @return 银行流水号
     */
    public String getUpBankTranSeq() {
        return upBankTranSeq;
    }

    /** 
     * 设置银行流水号
     * 
     * @param upBankTranSeq 银行流水号
     */
    public void setUpBankTranSeq(String upBankTranSeq) {
        this.upBankTranSeq = upBankTranSeq;
    }

    /** 
     * 获取产品号码
     * 
     * @return 产品号码
     */
    public String getProductNo() {
        return productNo;
    }

    /** 
     * 设置产品号码
     * 
     * @param productNo 产品号码
     */
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    /** 
     * 获取数字签名
     * 
     * @return 数字签名
     */
    public String getSignInfo() {
        return signInfo;
    }

    /** 
     * 设置数字签名
     * 
     * @param signInfo 数字签名
     */
    public void setSignInfo(String signInfo) {
        this.signInfo = signInfo;
    }

}
