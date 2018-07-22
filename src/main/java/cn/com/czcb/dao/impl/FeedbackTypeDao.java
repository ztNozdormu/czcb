/**
 * 2018/4/9 10:53:23 Wen Jun created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IFeedbackTypeDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.FeedbackType;

/**
 *  Ibatis Dao 实现
 * Created by Wen Jun on 2018/04/09.
 */
@Repository
public class FeedbackTypeDao extends IbatisDataProvider<FeedbackType, String> implements IFeedbackTypeDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="feedbackTypeDao" class="cn.com.czcb.dao.impl.FeedbackTypeDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public FeedbackTypeDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IFeedbackTypeDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IFeedbackTypeDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IFeedbackTypeDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IFeedbackTypeDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IFeedbackTypeDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IFeedbackTypeDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IFeedbackTypeDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IFeedbackTypeDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IFeedbackTypeDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
