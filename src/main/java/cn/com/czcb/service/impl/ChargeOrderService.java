/**
 * 2018/1/25 15:06:05 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IChargeOrderDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.service.IChargeOrderService;
/**
 *  Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class ChargeOrderService extends ModelService<ChargeOrder> implements IChargeOrderService {

    /**  */
    @Autowired
	private IChargeOrderDao chargeOrderDao;

	/** 
	 * @see cn.com.czcb.service.impl.ModelService#getModelDao()
	 */
	@Override
	public IIbatisDataProvider<ChargeOrder, String> getModelDao() {
		return this.chargeOrderDao;
	}

    /** 
     * @see cn.com.czcb.service.IChargeOrderService#queryUnReadCount(cn.com.czcb.dao.QueryParams)
     */
    @Override
    public int queryUnReadCount(QueryParams params) {
        return chargeOrderDao.queryUnReadCount(params);
    }

}
