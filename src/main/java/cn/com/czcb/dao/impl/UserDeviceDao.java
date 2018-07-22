/**
 * 2018/5/7 星期一 14:47:51 Cron created.
 */

package cn.com.czcb.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IUserDeviceDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.model.UserDevice;
import cn.com.czcb.model.vo.DeviceVo;

/**
 *  Ibatis Dao 实现
 * Created by Cron on 2018/05/07.
 */
@Repository
public class UserDeviceDao extends IbatisDataProvider<UserDevice, String> implements IUserDeviceDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="userDeviceDao" class="cn.com.czcb.dao.impl.UserDeviceDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public UserDeviceDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IUserDeviceDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IUserDeviceDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IUserDeviceDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IUserDeviceDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IUserDeviceDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IUserDeviceDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IUserDeviceDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IUserDeviceDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IUserDeviceDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

	@Override
	public List<DeviceVo> getMyDeviceList(String userId) {
		return getSqlSession().selectList("cn.com.czcb.dao.IUserDeviceDao.getMyDeviceList", userId);
	}

}
