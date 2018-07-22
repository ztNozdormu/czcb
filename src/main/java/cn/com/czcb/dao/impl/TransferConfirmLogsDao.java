/**
 * 2018/3/1 17:35:06 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.ITransferConfirmLogsDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.TransferConfirmLogs;

/**
 * 圈存记录 Ibatis Dao 实现
 * Created by AJiong on 2018/03/01.
 */
@Repository
public class TransferConfirmLogsDao extends IbatisDataProvider<TransferConfirmLogs, String> implements ITransferConfirmLogsDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="transferConfirmLogsDao" class="cn.com.czcb.dao.impl.TransferConfirmLogsDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public TransferConfirmLogsDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.ITransferConfirmLogsDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
