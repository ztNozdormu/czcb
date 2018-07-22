/**
 * 2018/5/7 星期一 14:47:50 Cron created.
 */

package cn.com.czcb.dao.impl;

import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IDeviceDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.UserInfoDto;
import cn.com.czcb.model.Device;

/**
 *  Ibatis Dao 实现
 * Created by Cron on 2018/05/07.
 */
@Repository
public class DeviceDao extends IbatisDataProvider<Device, String> implements IDeviceDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="deviceDao" class="cn.com.czcb.dao.impl.DeviceDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public DeviceDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IDeviceDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IDeviceDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IDeviceDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IDeviceDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IDeviceDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IDeviceDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IDeviceDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IDeviceDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IDeviceDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

	@Override
	public int queryUserInfoCount(QueryParams queryParams) {
		return getSqlSession().selectOne("cn.com.czcb.dao.IDeviceDao.queryUserInfoCount",queryParams);
	}

	@Override
	public List<UserInfoDto> queryUserInfoList(QueryParams queryParams) {
		return getSqlSession().selectList("cn.com.czcb.dao.IDeviceDao.queryUserInfoList",queryParams);
	}
}
