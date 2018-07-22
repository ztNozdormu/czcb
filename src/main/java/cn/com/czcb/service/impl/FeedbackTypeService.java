/**
 * 2018/4/9 10:53:24 Wen Jun created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IFeedbackTypeDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.FeedbackType;
import cn.com.czcb.service.IFeedbackTypeService;
/**
 *  Service 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Service
public class FeedbackTypeService extends ModelService<FeedbackType> implements IFeedbackTypeService {

    @Autowired
	private IFeedbackTypeDao feedbackTypeDao;

	@Override
	public IIbatisDataProvider<FeedbackType, String> getModelDao() {
		return this.feedbackTypeDao;
	}

}
