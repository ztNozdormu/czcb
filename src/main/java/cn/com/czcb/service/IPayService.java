/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.service;

import java.util.Map;
import java.util.SortedMap;

import cn.com.czcb.model.BestPayQueryModel;

/**
 * 微信支付服务接口
 * @author AJiong
 * @version $Id: PayService.java, v 0.1 2018年1月29日 上午10:51:23 Ajiong Exp $
 */
public interface IPayService {
     /**
      * 微信支付下单
      * 
      * @param parameters
      * @return string
     * @throws Exception 
      */
    public Map<String, Object> createWechatPayOrder(SortedMap<String, Object> parameters) throws Exception;

	/**
	 * 翼支付订单下单
	 * @param merchantId 商户号
	 * @param merKey 交易密码
	 * @param orderAMT 交易金额
	 * @param orderseq 订单号
	 * @param orderTranseq 订单流水号
	 * @param orderTime 订单时间
	 * @param productdesc 商品描述
	 * @param productId 产品ID
	 * @param customerId 用户手机号
	 * @param transCode  收单类交易，默认填 01
	 * @param orderCcy  ORDERCCY
	 * @param attach 附件信息
	 * @param requestSystem  请求来源 固定:1 此参数必传, 请关注
	 * @param encodeType  MAC  字段 的加密方式 1
	 * @throws Exception
	 */
	public boolean addBestPayOrder(String merchantId, String merKey, Integer orderAMT, String orderseq,
	                               String orderTranseq, String orderTime, String productdesc,
	                               String productId, String customerId, String transCode,
	                               String orderCcy, String attach, String requestSystem,
	                               String encodeType) throws Exception;

	/**
	 * 获取公钥
	 * @param keyIndex 公钥索引
	 * @param encryKey AES秘钥加密串
	 * @param encryStr 请求报文加密串
	 * @param interCode 接口请求业务编码 固定值：INTER.SYSTEM.001
	 * @return Map<String, Object>
	 */
	public Map<String, Object> obtainPublicKey(String keyIndex, String encryKey, String encryStr,
	                                           String interCode);

	/**
	 * 获取访问的Url地址
	 * @param publicKeyJson 公钥
	 * @param merchantId 商户号
	 * @param merchantPWD 商户密码
	 * @param backmerchanturl 后台回调地址
	 * @param orderseq 订单id
	 * @param orderTranseq 订单流水号
	 * @param orderTime 订单时间
	 * @param ordervalidityTime 订单有效时间
	 * @param subject 商品标题
	 * @param productId 商品id
	 * @param orderAMT 商品价格
	 * @param productdesc 商品描述
	 * @param customerId 用户手机号
	 * @param swtichAcc 是否可以切换用户支付 true/false
	 * @param merKey 商户key
	 * @param attachAmount 附加金额
	 * @param busiType 同为业务标识代码
	 * @param accountid 翼支付账号
	 * @param userip 用户ip
	 * @param beforeBackUrl 后台回调地址
	 * @param userLanguage 用户语言环境
	 * @return String
	 */
	public String obtainTransUrl(String publicKeyJson, String merchantId, String merchantPWD,
	                             String backmerchanturl, String orderseq, String orderTranseq,
	                             String orderTime, String ordervalidityTime, String subject,
	                             String productId, String orderAMT, String productdesc,
	                             String customerId, String swtichAcc, String merKey,
	                             String attachAmount, String busiType, String accountid,
	                             String userip, String beforeBackUrl, String userLanguage);

	/**
	 * 根据订单号查询翼支付交易流水号
	 * @param orderSeqNo 订单号
	 * @param tranSerialsNo 订单流水号
	 * @param createdAt 订单创建时间
	 * @return BestPayQueryModel
	 * @throws Exception
	 */
	public BestPayQueryModel findTranOrderByOrderNo(String orderSeqNo, String tranSerialsNo,
	                                                String orderDate) throws Exception;
}
