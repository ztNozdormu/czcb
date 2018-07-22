/**
 * 2018/2/1 10:27:44 AJiong created.
 */

package cn.com.czcb.model;


/**
 * 2018/02/01
 * Created by AJiong.
 */
public class Admin implements java.io.Serializable {

    // 序列化版本
    private static final long serialVersionUID = -2979145149224970752L;

    // 唯一主键 [主键]
    private String id;
    // 帐号
    private String account;
    // 密码
    private String password;

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
     * 获取帐号
     * 
     * @return 帐号
     */
    public String getAccount() {
        return account;
    }

    /** 
     * 设置帐号
     * 
     * @param account 帐号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /** 
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /** 
     * 设置密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
