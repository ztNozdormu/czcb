/**
 * 2018/1/25 15:06:09 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IWechatPayRecordDao;
import cn.com.czcb.model.WechatPayRecord;
import cn.com.czcb.service.IWechatPayRecordService;
/**
 *  Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class WechatPayRecordService extends ModelService<WechatPayRecord> implements IWechatPayRecordService {

    @Autowired
	private IWechatPayRecordDao wechatPayRecordDao;

	@Override
	public IIbatisDataProvider<WechatPayRecord, String> getModelDao() {
		return this.wechatPayRecordDao;
	}

}
