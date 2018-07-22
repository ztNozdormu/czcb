/**
 * 2018/1/25 15:06:07 AJiong created.
 */

package cn.com.czcb.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IUserDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.User;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class UserDao extends IbatisDataProvider<User, String> implements IUserDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="userDao" class="cn.com.czcb.dao.impl.UserDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public UserDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IUserDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IUserDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IUserDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IUserDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IUserDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IUserDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IUserDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IUserDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IUserDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

}
