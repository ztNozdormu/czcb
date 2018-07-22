/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
package cn.com.czcb.pub.pay.bestpay;

import com.alibaba.fastjson.JSONObject;

import cn.com.czcb.pub.InitConfig;

/**
 * 翼支付风控参数
 * @author liufei
 * @version $Id: RiskControlConfig.java, v 0.1 2017年8月18日 下午4:25:21 liufei Exp $
 */
public class RiskControlConfig {

    // 处理风控参数
    /** 商品描述 */
    public static final String body            = "黑晶芯-天府通充值";
    /** 购买数量 */
    public static final String goodCount       = "1";
    /** 收银台展示地址 */
    public static final String showUrl         = InitConfig.getProductShowUrl();
    
    /** 服务类型 */
    public static final String serviceType     = "黑晶芯-天府通充值";
    /** 业务标识 */
    public static final String serviceIdentify = "104";
    /** 服务名称 */
    public static final String subject         = "黑晶芯-天府通充值";
    /** 商品类型 1:实物商品；2:虚拟商品 3:实物+虚拟 */
    public static final String productType     = "2";

    /**
     * 获取风控参数
     * @return String
     */
    public static String obtianRishControlParams() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("service_identify", serviceIdentify);
        jsonObject.put("subject", subject);
        jsonObject.put("product_type", productType);
        jsonObject.put("boby", body);
        jsonObject.put("goods_count", goodCount);
        jsonObject.put("show_url", showUrl);
        jsonObject.put("services_type", serviceType);
        return jsonObject.toString();
    }

}
