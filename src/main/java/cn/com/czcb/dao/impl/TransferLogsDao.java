/**
 * 2018/3/1 17:35:07 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.ITransferLogsDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.TransferLogs;

/**
 * 圈存记录 Ibatis Dao 实现
 * Created by AJiong on 2018/03/01.
 */
@Repository
public class TransferLogsDao extends IbatisDataProvider<TransferLogs, String> implements ITransferLogsDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="transferLogsDao" class="cn.com.czcb.dao.impl.TransferLogsDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public TransferLogsDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.ITransferLogsDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.ITransferLogsDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.ITransferLogsDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.ITransferLogsDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.ITransferLogsDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.ITransferLogsDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.ITransferLogsDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.ITransferLogsDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.ITransferLogsDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
