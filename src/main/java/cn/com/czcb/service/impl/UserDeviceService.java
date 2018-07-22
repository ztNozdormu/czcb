/**
 * 2018/5/7 星期一 14:47:51 Cron created.
 */

package cn.com.czcb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IUserDeviceDao;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.UserDevice;
import cn.com.czcb.model.vo.DeviceVo;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.service.IUserDeviceService;

/**
 * Service 实现 Created by Cron on 2018/05/07.
 */
@Service
public class UserDeviceService extends ModelService<UserDevice> implements IUserDeviceService {

	@Autowired
	private IUserDeviceDao userDeviceDao;

	@Override
	public IIbatisDataProvider<UserDevice, String> getModelDao() {
		return this.userDeviceDao;
	}

	/**
	 * 设置自动连接
	 */
	@Override
	public void setAutoConner(String deviceId, String userId) {
		if (StringUtils.isBlank(deviceId)) {
			throw new AppException("设备为空！");
		}
		QueryParams params = new QueryParams();
		params.put("userId", userId);
		List<UserDevice> oldList = queryList(params);
		for (UserDevice userDevice : oldList) {
			userDevice.setTag("0");
			userDeviceDao.updateObject(userDevice);
		}
		params.put("deviceId", deviceId);
		List<UserDevice> oneList = queryList(params);
		if (oneList.size() > 0) {
			UserDevice userDevice = oneList.get(0);
			userDevice.setTag("1");
			userDeviceDao.updateObject(userDevice);
		}
	}

	/**
	 * 获取用户我的设备
	 */
	@Override
	public List<DeviceVo> getMyDeviceList(String userId) {
		return userDeviceDao.getMyDeviceList(userId);
	}

}
