/**
 * 2018/4/9 10:53:22 Wen Jun created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IFeedbackProblemDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.FeedbackProblem;
import cn.com.czcb.service.IFeedbackProblemService;
/**
 *  Service 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Service
public class FeedbackProblemService extends ModelService<FeedbackProblem> implements IFeedbackProblemService {

    @Autowired
	private IFeedbackProblemDao feedbackProblemDao;

	@Override
	public IIbatisDataProvider<FeedbackProblem, String> getModelDao() {
		return this.feedbackProblemDao;
	}

}
