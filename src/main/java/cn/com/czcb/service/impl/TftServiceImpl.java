/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.service.impl;

import cn.com.czcb.dao.IChargeOrderDao;
import cn.com.czcb.dao.IChargeRecordDao;
import cn.com.czcb.dao.ITransferConfirmLogsDao;
import cn.com.czcb.dao.ITransferLogsDao;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.model.ChargeRecord;
import cn.com.czcb.model.TransferConfirmLogs;
import cn.com.czcb.model.TransferLogs;
import cn.com.czcb.model.User;
import cn.com.czcb.model.tft.AccountTransfer;
import cn.com.czcb.model.tft.AccountTransferConfirm;
import cn.com.czcb.model.tft.AccountTransferData;
import cn.com.czcb.model.tft.ResultInfo;
import cn.com.czcb.pub.AppException;
import cn.com.czcb.pub.InitConfig;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.pub.center.RSAEncrypt;
import cn.com.czcb.pub.pay.PayConstants;
import cn.com.czcb.pub.tft.HttpUtil;
import cn.com.czcb.pub.tft.SignUtil;
import cn.com.czcb.service.ITftService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * 
 * @author AJiong
 * @version $Id: TftServiceImpl.java, v 0.1 2018年2月27日 上午9:49:30 Ajiong Exp $
 */
@Service
public class TftServiceImpl implements ITftService {
    /** 日志对象 */
    private Logger                  log = LoggerFactory.getLogger(getClass());

    /** 圈存记录 */
    @Autowired
    private IChargeRecordDao        chargeRecordDao;
    /**
     * 充值订单
     */
    @Autowired
    private IChargeOrderDao         chargeOrderDao;
    /**
     * 圈存日志
     */
    @Autowired
    private ITransferLogsDao        transferLogsDao;
    /**
     * 圈存确认日志
     */
    @Autowired
    private ITransferConfirmLogsDao transferConfirmLogsDao;

    /** 
     * @see cn.com.czcb.service.ITftService#transfer(java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, cn.com.czcb.model.User, cn.com.czcb.model.ChargeOrder)
     */
    @Override
    public AccountTransferData transfer(String cardNo, String orderId, Integer cardBalance,
                                        String initRapdu, Integer transMark, String userId,
                                        User user, ChargeOrder chargeOrder) throws Exception {
        synchronized (orderId.intern()) {
            AccountTransfer accountTransfer = new AccountTransfer();
            accountTransfer.setAmount(chargeOrder.getChargeNum());
            accountTransfer.setCardBalance(cardBalance);
            accountTransfer.setCardNo(cardNo);
            accountTransfer.setInitRapdu(initRapdu);
            accountTransfer.setOrderNo(orderId);
            accountTransfer.setPhone(user.getPhone());
            accountTransfer.setTransMark(transMark);
            accountTransfer.setUserId(userId);
            //获取天府通圈存请求url
            String url = InitConfig.getTftAccountTransferUrl();
            //生成验签签名
            String sign = SignUtil
                .encodeBase64((SignUtil.sign256(JSONObject.toJSONString(accountTransfer),
                    RSAEncrypt.loadPrivateKeyByStr(InitConfig.getTftPrivateKey()))));
            String json = JSONObject.toJSONString(accountTransfer);
            //返回数据
            log.info("圈存接口请求参数:{}", json);
            String result = HttpUtil.tftPost(url, json, sign);
            log.info("圈存接口返回数据:{}", result);
            ResultInfo<AccountTransferData> resultInfo = JSONObject.parseObject(result,
                new TypeReference<ResultInfo<AccountTransferData>>() {
                });
            if (!"200".equals(resultInfo.getCode())) {
                throw new AppException(resultInfo.getMsg(), 1);
            }
            AccountTransferData accountTransferData = resultInfo.getData();
            QueryParams params = new QueryParams();
            params.addParameter("orderId", orderId);
            List<ChargeRecord> chargeRecordList = chargeRecordDao.queryList(params, 0, 1, false);
            ChargeRecord chargeRecord = null;
            if (chargeRecordList != null && chargeRecordList.size() > 0) {
                chargeRecord = chargeRecordList.get(0);
                chargeRecord.setCardNo(cardNo);
                chargeRecord.setCardBalance(cardBalance);
                chargeRecord.setInitRapdu(initRapdu);
                chargeRecord.setTransMark(transMark);
                chargeRecord.setCenterFlowNo(accountTransferData.getCenterFlowNo());
                chargeRecord.setRapdu(ObjectUtils.arrayToString(accountTransferData.getApdu()));
                chargeRecord.setTransDate(accountTransferData.getTransDate());
                chargeRecord.setTransTime(accountTransferData.getTransTime());
                // 最后一次圈存时间
                chargeRecord.setLastTime(System.currentTimeMillis());
                chargeRecordDao.updateObject(chargeRecord);
            } else {
                chargeRecord = new ChargeRecord();
                chargeRecord.setId(UUID.randomUUID().toString());
                chargeRecord.setCardNo(cardNo);
                chargeRecord.setOrderId(orderId);
                chargeRecord.setCardBalance(cardBalance);
                chargeRecord.setInitRapdu(initRapdu);
                chargeRecord.setTransMark(transMark);
                chargeRecord.setCenterFlowNo(accountTransferData.getCenterFlowNo());
                chargeRecord.setRapdu(ObjectUtils.arrayToString(accountTransferData.getApdu()));
                chargeRecord.setTransDate(accountTransferData.getTransDate());
                chargeRecord.setTransTime(accountTransferData.getTransTime());
                chargeRecord.setConfirmed(false);
                chargeRecord.setCreateTime(System.currentTimeMillis());
                chargeRecordDao.insertObject(chargeRecord);
            }
            doAddTransferLogs(chargeRecord, resultInfo.getMsg());
            return accountTransferData;
        }
    }

    /** 
     * @see ITftService#confirm(String, ChargeOrder, List, Integer, String[])
     */
    @Override
    public void confirm(String orderId, ChargeOrder chargeOrder,
                        List<ChargeRecord> chargeRecordList, Integer cardBalance,
                        String[] rapdu) throws Exception {
        ChargeRecord chargeRecord = chargeRecordList.get(0);
        AccountTransferConfirm accountTransferConfirm = new AccountTransferConfirm();
        accountTransferConfirm.setAmount(chargeOrder.getChargeNum());
        accountTransferConfirm.setCardBalance(cardBalance);
        accountTransferConfirm.setCardNo(chargeRecord.getCardNo());
        accountTransferConfirm.setCenterFlowNo(chargeRecord.getCenterFlowNo());
        accountTransferConfirm.setOrderNo(orderId);
        accountTransferConfirm.setRapdu(rapdu);
        accountTransferConfirm.setTransDate(chargeRecord.getTransDate());
        accountTransferConfirm.setTransTime(chargeRecord.getTransTime());
        //获取天府通圈存确认请求url
        String url = InitConfig.getTftAccountTransferConfirmUrl();
        //生成验签签名
        String sign = SignUtil
            .encodeBase64((SignUtil.sign256(JSONObject.toJSONString(accountTransferConfirm),
                RSAEncrypt.loadPrivateKeyByStr(InitConfig.getTftPrivateKey()))));
        String json = JSONObject.toJSONString(accountTransferConfirm);
        log.info("圈存确认接口请求参数:{}", json);
        String result = HttpUtil.tftPost(url, json, sign);
        log.info("圈存确认接口返回数据:{}", result);
        ResultInfo<Void> resultInfo = JSONObject.parseObject(result,
            new TypeReference<ResultInfo<Void>>() {
            });
        chargeRecord.setCardBalance(cardBalance);
        doAddTransferConfirmLogs(chargeRecord, resultInfo.getMsg());
        if (!"200".equals(resultInfo.getCode())) {
            // 圈存确认失败
            log.info("圈存确认失败,resultInfo: {}", resultInfo);
            throw new AppException("圈存确认失败, " + resultInfo.getMsg());
        }
        chargeOrder.setStatus(PayConstants.PAY_STATUS_CHARGED);
        chargeOrderDao.updateObject(chargeOrder);
        chargeRecord.setConfirmed(true);
        chargeRecordDao.updateObject(chargeRecord);
    }

    /**
    *
     * @param cardNo
     */
    @Override
    public String findAccountBalance(String cardNo) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("cradNo", cardNo);
        //获取天府通圈存确认请求url
        String url = InitConfig.getTftAccountBalanceUrl();
        //生成验签签名
        String sign = SignUtil.encodeBase64((SignUtil.sign256(JSONObject.toJSONString(params),
            RSAEncrypt.loadPrivateKeyByStr(InitConfig.getTftPrivateKey()))));
        String json = JSONObject.toJSONString(params);
        log.info("账户余额查询接口请求参数:{}", json);
        String result = HttpUtil.post(url, json, sign);
        log.info("账户余额查询接口返回数据:{}", result);
        return result;
    }

    /**
     * 增加圈存日志记录
     * @param chargeRecord
     * @param msg
     */
    private void doAddTransferLogs(ChargeRecord chargeRecord, String msg) {
        TransferLogs transferLogs = new TransferLogs();
        transferLogs.setId(UUID.randomUUID().toString());
        transferLogs.setCardBalance(chargeRecord.getCardBalance());
        transferLogs.setCardNo(chargeRecord.getCardNo());
        transferLogs.setCenterFlowNo(chargeRecord.getCenterFlowNo());
        transferLogs.setOrderId(chargeRecord.getOrderId());
        transferLogs.setInitRapdu(chargeRecord.getInitRapdu());
        transferLogs.setTransMark(chargeRecord.getTransMark());
        transferLogs.setRapdu(chargeRecord.getRapdu());
        transferLogs.setTransDate(chargeRecord.getTransDate());
        transferLogs.setTransTime(chargeRecord.getTransTime());
        transferLogs.setReturnMsg(msg);
        transferLogs.setCreateTime(System.currentTimeMillis());
        transferLogsDao.insertObject(transferLogs);
    }

    /**
     * 增加圈存确认日志记录
     * @param chargeRecord
     * @param msg
     */
    private void doAddTransferConfirmLogs(ChargeRecord chargeRecord, String msg) {
        TransferConfirmLogs transferConfirmLogs = new TransferConfirmLogs();
        transferConfirmLogs.setId(UUID.randomUUID().toString());
        transferConfirmLogs.setCardBalance(chargeRecord.getCardBalance());
        transferConfirmLogs.setCardNo(chargeRecord.getCardNo());
        transferConfirmLogs.setCenterFlowNo(chargeRecord.getCenterFlowNo());
        transferConfirmLogs.setOrderId(chargeRecord.getOrderId());
        transferConfirmLogs.setTransMark(chargeRecord.getTransMark());
        transferConfirmLogs.setRapdu(chargeRecord.getRapdu());
        transferConfirmLogs.setTransDate(chargeRecord.getTransDate());
        transferConfirmLogs.setTransTime(chargeRecord.getTransTime());
        transferConfirmLogs.setReturnMsg(msg);
        transferConfirmLogs.setCreateTime(System.currentTimeMillis());
        transferConfirmLogsDao.insertObject(transferConfirmLogs);
    }
}
