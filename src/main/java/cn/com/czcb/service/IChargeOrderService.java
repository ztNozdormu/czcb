/**
 * 2018/1/25 15:06:05 AJiong created.
 */

package cn.com.czcb.service;

import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.ChargeDto;
import cn.com.czcb.model.ChargeOrder;

/**
 *  Service 接口
 * Created by AJiong on 2018/01/25.
 */
public interface IChargeOrderService extends IModelService<ChargeOrder> {
    /**
     * 查询未读消息多少条
     * @param params
     * @return int
     */
    int queryUnReadCount(QueryParams params);

}
