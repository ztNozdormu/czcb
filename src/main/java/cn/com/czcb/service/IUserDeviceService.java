/**
 * 2018/5/7 星期一 14:47:51 Cron created.
 */

package cn.com.czcb.service;

import java.util.List;

import cn.com.czcb.model.UserDevice;
import cn.com.czcb.model.vo.DeviceVo;

/**
 *  Service 接口
 * Created by Cron on 2018/05/07.
 */
public interface IUserDeviceService extends IModelService<UserDevice> {

	/**
	 * 设置连接为自动连接
	 * @param matchCode
	 * @param userSession
	 */
	public void setAutoConner(String deviceId, String userId);

	/**
	 * 个人中心——我的设备列表
	 * @param params
	 * @return
	 */
	public List<DeviceVo> getMyDeviceList(String userId);
}
