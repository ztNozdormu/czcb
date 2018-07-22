package cn.com.czcb.controller;

import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.model.ChargeRecord;
import cn.com.czcb.model.User;
import cn.com.czcb.model.VisitOrderLog;
import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.model.tft.AccountTransferData;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.pay.PayConstants;
import cn.com.czcb.service.IChargeOrderService;
import cn.com.czcb.service.IChargeRecordService;
import cn.com.czcb.service.ITftService;
import cn.com.czcb.service.IUserService;

/**
 * 天府通
 * @author AJiong
 * @version $Id: TftController.java, v 0.1 2018年2月7日 上午9:39:12 Ajiong Exp $
 */
@Controller
@ResponseBody
@RequestMapping(value = "/tft")
public class TftController extends BaseController {

    /** 日志对象 */
    private Logger              log = LoggerFactory.getLogger(getClass());
    /**
     *充值订单服务 
     */
    @Autowired
    private IChargeOrderService chargeOrderService;
    /**
     *圈存记录服务 
     */
    @Autowired
    private IChargeRecordService chargeRecordService;
    /**
     * 天府通服务
     */
    @Autowired
    private ITftService tftService;
    /** 用户服务 */
    @Autowired
    private IUserService         userService;

    /**
    * 查询正在充值的订单
    * @param cardNo
    * @param request
    * @return String
    */
    @RequestMapping(value="findChargingOrder", method = RequestMethod.POST)
    public String findChargingOrder(String cardNo,HttpServletRequest request){
        try {
            QueryParams params = new QueryParams();
            params.addParameter("cardNo", cardNo);
            params.addParameter("status", 1);
            List<ChargeOrder> chargeOrderList = chargeOrderService.queryList(params);
            Map<String, Object> result = new HashMap<>();
            if (chargeOrderList != null && chargeOrderList.size() > 0) {
                result.put("chargeOrderList", chargeOrderList);
            }
            return returnJsonResult(result);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }
    /**
     * 账户圈存接口
     * @param orderId 订单编号
     * @param cardBalance 卡余额
     * @param initRapdu 圈存初始化指令
     * @param transMark 交易标志 1：正常交易，2：冲正交易
     * @return String 
     */
    @RequestMapping(value = "/accountTransfer", method = RequestMethod.POST)
    public String accountTransfer(@RequestParam String orderId, @RequestParam Integer cardBalance,
                                  @RequestParam String initRapdu, @RequestParam Integer transMark) {
        try {
            UserSession userSession = AppContext.getCurrUserSession();
            String userId = userSession.getUserId();
            User user = userService.getModelById(userId);
            ChargeOrder chargeOrder = chargeOrderService.getModelById(orderId);
            if (chargeOrder == null || !PayConstants.PAY_STATUS_PAID.equals(chargeOrder.getStatus())) {
                if (PayConstants.PAY_STATUS_UNPAY.equals(chargeOrder.getStatus())) {
                    throw new AppException("该订单未支付，暂不能发起圈存",1);
                }else if(PayConstants.PAY_STATUS_CHARGED.equals(chargeOrder.getStatus())){
                    throw new AppException("该订单已经圈存确认，不能重复圈存",1);
                }
            }
            AccountTransferData accountTransferData = tftService.transfer(chargeOrder.getCardNo(), orderId, cardBalance, initRapdu,
                transMark, userId, user, chargeOrder);
            log.info("圈存接口返回客户端信息: {}", accountTransferData);
            return returnJsonResult(accountTransferData);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }


    /**
     * 圈存确认接口
     * @param cardBalance 卡余额
     * @param rapdu 脚本执行结果
     * @param orderId 订单编号
     * @return String
     */
    @RequestMapping(value = "/accountTransferConfirm", method = RequestMethod.POST)
    public String accountTransferConfirm(@RequestParam Integer cardBalance,@RequestParam String[] rapdu,@RequestParam String orderId) {
        try {
            ChargeOrder chargeOrder = chargeOrderService.getModelById(orderId);
            QueryParams params = new QueryParams();
            params.addParameter("orderId", orderId);
            params.addOrderBy("createTime",false);
            List<ChargeRecord> chargeRecordList = chargeRecordService.queryList(params);
            if (chargeOrder == null || !PayConstants.PAY_STATUS_PAID.equals(chargeOrder.getStatus())) {
                if (PayConstants.PAY_STATUS_UNPAY.equals(chargeOrder.getStatus())) {
                    throw new AppException("该订单未支付，暂不能发起圈存",1);
                }else if(PayConstants.PAY_STATUS_CHARGED.equals(chargeOrder.getStatus())){
                    throw new AppException("该订单已经进行圈存，不能重复圈存",1);
                }
            }
            if (chargeRecordList == null || chargeRecordList.size() == 0) {
                throw new AppException("未找到圈存记录，不能进行圈存确认",1); 
            }
            tftService.confirm(orderId, chargeOrder, chargeRecordList,cardBalance,rapdu);
            return returnJsonResult(SUCCESS);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }

    /**
     * 圈存确认接口
     * @param cardNo 卡号
     * @return String
     */
    @RequestMapping(value = "/accountBalance", method = RequestMethod.POST)
    public String accountBalance(@RequestParam String cardNo) {
        try {
            String result = tftService.findAccountBalance(cardNo);
            return returnJsonResult(result);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam  String test) {
        try {

             String result="测试接口返回值:"+test;
            return returnJsonResult(result);
        } catch (Exception e) {
            return returnJsonResultWithError(e);
        }
    }
}
