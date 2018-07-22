package cn.com.czcb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.Device;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.model.vo.DeviceVo;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.RedisUtil;
import cn.com.czcb.service.IDeviceService;
import cn.com.czcb.service.IUserDeviceService;

/**
 * 说明 设备
 * 
 * @author Cron
 * @date 2018年5月7日 下午3:27:02
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
	/**
	 * 日志对象
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 设备服务
	 */
	@Autowired
	private IDeviceService deviceService;

	/**
	 * 缓存工具
	 */
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 连接服务
	 */
	@Autowired
	private IUserDeviceService userDeviceService;

	/**
	 * 设备保存
	 * 
	 * @param request
	 * @param device
	 * @return
	 */
	@RequestMapping("/saveDevice")
	@ResponseBody
	public Object saveDevice(HttpServletRequest request, @ModelAttribute Device device) {
		Map<String, Object> result = new HashMap<>();
		try {
			deviceService.saveDevice(device);
			result.put("status", true);
		} catch (Exception e) {
			result.put("status", false);
			result.put("exceptionMsg", e.getMessage());
		}
		return result;
	}

	/**
	 * 获取搜索到的设备列中是否有已经连接过的设备
	 * 
	 * @param request
	 * @param deviceNames
	 * @return
	 */
	@RequestMapping("/getDevice")
	@ResponseBody
	public Object getDevice(HttpServletRequest request, String deviceName) {
		
		Map<String, Object> result = null;
		try {
			result = deviceService.getDevicesByNames(deviceName);
		} catch (Exception e) {
			logger.error("查询已连接设备列表异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 个人中心——我的设备列表
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/myDeviceList")
	@ResponseBody
	public Object myDeviceList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		try {
			UserSession userSession = (UserSession) redisUtil.get(redisUtil.get("keydom_session_token").toString());
			List<DeviceVo> list = userDeviceService.getMyDeviceList(userSession.getUserId());
			result.put("status", true);
			result.put("list", list);
		} catch (Exception e) {
			result.put("status", false);
			result.put("exceptionMsg", e.getMessage());
		}
		return result;
	}

	/**
	 * 个人中心——我的设备设置自动连接
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	@RequestMapping("/setAutoConnecter")
	@ResponseBody
	public Object setAutoConnecter(HttpServletRequest request,String deviceId) {
		Map<String, Object> result = new HashMap<>();
		try {
			UserSession userSession = (UserSession) redisUtil.get(redisUtil.get("keydom_session_token").toString());
			userDeviceService.setAutoConner(deviceId, userSession.getUserId());
			result.put("status", true);
		} catch (Exception e) {
			result.put("status", false);
			result.put("exceptionMsg", e.getMessage());
		}
		return result;
	}

}
