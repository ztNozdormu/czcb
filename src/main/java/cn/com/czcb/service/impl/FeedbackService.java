/**
 * 2018/4/9 10:53:20 Wen Jun created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IFeedbackDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.Feedback;
import cn.com.czcb.service.IFeedbackService;
/**
 *  Service 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Service
public class FeedbackService extends ModelService<Feedback> implements IFeedbackService {

    @Autowired
	private IFeedbackDao feedbackDao;

	@Override
	public IIbatisDataProvider<Feedback, String> getModelDao() {
		return this.feedbackDao;
	}

}
