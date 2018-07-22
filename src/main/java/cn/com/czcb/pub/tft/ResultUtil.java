package cn.com.czcb.pub.tft;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 前端返回工具类
 * @author Administrator
 *
 */
public class ResultUtil {
	/**
	 * 结果返回工具类
	 * @param message
	 * @param status
	 * @param data
	 * @return string
	 */
	public static String resultInfo(String message,String status,Object data){
		Map<String, Object> result = new HashMap<>();
		//提示信息
		result.put("message", message);
		//返回状态
		result.put("messageStatus", status);
		//返回数据
		result.put("data", data);
		String json = JSONObject.toJSONString(result);
		return json;
	}
	
	/**
	 * 结果返回工具类(天府通专用)
	 * @param message
	 * @param status
	 * @param data
	 * @return map
	 */
	public static Map<String, Object> resultforTft(String message,String status,Object data){
		Map<String, Object> result = new HashMap<>();
		//提示信息
		result.put("message", message);
		//返回状态
		result.put("messageStatus", status);
		//返回数据
		result.put("data", data);
		return result;
	}
	
}
