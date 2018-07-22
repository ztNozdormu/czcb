/**
 * 2018/1/25 15:06:06 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IChargeRecordDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.ChargeRecord;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class ChargeRecordDao extends IbatisDataProvider<ChargeRecord, String> implements IChargeRecordDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="chargeRecordDao" class="cn.com.czcb.dao.impl.ChargeRecordDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public ChargeRecordDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IChargeRecordDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IChargeRecordDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IChargeRecordDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IChargeRecordDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IChargeRecordDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IChargeRecordDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IChargeRecordDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IChargeRecordDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IChargeRecordDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
