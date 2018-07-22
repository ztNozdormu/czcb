/**
 * 2018/1/25 15:06:08 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/01/25
 * Created by AJiong.
 */
public class WechatPayRecord implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -6055661750051966976L;

    // 唯一主键 [主键]
    private String id;
    // 小程序ID
    private String appid;
    // 商户号
    private String mch_id;
    // 设备号
    private String device_info;
    // 随机字符串
    private String nonce_str;
    // 签名
    private String sign;
    // 签名类型
    private String sign_type;
    // 业务结果
    private String result_code;
    // 错误代码
    private String err_code;
    // 错误代码描述
    private String err_code_des;
    // 用户标识
    private String openid;
    // 是否关注公众账号
    private String is_subscribe;
    // 交易类型
    private String trade_type;
    // 付款银行
    private String bank_type;
    // 订单金额（单位为分）
    private Integer total_fee;
    // 应结订单金额
    private Integer settlement_total_fee;
    // 货币种类
    private String fee_type;
    // 现金支付金额
    private Integer cash_fee;
    // 现金支付货币类型
    private String cash_fee_type;
    // 总代金券金额
    private Integer coupon_fee;
    // 代金券使用数量
    private Integer coupon_count;
    // 代金券类型
    private String coupon_type_$n;
    // 代金券ID
    private String coupon_id_$n;
    // 单个代金券支付金额
    private Integer coupon_fee_$n;
    // 微信支付订单号
    private String transaction_id;
    // 商户订单号
    private String out_trade_no;
    // 商家数据包
    private String attach;
    // 支付完成时间
    private String time_end;

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
     * 获取小程序ID
     * 
     * @return 小程序ID
     */
    public String getAppid() {
        return appid;
    }

    /** 
     * 设置小程序ID
     * 
     * @param appid 小程序ID
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /** 
     * 获取商户号
     * 
     * @return 商户号
     */
    public String getMch_id() {
        return mch_id;
    }

    /** 
     * 设置商户号
     * 
     * @param mch_id 商户号
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    /** 
     * 获取设备号
     * 
     * @return 设备号
     */
    public String getDevice_info() {
        return device_info;
    }

    /** 
     * 设置设备号
     * 
     * @param device_info 设备号
     */
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    /** 
     * 获取随机字符串
     * 
     * @return 随机字符串
     */
    public String getNonce_str() {
        return nonce_str;
    }

    /** 
     * 设置随机字符串
     * 
     * @param nonce_str 随机字符串
     */
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    /** 
     * 获取签名
     * 
     * @return 签名
     */
    public String getSign() {
        return sign;
    }

    /** 
     * 设置签名
     * 
     * @param sign 签名
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /** 
     * 获取签名类型
     * 
     * @return 签名类型
     */
    public String getSign_type() {
        return sign_type;
    }

    /** 
     * 设置签名类型
     * 
     * @param sign_type 签名类型
     */
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    /** 
     * 获取业务结果
     * 
     * @return 业务结果
     */
    public String getResult_code() {
        return result_code;
    }

    /** 
     * 设置业务结果
     * 
     * @param result_code 业务结果
     */
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    /** 
     * 获取错误代码
     * 
     * @return 错误代码
     */
    public String getErr_code() {
        return err_code;
    }

    /** 
     * 设置错误代码
     * 
     * @param err_code 错误代码
     */
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    /** 
     * 获取错误代码描述
     * 
     * @return 错误代码描述
     */
    public String getErr_code_des() {
        return err_code_des;
    }

    /** 
     * 设置错误代码描述
     * 
     * @param err_code_des 错误代码描述
     */
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    /** 
     * 获取用户标识
     * 
     * @return 用户标识
     */
    public String getOpenid() {
        return openid;
    }

    /** 
     * 设置用户标识
     * 
     * @param openid 用户标识
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /** 
     * 获取是否关注公众账号
     * 
     * @return 是否关注公众账号
     */
    public String getIs_subscribe() {
        return is_subscribe;
    }

    /** 
     * 设置是否关注公众账号
     * 
     * @param is_subscribe 是否关注公众账号
     */
    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    /** 
     * 获取交易类型
     * 
     * @return 交易类型
     */
    public String getTrade_type() {
        return trade_type;
    }

    /** 
     * 设置交易类型
     * 
     * @param trade_type 交易类型
     */
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    /** 
     * 获取付款银行
     * 
     * @return 付款银行
     */
    public String getBank_type() {
        return bank_type;
    }

    /** 
     * 设置付款银行
     * 
     * @param bank_type 付款银行
     */
    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    /** 
     * 获取订单金额（单位为分）
     * 
     * @return 订单金额（单位为分）
     */
    public Integer getTotal_fee() {
        return total_fee;
    }

    /** 
     * 设置订单金额（单位为分）
     * 
     * @param total_fee 订单金额（单位为分）
     */
    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    /** 
     * 获取应结订单金额
     * 
     * @return 应结订单金额
     */
    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    /** 
     * 设置应结订单金额
     * 
     * @param settlement_total_fee 应结订单金额
     */
    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    /** 
     * 获取货币种类
     * 
     * @return 货币种类
     */
    public String getFee_type() {
        return fee_type;
    }

    /** 
     * 设置货币种类
     * 
     * @param fee_type 货币种类
     */
    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    /** 
     * 获取现金支付金额
     * 
     * @return 现金支付金额
     */
    public Integer getCash_fee() {
        return cash_fee;
    }

    /** 
     * 设置现金支付金额
     * 
     * @param cash_fee 现金支付金额
     */
    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    /** 
     * 获取现金支付货币类型
     * 
     * @return 现金支付货币类型
     */
    public String getCash_fee_type() {
        return cash_fee_type;
    }

    /** 
     * 设置现金支付货币类型
     * 
     * @param cash_fee_type 现金支付货币类型
     */
    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    /** 
     * 获取总代金券金额
     * 
     * @return 总代金券金额
     */
    public Integer getCoupon_fee() {
        return coupon_fee;
    }

    /** 
     * 设置总代金券金额
     * 
     * @param coupon_fee 总代金券金额
     */
    public void setCoupon_fee(Integer coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    /** 
     * 获取代金券使用数量
     * 
     * @return 代金券使用数量
     */
    public Integer getCoupon_count() {
        return coupon_count;
    }

    /** 
     * 设置代金券使用数量
     * 
     * @param coupon_count 代金券使用数量
     */
    public void setCoupon_count(Integer coupon_count) {
        this.coupon_count = coupon_count;
    }

    /** 
     * 获取代金券类型
     * 
     * @return 代金券类型
     */
    public String getCoupon_type_$n() {
        return coupon_type_$n;
    }

    /** 
     * 设置代金券类型
     * 
     * @param coupon_type_$n 代金券类型
     */
    public void setCoupon_type_$n(String coupon_type_$n) {
        this.coupon_type_$n = coupon_type_$n;
    }

    /** 
     * 获取代金券ID
     * 
     * @return 代金券ID
     */
    public String getCoupon_id_$n() {
        return coupon_id_$n;
    }

    /** 
     * 设置代金券ID
     * 
     * @param coupon_id_$n 代金券ID
     */
    public void setCoupon_id_$n(String coupon_id_$n) {
        this.coupon_id_$n = coupon_id_$n;
    }

    /** 
     * 获取单个代金券支付金额
     * 
     * @return 单个代金券支付金额
     */
    public Integer getCoupon_fee_$n() {
        return coupon_fee_$n;
    }

    /** 
     * 设置单个代金券支付金额
     * 
     * @param coupon_fee_$n 单个代金券支付金额
     */
    public void setCoupon_fee_$n(Integer coupon_fee_$n) {
        this.coupon_fee_$n = coupon_fee_$n;
    }

    /** 
     * 获取微信支付订单号
     * 
     * @return 微信支付订单号
     */
    public String getTransaction_id() {
        return transaction_id;
    }

    /** 
     * 设置微信支付订单号
     * 
     * @param transaction_id 微信支付订单号
     */
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    /** 
     * 获取商户订单号
     * 
     * @return 商户订单号
     */
    public String getOut_trade_no() {
        return out_trade_no;
    }

    /** 
     * 设置商户订单号
     * 
     * @param out_trade_no 商户订单号
     */
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    /** 
     * 获取商家数据包
     * 
     * @return 商家数据包
     */
    public String getAttach() {
        return attach;
    }

    /** 
     * 设置商家数据包
     * 
     * @param attach 商家数据包
     */
    public void setAttach(String attach) {
        this.attach = attach;
    }

    /** 
     * 获取支付完成时间
     * 
     * @return 支付完成时间
     */
    public String getTime_end() {
        return time_end;
    }

    /** 
     * 设置支付完成时间
     * 
     * @param time_end 支付完成时间
     */
    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

}
