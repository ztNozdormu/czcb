package cn.com.czcb.pub;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * 服务器启动时，初始化相关配置文件
 * @author User
 *
 */
public class AppStart extends HttpServlet{

	private static final long serialVersionUID = 6180690822896681194L;
	
	@Override
	public void init() throws ServletException {
		super.init();
		ObjectUtils.isDebug = Boolean.valueOf(getInitParameter("debug"));
		try {
			//1.初始配置初始化
			InitConfig.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
		try {
			//2.微信配置初始化
			//WechatUtils.init();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

}
