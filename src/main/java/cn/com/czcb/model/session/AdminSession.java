package cn.com.czcb.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 
 * @author AJiong
 * @version $Id: AdminSession.java, v 0.1 2017年11月7日 下午3:54:47 Ajiong Exp $
 */
@Component
@Scope("session")
public class AdminSession implements Serializable{
	/**
	 * 序列号版本号
	 */
	private static final long serialVersionUID = 395374263385692951L;
	
	/**
	 *  管理员id [主键]
	 */
    private String adminId;
    /**
     *  管理的班级id
     */
    private String teacherId;
    /**
     *  后台登录用户名
     */
    private String name;
    /**
     *  后台登录密码
     */
    private String pwd;
    /**
     *  
     */
    private long createTime;

   

    /**
     * 获取管理员id
     * @return 管理员id
     */
    public String getAdminId() {
		return adminId;
	}

	/**
	 * 设置管理员id
	 * @param adminId
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/** 
     * 获取管理的班级id
     * 
     * @return 管理的班级id
     */
    public String getTeacherId() {
        return teacherId;
    }

    /** 
     * 设置管理的班级id
     * 
     * @param teacherId 管理的班级id
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /** 
     * 获取后台登录用户名
     * 
     * @return 后台登录用户名
     */
    public String getName() {
        return name;
    }

    /** 
     * 设置后台登录用户名
     * 
     * @param name 后台登录用户名
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * 获取后台登录密码
     * 
     * @return 后台登录密码
     */
    public String getPwd() {
        return pwd;
    }

    /** 
     * 设置后台登录密码
     * 
     * @param pwd 后台登录密码
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /** 
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public long getCreateTime() {
        return createTime;
    }

    /** 
     * 设置
     * 
     * @param createTime 
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

}
