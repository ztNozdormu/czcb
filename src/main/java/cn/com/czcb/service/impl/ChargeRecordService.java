/**
 * 2018/1/25 15:06:07 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IChargeRecordDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.ChargeRecord;
import cn.com.czcb.service.IChargeRecordService;
/**
 *  Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class ChargeRecordService extends ModelService<ChargeRecord> implements IChargeRecordService {

    @Autowired
	private IChargeRecordDao chargeRecordDao;

	@Override
	public IIbatisDataProvider<ChargeRecord, String> getModelDao() {
		return this.chargeRecordDao;
	}

}
