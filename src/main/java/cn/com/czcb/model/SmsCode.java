/**
 * 2018/1/25 17:05:18 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/01/25
 * Created by AJiong.
 */
public class SmsCode implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -4674665902124096512L;

    // 唯一主键 [主键]
    private String id;
    // 接收手机号
    private String phone;
    // 验证码
    private String validatCode;
    // 短信是否已经验证
    private Boolean validated;
    // 验证码创建时间
    private Long createTime;

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
     * 获取接收手机号
     * 
     * @return 接收手机号
     */
    public String getPhone() {
        return phone;
    }

    /** 
     * 设置接收手机号
     * 
     * @param phone 接收手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 
     * 获取验证码
     * 
     * @return 验证码
     */
    public String getValidatCode() {
        return validatCode;
    }

    /** 
     * 设置验证码
     * 
     * @param validatCode 验证码
     */
    public void setValidatCode(String validatCode) {
        this.validatCode = validatCode;
    }

    /** 
     * 获取短信是否已经验证
     * 
     * @return 短信是否已经验证
     */
    public Boolean isValidated() {
        return validated;
    }

    /** 
     * 设置短信是否已经验证
     * 
     * @param validated 短信是否已经验证
     */
    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    /** 
     * 获取验证码创建时间
     * 
     * @return 验证码创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /** 
     * 设置验证码创建时间
     * 
     * @param createTime 验证码创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
