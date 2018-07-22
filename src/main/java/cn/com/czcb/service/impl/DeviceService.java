/**
 * 2018/5/7 星期一 14:47:50 Cron created.
 */

package cn.com.czcb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IDeviceDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IUserDeviceDao;
import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.UserInfoDto;
import cn.com.czcb.model.Device;
import cn.com.czcb.model.UserDevice;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.model.vo.DeviceVo;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.RedisUtil;
import cn.com.czcb.service.IDeviceService;
import cn.com.czcb.service.IUserDeviceService;
import cn.com.czcb.util.UUIDUtil;

/**
 * Service 实现 Created by Cron on 2018/05/07.
 */
@Service
public class DeviceService extends ModelService<Device> implements IDeviceService {

	@Autowired
	private IDeviceDao deviceDao;

	@Autowired
	private IUserDeviceDao userDeviceDao;

	/**
	 * 缓存工具
	 */
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IUserDeviceService userDeviceService;

	@Override
	public IIbatisDataProvider<Device, String> getModelDao() {
		return this.deviceDao;
	}

	@Override
	public PagedList<UserInfoDto> queryUserInfoListByPage(QueryParams queryParams, Integer start, Integer size) {
		PagedList<UserInfoDto> result = new PagedList<UserInfoDto>();
		queryParams.addParameter("__start", start*size);
		queryParams.addParameter("__size", size);
		int count = deviceDao.queryUserInfoCount(queryParams);
		List<UserInfoDto> list = deviceDao.queryUserInfoList(queryParams);
		result.getList().addAll(list);
		result.setPageIndex(start);
		result.setPageSize(size);
		result.setTotalSize(count);
		if (count % size == 0)
			result.setPageCount(count / size);
		else
			result.setPageCount(count / size + 1);
		return result;
	}

	@Override
	public List<UserInfoDto> queryUserInfoList(QueryParams queryParams) {
		return deviceDao.queryUserInfoList(queryParams);
	}

	/**
	 * 设备保存
	 */
	@Override
	public void saveDevice(Device device) {
		// 判断是否保存过
		if (StringUtils.isNoneBlank(device.getDeviceName())) {
			UserSession userSession = (UserSession) redisUtil.get(redisUtil.get("keydom_session_token").toString());
			QueryParams params = new QueryParams();
			params.put("deviceName", device.getDeviceName());
			List<Device> list = queryList(params);
			if (list.size() > 0) {
				// 设备已经保存过
				// 保存用户-设备中间表（连接设备记录、新连接才保存，连接过的话不做保存记录）
				// 连接过是指用户和设备连接数据库已存在对应连接
				params.clear();
				params.put("deviceId", list.get(0).getId());
				params.put("userId", userSession.getUserId());
				List<UserDevice> userDeviceList = userDeviceService.queryList(params);
				if (userDeviceList.size() < 1) {
					//如果该用户存在旧连接，则将旧连接设置为非自动
					params.clear();
					params.put("userId", userSession.getUserId());
					List<UserDevice> list2 = userDeviceService.queryList(params);
					if(list2.size()>0) {
						for (UserDevice userDevice : list2) {
							userDevice.setTag("0");
							userDeviceDao.updateObject(userDevice);
						}
					}
					UserDevice userDevice = new UserDevice();
					userDevice.setId(UUIDUtil.uuid());
					userDevice.setDeviceId(list.get(0).getId());
					userDevice.setUserId(userSession.getUserId());
					userDevice.setTag("1");
					userDeviceDao.insertObject(userDevice);
				}
				throw new AppException("设备已存在！");
			}
			device.setId(UUIDUtil.uuid());
			device.setMatchTime(new Date().getTime());
			deviceDao.insertObject(device);

			// 保存用户-设备中间表（连接设备记录）
			UserDevice userDevice = new UserDevice();
			userDevice.setId(UUIDUtil.uuid());
			userDevice.setDeviceId(device.getId());
			userDevice.setUserId(userSession.getUserId());
			userDevice.setTag("1");

			userDeviceDao.insertObject(userDevice);

		}

	}

	/**
	 * 查询连接设备列表已连过的设备
	 */
	@Override
	public Map<String, Object> getDevicesByNames(String deviceName) {

		Map<String, Object> map = new HashedMap<>();
		UserSession userSession = (UserSession) redisUtil.get(redisUtil.get("keydom_session_token").toString());
		List<DeviceVo> voList = userDeviceDao.getMyDeviceList(userSession.getUserId());
		if (voList.size() > 0) {
			for (int i = 0; i < voList.size(); i++) {
				if (voList.get(i).getDeviceName().equals(deviceName)) {
					map.put("device", voList.get(i));
				}
			}
		}
		return map;
	}

}
