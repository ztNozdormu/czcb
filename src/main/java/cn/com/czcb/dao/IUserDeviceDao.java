/**
 * 2018/5/7 星期一 14:47:51 Cron created.
 */

package cn.com.czcb.dao;

import java.util.List;

import cn.com.czcb.model.UserDevice;
import cn.com.czcb.model.vo.DeviceVo;

/**
 *  Ibatis Dao 接口
 * Created by Cron on 2018/05/07.
 */
public interface IUserDeviceDao extends IIbatisDataProvider<UserDevice, String> {

	/**
	 * 查询用户的设备（连接记录）
	 * @param userId
	 * @return
	 */
	public List<DeviceVo> getMyDeviceList(String userId);

}
