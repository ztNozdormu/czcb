package cn.com.czcb.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.interceptor.SessionInterceptor;
import cn.com.czcb.model.Admin;
import cn.com.czcb.model.User;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.EncpUtils;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.RedisUtil;
import cn.com.czcb.pub.WechatUtil;
import cn.com.czcb.service.IAdminService;
import cn.com.czcb.service.IUserService;

/**
 * 
 * @author AJiong
 * @version $Id: PublicController.java, v 0.1 2017年11月7日 下午3:47:33 Ajiong Exp $
 */
@Controller
@ResponseBody
@RequestMapping("pub")
public class PublicController extends BaseController {
    /**
     * 日志对象 
     */
    private Logger      logger = LoggerFactory.getLogger(getClass());
    /**
     * 用户服务
     */
    @Autowired
    private IUserService userService;
    
    /**  */
    @Autowired
    private IAdminService adminService;
    
    /**
     * redis
     */
    @Autowired
    private RedisUtil   redisUtil;

    /**
     * 登录小程序
     * @param code
     * @param nickName
     * @param avatarUrl
     * @param gender
     * @param city
     * @param province
     * @param country
     * @param language
     * @param request
     * @param response
     * @return string
     */
    @RequestMapping(value="wechatLogin",method=RequestMethod.POST)
    public String wechatLogin(@RequestParam(value = "code", required = true) String code,
                              @RequestParam(value = "nickName", required = false) String nickName,
                              @RequestParam(value = "avatarUrl", required = false) String avatarUrl,
                              @RequestParam(value = "gender", required = false) String gender,
                              @RequestParam(value = "city", required = false) String city,
                              @RequestParam(value = "province", required = false) String province,
                              @RequestParam(value = "country", required = false) String country,
                              @RequestParam(value = "language", required = false) String language,
                              HttpServletRequest request, HttpServletResponse response) {
        try {
            if (ObjectUtils.isEmpty(code)) {
                throw new AppException("code不能为空",-1);
            }
            String openIdAndSessionKey = WechatUtil.getOpenIdAndSessionKey(code);
            JSONObject jsonObject = JSONObject.parseObject(openIdAndSessionKey);
            if (jsonObject.get("errcode") != null) {
                throw new AppException("登录失败,未获取到openid",-1);
            }
            String openid = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");
            String unionid = jsonObject.getString("unionid");
            QueryParams params = new QueryParams();
            params.addParameter("openid", openid);
            List<User> users = userService.queryList(params);
            User user = null;
            if (ObjectUtils.isEmpty(users)) {
                user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setOpenid(openid);
                user.setUnionid(unionid);
                user.setNickName(nickName);
                user.setAvatarUrl(avatarUrl);
                user.setGender(gender);
                user.setCity(city);
                user.setProvince(province);
                user.setCountry(country);
                user.setLanguage(language);
                user.setUpdateTime(System.currentTimeMillis());
                user.setCreateTime(System.currentTimeMillis());
                userService.doAddModel(user);
            } else {
                user = users.get(0);
                user.setNickName(nickName);
                user.setAvatarUrl(avatarUrl);
                user.setGender(gender);
                user.setCity(city);
                user.setProvince(province);
                user.setCountry(country);
                user.setLanguage(language);
                user.setUpdateTime(System.currentTimeMillis());
                userService.doUpdateModel(user);
            }
            //存储用户信息到redis中
            UserSession userSession = new UserSession();
            userSession.setUserId(user.getId());
            userSession.setOpenid(openid);
            userSession.setSessionKey(sessionKey);
            userSession.setUnionid(unionid);
            String sessionToken = UUID.randomUUID().toString();
            redisUtil.set(sessionToken, userSession, 1800L);
            redisUtil.set("keydom_session_token", sessionToken);
            logger.info("登录成功,openid: {}", openid);
            Boolean hasPhone = !ObjectUtils.isEmpty(user.getPhone());
            //返回session_token和是否有手机号给小程序
            Map<String, Object> result = new HashMap<>();
            logger.info("keydom_session_token: {}",sessionToken);
            result.put(SessionInterceptor.KEYDOM_SESSION_TOKEN, sessionToken);
            result.put("hasPhone", hasPhone);
            result.put("avatarUrl", user.getAvatarUrl());
            result.put("phoneNumber", user.getPhone());
            if (!hasPhone) {
                String smsTokenSession = UUID.randomUUID().toString();
                redisUtil.set("sms_" + sessionToken, smsTokenSession, 300L);
                result.put("sms_session_token", smsTokenSession);
            }
            return returnJsonResult(result);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }

}
