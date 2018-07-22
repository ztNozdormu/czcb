/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
package cn.com.czcb.model;

import java.io.Serializable;

/**
 * 翼支付查询对象结果
 * @author liufei
 * @version $Id: BestPayQueryModel.java, v 0.1 2017年8月18日 下午8:32:59 liufei Exp $
 */
public class BestPayQueryModel implements Serializable {

    /** 序列化 */
    private static final long serialVersionUID = 1L;

    /**商户号  */
    private String            merchantId;

    /**商户总订单号*/
    private String            orderNo;

    /** 商户总订单请求流 水号*/
    private String            orderReqNo;

    /**商户下单时间*/
    private String            orderDate;

    /**网关平台流水号*/
    private String            ourTransNo;

    /** 交易金额 */
    private String            transAmt;

    /**交易状态  */
    private String            transStatus;

    /** 加密方式 */
    private String            encodeType;

    /** sign 校验域*/
    private String            sign;

    /** 退款标识 */
    private String            refundFlag;

    /** 客户支付手机号*/
    private String            customerID;

    /** 优惠金额 */
    private String            coupon;

    /** 商户营销优惠成本*/
    private String            scValue;

    /** 付款人账号*/
    private String            payerAccount;

    /** 收款人账号*/
    private String            payeeAccount;

    /**付款明细  */
    private String            payChannel;

    /**备注  */
    private String            productDesc;

    /**  
     * 获取商户号  
     * @return merchantId 商户号  
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**  
     * 设置商户号  
     * @param merchantId 商户号  
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**  
     * 获取商户总订单号  
     * @return orderNo 商户总订单号  
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**  
     * 设置商户总订单号  
     * @param orderNo 商户总订单号  
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**  
     * 获取商户总订单请求流水号  
     * @return orderReqNo 商户总订单请求流水号  
     */
    public String getOrderReqNo() {
        return orderReqNo;
    }

    /**  
     * 设置商户总订单请求流水号  
     * @param orderReqNo 商户总订单请求流水号  
     */
    public void setOrderReqNo(String orderReqNo) {
        this.orderReqNo = orderReqNo;
    }

    /**  
     * 获取商户下单时间  
     * @return orderDate 商户下单时间  
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**  
     * 设置商户下单时间  
     * @param orderDate 商户下单时间  
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    /**  
     * 获取网关平台流水号  
     * @return ourTransNo 网关平台流水号  
     */
    public String getOurTransNo() {
        return ourTransNo;
    }

    /**  
     * 设置网关平台流水号  
     * @param ourTransNo 网关平台流水号  
     */
    public void setOurTransNo(String ourTransNo) {
        this.ourTransNo = ourTransNo;
    }

    /**  
     * 获取交易金额  
     * @return transAmt 交易金额  
     */
    public String getTransAmt() {
        return transAmt;
    }

    /**  
     * 设置交易金额  
     * @param transAmt 交易金额  
     */
    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    /**  
     * 获取交易状态  
     * @return transStatus 交易状态  
     */
    public String getTransStatus() {
        return transStatus;
    }

    /**  
     * 设置交易状态  
     * @param transStatus 交易状态  
     */
    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    /**  
     * 获取加密方式  
     * @return encodeType 加密方式  
     */
    public String getEncodeType() {
        return encodeType;
    }

    /**  
     * 设置加密方式  
     * @param encodeType 加密方式  
     */
    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    /**  
     * 获取sign校验域  
     * @return sign sign校验域  
     */
    public String getSign() {
        return sign;
    }

    /**  
     * 设置sign校验域  
     * @param sign sign校验域  
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**  
     * 获取退款标识  
     * @return refundFlag 退款标识  
     */
    public String getRefundFlag() {
        return refundFlag;
    }

    /**  
     * 设置退款标识  
     * @param refundFlag 退款标识  
     */
    public void setRefundFlag(String refundFlag) {
        this.refundFlag = refundFlag;
    }

    /**  
     * 获取客户支付手机号  
     * @return customerID 客户支付手机号  
     */
    public String getCustomerID() {
        return customerID;
    }

    /**  
     * 设置客户支付手机号  
     * @param customerID 客户支付手机号  
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**  
     * 获取优惠金额  
     * @return coupon 优惠金额  
     */
    public String getCoupon() {
        return coupon;
    }

    /**  
     * 设置优惠金额  
     * @param coupon 优惠金额  
     */
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    /**  
     * 获取商户营销优惠成本  
     * @return scValue 商户营销优惠成本  
     */
    public String getScValue() {
        return scValue;
    }

    /**  
     * 设置商户营销优惠成本  
     * @param scValue 商户营销优惠成本  
     */
    public void setScValue(String scValue) {
        this.scValue = scValue;
    }

    /**  
     * 获取付款人账号  
     * @return payerAccount 付款人账号  
     */
    public String getPayerAccount() {
        return payerAccount;
    }

    /**  
     * 设置付款人账号  
     * @param payerAccount 付款人账号  
     */
    public void setPayerAccount(String payerAccount) {
        this.payerAccount = payerAccount;
    }

    /**  
     * 获取收款人账号  
     * @return payeeAccount 收款人账号  
     */
    public String getPayeeAccount() {
        return payeeAccount;
    }

    /**  
     * 设置收款人账号  
     * @param payeeAccount 收款人账号  
     */
    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    /**  
     * 获取付款明细  
     * @return payChannel 付款明细  
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**  
     * 设置付款明细  
     * @param payChannel 付款明细  
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    /**  
     * 获取备注  
     * @return productDesc 备注  
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**  
     * 设置备注  
     * @param productDesc 备注  
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**  
     * 获取序列化  
     * @return serialVersionUID 序列化  
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
