/**
 * 2018/4/9 10:53:22 Wen Jun created.
 */

package cn.com.czcb.model;


/**
 * 2018/04/09
 * Created by Wen Jun.
 */
public class FeedbackType implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -4224325946636649472L;

    // 唯一标识 [主键]
    private String id;
    // 反馈类型
    private String text;
    // 创建时间
    private Long createTime;
    // 删除标识（0：未删除，1：已删除）
    private Integer deleteFlag;

    /** 
     * 获取唯一标识 [主键]
     * 
     * @return 唯一标识
     */
    public String getId() {
        return id;
    }

    /** 
     * 设置唯一标识 [主键]
     * 
     * @param id 唯一标识
     */
    public void setId(String id) {
        this.id = id;
    }

    /** 
     * 获取反馈类型
     * 
     * @return 反馈类型
     */
    public String getText() {
        return text;
    }

    /** 
     * 设置反馈类型
     * 
     * @param text 反馈类型
     */
    public void setText(String text) {
        this.text = text;
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
     * 获取删除标识（0：未删除，1：已删除）
     * 
     * @return 删除标识（0：未删除，1：已删除）
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /** 
     * 设置删除标识（0：未删除，1：已删除）
     * 
     * @param deleteFlag 删除标识（0：未删除，1：已删除）
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
