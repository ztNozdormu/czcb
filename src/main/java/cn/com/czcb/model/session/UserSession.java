package cn.com.czcb.model.session;

import java.io.Serializable;

/**
 * 
 * @author AJiong
 * @version $Id: UserSession.java, v 0.1 2018年1月25日 下午2:13:41 Ajiong Exp $
 */
public class UserSession implements Serializable {
	/**
	 *  序列化版本
	 */
	private static final long serialVersionUID = -2760477452104895322L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 *  小程序openid
	 */
	private String openid;
	/**
	 *  小程序sessionKey
	 */
	private String sessionKey;
	/**
	 *  小程序unionid
	 */
	private String unionid;
	
    /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * Getter method for property <tt>openid</tt>.
     * 
     * @return property value of openid
     */
    public String getOpenid() {
        return openid;
    }
    /**
     * Setter method for property <tt>openid</tt>.
     * 
     * @param openid value to be assigned to property openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }
    /**
     * Getter method for property <tt>sessionKey</tt>.
     * 
     * @return property value of sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }
    /**
     * Setter method for property <tt>sessionKey</tt>.
     * 
     * @param sessionKey value to be assigned to property sessionKey
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
    /**
     * Getter method for property <tt>unionid</tt>.
     * 
     * @return property value of unionid
     */
    public String getUnionid() {
        return unionid;
    }
    /**
     * Setter method for property <tt>unionid</tt>.
     * 
     * @param unionid value to be assigned to property unionid
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
	
	
}
