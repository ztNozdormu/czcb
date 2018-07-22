/**
 * 2018/2/7 11:37:58 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/02/07
 * Created by AJiong.
 */
public class VisitOrderLog implements java.io.Serializable {

    /**
     *  序列化版本
     */
    private static final long serialVersionUID = -2719117381013518848L;

    /**
     *  唯一主键 [主键]
     */
    private String id;
    /**
     *  用户id
     */
    private String userId;
    /**
     *  创建时间
     */
    private Long createTime;
    /**
     *  备注
     */
    private String remark;
    
    /**
     * @param id
     * @param userId
     * @param createTime
     * @param remark
     */
    public VisitOrderLog(String id, String userId, Long createTime, String remark) {
        super();
        this.id = id;
        this.userId = userId;
        this.createTime = createTime;
        this.remark = remark;
    }

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
     * 获取用户id
     * 
     * @return 用户id
     */
    public String getUserId() {
        return userId;
    }

    /** 
     * 设置用户id
     * 
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
