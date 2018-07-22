/**
 * 2018/3/1 17:35:06 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.ITransferConfirmLogsDao;
import cn.com.czcb.model.TransferConfirmLogs;
import cn.com.czcb.service.ITransferConfirmLogsService;
/**
 * 圈存记录 Service 实现
 * Created by AJiong on 2018/03/01.
 */
@Service
public class TransferConfirmLogsService extends ModelService<TransferConfirmLogs> implements ITransferConfirmLogsService {

    @Autowired
	private ITransferConfirmLogsDao transferConfirmLogsDao;

	@Override
	public IIbatisDataProvider<TransferConfirmLogs, String> getModelDao() {
		return this.transferConfirmLogsDao;
	}

}
