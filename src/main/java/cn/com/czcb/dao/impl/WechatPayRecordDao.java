/**
 * 2018/1/25 15:06:08 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IWechatPayRecordDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.WechatPayRecord;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class WechatPayRecordDao extends IbatisDataProvider<WechatPayRecord, String> implements IWechatPayRecordDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="wechatPayRecordDao" class="cn.com.czcb.dao.impl.WechatPayRecordDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public WechatPayRecordDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IWechatPayRecordDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IWechatPayRecordDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IWechatPayRecordDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IWechatPayRecordDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IWechatPayRecordDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IWechatPayRecordDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IWechatPayRecordDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IWechatPayRecordDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IWechatPayRecordDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
