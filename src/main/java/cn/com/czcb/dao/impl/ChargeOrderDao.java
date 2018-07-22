/**
 * 2018/1/25 15:06:05 AJiong created.
 */

package cn.com.czcb.dao.impl;

import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.czcb.dao.IChargeOrderDao;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.dto.ChargeDto;
import cn.com.czcb.model.ChargeOrder;
import cn.com.czcb.pub.ObjectUtils;

/**
 *  Ibatis Dao 实现
 * Created by AJiong on 2018/01/25.
 */
@Repository
public class ChargeOrderDao extends IbatisDataProvider<ChargeOrder, String> implements IChargeOrderDao {

    private IbatisBaseStatement ibatisBaseStatement;

    // dao的spring配置
    /*
    <bean id="chargeOrderDao" class="cn.com.czcb.dao.impl.ChargeOrderDao">
        <constructor-arg name="sqlSession" ref="sqlSession"/>
    </bean>
    */

    /**
     * 用Ibatis会话创建Dao
     * @param sqlSession Ibatis会话
     */
    @Autowired
    public ChargeOrderDao(SqlSessionTemplate sqlSession){
        super(sqlSession);
    }

	@Override
	protected IbatisBaseStatement getIbatisBaseStatement() {
		if (ibatisBaseStatement == null) {
			ibatisBaseStatement = new IbatisBaseStatement();
			ibatisBaseStatement.setInsertStatementId("cn.com.czcb.dao.IChargeOrderDao.insertObject");
			ibatisBaseStatement.setUpdateStatementId("cn.com.czcb.dao.IChargeOrderDao.updateObject");
			ibatisBaseStatement.setDeleteStatementId("cn.com.czcb.dao.IChargeOrderDao.deleteObject");
			ibatisBaseStatement.setGetHasDetailStatementId("cn.com.czcb.dao.IChargeOrderDao.getDetailObject");
			ibatisBaseStatement.setGetNoDetailStatementId("cn.com.czcb.dao.IChargeOrderDao.getBaseObject");
			ibatisBaseStatement.setWhereDeleteStatementId("cn.com.czcb.dao.IChargeOrderDao.deleteObjectByWhere");
			ibatisBaseStatement.setQueryCountStatementId("cn.com.czcb.dao.IChargeOrderDao.queryCount");
			ibatisBaseStatement.setQueryHasDetailListStatementId("cn.com.czcb.dao.IChargeOrderDao.queryDetailList");
			ibatisBaseStatement.setQueryNoDetailListStatementId("cn.com.czcb.dao.IChargeOrderDao.queryBaseList");
		}
		return ibatisBaseStatement;
	}

    /** 
     * @see cn.com.czcb.dao.IChargeOrderDao#queryUnReadCount(cn.com.czcb.dao.QueryParams)
     */
    @Override
    public int queryUnReadCount(QueryParams params) {
        return getSqlSession().selectOne("cn.com.czcb.dao.IChargeOrderDao.queryUnReadCount", params);
    }
}
