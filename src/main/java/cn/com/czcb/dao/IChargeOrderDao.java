/**
 * 2018/1/25 15:06:05 AJiong created.
 */

package cn.com.czcb.dao;

import cn.com.czcb.dto.ChargeDto;
import cn.com.czcb.model.ChargeOrder;

/**
 *  Ibatis Dao 接口
 * Created by AJiong on 2018/01/25.
 */
public interface IChargeOrderDao extends IIbatisDataProvider<ChargeOrder, String> {

    /**
     * 查询未读消息多少条
     * @param params
     * @return int
     */
    int queryUnReadCount(QueryParams params);

}
