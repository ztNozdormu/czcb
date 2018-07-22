/**
 * 2018/5/7 星期一 14:47:50 Cron created.
 */

package cn.com.czcb.service;

import java.util.List;
import java.util.Map;

import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.UserInfoDto;
import cn.com.czcb.model.Device;

/**
 * Service 接口 Created by Cron on 2018/05/07.
 */
public interface IDeviceService extends IModelService<Device> {

	/**
	 * 查询后台用户信息
	 * 
	 * @param queryParams
	 * @return
	 */
	public PagedList<UserInfoDto> queryUserInfoListByPage(QueryParams queryParams, Integer start, Integer size);

	public List<UserInfoDto> queryUserInfoList(QueryParams queryParams);

	/**
	 * 设备保存
	 * 
	 * @param device
	 */
	public void saveDevice(Device device);

	/**
	 * 获取是否已经有配对过的设备
	 * 
	 * @param deviceNames
	 * @return
	 */
	public Map<String, Object> getDevicesByNames(String deviceName);

}
