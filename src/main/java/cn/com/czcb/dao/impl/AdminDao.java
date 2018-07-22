/**
 * 2018/2/1 10:27:45 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IAdminDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.Admin;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/02/01.
 */
@Repository
public class AdminDao extends IbatisDataProvider<Admin, String> implements IAdminDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="adminDao" class="cn.com.czcb.dao.impl.AdminDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public AdminDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IAdminDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IAdminDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IAdminDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IAdminDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IAdminDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IAdminDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IAdminDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IAdminDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IAdminDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
