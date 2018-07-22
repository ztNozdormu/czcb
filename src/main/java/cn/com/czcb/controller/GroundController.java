package cn.com.czcb.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.interceptor.SessionInterceptor;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.model.Feedback;
import cn.com.czcb.model.User;
import cn.com.czcb.model.VisitOrderLog;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.RedisUtil;
import cn.com.czcb.pub.SmsUtil;
import cn.com.czcb.pub.pay.IDUtils;
import cn.com.czcb.pub.pay.PayConstants;
import cn.com.czcb.pub.pay.WechatPayUtil;
import cn.com.czcb.pub.pay.bestpay.BestpayConfig;
import cn.com.czcb.service.IChargeOrderService;
import cn.com.czcb.service.IFeedbackProblemService;
import cn.com.czcb.service.IFeedbackService;
import cn.com.czcb.service.IFeedbackTypeService;
import cn.com.czcb.service.IPayService;
import cn.com.czcb.service.ISmsCodeService;
import cn.com.czcb.service.IUserService;
import cn.com.czcb.service.IVisitOrderLogService;
import cn.com.czcb.util.FileUtil;
import cn.com.czcb.util.UUIDUtil;

/**
 * 前端访问控制类
 * 
 * @author AJiong
 * @version $Id: GroundController.java, v 0.1 2017年11月7日 下午3:47:22 Ajiong Exp $
 */
@RequestMapping("ground")
@ResponseBody
@Controller
public class GroundController extends BaseController {
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 用户服务
	 */
	@Autowired
	private IUserService userService;
	/**
	 * 短信验证码服务
	 */
	@Autowired
	private ISmsCodeService smsCodeService;
	/**
	 * 充值订单服务
	 */
	@Autowired
	private IChargeOrderService chargeOrderService;
	/**
	 * 微信支付服务接口
	 */
	@Autowired
	private IPayService payService;
	/**
	 * 记录访问日志
	 */
	@Autowired
	private IVisitOrderLogService visitOrderService;
	/**
	 * redis
	 */
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IFeedbackTypeService feedbackTypeService;

	@Autowired
	private IFeedbackProblemService feedbackProblemService;

	@Autowired
	private IFeedbackService feedbackService;

	/**
	 * 小程序首页
	 * 
	 * @param request
	 * @return string
	 */
	// @RequestMapping(value="index",method=RequestMethod.POST)
	public String index(HttpServletRequest request) {
		try {
			UserSession userSession = AppContext.getCurrUserSession();
			QueryParams logsParams = new QueryParams();
			logsParams.addParameter("userId", userSession.getUserId());
			logsParams.addOrderBy("createTime", false);
			logsParams.setStart(0);
			logsParams.setSize(1);
			List<VisitOrderLog> visitList = visitOrderService.queryList(logsParams);
			Long lastVisitTime = 0L;
			if (visitList != null && visitList.size() > 0) {
				lastVisitTime = visitList.get(0).getCreateTime();
			}
			QueryParams params = new QueryParams();
			params.addParameter("userId", userSession.getUserId());
			params.addParameter("createTime", lastVisitTime);
			int unReadCount = chargeOrderService.queryUnReadCount(params);
			Map<String, Object> result = new HashMap<>();
			result.put("unReadCount", unReadCount);
			return returnJsonResult(result);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}
	}

	/**
	 * 查询充值订单
	 * 
	 * @param request
	 * @return string
	 */
	@RequestMapping(value = "findChargeOrder", method = RequestMethod.POST)
	public String findChargeOrder(HttpServletRequest request) {
		try {
			UserSession userSession = AppContext.getCurrUserSession();
			User user = this.userService.getModelById(userSession.getUserId());
			QueryParams params = new QueryParams();
			params.addParameter("userId", user.getId());
			params.addParameter("status", PayConstants.PAY_STATUS_PAID);
			List<ChargeOrder> chargeOrderList = chargeOrderService.queryList(params);
			// 增加查询流水
			visitOrderService.doAddModel(
					new VisitOrderLog(UUID.randomUUID().toString(), user.getId(), System.currentTimeMillis(), ""));
			Map<String, Object> result = new HashMap<>();
			if (chargeOrderList != null && chargeOrderList.size() > 0) {
				result.put("chargeOrderList", chargeOrderList);
			}
			return returnJsonResult(result);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param phone
	 * @param smsToken
	 * @param request
	 * @param response
	 * @return page
	 */
	@RequestMapping(value = "/sendSmsCode", method = RequestMethod.POST)
	public String sendSmsCode(@RequestParam(required = true) String phone,
			@RequestParam(required = true) String smsToken, HttpServletRequest request, HttpServletResponse response) {
		try {
			String sessionToken = request.getHeader(SessionInterceptor.KEYDOM_SESSION_TOKEN);
			if (ObjectUtils.isEmpty(phone) || !ObjectUtils.isMobile(phone)) {
				throw new AppException("请填写正确的手机号", 1);
			}
			String smsSessionToken = (String) redisUtil.get("sms_" + sessionToken);
			if (ObjectUtils.isEmpty(smsSessionToken) || !smsSessionToken.equals(smsToken)) {
				throw new AppException("页面已过期，请刷新页面重试", 1);
			}
			// 查询该手机号是否超过5次限制
			QueryParams params = new QueryParams();
			params.addParameter("phone", phone);
			params.addParameterByRange("createTime", ObjectUtils.dayMinMills(System.currentTimeMillis()),
					ObjectUtils.dayMaxMills(System.currentTimeMillis()));
			int smsCodeCount = smsCodeService.queryCount(params);
			if (smsCodeCount >= 5) {
				logger.error("该手机号今日发送验证码已超过限制");
				throw new AppException("该手机号今日发送验证码已超过限制(5次)", 1);
			}
			// 开始发送验证码
			String code = SmsUtil.createCode(6);
			smsCodeService.sendSms(phone, code);
			redisUtil.set("phone_" + sessionToken, phone, 300L);
			logger.info("手机号：{} 发送的短信验证码是： {}", phone, code);
			return returnJsonResult(SUCCESS);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}

	}

	/**
	 * 修改手机号
	 * 
	 * @param phone
	 * @param code
	 * @param request
	 * @param response
	 * @return page
	 */
	@RequestMapping(value = "/submitPhone", method = RequestMethod.POST)
	public String submitPhone(@RequestParam(required = true) String phone, @RequestParam(required = true) String code,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String sessionToken = request.getHeader(SessionInterceptor.KEYDOM_SESSION_TOKEN);
			if (ObjectUtils.isEmpty(phone) || !ObjectUtils.isMobile(phone)) {
				throw new AppException("请填写正确的手机号", 1);
			}
			String sessionPhone = (String) redisUtil.get("phone_" + sessionToken);
			if (ObjectUtils.isEmpty(sessionPhone)) {
				throw new AppException("页面已过期，请刷新页面重试", 1);
			} else {
				if (!phone.equals(sessionPhone)) {
					throw new AppException("当前手机号与获取验证码手机号不匹配", 1);
				}
			}
			Boolean succeed = smsCodeService.validateSmsCode(phone, code);
			if (!succeed) {
				// 验证未通过
				throw new AppException("验证码错误", 1);
			}
			UserSession userSession = AppContext.getCurrUserSession();
			User user = this.userService.getModelById(userSession.getUserId());
			user.setPhone(phone);
			this.userService.doUpdateModel(user);
			logger.info("填写手机号成功,userId:{},phone:{}", userSession.getUserId(), phone);
			return returnJsonResult(SUCCESS);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}
	}

	/**
	 * 发起微信支付
	 * 
	 * @param chargeNum
	 *            充值金额（分）
	 * @param cardNo
	 *            充值卡号
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping(value = "wechatPay", method = RequestMethod.POST)
	public String wechatPay(@RequestParam(required = true) Integer chargeNum,
			@RequestParam(required = true) String cardNo, HttpServletRequest request, HttpServletResponse response) {
		try {
			UserSession userSession = AppContext.getCurrUserSession();
			String openid = userSession.getOpenid();
			// 将元转换为分
			// Integer chargeNumCent = new BigDecimal(chargeNum).multiply(new
			// BigDecimal(100)).intValue();
			// 在本地创建订单
			ChargeOrder chargeOrder = doChargeOrder(chargeNum, cardNo, userSession);
			// 创建微信支付订单
			SortedMap<String, Object> parameters = new TreeMap<String, Object>();
			parameters.put("appid", InitConfig.getAppid());
			parameters.put("mch_id", InitConfig.getMchId());
			parameters.put("nonce_str", WechatPayUtil.createNoncestr(16));
			parameters.put("body",  new String("黑晶芯-天府通充值".getBytes(),"utf-8"));
			parameters.put("out_trade_no", chargeOrder.getId()); // 订单id
			parameters.put("fee_type", "CNY");
			parameters.put("total_fee", chargeNum.toString());
			parameters.put("spbill_create_ip", ObjectUtils.getIpAddr(request));
			parameters.put("notify_url", InitConfig.getNotifyUrl());
			parameters.put("trade_type", "JSAPI");
			parameters.put("openid", openid);
			String sign = WechatPayUtil.createSign(parameters);
			// 签名
			parameters.put("sign", sign);
			Map<String, Object> wechatMap = payService.createWechatPayOrder(parameters);
			// 封装JS拉起微信支付所需参数
			SortedMap<String, Object> resultMap = new TreeMap<String, Object>();
			resultMap.put("appId", InitConfig.getAppid());
			resultMap.put("timeStamp", System.currentTimeMillis() / 1000);
			resultMap.put("nonceStr", WechatPayUtil.createNoncestr(16));
			resultMap.put("package", "prepay_id=" + wechatMap.get("prepay_id"));
			resultMap.put("signType", "MD5");
			String jsSign = WechatPayUtil.createSign(resultMap);
			resultMap.put("paySign", jsSign);
			return returnJsonResult(resultMap);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}
	}

	/**
	 * 发起翼支付
	 * 
	 * @param chargeNum
	 *            充值金额（分）
	 * @param cardNo
	 *            充值卡号
	 * @param request
	 * @param response
	 * @return String
	 */
	// @RequestMapping(value="bestPay",method=RequestMethod.POST)
	public String bestPay(@RequestParam(required = true) Integer chargeNum,
			@RequestParam(required = true) String cardNo, HttpServletRequest request, HttpServletResponse response) {
		try {
			UserSession userSession = AppContext.getCurrUserSession();
			User user = this.userService.getModelById(userSession.getUserId());
			// 将元转换为分
			// Integer chargeNumCent = new BigDecimal(chargeNum).multiply(new
			// BigDecimal(100)).intValue();
			// 在本地创建订单
			ChargeOrder chargeOrder = doChargeOrder(chargeNum, cardNo, userSession);
			// 创建翼支付订单
			String orderId = chargeOrder.getId();
			String serialNum = IDUtils.generateSerialNum(chargeOrder.getId());
			String orderTime = ObjectUtils.formatDate(chargeOrder.getCreateTime(), "yyyyMMddhhmmss");
			boolean result = payService.addBestPayOrder(BestpayConfig.BESTPAY_PARTNER, BestpayConfig.MER_KEY, chargeNum,
					orderId, serialNum, orderTime, "黑晶芯-天府通充值", "04", "", "01", "RMB", "true", "1", "1");
			// 下单成功，获取公钥接口
			if (!result) {
				throw new AppException("下单失败");
			}
			logger.info("下单成功！");
			BigDecimal chargeNumYuan = new BigDecimal(chargeNum).divide(new BigDecimal(100));
			Map<String, Object> publicKeyJson = payService.obtainPublicKey("", "", "",
					BestpayConfig.BESTPAY_INTER_SYSTEM);
			String obtainTransUrl = payService.obtainTransUrl(publicKeyJson.get("responseStr").toString(),
					BestpayConfig.BESTPAY_PARTNER, BestpayConfig.BESTPAY_DATA_KEY, InitConfig.getBackMerchantUrl(),
					orderId, serialNum, orderTime, "", "黑晶芯-天府通充值", "04", chargeNumYuan + "", "黑晶芯-天府通充值",
					user.getPhone(), "true", BestpayConfig.MER_KEY, "0", "04", "", "127.0.0.1",
					InitConfig.getBeforeBackUrl(), "zh");
			logger.info("支付地址{}", obtainTransUrl);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("url", obtainTransUrl);
			return returnJsonResult(resultMap);
		} catch (Exception e) {
			return returnJsonResultWithError(e);
		}
	}

	/**
	 * 创建充值订单
	 * 
	 * @param chargeNum
	 * @param cardNo
	 * @param userSession
	 * @return
	 */
	private ChargeOrder doChargeOrder(Integer chargeNum, String cardNo, UserSession userSession) {
		ChargeOrder chargeOrder = new ChargeOrder();
		String orderId = WechatPayUtil.generateOrderId();
		String serialNum = WechatPayUtil.generateSerialNum(orderId);
		chargeOrder.setId(orderId);
		chargeOrder.setUserId(userSession.getUserId());
		chargeOrder.setChargeNum(chargeNum);
		chargeOrder.setCardNo(cardNo);
		chargeOrder.setSerialNum(serialNum);
		chargeOrder.setStatus(PayConstants.PAY_STATUS_UNPAY);
		chargeOrder.setPayType(PayConstants.PAY_TYPE_WECHAT);
		chargeOrder.setCreateTime(System.currentTimeMillis());
		this.chargeOrderService.doAddModel(chargeOrder);
		return chargeOrder;
	}

	/**
	 * * 反馈类型列表
	 * 
	 * @return list<T>
	 */
	@RequestMapping("/feedbackTypeList")
	@ResponseBody
	public Object feedbackTypeList() {
		QueryParams params = new QueryParams();
		params.addParameter("deleteFlag", 0);
		params.addOrderBy("createTime", false);
		return feedbackTypeService.queryList(params);
	}

	/**
	 * * 问题列表
	 * 
	 * @return list<T>
	 */
	@RequestMapping("/feedbackProblemList")
	@ResponseBody
	public Object feedbackProblemList(String typeId) {
		QueryParams params = new QueryParams();
		params.addParameter("deleteFlag", 0);
		params.addOrderBy("createTime", false);
		if (StringUtils.isNoneBlank(typeId)) {
			params.addParameter("typeId", typeId);
		}
		return feedbackProblemService.queryList(params);
	}

	/**
	 * 意见反馈保存
	 * 
	 * @param feedback
	 * @param request
	 * @return result
	 */
	@RequestMapping("/saveFeedback")
	@ResponseBody
	public Object saveFeedback(@ModelAttribute Feedback feedback, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<>();
		try {
			// 获取用户
			UserSession userSession = AppContext.getCurrUserSession();
			User user = this.userService.getModelById(userSession.getUserId());
			if (user != null) {
				feedback.setPhone(user.getPhone());
			}
			feedback.setCreateTime(new Date().getTime());
			feedback.setId(UUIDUtil.uuid());
			feedbackService.doAddModel(feedback);
			result.put("status", true);
		} catch (Exception e) {
			result.put("status", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}

	/**
	 * wx小程序图片上传
	 * 
	 * @param request
	 * @return 图片保存的服务器路径
	 * @throws Exception
	 */
//	@RequestMapping("/saveFeedbackImg")
//	@ResponseBody
//	public Object uploadPicture(HttpServletRequest request) {
//		Map<String, Object> result = new HashMap<>();
//		try {
//			String imgPath = fileUtil.saveImg(request);
//			result.put("status", true);
//			result.put("imgPath", imgPath);
//		} catch (Exception e) {
//			result.put("status", false);
//			result.put("msg", e.getMessage());
//		}
//		return result;
//	}
	
	/**
	 * wx小程序图片上传 by tomcat临时缓存保存
	 * 
	 * @param request
	 * @return 图片保存的服务器路径
	 * @throws Exception
	 */
	@RequestMapping("/saveFeedbackImg")
	@ResponseBody
	public Object saveFeedbackImgBycache(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			String imgPath = FileUtil.saveImgByCache(request);
			result.put("status", true);
			result.put("imgPath", imgPath);
		} catch (Exception e) {
			result.put("status", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
}
