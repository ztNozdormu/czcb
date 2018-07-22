/**
 * 2018/1/25 17:05:18 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.ISmsCodeDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.SmsCode;

/**
 * 短信验证码 Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class SmsCodeDao extends IbatisDataProvider<SmsCode, String> implements ISmsCodeDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="smsCodeDao" class="cn.com.czcb.dao.impl.SmsCodeDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public SmsCodeDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.ISmsCodeDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.ISmsCodeDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.ISmsCodeDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.ISmsCodeDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.ISmsCodeDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.ISmsCodeDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.ISmsCodeDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.ISmsCodeDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.ISmsCodeDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}
	
	/** 
     * @see cn.com.czcb.dao.ISmsCodeDao#updateSmsCodeList(cn.com.czcb.dao.QueryParams)
     */
    @Override
    public void updateSmsCodeList(QueryParams wheres) {
        Integer sum = getSqlSession().selectOne("cn.com.czcb.dao.ISmsCodeDao.updateSmsCodeList",wheres);
        if(sum==null){
            sum = 0;
        }
    }
}
