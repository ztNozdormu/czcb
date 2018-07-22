/**
 * 2018/3/1 17:35:07 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.ITransferLogsDao;
import cn.com.czcb.model.TransferLogs;
import cn.com.czcb.service.ITransferLogsService;
/**
 * 圈存记录 Service 实现
 * Created by AJiong on 2018/03/01.
 */
@Service
public class TransferLogsService extends ModelService<TransferLogs> implements ITransferLogsService {

    @Autowired
	private ITransferLogsDao transferLogsDao;

	@Override
	public IIbatisDataProvider<TransferLogs, String> getModelDao() {
		return this.transferLogsDao;
	}

}
