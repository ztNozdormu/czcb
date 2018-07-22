package cn.com.czcb.pub;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;



/**
 * 
 * @author AJiong
 * @version $Id: InitConfig.java, v 0.1 2017年11月8日 上午11:44:08 Ajiong Exp $
 */
public class InitConfig {
	/**  */
	private static String classPath;
	/**
	 * 回调地址
	 */
	private static String wReturnPrefix;
	/**
	 * 小程序appid
	 */
	private static String appid;
	/**
	 * 小程序secret
	 */
	private static String secret;
	/**
	 * 商户号
	 */
	private static String mchId;
	/**
	 * 微信支付异步回调地址
	 */
	private static String notifyUrl;
	/**
	 * 商户验签key
	 */
	private static String apiKey;
	/**
	 * 天府通私钥
	 */
	private static String tftPrivateKey;
	/**
	 * 天府通公钥
	 */
	private static String tftPublicKey;
	/**
	 * 第三方渠道号
	 */
	private static String thirdChannelCode;
	/** 天府通圈存接口地址 */
	private static String tftAccountTransferUrl;
	/** 天府通圈存确认接口地址 */
	private static String tftAccountTransferConfirmUrl;
	/**
	 * 天府通帐号余额查询接口
	 */
	private static String tftAccountBalanceUrl;
    /**
    * 合作商户号
    */
	private static String bestpayPartner;
	/**
	 * 交易Key
	 */
	private static String bestpayTransactionKey;
	/**
	 * 商户交易密码
	 */
	private static String bestpayDataKey;
	/**
	 * 回调前台地址
	 */
	private static String beforeBackUrl;
	/**
	 * 异步回调地址
	 */
	private static String backMerchantUrl;
	/**
	 * 商品展示地址
	 */
	private static String productShowUrl;
	/**
	 * 系统配置
	 */
	private static String fileUploadFolder;//上传文件保存目录
	/**
	 * 系统配置
	 */
	private static String tmpFiledir;//临时文件位置
	/**  */
	@SuppressWarnings("serial")
	private static Map<String,String> provenceMap = new LinkedHashMap<String, String>(){
		{
				put("bj","北京");
				put("tj","天津");
				put("heb","河北");
				put("sx1","山西");
				put("nmg","内蒙古");
				put("ll","辽宁");
				put("jl","吉林");
				put("hlj","黑龙江");
				put("sh","上海");
				put("js","江苏");
				put("zj","浙江");
				put("ah","安徽");
				put("fj","福建");
				put("jx","江西");
				put("sd","山东");
				put("hen","河南");
				put("hb","湖北");
				put("hun","湖南");
				put("gd","广东");
				put("gx","广西");
				put("hain","海南");
				put("cq","重庆");
				put("sc","四川");
				put("gz","贵州");
				put("yn","云南");
				put("xz","西藏");
				put("sx2","陕西");
				put("gs","甘肃");
				put("qh","青海");
				put("nx","宁夏");
				put("xj","新疆");
				put("xg","香港");
				put("am","澳门");
				put("tw","台湾省");
			}
		};

	
	/**
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void init() throws IOException, URISyntaxException{
		generateClassPath();
		Properties props = new Properties();
		//加载微信相关的配置文件
		props.load(InitConfig.class.getClassLoader().getResourceAsStream("config.properties"));
		wReturnPrefix = props.getProperty("wReturnPrefix");
		appid = props.getProperty("appid");
		secret = props.getProperty("secret");
		mchId = props.getProperty("mchId");
		notifyUrl = props.getProperty("notifyUrl");
		apiKey = props.getProperty("apiKey");
		tftPrivateKey = props.getProperty("tftPrivateKey");
		tftPublicKey = props.getProperty("tftPublicKey");
		thirdChannelCode = props.getProperty("thirdChannelCode");
		tftAccountTransferUrl = props.getProperty("tftAccountTransferUrl");
		tftAccountTransferConfirmUrl = props.getProperty("tftAccountTransferConfirmUrl");
		tftAccountBalanceUrl = props.getProperty("tftAccountBalanceUrl");
		bestpayPartner = props.getProperty("bestpayPartner");
		bestpayTransactionKey = props.getProperty("bestpayTransactionKey");
		bestpayDataKey = props.getProperty("bestpayDataKey");
		beforeBackUrl = props.getProperty("beforeBackUrl");
		backMerchantUrl = props.getProperty("backMerchantUrl");
		productShowUrl = props.getProperty("productShowUrl");
		tmpFiledir = props.getProperty("tmpFiledir");
		generateFileUploadPath(props);
	}

	/**
	 * 
	 * @param props
	 * @throws IOException
	 */
	private static void generateFileUploadPath(Properties props) throws IOException {
		String configuredPath = props.getProperty("fileUploadFolder",classPath);
		if(configuredPath.startsWith("classpath:")){
			configuredPath = configuredPath.split("classpath:")[1];
			configuredPath.replace('/', File.separatorChar);
			final File uploadFolder = new File(classPath+File.separatorChar+configuredPath);
			if(!uploadFolder.exists()){
				uploadFolder.mkdir();
			}
			configuredPath = uploadFolder.getCanonicalPath();
		}
		fileUploadFolder = configuredPath;
	}
	
	/**
	 * 
	 * @throws URISyntaxException
	 */
	private static void generateClassPath() throws URISyntaxException {
		classPath = new File(InitConfig.class.getClassLoader().getResource("").toURI()).getAbsolutePath();
		if(classPath.endsWith(File.separator)){
			classPath = classPath.substring(0, classPath.length()-2);
		}
	}
	/**
	 * 获取项目回调地址
	 * @return 项目回调地址
	 */
	public static String getwReturnPrefix() {
		return wReturnPrefix;
	}
	/**
	 * @return 
	 */
	public static String getTmpFiledir() {
		return tmpFiledir;
	}
	/**
	 * ClassPath
	 * @return ClassPath
	 */
	public static String getClassPath() {
		return classPath;
	}
	/**
	 * 获取文件上传文件夹
	 * @return 文件上传文件夹
	 */
	public static String getFileUploadFolder() {
		return fileUploadFolder;
	}
	/**
	 * 获取省份集合
	 * @return 省份集合
	 */
	public static Map<String, String> getProvenceMap() {
		return provenceMap;
	}

    /**
     * Getter method for property <tt>appid</tt>.
     * 
     * @return property value of appid
     */
    public static String getAppid() {
        return appid;
    }

    /**
     * Getter method for property <tt>secret</tt>.
     * 
     * @return property value of secret
     */
    public static String getSecret() {
        return secret;
    }

    /**
     * Getter method for property <tt>mchId</tt>.
     * 
     * @return property value of mchId
     */
    public static String getMchId() {
        return mchId;
    }

    /**
     * Getter method for property <tt>notifyUrl</tt>.
     * 
     * @return property value of notifyUrl
     */
    public static String getNotifyUrl() {
        return wReturnPrefix + notifyUrl;
    }

    /**
     * Getter method for property <tt>apiKey</tt>.
     * 
     * @return property value of apiKey
     */
    public static String getApiKey() {
        return apiKey;
    }

    /**
     * Getter method for property <tt>tftPrivateKey</tt>.
     * 
     * @return property value of tftPrivateKey
     */
    public static String getTftPrivateKey() {
        return tftPrivateKey;
    }

    /**
     * Getter method for property <tt>tftPublicKey</tt>.
     * 
     * @return property value of tftPublicKey
     */
    public static String getTftPublicKey() {
        return tftPublicKey;
    }

    /**
     * Getter method for property <tt>thirdChannelCode</tt>.
     * 
     * @return property value of thirdChannelCode
     */
    public static String getThirdChannelCode() {
        return thirdChannelCode;
    }

    /**
     * Getter method for property <tt>tftAccountTransferUrl</tt>.
     * 
     * @return property value of tftAccountTransferUrl
     */
    public static String getTftAccountTransferUrl() {
        return tftAccountTransferUrl;
    }

    /**
     * Getter method for property <tt>tftAccountTransferConfirmUrl</tt>.
     * 
     * @return property value of tftAccountTransferConfirmUrl
     */
    public static String getTftAccountTransferConfirmUrl() {
        return tftAccountTransferConfirmUrl;
    }

    /**
    *
    * @return 
    */
	public static String getTftAccountBalanceUrl() {
		return tftAccountBalanceUrl;
    }

    /**
    *
    * @return 
    */
	public static String getBestpayPartner() {
		return bestpayPartner;
    }

    /**
    *
    * @return 
    */
	public static String getBestpayTransactionKey() {
		return bestpayTransactionKey;
    }

    /**
    *
    * @return 
    */
	public static String getBestpayDataKey() {
		return bestpayDataKey;
    }

    /**
    *
    * @return 
    */
	public static String getBeforeBackUrl() {
		return beforeBackUrl;
    }

    /**
    *
    * @return 
    */
	public static String getBackMerchantUrl() {
		return backMerchantUrl;
    }

    /**
    *
    * @return 
    */
	public static String getProductShowUrl() {
		return productShowUrl;
	}
	
	
}
