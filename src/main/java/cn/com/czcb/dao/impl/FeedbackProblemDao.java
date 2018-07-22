/**
 * 2018/4/9 10:53:21 Wen Jun created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IFeedbackProblemDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.FeedbackProblem;

/**
 *  Ibatis Dao 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Repository
public class FeedbackProblemDao extends IbatisDataProvider<FeedbackProblem, String> implements IFeedbackProblemDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="feedbackProblemDao" class="cn.com.czcb.dao.impl.FeedbackProblemDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public FeedbackProblemDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IFeedbackProblemDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IFeedbackProblemDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IFeedbackProblemDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IFeedbackProblemDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IFeedbackProblemDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IFeedbackProblemDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IFeedbackProblemDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IFeedbackProblemDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IFeedbackProblemDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
