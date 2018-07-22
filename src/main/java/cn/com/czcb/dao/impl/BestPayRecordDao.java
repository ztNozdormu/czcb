/**
 * 2018/1/25 15:06:04 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IBestPayRecordDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.BestPayRecord;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class BestPayRecordDao extends IbatisDataProvider<BestPayRecord, String> implements IBestPayRecordDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="bestPayRecordDao" class="cn.com.czcb.dao.impl.BestPayRecordDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public BestPayRecordDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IBestPayRecordDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IBestPayRecordDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IBestPayRecordDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IBestPayRecordDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IBestPayRecordDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IBestPayRecordDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IBestPayRecordDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IBestPayRecordDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IBestPayRecordDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
