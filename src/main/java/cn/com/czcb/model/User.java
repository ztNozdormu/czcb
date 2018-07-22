/**
 * 2018/1/25 15:06:07 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/01/25
 * Created by AJiong.
 */
public class User implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -5074925601982942208L;

    // 唯一主键 [主键]
    private String id;
    // 小程序openid
    private String openid;
    // 用户在开放平台的唯一标识符
    private String unionid;
    // 用户名/昵称
    private String nickName;
    // 手机号
    private String phone;
    // 用户头像
    private String avatarUrl;
    // 用户性别
    private String gender;
    // 用户所在城市
    private String city;
    // 用户所在省份
    private String province;
    // 用户所在国家
    private String country;
    // 用户的语言
    private String language;
    // 最后一次进来时间
    private Long updateTime;
    // 创建时间
    private Long createTime;
    // 备注
    private String remark;

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
     * 获取小程序openid
     * 
     * @return 小程序openid
     */
    public String getOpenid() {
        return openid;
    }

    /** 
     * 设置小程序openid
     * 
     * @param openid 小程序openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /** 
     * 获取用户在开放平台的唯一标识符
     * 
     * @return 用户在开放平台的唯一标识符
     */
    public String getUnionid() {
        return unionid;
    }

    /** 
     * 设置用户在开放平台的唯一标识符
     * 
     * @param unionid 用户在开放平台的唯一标识符
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /** 
     * 获取用户名/昵称
     * 
     * @return 用户名/昵称
     */
    public String getNickName() {
        return nickName;
    }

    /** 
     * 设置用户名/昵称
     * 
     * @param nickName 用户名/昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /** 
     * 获取手机号
     * 
     * @return 手机号
     */
    public String getPhone() {
        return phone;
    }

    /** 
     * 设置手机号
     * 
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 
     * 获取用户头像
     * 
     * @return 用户头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /** 
     * 设置用户头像
     * 
     * @param avatarUrl 用户头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /** 
     * 获取用户性别
     * 
     * @return 用户性别
     */
    public String getGender() {
        return gender;
    }

    /** 
     * 设置用户性别
     * 
     * @param gender 用户性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /** 
     * 获取用户所在城市
     * 
     * @return 用户所在城市
     */
    public String getCity() {
        return city;
    }

    /** 
     * 设置用户所在城市
     * 
     * @param city 用户所在城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /** 
     * 获取用户所在省份
     * 
     * @return 用户所在省份
     */
    public String getProvince() {
        return province;
    }

    /** 
     * 设置用户所在省份
     * 
     * @param province 用户所在省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /** 
     * 获取用户所在国家
     * 
     * @return 用户所在国家
     */
    public String getCountry() {
        return country;
    }

    /** 
     * 设置用户所在国家
     * 
     * @param country 用户所在国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** 
     * 获取用户的语言
     * 
     * @return 用户的语言
     */
    public String getLanguage() {
        return language;
    }

    /** 
     * 设置用户的语言
     * 
     * @param language 用户的语言
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /** 
     * 获取最后一次进来时间
     * 
     * @return 最后一次进来时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /** 
     * 设置最后一次进来时间
     * 
     * @param updateTime 最后一次进来时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /** 
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /** 
     * 设置创建时间
     * 
     * @param createTime 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /** 
     * 设置备注
     * 
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
