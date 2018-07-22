/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.czcb.model.BestPayQueryModel;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.WechatUtil;
import cn.com.czcb.pub.pay.XMLUtil;
import cn.com.czcb.pub.pay.bestpay.AES256;
import cn.com.czcb.pub.pay.bestpay.BestpayConfig;
import cn.com.czcb.pub.pay.bestpay.CryptTool;
import cn.com.czcb.pub.pay.bestpay.HttpRequestUtils;
import cn.com.czcb.pub.pay.bestpay.RSA;
import cn.com.czcb.pub.pay.bestpay.Resp;
import cn.com.czcb.pub.pay.bestpay.RiskControlConfig;
import cn.com.czcb.pub.pay.bestpay.RsaKeyBO;
import cn.com.czcb.service.IPayService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 微信支付服务实现类
 * @author AJiong
 * @version $Id: WechatPayServiceImpl.java, v 0.1 2018年1月29日 上午10:51:51 Ajiong Exp $
 */
@Service
public class PayService implements IPayService {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /** 
     * @throws Exception 
     * @see cn.com.czcb.service.IPayService#createWechatPayOrder(java.util.SortedMap)
     */
    @Override
    public Map<String, Object> createWechatPayOrder(SortedMap<String, Object> parameters) throws Exception {
	    String resutlXML = WechatUtil.createWechatPayOrder(parameters);
        Map<String, Object> resultMap = XMLUtil.doXMLParse(resutlXML);
        String return_code = (String) resultMap.get("return_code");
        String result_code = (String) resultMap.get("result_code");
        logger.info("微信支付下单接口返回参数,result: {}",JSON.toJSON(resultMap));
        if ("SUCCESS".equals(return_code)) {
            if ("SUCCESS".equals(result_code)) {
                return resultMap;
            }else {
                logger.error("微信支付下单调用失败,result_code:fail");
                throw new AppException("微信支付下单失败");
            }
        }else {
            logger.error("微信支付下单调用失败,return_msg: {}",resultMap.get("return_msg"));
            throw new AppException("微信支付调用失败,签名信息错误");
        }
    }

	@Override public boolean addBestPayOrder(String merchantId, String merKey, Integer orderAMT,
	                                         String orderseq, String orderTranseq, String orderTime,
	                                         String productdesc, String productId,
	                                         String customerId, String transCode, String orderCcy,
	                                         String attach, String requestSystem, String encodeType)
			throws Exception {
		logger.info("*******************调用翼支付下单接口,订单号:{}", orderseq);
		//格式化风控参数
		logger.info("下单前orderseq:{}", orderseq);
		logger.info("下单前orderTranseq:{}", orderTranseq);
		String rishControlInfo = RiskControlConfig.obtianRishControlParams();
		//拼接MAC参数
		StringBuilder sb = new StringBuilder();
		sb.append("MERCHANTID=").append(merchantId);
		sb.append("&ORDERSEQ=").append(orderseq);
		sb.append("&ORDERREQTRANSEQ=").append(orderTranseq);
		sb.append("&ORDERREQTIME=").append(orderTime);
		sb.append("&RISKCONTROLINFO=").append(rishControlInfo);
		sb.append("&KEY=").append(merKey);

		logger.info("MD5Param:{}", sb.toString());
		String mac = null;
		try {
			mac = CryptTool.md5Digest(sb.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		logger.info("MAC(MD5):{}", mac);

		//拼接请求参数
		StringBuilder perpareSignParam = new StringBuilder();
		perpareSignParam.append("MERCHANTID=").append(merchantId).append("&SUBMERCHANTID=")
				.append("").append("&ORDERSEQ=").append(orderseq).append("&ORDERREQTRANSEQ=")
				.append(orderTranseq).append("&ORDERREQTIME=").append(orderTime).append("&TRANSCODE=")
				.append("01").append("&ORDERAMT=").append(orderAMT).append("&ORDERCCY=")
				.append("RMB").append("&SERVICECODE=").append("05").append("&PRODUCTID=")
				.append(productId).append("&PRODUCTDESC=").append(productdesc).append("&LOGINNO=")
				.append(customerId).append("&PROVINCECODE=").append("").append("&CITYCODE=").append("")
				.append("&DIVDETAILS=").append("").append("&ENCODETYPE=").append("1").append("&MAC=")
				.append(mac).append("&ATTACH=").append("ture").append("&requestSystem=").append("1")
				.append("&RISKCONTROLINFO=").append(rishControlInfo).append("&MCHNT_BR_CODE=")
				.append("").append("&PGURL=").append(BestpayConfig.BEFORE_BACK_URL).append("&BGURL=")
				.append(BestpayConfig.BACK_MERCHANT_URL);

		String requestBody = perpareSignParam.toString();
		logger.info("HttpRequest:{}" + requestBody);
		//String responseStr = HttpUtil.post(BestpayConfig.BESTPAY_ADD_ORDER, requestBody,"application/x-www-form-urlencoded");
		HttpPost httpPost = new HttpPost(BestpayConfig.BESTPAY_ADD_ORDER);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		StringEntity se = new StringEntity(requestBody, "UTF-8");
		httpPost.setEntity(se);
		HttpResponse httpResponse = HttpClients.createDefault().execute(httpPost);
		String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		logger.info("HttpResponse:{}" + responseStr);
		if (responseStr.startsWith("00")) {
			return true;
		}
		return false;
	}

	@Override public Map<String, Object> obtainPublicKey(String keyIndex, String encryKey,
	                                                     String encryStr, String interCode) {
		Map<String, Object> maps = new HashMap<String, Object>();
		JSONObject jsonParams = new JSONObject();
		jsonParams.put("keyIndex", keyIndex);
		jsonParams.put("encryKey", encryKey);
		jsonParams.put("encryStr", encryStr);
		jsonParams.put("interCode", interCode);
		String requestBody = jsonParams.toJSONString();
		String responseStr = HttpRequestUtils.doPost(BestpayConfig.BESTPAY_PUBLIC_KEY_URL,requestBody);
		//String responseStr = HttpUtil.post(BestpayConfig.BESTPAY_ADD_ORDER, requestBody, "application/json");
		JSONObject respObj = JSONObject.parseObject(responseStr);
		String isSuccess = respObj.getString("success");
		String resultStr = respObj.getString("result");
		if (Boolean.parseBoolean(isSuccess)) {
			JSONObject resultObj = JSONObject.parseObject(resultStr);
			String _keyIndex = resultObj.getString("keyIndex");
			String _pubKey = resultObj.getString("pubKey");
			String _sessionId = resultObj.getString("sessionId");
			String _aesIndex = resultObj.getString("aesIndex");
			if (_keyIndex.length() + _pubKey.length() + _sessionId.length() > 3) {
				keyIndex = _keyIndex;
				encryKey = _pubKey;
				encryStr = _sessionId;
				interCode = _aesIndex;
			}
		}
		System.out.println("获取公钥接口: " + responseStr);
		maps.put("keyIndex", keyIndex);
		maps.put("encryKey", encryKey);
		maps.put("encryStr", encryStr);
		maps.put("interCode", interCode);
		maps.put("responseStr", responseStr);
		return maps;
	}

	@Override public String obtainTransUrl(String publicKeyJson, String merchantId,
	                                       String merchantPWD, String backmerchanturl,
	                                       String orderseq, String orderTranseq, String orderTime,
	                                       String ordervalidityTime, String subject,
	                                       String productId, String orderAMT, String productdesc,
	                                       String customerId, String swtichAcc, String merKey,
	                                       String attachAmount, String busiType, String accountid,
	                                       String userip, String beforeBackUrl,
	                                       String userLanguage) {
		logger.info("*******************调用翼支付H5支付页面,订单号: {}" + orderseq);
		// 获取公钥接口
		Object result = JSONObject.parseObject(publicKeyJson, Resp.class).getResult();
		RsaKeyBO rsaKeyBO = JSONObject.parseObject(result.toString(), RsaKeyBO.class);

		//组装交易数据
		StringBuilder md5Builder = new StringBuilder();
		md5Builder.append("SERVICE=").append("mobile.securitypay.pay").append("&MERCHANTID=")
				.append(merchantId).append("&MERCHANTPWD=").append(merchantPWD)
				.append("&SUBMERCHANTID=").append("").append("&BACKMERCHANTURL=")
				.append(backmerchanturl).append("&ORDERSEQ=").append(orderseq)
				.append("&ORDERREQTRANSEQ=").append(orderTranseq).append("&ORDERTIME=")
				.append(orderTime).append("&ORDERVALIDITYTIME=").append(ordervalidityTime)
				.append("&CURTYPE=").append("RMB").append("&ORDERAMOUNT=").append(orderAMT)
				.append("&SUBJECT=").append(subject).append("&PRODUCTID=").append(productId)
				.append("&PRODUCTDESC=").append(productdesc).append("&CUSTOMERID=").append(customerId)
				.append("&SWTICHACC=").append(swtichAcc).append("&KEY=").append(merKey);
		logger.info("加密参数: {}" + md5Builder.toString());
		String sign = null;
		try {
			sign = CryptTool.md5Digest(md5Builder.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		logger.info("加密后MD5签名: {}" + sign);

		//请求商品参数
		final String paramStr = "MERCHANTID=" + merchantId + "&SUBMERCHANTID=" + ""
		                        + "&MERCHANTPWD=" + merchantPWD + "&ORDERSEQ=" + orderseq
		                        + "&ORDERAMOUNT=" + orderAMT + "&ORDERTIME=" + orderTime
		                        + "&ORDERVALIDITYTIME=" + ordervalidityTime + "&PRODUCTDESC="
		                        + productdesc + "&CUSTOMERID=" + customerId + "&PRODUCTAMOUNT="
		                        + orderAMT + "&ATTACHAMOUNT=" + attachAmount + "&CURTYPE=" + "RMB"
		                        + "&BEFOREMERCHANTURL=" + beforeBackUrl + "&BACKMERCHANTURL="
		                        + backmerchanturl + "&ATTACH=" + "" + "&PRODUCTID=" + productId
		                        + "&USERIP=" + userip + "&DIVDETAILS=" + "" + "&ACCOUNTID="
		                        + accountid + "&BUSITYPE=" + busiType + "&ORDERREQTRANSEQ="
		                        + orderTranseq + "&SERVICE=" + "mobile.securitypay.pay" + "&BANKID="
		                        + "ICBC_B2C" + "&SIGNTYPE=" + "MD5" + "&SIGN=" + sign + "&SUBJECT="
		                        + subject + "&SWTICHACC=" + swtichAcc + "&USERLANGUAGE="
		                        + userLanguage;

		System.out.println("商品请求参数 :" + paramStr);

		String key = AES256.getStringRandom(32);
		//  String key="12345678901234567890123456789012";
		System.out.println("key签名" + sign);
		System.out.println("key值: " + key);
		String encryStr = "";
		try {
			encryStr = AES256.AES_Encode(paramStr, key);
		} catch (Exception e) {
			ObjectUtils.logError("翼支付加密处理异常", e);
		}
		try {
			key = RSA.encrypt(key, rsaKeyBO.getPubKey(), BestpayConfig.SYS_CHARSET);
		} catch (Exception e) {
			ObjectUtils.logError("翼支付密钥处理异常", e);
		}
		String webUrl = BestpayConfig.BETSPAY_GATEWAY_NEW + "?platform=wap_3.0" + "&encryStr="
		                + encryStr + "&keyIndex=" + rsaKeyBO.getKeyIndex() + "&encryKey=" + key;
		String vaildUrl = webUrl.replaceAll("\\+", "%2B");
		logger.info("访问地址: {}" + vaildUrl);
		return vaildUrl;
	}


	@Override
	public BestPayQueryModel findTranOrderByOrderNo(String orderSeqNo, String tranSerialsNo,
	                                                String orderDate) throws Exception {
		logger.info("*******************调用翼支付查询接口,订单号: {}" + orderSeqNo);
		String merchantId = BestpayConfig.BESTPAY_PARTNER;
		String orderNo = orderSeqNo;
		String orderReqNo = tranSerialsNo;
		String KEY = BestpayConfig.MER_KEY;

		StringBuilder sb = new StringBuilder();//组装mac加密明文串
		sb.append("MERCHANTID=").append(merchantId);
		sb.append("&ORDERNO=").append(orderNo);
		sb.append("&ORDERREQNO=").append(orderReqNo);
		sb.append("&ORDERDATE=").append(orderDate);
		sb.append("&KEY=").append(KEY);
		logger.info("MAC原串: {}" + sb);

		String mac = CryptTool.md5Digest(sb.toString());//进行md5加密(商户自己封装MD5加密工具类，此处只提供参考)

		Map<String, String> param = new HashMap<String, String>();//组装请求参数

		param.put("merchantId", merchantId);
		param.put("orderNo", orderNo);
		param.put("orderReqNo", orderReqNo);
		param.put("orderDate", orderDate);
		param.put("mac", mac);

		logger.info("数据原串: {}" + param);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = null;
		CloseableHttpResponse response = null;
		try {
			httpPost = new HttpPost(BestpayConfig.BESTPAY_QUERY_URL);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (String key : param.keySet()) {
				paramList.add(new BasicNameValuePair(key, param.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, Consts.UTF_8));
			httpPost.setConfig(
					RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build());
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			String responseStr = EntityUtils.toString(entity);
			if (HttpStatus.SC_OK == statusCode) {
				//如果响应码是200
				logger.info("查询支付信息: {}" + responseStr);
			}

			JSONObject jsonObject = (JSONObject) JSONObject.parse(responseStr);
			boolean success = jsonObject.getBoolean("success");
			if (!success) {
				return null;
			}

			String result = jsonObject.getString("result");
			BestPayQueryModel bestPayQueryModel = new BestPayQueryModel();
			if (StringUtils.isNotBlank(result)) {
				bestPayQueryModel = JSONObject.parseObject(jsonObject.getString("result"),
						BestPayQueryModel.class);
				if (null == bestPayQueryModel) {
					logger.info("获取订单支付流水号详情失败,orderSeqNo: {}" +  orderSeqNo);
				} else {

				}
			}

			return bestPayQueryModel;
		} catch (Exception e) {
			ObjectUtils.logError("查询信息出错: ", e);
		}
		return null;
	}
}
