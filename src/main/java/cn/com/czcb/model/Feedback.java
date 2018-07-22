/**
 * 2018/4/9 10:53:18 Wen Jun created.
 */

package cn.com.czcb.model;


/**
 * 2018/04/09
 * Created by Wen Jun.
 */
public class Feedback implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -4180115644903907328L;

    // 唯一主键 [主键]
    private String id;
    // 用户电话号码
    private String phone;
    // 反馈类型id
    private String feedbackTypeId;
    // 反馈类型内容
    private String feedbackTypeText;
    // 反馈问题id
    private String feedbackProblemId;
    // 反馈问题内容
    private String feedbackProblemText;
    // 反馈详细内容
    private String text;
    // 反馈图片
    private String picture1;
    // 反馈图片
    private String picture2;
    // 反馈图片
    private String picture3;
    // 反馈图片
    private String picture4;
    // 创建时间
    private Long createTime;
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
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
     * 获取用户电话号码
     * 
     * @return 用户电话号码
     */
    public String getPhone() {
        return phone;
    }

    /** 
     * 设置用户电话号码
     * 
     * @param phone 用户电话号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** 
     * 获取反馈类型id
     * 
     * @return 反馈类型id
     */
    public String getFeedbackTypeId() {
        return feedbackTypeId;
    }

    /** 
     * 设置反馈类型id
     * 
     * @param feedbackTypeId 反馈类型id
     */
    public void setFeedbackTypeId(String feedbackTypeId) {
        this.feedbackTypeId = feedbackTypeId;
    }

    /** 
     * 获取反馈类型内容
     * 
     * @return 反馈类型内容
     */
    public String getFeedbackTypeText() {
        return feedbackTypeText;
    }

    /** 
     * 设置反馈类型内容
     * 
     * @param feedbackTypeText 反馈类型内容
     */
    public void setFeedbackTypeText(String feedbackTypeText) {
        this.feedbackTypeText = feedbackTypeText;
    }

    /** 
     * 获取反馈问题id
     * 
     * @return 反馈问题id
     */
    public String getFeedbackProblemId() {
        return feedbackProblemId;
    }

    /** 
     * 设置反馈问题id
     * 
     * @param feedbackProblemId 反馈问题id
     */
    public void setFeedbackProblemId(String feedbackProblemId) {
        this.feedbackProblemId = feedbackProblemId;
    }

    /** 
     * 获取反馈问题内容
     * 
     * @return 反馈问题内容
     */
    public String getFeedbackProblemText() {
        return feedbackProblemText;
    }

    /** 
     * 设置反馈问题内容
     * 
     * @param feedbackProblemText 反馈问题内容
     */
    public void setFeedbackProblemText(String feedbackProblemText) {
        this.feedbackProblemText = feedbackProblemText;
    }

    /** 
     * 获取反馈详细内容
     * 
     * @return 反馈详细内容
     */
    public String getText() {
        return text;
    }

    /** 
     * 设置反馈详细内容
     * 
     * @param text 反馈详细内容
     */
    public void setText(String text) {
        this.text = text;
    }

    /** 
     * 获取反馈图片
     * 
     * @return 反馈图片
     */
    public String getPicture1() {
        return picture1;
    }

    /** 
     * 设置反馈图片
     * 
     * @param picture1 反馈图片
     */
    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    /** 
     * 获取反馈图片
     * 
     * @return 反馈图片
     */
    public String getPicture2() {
        return picture2;
    }

    /** 
     * 设置反馈图片
     * 
     * @param picture2 反馈图片
     */
    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }

    /** 
     * 获取反馈图片
     * 
     * @return 反馈图片
     */
    public String getPicture3() {
        return picture3;
    }

    /** 
     * 设置反馈图片
     * 
     * @param picture3 反馈图片
     */
    public void setPicture3(String picture3) {
        this.picture3 = picture3;
    }

    /** 
     * 获取反馈图片
     * 
     * @return 反馈图片
     */
    public String getPicture4() {
        return picture4;
    }

    /** 
     * 设置反馈图片
     * 
     * @param picture4 反馈图片
     */
    public void setPicture4(String picture4) {
        this.picture4 = picture4;
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

}
