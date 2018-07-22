package cn.com.czcb.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.czcb.controller.BaseController;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.Admin;
import cn.com.czcb.model.Feedback;
import cn.com.czcb.model.FeedbackType;
import cn.com.czcb.pub.EncpUtils;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.service.IAdminService;
import cn.com.czcb.service.IFeedbackTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wenjun on 2018/4/9.
 */
@Controller
@RequestMapping("manage")
public class ManageGroundController extends BaseController {

    /**  */
    @Autowired
    private IAdminService adminService;

    @Autowired
    private IFeedbackTypeService feedbackTypeService;

    @RequestMapping("index")
    public String goIndex() {
        return "manage/default";
    }

    @RequestMapping("login")
    public String goLogin() {
        return "manage/login";
    }

    @RequestMapping("recharge")
    public String goRecharge() {
        return "manage/recharge";
    }

    @RequestMapping("admin_fk")
    public String goFeedback(HttpServletRequest request) {
        QueryParams params = new QueryParams();
        params.addParameter("deleteFlag", 0);
        List<FeedbackType> list = feedbackTypeService.queryList(params);
        request.setAttribute("feeds", list);
        return "manage/admin_fk";
    }

    @RequestMapping("admin_user")
    public String goFeedbackConfig() {
        return "manage/admin_user";
    }

    @RequestMapping("fk_config")
    public String goConfig(String id, HttpServletRequest request) {
        FeedbackType feedbackType = feedbackTypeService.getModelById(id);
        request.setAttribute("feedbackType", feedbackType);
        return "manage/fk_config";
    }

    @RequestMapping("user_info")
    public String goUserInfo(){
        return "manage/user_info";
    }

    /**
     * 执行后台登录
     *
     * @return string
     */
    @RequestMapping(value = "doBGLogin", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String bgLogin(HttpServletRequest request, HttpServletResponse response, String name,
            String pwd) {
        QueryParams params = new QueryParams();
        if (!ObjectUtils.isEmpty(name)) {
            params.addParameter("account", name);
        }
        if (!ObjectUtils.isEmpty(pwd)) {
            params.addParameter("password", EncpUtils.encpUserPwd(pwd));
        }
        List<Admin> admins = adminService.queryList(params);
        // 判断用户名和密码是否正确
        if (!admins.isEmpty()) {
            request.getSession().setAttribute("admin", admins.get(0).getId());
            return SUCCESS;
        }
        return "error";

    }

    @RequestMapping(value = "exit")
    public String exit(HttpServletRequest request) {
        request.getSession().removeAttribute("admin");
        return "redirect:login";
    }
}
