/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.pub.pay;

/**
 * 支付常量池
 * @author AJiong
 * @version $Id: PayConstants.java, v 0.1 2018年1月29日 上午10:46:07 Ajiong Exp $
 */
public interface PayConstants {
    /** 未支付 */
    public final static String PAY_STATUS_UNPAY = "0";
    /** 已支付,未充值 */
    public final static String PAY_STATUS_PAID = "1";
    /** 已充值 */
    public final static String PAY_STATUS_CHARGED = "2";
    /** 微信支付 */
    public final static Integer PAY_TYPE_WECHAT = 1;
}
