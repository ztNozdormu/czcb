/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2018 All Rights Reserved.
 */
package cn.com.czcb.service;

import java.util.List;

import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.model.ChargeRecord;
import cn.com.czcb.model.User;
import cn.com.czcb.model.tft.AccountTransferData;

/**
 * 
 * @author AJiong
 * @version $Id: ITftService.java, v 0.1 2018年2月27日 上午9:47:45 Ajiong Exp $
 */
public interface ITftService {
    /**
     * 圈存
     * @param cardNo
     * @param orderId
     * @param cardBalance
     * @param initRapdu
     * @param transMark
     * @param userId
     * @param user
     * @param chargeOrder
     * @return AccountTransferData
     * @throws Exception
     */
    AccountTransferData transfer(String cardNo, String orderId, Integer cardBalance,
                                          String initRapdu, Integer transMark, String userId,
                                          User user, ChargeOrder chargeOrder)throws Exception;
    /**
     * 圈存确认
     * @param orderId
     * @param chargeOrder
     * @param chargeRecordList
     * @param cardBalance
     * @param rapdu
     * @throws Exception
     */
    void confirm(String orderId, ChargeOrder chargeOrder, List<ChargeRecord> chargeRecordList,
                 Integer cardBalance, String[] rapdu) throws Exception;

    /**
     * 查询账户余额
     * @param cardNo
     */
    String findAccountBalance(String cardNo) throws Exception;
}
