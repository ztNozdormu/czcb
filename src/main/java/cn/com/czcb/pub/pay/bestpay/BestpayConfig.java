package cn.com.czcb.pub.pay.bestpay;

import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.com.czcb.pub.InitConfig;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究翼支付接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约翼支付账号登录翼支付网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class BestpayConfig {

	// 翼支付支付网关
	public static final String BETSPAY_GATEWAY_NEW = "https://capi.bestpay.com.cn/gateway.pay";

	// 翼支付下单地址
	public static final String BESTPAY_ADD_ORDER = "https://webpaywg.bestpay.com.cn/order.action";

	public static final String BESTPAY_URL = "https://capi.bestpay.com.cn";

	/** 公钥请求接口 */
	public static final String BESTPAY_PUBLIC_KEY_URL = "https://capi.bestpay.com.cn/common/interface";

	/** 查询地址 */
	public static final String BESTPAY_QUERY_URL = "https://webpaywg.bestpay.com.cn/query/queryOrder";

	/** 公钥参数 */
	public static final String BESTPAY_INTER_SYSTEM = "INTER.SYSTEM.001";

	// 翼支付回调地址

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 合作商户号
	 */
	public static String BESTPAY_PARTNER = InitConfig.getBestpayPartner();
	// 交易Key
	public static String MER_KEY = InitConfig.getBestpayTransactionKey();
	// 商户交易密码
	public static String BESTPAY_DATA_KEY = InitConfig.getBestpayDataKey();
	// 回调前台地址
	public static String BEFORE_BACK_URL = InitConfig.getBeforeBackUrl();
	// 异步回调地址
	public static String BACK_MERCHANT_URL = InitConfig.getBackMerchantUrl();

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	//public static String log_path = "/Users/Documents/java/logs";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "MD5";
	/**
	 * 系统编码
	 */
	public static final String SYS_CHARSET = "UTF-8";

	/**
	 * 
	 * @param merchantId
	 *            商户号
	 * @param merKey
	 *            交易密码
	 * @param orderAMT
	 *            交易金额
	 * @param orderseq
	 *            订单号
	 * @param orderTranseq
	 *            订单流水号
	 * @param orderTime
	 *            订单时间
	 * @param productdesc
	 *            商品描述
	 * @param productId
	 *            产品ID
	 * @param customerId
	 *            用户手机号
	 * @throws Exception
	 */
	public void addBestPayOrder(String merchantId, String merKey, Float orderAMT, String orderseq, String orderTranseq,
			Date orderTime, String productdesc, String productId, String customerId) throws Exception {

		// 拼接MAC参数
		String rishControlInfo = RiskControlConfig.obtianRishControlParams();
		StringBuilder sb = new StringBuilder();
		sb.append("MERCHANTID=").append(merchantId);
		sb.append("&ORDERSEQ=").append(orderseq);
		sb.append("&ORDERREQTRANSEQ=").append(orderTranseq);
		sb.append("&ORDERREQTIME=").append(orderTime);
		sb.append("&RISKCONTROLINFO=").append(rishControlInfo);
		sb.append("&KEY=").append(merKey);

		System.out.println("MD5Param:" + sb.toString());
		String mac = null;
		try {
			mac = CryptTool.md5Digest(sb.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("MAC(MD5):" + mac);

		// 拼接请求参数
		StringBuilder perpareSignParam = new StringBuilder();
		perpareSignParam.append("MERCHANTID=").append(merchantId).append("&SUBMERCHANTID=").append("")
				.append("&ORDERSEQ=").append(orderseq).append("&ORDERREQTRANSEQ=").append(orderTranseq)
				.append("&ORDERREQTIME=").append(orderTime).append("&TRANSCODE=").append("01").append("&ORDERAMT=")
				.append(orderAMT.intValue()).append("&ORDERCCY=").append("RMB").append("&SERVICECODE=").append("05")
				.append("&PRODUCTID=").append(productId).append("&PRODUCTDESC=").append(productdesc).append("&LOGINNO=")
				.append(customerId).append("&PROVINCECODE=").append("").append("&CITYCODE=").append("")
				.append("&DIVDETAILS=").append("").append("&ENCODETYPE=").append("1").append("&MAC=").append(mac)
				.append("&ATTACH=").append("ture").append("&requestSystem=").append("1").append("&RISKCONTROLINFO=")
				.append(rishControlInfo);

		String requestBody = perpareSignParam.toString();
		System.out.println("HttpRequest:" + requestBody);
		HttpPost httpPost = new HttpPost(BESTPAY_ADD_ORDER);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		StringEntity se = new StringEntity(requestBody, "UTF-8");
		httpPost.setEntity(se);
		HttpResponse httpResponse = HttpClients.createDefault().execute(httpPost);
		String responseStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		System.out.println("HttpResponse:" + responseStr);
	}

}