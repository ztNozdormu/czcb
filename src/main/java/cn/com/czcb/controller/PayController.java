package cn.com.czcb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.com.czcb.model.BestPayQueryModel;
import cn.com.czcb.model.BestPayRecord;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.model.User;
import cn.com.czcb.model.WechatPayRecord;
import cn.com.czcb.pub.AppConstant;
import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.pay.PayConstants;
import cn.com.czcb.pub.pay.WechatPayUtil;
import cn.com.czcb.pub.pay.XMLUtil;
import cn.com.czcb.pub.pay.bestpay.BestpayConfig;
import cn.com.czcb.pub.pay.bestpay.CryptTool;
import cn.com.czcb.pub.pay.bestpay.MD5Utils;
import cn.com.czcb.service.IBestPayRecordService;
import cn.com.czcb.service.IChargeOrderService;
import cn.com.czcb.service.IPayService;
import cn.com.czcb.service.IWechatPayRecordService;


/**
 * 
 * @author AJiong
 * @version $Id: PayController.java, v 0.1 2018年1月29日 下午2:49:06 Ajiong Exp $
 */
@RequestMapping("pay")
@ResponseBody
@Controller
public class PayController extends BaseController {
    /**
     * 日志对象
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 充值订单服务
     */
    @Autowired
    private IChargeOrderService     chargeOrderService;
    /**
     * 微信支付记录服务
     */
    @Autowired
    private IWechatPayRecordService wechatPayRecordService;
    /**
     * 翼支付记录服务
     */
    @Autowired
    private IBestPayRecordService   bestPayRecordService;
	/**
	 * 微信支付服务接口
	 */
	@Autowired
	private IPayService             payService;
    /**
     * 微信支付回调函数
     * @param request
     * @param response
     * @return string
     */
    @RequestMapping("wechatNotify")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        try {
            SortedMap<String, Object> paramsMap = readRequest(request);
	        String wechatResult = JSON.toJSONString(paramsMap);
	        logger.info("微信支付异步参数：{}",wechatResult);
            if (WechatPayUtil.isTenpaySign(paramsMap)) {
                if ("SUCCESS".equals((String) paramsMap.get("result_code"))) {
                    // 将微信结果参数转换为Object
                    WechatPayRecord wechatPayRecord = JSONObject
                            .parseObject(wechatResult, WechatPayRecord.class);
                    //商户号
                    String mchId = wechatPayRecord.getMch_id();
                    //商户订单号
                    String outTradeNo = wechatPayRecord.getOut_trade_no();
                    Integer total_fee = wechatPayRecord.getTotal_fee();
                    // 根据订单号查询订单
                    ChargeOrder chargeOrder = this.chargeOrderService.getModelById(outTradeNo);
                    if (!InitConfig.getMchId().equals(mchId) || chargeOrder == null
                        || new BigDecimal(total_fee)
                            .compareTo(new BigDecimal(chargeOrder.getChargeNum())) != 0) {
                        logger.info("支付失败,错误信息:" + "参数错误");
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                 + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                    } else {
                        if (PayConstants.PAY_STATUS_UNPAY.equals(chargeOrder.getStatus())) {
	                        wechatPayRecord.setId(UUID.randomUUID().toString());
                            //创建支付记录
                            wechatPayRecordService.doAddModel(wechatPayRecord);
                            //订单状态的修改。根据实际业务逻辑执行                    
                            chargeOrder.setStatus(PayConstants.PAY_STATUS_PAID);
                            this.chargeOrderService.doUpdateModel(chargeOrder);
                        } else {
                            logger.info("订单已处理,订单号: {}", outTradeNo);
                        }
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                 + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    }
                } else {
                    logger.info("支付失败,错误信息：" + paramsMap.get("err_code"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                             + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }
            }else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
               logger.info("通知签名验证失败");
           }
        } catch (Exception e) {
        	e.printStackTrace();
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }
	    logger.info("返回给微信支付XML: {}",resXml);
        return resXml;
    }

	/**
	 * 翼支付异步回调地址
	 * @param uptranSeq 翼支付网关平台交易流水号
	 * @param merchantId 商户号
	 * @param tranDate 翼支付网关平台交易日期
	 * @param retnInfo 处理结果解释码
	 * @param retnCode 处理结果码
	 * @param orderReqTranSeq 订单请求交易流水号
	 * @param orderSeq 订单号
	 * @param orderAmount 订单总金额
	 * @param productAmount 产品金额
	 * @param attachAmount 附加金额
	 * @param curType 币种
	 * @param encodeType 加密方式
	 * @param bankId 银行编码
	 * @param attach 商户附加信息
	 * @param upReqTranSeq 网关平台请求交易流水号
	 * @param upBankTranSeq 银行流水号
	 * @param productNo 产品号码
	 * @param signInfo 数字签名
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping("bestPayNotify")
	@ResponseBody
	public String payCallbackInfo(@RequestParam(value = "UPTRANSEQ", required = false) String uptranSeq,
	                              @RequestParam(value = "MERCHANTID", required = false) String merchantId,
	                              @RequestParam(value = "TRANDATE", required = false) String tranDate,
	                              @RequestParam(value = "RETNINFO", required = false) String retnInfo,
	                              @RequestParam(value = "RETNCODE", required = false) String retnCode,
	                              @RequestParam(value = "ORDERREQTRANSEQ", required = false) String orderReqTranSeq,
	                              @RequestParam(value = "ORDERSEQ", required = false) String orderSeq,
	                              @RequestParam(value = "ORDERAMOUNT", required = false) String orderAmount,
	                              @RequestParam(value = "PRODUCTAMOUNT", required = false) String productAmount,
	                              @RequestParam(value = "ATTACHAMOUNT", required = false) String attachAmount,
	                              @RequestParam(value = "CURTYPE", required = false) String curType,
	                              @RequestParam(value = "ENCODETYPE", required = false) String encodeType,
	                              @RequestParam(value = "BANKID", required = false) String bankId,
	                              @RequestParam(value = "ATTACH", required = false) String attach,
	                              @RequestParam(value = "UPREQTRANSEQ", required = false) String upReqTranSeq,
	                              @RequestParam(value = "UPBANKTRANSEQ", required = false) String upBankTranSeq,
	                              @RequestParam(value = "PRODUCTNO", required = false) String productNo,
	                              @RequestParam(value = "SIGN", required = false) String signInfo,
	                              HttpServletRequest request) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("UPTRANSEQ=").append(uptranSeq).append("&MERCHANTID=").append(merchantId)
				.append("&ORDERSEQ=").append(orderSeq).append("&ORDERAMOUNT=").append(orderAmount)
				.append("&RETNCODE=").append(retnCode).append("&RETNINFO=").append(retnInfo)
				.append("&TRANDATE=").append(tranDate).append("&KEY=")
				.append(BestpayConfig.MER_KEY);

		logger.info("回调参数信息: {}", sb.toString());
		String sign = MD5Utils.signature(sb.toString()).toUpperCase();
		logger.info("sign: {}", sign);
		logger.info("signInfo: {}", signInfo);
		//此处不做状态回写
		if (!sign.equals(signInfo)) {
			logger.error("签名数据不一致,不做数据应答.sign:{},signInfo:{},orderSeq ", sign, signInfo, orderSeq);
			return "";
		}
		logger.info("支付完成{}", orderSeq);
		//根据订单号查询订单
		ChargeOrder chargeOrder = this.chargeOrderService.getModelById(orderSeq);
		//如果订单未创建支付记录
		if (PayConstants.PAY_STATUS_UNPAY.equals(chargeOrder.getStatus())) {
			// 更改支付状态
			// 创建支付记录
			doAddBestPayRecord(uptranSeq, merchantId, tranDate, retnInfo, retnCode, orderReqTranSeq, orderSeq,
					orderAmount, productAmount, attachAmount, curType, encodeType, bankId, attach,
					upReqTranSeq, upBankTranSeq, productNo, signInfo);

			//	修改订单状态
			chargeOrder.setStatus(PayConstants.PAY_STATUS_PAID);
			this.chargeOrderService.doUpdateModel(chargeOrder);
			logger.info("充值完成,订单号:{}", orderSeq);
		}

		StringBuffer sb2 = new StringBuffer();
		sb2.append("UPTRANSEQ=").append(uptranSeq).append("&MERCHANTID=").append(merchantId)
				.append("&ORDERSEQ=").append(orderSeq).append("&ORDERAMOUNT=").append(orderAmount)
				.append("&RETNCODE=").append(retnCode).append("&RETNINFO=").append(retnInfo)
				.append("&TRANDATE=").append(tranDate).append("&KEY=")
				.append(InitConfig.getBestpayTransactionKey());
		logger.info("回调参数信息:{}", sb2.toString());
		String sign2 = CryptTool.md5Digest(sb2.toString());
		if (!signInfo.equals(sign2)) {
			return null;
		}
		String returnMsg = "UPTRANSEQ_" + uptranSeq;
		logger.info("支付应答:{}", returnMsg);
		return returnMsg;

	}

	/**
	 * 前台返回页面
	 * @param request
	 * @param resultCode 结果状态码
	 * @param orderSeq 订单号
	 * @param orderAmt 订单金额，单位：元
	 * @param result 支付结果
	 * @param response
	 * @return string
	 * @throws Exception
	 */
	@RequestMapping("beforeCall")
	public String beforeCall(HttpServletRequest request,String resultCode,String orderSeq,String orderAmt,String result,HttpServletResponse response) throws Exception{
		ChargeOrder chargeOrder = this.chargeOrderService.getModelById(orderSeq);
		try {
			if (!"-1".equals(resultCode)) {
				logger.info(result + " : " + orderSeq);
				//支付失败
				response.sendRedirect(InitConfig.getwReturnPrefix() + "");
				return null;
			}
			String orderDate = ObjectUtils.formatDate(chargeOrder.getCreateTime(), "yyyyMMdd");
			//查询是否支付成功
			BestPayQueryModel findTranOrderByOrderNo = payService.findTranOrderByOrderNo(orderSeq, chargeOrder.getSerialNum(), orderDate);
			if (findTranOrderByOrderNo == null || !"B".equals(findTranOrderByOrderNo.getTransStatus())) {
				logger.info("支付失败,订单号:{} ", orderSeq);
				// 支付失败
				response.sendRedirect(InitConfig.getwReturnPrefix() + "");
				return null;
			}
			if (PayConstants.PAY_STATUS_PAID.equals(chargeOrder.getStatus())) {
				response.sendRedirect(InitConfig.getwReturnPrefix() + "");
			}else {
				response.sendRedirect(InitConfig.getwReturnPrefix() + "");
			}
		} catch (Exception e) {
			ObjectUtils.logError(e.getMessage(), e);
			response.sendRedirect(InitConfig.getwReturnPrefix() + "");
		}
		return null;
	}

    /**
     * 请求中获取参数
     * @param request
     * @return xml参数
     */
    private SortedMap<String, Object> readRequest(HttpServletRequest request) {
        InputStream inputStream = null;
        ;
        BufferedReader in = null;
        try {
            //读取参数  
            StringBuffer sb = new StringBuffer();
            inputStream = request.getInputStream();
            String s;
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            String paramsXML = sb.toString();
            Map<String, Object> paramsMap = XMLUtil.doXMLParse(paramsXML);
            //过滤空 设置 TreeMap  
            SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
            Iterator<String> it = paramsMap.keySet().iterator();
            while (it.hasNext()) {
                String parameter = (String) it.next();
                String parameterValue = paramsMap.get(parameter).toString();

                String v = "";
                if (null != parameterValue) {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            return packageParams;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                in.close();
                inputStream.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }

	private void doAddBestPayRecord(@RequestParam(value = "UPTRANSEQ", required = false) String uptranSeq,
	                                @RequestParam(value = "MERCHANTID", required = false) String merchantId,
	                                @RequestParam(value = "TRANDATE", required = false) String tranDate,
	                                @RequestParam(value = "RETNINFO", required = false) String retnInfo,
	                                @RequestParam(value = "RETNCODE", required = false) String retnCode,
	                                @RequestParam(value = "ORDERREQTRANSEQ", required = false) String orderReqTranSeq,
	                                @RequestParam(value = "ORDERSEQ", required = false) String orderSeq,
	                                @RequestParam(value = "ORDERAMOUNT", required = false) String orderAmount,
	                                @RequestParam(value = "PRODUCTAMOUNT", required = false) String productAmount,
	                                @RequestParam(value = "ATTACHAMOUNT", required = false) String attachAmount,
	                                @RequestParam(value = "CURTYPE", required = false) String curType,
	                                @RequestParam(value = "ENCODETYPE", required = false) String encodeType,
	                                @RequestParam(value = "BANKID", required = false) String bankId,
	                                @RequestParam(value = "ATTACH", required = false) String attach,
	                                @RequestParam(value = "UPREQTRANSEQ", required = false) String upReqTranSeq,
	                                @RequestParam(value = "UPBANKTRANSEQ", required = false) String upBankTranSeq,
	                                @RequestParam(value = "PRODUCTNO", required = false) String productNo,
	                                @RequestParam(value = "SIGN", required = false) String signInfo) {
		BestPayRecord payRecord = new BestPayRecord();
		payRecord.setId(UUID.randomUUID().toString());
		payRecord.setAttach(attach);
		payRecord.setAttachAmount(attachAmount);
		payRecord.setBankId(bankId);
		payRecord.setCurType(curType);
		payRecord.setEncodeType(encodeType);
		payRecord.setMerchantId(merchantId);
		payRecord.setOrderAmount(orderAmount);
		payRecord.setOrderId(orderSeq);
		payRecord.setOrderReqTranSeq(orderReqTranSeq);
		payRecord.setProductAmount(productAmount);
		payRecord.setProductNo(productNo);
		payRecord.setRetnCode(retnCode);
		payRecord.setRetnInfo(retnInfo);
		payRecord.setSignInfo(signInfo);
		payRecord.setTranDate(tranDate);
		payRecord.setUpBankTranSeq(upBankTranSeq);
		payRecord.setUpReqTranSeq(upReqTranSeq);
		payRecord.setUptranSeq(uptranSeq);
		this.bestPayRecordService.doAddModel(payRecord);
	}
}
