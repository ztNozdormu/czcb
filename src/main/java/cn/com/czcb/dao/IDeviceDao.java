/**
 * 2018/5/7 星期一 14:47:50 Cron created.
 */

package cn.com.czcb.dao;

import java.util.List;

import cn.com.czcb.dto.UserInfoDto;
import cn.com.czcb.model.Device;

import java.util.List;

/**
 *  Ibatis Dao 接口
 * Created by Cron on 2018/05/07.
 */
public interface IDeviceDao extends IIbatisDataProvider<Device, String> {

    int queryUserInfoCount(QueryParams queryParams);

    List<UserInfoDto> queryUserInfoList(QueryParams queryParams);

}
