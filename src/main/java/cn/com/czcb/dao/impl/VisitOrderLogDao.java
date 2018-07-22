/**
 * 2018/2/7 11:37:59 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IVisitOrderLogDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.VisitOrderLog;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/02/07.
 */
@Repository
public class VisitOrderLogDao extends IbatisDataProvider<VisitOrderLog, String> implements IVisitOrderLogDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="visitOrderLogDao" class="cn.com.czcb.dao.impl.VisitOrderLogDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public VisitOrderLogDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IVisitOrderLogDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IVisitOrderLogDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IVisitOrderLogDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IVisitOrderLogDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IVisitOrderLogDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IVisitOrderLogDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IVisitOrderLogDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IVisitOrderLogDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IVisitOrderLogDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
