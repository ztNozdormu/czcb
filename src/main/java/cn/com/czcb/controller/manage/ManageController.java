package cn.com.czcb.controller.manage;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.czcb.controller.BaseController;
import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.UserInfoDto;
import cn.com.czcb.model.*;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.GsonUtil;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.service.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Wen Jun
 * @version $Id: ManageController.java, v 0.1 2017年11月7日 下午3:45:31 Wen Jun Exp $
 */
@RequestMapping("BG")
@Controller
public class ManageController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IChargeOrderService chargeOrderService;

    @Autowired
    private IFeedbackService feedbackService;

    @Autowired
    private IFeedbackTypeService feedbackTypeService;

    @Autowired
    private IFeedbackProblemService feedbackProblemService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IWechatPayRecordService wechatPayRecordService;

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private IUserDeviceService userDeviceService;

    /**
     * 查询所有订单信息
     */
    @RequestMapping(value = "charge/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String chargeList(String startTime, String endTime, String nickName, String payType,
            String status, String wechatNo,String cardNo,String chargeNum,
            Integer pageNumber, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        QueryParams queryParams = new QueryParams();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        try {
            if (!ObjectUtils.isEmpty(startTime)) {
                queryParams.addParameterByRange("createTime",
                        ObjectUtils.parseDateByDay(startTime).getTime(),
                        ObjectUtils.parseDateByDay(endTime).getTime());
            }
            if (!ObjectUtils.isEmpty(payType)) {
                queryParams.addParameter("payType", Integer.valueOf(payType));
            }
            if (!ObjectUtils.isEmpty(chargeNum)) {
                queryParams.addParameter("chargeNum", Integer.valueOf(chargeNum));
            }
            if (!ObjectUtils.isEmpty(cardNo)) {
                queryParams.addParameter("cardNo", cardNo);
            }
            if (!ObjectUtils.isEmpty(status)){
                queryParams.addParameter("status",status);
            }else{
                queryParams.addParameterByEnum("status",1,2);
            }
            if(!ObjectUtils.isEmpty(wechatNo)){
                QueryParams queryWechatParams = new QueryParams();
                queryWechatParams.addParameter("transaction_id",wechatNo);
                List<WechatPayRecord> wechatPayRecords = wechatPayRecordService.queryList(queryWechatParams);
                if (!wechatPayRecords.isEmpty()){
                    queryParams.addParameter("id",wechatPayRecords.get(0).getOut_trade_no());
                }else{
                    queryParams.addParameter("id","abcde123");
                }
            }
            if (!ObjectUtils.isEmpty(nickName)) {
                QueryParams userParams = new QueryParams();
                userParams.addParameter("nickName", nickName);
                List<User> users = userService.queryList(userParams);
                if (!users.isEmpty()) {
                    queryParams.addParameter("userId", users.get(0).getId());
                } else {
                    queryParams.addParameter("userId", "abcde123");
                }
            }
            PagedList<ChargeOrder> pages = chargeOrderService
                    .queryPagedList(queryParams, pageNumber - 1, pageSize);
            QueryParams wechatParams = new QueryParams();

            for (int i = 0; i < pages.getList().size(); i++) {
                pages.getList().get(i).setPhone(
                        userService.getModelById(pages.getList().get(i).getUserId()).getNickName());
                wechatParams.addParameter("out_trade_no",pages.getList().get(i).getId());
                List<WechatPayRecord> list = wechatPayRecordService.queryList(wechatParams);
                if (!list.isEmpty()){
                    pages.getList().get(i).setTransactionId(list.get(0).getTransaction_id());
                }
            }
            result.put("rows", pages);
            map.put("total", pages.getTotalSize());
            map.put("rows", pages.getList());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }

        return GsonUtil.gsonString(map);
    }

    /**
     * 查看所有反馈信息
     */
    @RequestMapping(value = "feedback/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getFeedbackList(String phone, String feedbackTypeId, String startTime,
            String endTime,
            Integer pageNumber, Integer pageSize) {
        PagedList<Feedback> pagedList = null;
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> map = new HashMap<>();
        List<Feedback> list = null;
        try {
            QueryParams params = new QueryParams();
            if (!ObjectUtils.isEmpty(phone)) {
                params.addParameter("phone", phone);
            }
            if (!ObjectUtils.isEmpty(feedbackTypeId)) {
                params.addParameter("feedbackTypeId", feedbackTypeId);
            }
            if (!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)) {
                params.addParameterByRange("createTime",
                        ObjectUtils.parseDateByDay(startTime).getTime(),
                        ObjectUtils.parseDateByDay(endTime).getTime());
            }
            pagedList = feedbackService.queryPagedList(params, pageNumber - 1, pageSize);
            list = pagedList.getList();
            for (int m = 0; m < list.size(); m++) {
                int i = 0;
                if (!ObjectUtils.isEmpty(list.get(m).getPicture1())) {
                    i++;
                }
                if (!ObjectUtils.isEmpty(list.get(m).getPicture2())) {
                    i++;
                }
                if (!ObjectUtils.isEmpty(list.get(m).getPicture3())) {
                    i++;
                }
                if (!ObjectUtils.isEmpty(list.get(m).getPicture4())) {
                    i++;
                }
                list.get(m).setSum(i);
                //处理反馈问题展示
                String problemes = list.get(m).getFeedbackProblemText();
                if(StringUtils.isNotBlank(problemes)&&problemes.contains("@")) {
                	list.get(m).setFeedbackProblemText(problemes.replaceAll("@", "；"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
        map.put("total", pagedList.getTotalSize());
        map.put("rows", list);
        return GsonUtil.gsonString(map);
    }

    /**
     * 得到反馈详情
     */
    @RequestMapping(value = "feedback/info", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getFeedbackInfo(
            String id) {
        Feedback feedback = feedbackService.getModelById(id);
        //处理反馈问题展示
        String problemes = feedback.getFeedbackProblemText();
        if(StringUtils.isNotBlank(problemes)&&problemes.contains("@")) {
        	feedback.setFeedbackProblemText(problemes.replaceAll("@", "；"));
        }
        return GsonUtil.gsonString(feedback);
    }

    /**
     * 删除反馈信息
     */
    @RequestMapping(value = "feedback/delete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deleteFeedback(String feedbackId) {
        boolean flag = feedbackService.doDeleteModel(feedbackId);
        if (flag) {
            return SUCCESS;
        }
        return "error";
    }

    /**
     * 得到反馈类型列表
     */
    @RequestMapping(value = "feedbackType/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getFeedbackTypeList(Integer pageNumber, Integer pageSize) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> result = new HashMap<>();
        PagedList<FeedbackType> pagedList = null;
        try {
            QueryParams params = new QueryParams();
            params.addParameter("deleteFlag", 0);
            pagedList = feedbackTypeService.queryPagedList(params, pageNumber - 1, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
        result.put("total", pagedList.getTotalSize());
        result.put("rows", pagedList.getList());
        return GsonUtil.gsonString(result);
    }

    /**
     * 添加或修改反馈类型
     */
    @RequestMapping(value = "feedbackType/edit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String feedbackTypeEdit(String feedbackTypeId, String text) {
        try {
            if (!ObjectUtils.isEmpty(feedbackTypeId)) {
                FeedbackType feedbackType = feedbackTypeService.getModelById(feedbackTypeId);
                feedbackType.setText(text);
                feedbackType.setCreateTime(System.currentTimeMillis());
                feedbackType.setDeleteFlag(0);
                feedbackTypeService.doUpdateModel(feedbackType);
                return SUCCESS;
            }
            FeedbackType feedbackType = new FeedbackType();
            feedbackType.setId(UUID.randomUUID().toString());
            feedbackType.setText(text);
            feedbackType.setCreateTime(System.currentTimeMillis());
            feedbackType.setDeleteFlag(0);
            feedbackTypeService.doAddModel(feedbackType);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
    }

    /**
     * 删除反馈类型
     */
    @RequestMapping(value = "feedbackType/delete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String feedbackTypeDelete(String feedbackTypeId) {
        if (!ObjectUtils.isEmpty(feedbackTypeId)) {
            FeedbackType feedbackType = feedbackTypeService.getModelById(feedbackTypeId);
            feedbackType.setDeleteFlag(1);
            feedbackTypeService.doUpdateModel(feedbackType);
            return SUCCESS;
        }
        return "error";
    }

    /**
     * 添加或修改反馈问题
     */
    @RequestMapping(value = "feedbackProblem/edit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String feedbackProblemEdit(String feedbackProblemId, String text,
            String feedbackTypeId) {
        try {
            if (!ObjectUtils.isEmpty(feedbackProblemId)) {
                FeedbackProblem feedbackProblem = feedbackProblemService
                        .getModelById(feedbackProblemId);
                feedbackProblem.setText(text);
                feedbackProblem.setTypeId(feedbackTypeId);
                feedbackProblem.setCreateTime(System.currentTimeMillis());
                feedbackProblem.setDeleteFlag(0);
                feedbackProblemService.doUpdateModel(feedbackProblem);
                return SUCCESS;
            }
            FeedbackProblem feedbackProblem = new FeedbackProblem();
            feedbackProblem.setId(UUID.randomUUID().toString());
            feedbackProblem.setText(text);
            feedbackProblem.setTypeId(feedbackTypeId);
            feedbackProblem.setCreateTime(System.currentTimeMillis());
            feedbackProblem.setDeleteFlag(0);
            feedbackProblemService.doAddModel(feedbackProblem);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
    }

    /**
     * 得到反馈问题列表
     */
    @RequestMapping(value = "feedbackProblem/list", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String feedbackProblemList(String feedbackTypeId, Integer pageNumber, Integer pageSize) {
        Map<String, Object> result = new HashMap<>();
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedList<FeedbackProblem> pagedList = null;
        try {
            QueryParams params = new QueryParams();
            params.addParameter("deleteFlag", 0);
            params.addParameter("typeId", feedbackTypeId);
            pagedList = feedbackProblemService.queryPagedList(params, pageNumber - 1, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
        result.put("total", pagedList.getTotalSize());
        result.put("rows", pagedList.getList());
        return GsonUtil.gsonString(result);
    }

    /**
     * 删除反馈问题
     */
    @RequestMapping(value = "feedbackProblem/delete", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String feedbackProblemDelete(String feedbackProblemId) {
        if (!ObjectUtils.isEmpty(feedbackProblemId)) {
            FeedbackProblem feedbackProblem = feedbackProblemService
                    .getModelById(feedbackProblemId);
            feedbackProblem.setDeleteFlag(1);
            feedbackProblemService.doUpdateModel(feedbackProblem);
            return SUCCESS;
        }
        return "error";
    }

    /**
     * 导出表格 用户反馈意见
     */
    @RequestMapping("export")
    @ResponseBody
    public String exportHasOldCardTable(HttpServletResponse response, String phone,
            String feedbackTypeId, String startTime,
            String endTime) {
        try {
            QueryParams params = new QueryParams();
            if (!ObjectUtils.isEmpty(phone)) {
                params.addParameter("phone", phone);
            }
            if (!ObjectUtils.isEmpty(feedbackTypeId)) {
                params.addParameter("feedbackTypeId", feedbackTypeId);
            }
            if (!ObjectUtils.isEmpty(startTime) && !ObjectUtils.isEmpty(endTime)) {
                params.addParameterByRange("createTime",
                        ObjectUtils.parseDateByDay(startTime).getTime(),
                        ObjectUtils.parseDateByDay(endTime).getTime());
            }
            List<Feedback> feedbacks = feedbackService.queryList(params);
            //导出查询到的数据
            HSSFWorkbook hw = doExport(feedbacks);
            OutputStream output = response.getOutputStream();
            response.reset();
            String fileName = URLEncoder.encode("用户反馈信息.xls", "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            hw.write(output);
            output.close();
            return returnJsonResult(SUCCESS);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }

    /**
     * 导出表格 用户信息
     */
    @RequestMapping("export_user")
    @ResponseBody
    public String exportUserInfo(HttpServletResponse response, String openId, String startTime, String endTime) {
        try {
            QueryParams params = new QueryParams();
            if (!ObjectUtils.isEmpty(openId)) {
                params.addParameter("openId", openId);
            }
            if (!ObjectUtils.isEmpty(startTime)) {
                params.addParameter("startTime", ObjectUtils.parseDateByDay(startTime).getTime());
            }
            if (!ObjectUtils.isEmpty(endTime)) {
                params.addParameter("endTime", ObjectUtils.parseDateByDay(endTime).getTime());
            }
            List<UserInfoDto> list = deviceService.queryUserInfoList(params);
            for (int i = 0; i < list.size(); i++) {
                QueryParams countParams = new QueryParams();
                countParams.addParameter("userId", list.get(i).getUserId());
                int count = userDeviceService.queryCount(countParams);
                list.get(i).setDeviceCount(count);
            }
            HSSFWorkbook hw = doExportUser(list);
            OutputStream output = response.getOutputStream();
            response.reset();
            String fileName = URLEncoder.encode("用户信息.xls", "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            response.setContentType("application/msexcel");
            hw.write(output);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnJsonResult(SUCCESS);
    }

    /**
     * 将反馈信息对象的集合转化成HSSFWorkbook
     */
    private HSSFWorkbook doExport(List<Feedback> feedbacks) throws Exception {
        HSSFWorkbook wkb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wkb.createSheet("用户反馈信息");
        sheet.setColumnWidth((short) 0, 5000); //调整第一列宽度
        sheet.setColumnWidth((short) 1, 6000); //调整第二列宽度
        sheet.setColumnWidth((short) 2, 6000); //调整第三列宽度
        sheet.setColumnWidth((short) 3, 8000); //调整第四列宽度
        sheet.setColumnWidth((short) 4, 6000); //调整第五列宽度

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 500);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("用户反馈信息");

        HSSFCellStyle cellStyle = wkb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行
        // 设置单元格字体
        HSSFFont font = wkb.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        row2.setHeight((short) 400);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("用户电话");//新办,补办
        row2.createCell(1).setCellValue("反馈类型");//学校
        row2.createCell(2).setCellValue("反馈问题");//学校
        row2.createCell(3).setCellValue("详细描述");
        row2.createCell(4).setCellValue("反馈时间");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int i = 2;
        for (Feedback feedback : feedbacks) {
            //在sheet里创建第三行
            HSSFRow row3 = sheet.createRow(i);
            row3.setHeight((short) 400);
            row3.createCell(0).setCellValue(feedback.getPhone());
            row3.createCell(1).setCellValue(feedback.getFeedbackTypeText());
            row3.createCell(2).setCellValue(feedback.getFeedbackProblemText());
            row3.createCell(3).setCellValue(feedback.getText());
            if (feedback.getCreateTime() != null) {
                row3.createCell(4)
                        .setCellValue(simpleDateFormat.format(new Date(feedback.getCreateTime())));
            }
            i++;
        }
        return wkb;
    }

    /**
     * 将用户信息对象的集合转化成HSSFWorkbook
     */
    private HSSFWorkbook doExportUser(List<UserInfoDto> userInfoDtos) throws Exception {
        HSSFWorkbook wkb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wkb.createSheet("用户信息");
        sheet.setColumnWidth((short) 0, 5000); //调整第一列宽度
        sheet.setColumnWidth((short) 1, 6000); //调整第二列宽度
        sheet.setColumnWidth((short) 2, 6000); //调整第三列宽度

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        row1.setHeight((short) 500);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("用户信息");

        HSSFCellStyle cellStyle = wkb.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
        cellStyle.setWrapText(true);// 指定单元格自动换行
        // 设置单元格字体
        HSSFFont font = wkb.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 250);
        cellStyle.setFont(font);

        cell.setCellStyle(cellStyle);

        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        row2.setHeight((short) 400);
        //创建单元格并设置单元格内容
        row2.createCell(0).setCellValue("用户名称");//新办,补办
        row2.createCell(1).setCellValue("设备数量");//学校
        row2.createCell(2).setCellValue("注册时间");//学校
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int i = 2;
        for (UserInfoDto userInfo : userInfoDtos) {
            //在sheet里创建第三行
            HSSFRow row3 = sheet.createRow(i);
            row3.setHeight((short) 400);
            row3.createCell(0).setCellValue(userInfo.getOpenId());
            row3.createCell(1).setCellValue(userInfo.getDeviceCount());
            if (userInfo.getCreateTime() != null) {
                row3.createCell(2)
                        .setCellValue(simpleDateFormat.format(new Date(userInfo.getCreateTime())));
            }
            i++;
        }
        return wkb;
    }


    /**
     * 查询用户信息
     * @param nickName
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("userInfo")
    @ResponseBody
    public String getUserInfo(String nickName, String startTime, String endTime, Integer pageNumber, Integer pageSize ){
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Map<String, Object> map = new HashMap<>();
        PagedList<UserInfoDto> pagedList = null;
        QueryParams params = new QueryParams();
        try {
            if (!ObjectUtils.isEmpty(nickName)){
                params.addParameter("nickName", nickName);
            }
            if (!ObjectUtils.isEmpty(startTime)) {
                params.addParameter("startTime",ObjectUtils.parseDateByDay(startTime).getTime());
            }
            if(!ObjectUtils.isEmpty(endTime)){
                params.addParameter("endTime",ObjectUtils.parseDateByDay(endTime).getTime());
            }
            pagedList = deviceService.queryUserInfoListByPage(params,pageNumber-1,pageSize);
            List<UserInfoDto> userInfoDtos = pagedList.getList();
            for(int i=0;i<userInfoDtos.size();i++){
                QueryParams countParams = new QueryParams();
                countParams.addParameter("userId",userInfoDtos.get(i).getUserId());
                int count = userDeviceService.queryCount(countParams);
                userInfoDtos.get(i).setDeviceCount(count);
            }
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
        map.put("total", pagedList.getTotalSize());
        map.put("rows", pagedList.getList());

        return GsonUtil.gsonString(map);

    }

    @RequestMapping("userInfo/info")
    @ResponseBody
    public String getUserSimInfo(String openId){
        Map<String ,Object> result = new HashMap<>();
        QueryParams userParams = new QueryParams();
        QueryParams deviceParams = new QueryParams();
        try {
            userParams.addParameter("openid",openId);
            User user = userService.queryList(userParams).get(0);
            if(user==null){
                throw new AppException("用户为空");
            }
            deviceParams.addParameter("userId",user.getId());
            List<UserDevice> userDevices = userDeviceService.queryList(deviceParams);
            List<Device> list = new ArrayList<>();
            for(int i=0;i<userDevices.size();i++){
                list.add(deviceService.getModelById(userDevices.get(i).getDeviceId()));
            }
            result.put("user",user);
            result.put("device",list);
        } catch (AppException e) {
            e.printStackTrace();
            return returnJsonResultWithError(e);
        }
        return GsonUtil.gsonString(result);
    }

}
