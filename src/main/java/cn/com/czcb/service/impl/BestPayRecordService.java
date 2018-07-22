/**
 * 2018/1/25 15:06:04 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IBestPayRecordDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.BestPayRecord;
import cn.com.czcb.service.IBestPayRecordService;
/**
 *  Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class BestPayRecordService extends ModelService<BestPayRecord> implements IBestPayRecordService {

    @Autowired
	private IBestPayRecordDao bestPayRecordDao;

	@Override
	public IIbatisDataProvider<BestPayRecord, String> getModelDao() {
		return this.bestPayRecordDao;
	}

}
