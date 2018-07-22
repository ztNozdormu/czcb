/**
 * 2018/2/7 11:37:59 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IVisitOrderLogDao;
import cn.com.czcb.model.VisitOrderLog;
import cn.com.czcb.service.IVisitOrderLogService;
/**
 *  Service 实现
 * Created by AJiong on 2018/02/07.
 */
@Service
public class VisitOrderLogService extends ModelService<VisitOrderLog> implements IVisitOrderLogService {

    @Autowired
	private IVisitOrderLogDao visitOrderLogDao;

	@Override
	public IIbatisDataProvider<VisitOrderLog, String> getModelDao() {
		return this.visitOrderLogDao;
	}

}
