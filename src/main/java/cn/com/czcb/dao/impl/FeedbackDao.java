/**
 * 2018/4/9 10:53:19 Wen Jun created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IFeedbackDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.Feedback;

/**
 *  Ibatis Dao 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Repository
public class FeedbackDao extends IbatisDataProvider<Feedback, String> implements IFeedbackDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="feedbackDao" class="cn.com.czcb.dao.impl.FeedbackDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public FeedbackDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IFeedbackDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IFeedbackDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IFeedbackDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IFeedbackDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IFeedbackDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IFeedbackDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IFeedbackDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IFeedbackDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IFeedbackDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
