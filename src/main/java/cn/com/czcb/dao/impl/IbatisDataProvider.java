package cn.com.czcb.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IbatisBaseStatement;
import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.pub.ObjectUtils;


/**
 * Ibatis Dao 基类，所有Ibatis Dao 应继承此类
 * @author AJiong
 * @version $Id: IbatisDataProvider.java, v 0.1 2017年11月7日 下午3:54:15 Ajiong Exp $
 */
@SuppressWarnings("javadoc")
public abstract class IbatisDataProvider<T, PK> implements IIbatisDataProvider<T, PK> {

    private SqlSessionTemplate sqlSession;

    /**
     * 设置Ibatis会话
     *
     * @param sqlSession Ibatis会话
     */
    public IbatisDataProvider(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 获取此Ibatis Dao的基本语句ID
     *
     * @return 此Ibatis Dao的基本语句ID
     */
    protected abstract IbatisBaseStatement getIbatisBaseStatement();

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#insertObject(java.lang.Object)
     */
    @Override
    public int insertObject(T object) {
    	String insertStatementId = getIbatisBaseStatement().getInsertStatementId();
        return insertObject(insertStatementId, object);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#insertObject(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public int insertObject(String statement, T object) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        return sqlSession.insert(statement, object);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#updateObject(java.lang.Object)
     */
    @Override
    public int updateObject(T object) {
        return updateObject(getIbatisBaseStatement().getUpdateStatementId(), object);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#updateObject(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public int updateObject(String statement, T object) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        return sqlSession.update(statement, object);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#deleteObject(java.lang.Object)
     */
    @Override
    public int deleteObject(PK key) {
        return deleteObject(getIbatisBaseStatement().getDeleteStatementId(), key);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#deleteObject(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public int deleteObject(String statement, PK key) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        return sqlSession.delete(statement, key);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#getObject(java.lang.Object,
     * boolean)
     */
    @Override
    public T getObject(PK key, boolean detail) {
        return getObject(detail ? getIbatisBaseStatement().getGetHasDetailStatementId() : getIbatisBaseStatement()
                .getGetNoDetailStatementId(), key);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#getObject(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public T getObject(String statement, PK key) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        return sqlSession.selectOne(statement, key);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#insertList(java.lang.Object[])
     */
    @Override
    public int insertList(T[] objects) {
        int result = 0;
        for (T object : objects) {
            result += insertObject(object);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#insertList(java.lang.String,
     * java.lang.Object[])
     */
    @Override
    public int insertList(String statement, T[] objects) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        int result = 0;
        for (T object : objects) {
            result += insertObject(statement, object);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#updateList(java.lang.Object[])
     */
    @Override
    public int updateList(T[] objects) {
        int result = 0;
        for (T object : objects) {
            result += updateObject(object);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#updateList(java.lang.String,
     * java.lang.Object[])
     */
    @Override
    public int updateList(String statement, T[] objects) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        int result = 0;
        for (T object : objects) {
            result += updateObject(statement, object);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#deleteList(java.lang.Object[])
     */
    @Override
    public int deleteList(PK[] keies) {
        int result = 0;
        for (PK key : keies) {
            result += deleteObject(key);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#deleteList(java.lang.String,
     * java.lang.Object[])
     */
    @Override
    public int deleteList(String statement, PK[] keies) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        int result = 0;
        for (PK key : keies) {
            result += deleteObject(statement, key);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#getObjectList(java.lang.Object[],
     * boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] getObjectList(PK[] keies, boolean detail) {
        T[] result = (T[]) (new Object[keies.length]);
        for (int i = 0; i < keies.length; i++) {
            result[i] = getObject(keies[i], detail);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#getObjectList(java.lang.String,
     * java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public T[] getObjectList(String statement, PK[] keies) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        T[] result = (T[]) (new Object[keies.length]);
        for (int i = 0; i < keies.length; i++) {
            result[i] = getObject(statement, keies[i]);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see
     * com.funi.core.framework.daos.IDataProvider#deleteObjectByWhere(com.funi.core.QueryParams)
     */
    @Override
    public int deleteObjectByWhere(QueryParams wheres) {
        return deleteObjectByWhere(getIbatisBaseStatement().getWhereDeleteStatementId(), wheres);
    }

    /*
     * （非 Javadoc）
     *
     * @see
     * com.funi.core.framework.daos.IDataProvider#deleteObjectByWhere(java.lang.String,
     * com.funi.core.QueryParams)
     */
    @Override
    public int deleteObjectByWhere(String statement, QueryParams wheres) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        if (wheres == null) {
            wheres = new QueryParams();
        }
        return sqlSession.delete(statement, wheres);
    }

    /*
     * （非 Javadoc）
     *
     * @see
     * com.funi.core.framework.daos.IDataProvider#queryCount(com.funi.core.QueryParams
     * com.jksmart.activity.dao.IWorksDao.queryCount
     * )
     */
    @Override
    public int queryCount(QueryParams wheres) {
    	String queryCountStatementId = getIbatisBaseStatement().getQueryCountStatementId();
        return queryCount(queryCountStatementId, wheres);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#queryCount(java.lang.String,
     * com.funi.core.QueryParams)
     */
    @Override
    public int queryCount(String statement, QueryParams wheres) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        if (wheres == null) {
            wheres = new QueryParams();
        }
        return sqlSession.selectOne(statement, wheres);
    }

    /*
     * （非 Javadoc）
     *
     * @see
     * com.funi.core.framework.daos.IDataProvider#queryList(com.funi.core.QueryParams
     * , int, int, boolean)
     */
    @Override
    public List<T> queryList(QueryParams wheres, int skip, int size, boolean detail) {
        return queryList(detail ? getIbatisBaseStatement().getQueryHasDetailListStatementId()
                : getIbatisBaseStatement().getQueryNoDetailListStatementId(), wheres, skip, size);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#queryList(java.lang.String,
     * com.funi.core.QueryParams, int, int)
     */
    @Override
    public List<T> queryList(String statement, QueryParams wheres, int skip, int size) {
        if (ObjectUtils.isEmpty(statement)) {
            throw new IllegalArgumentException("statement参数不能为null或empty.");
        }
        if (skip < 0) {
            throw new IllegalArgumentException("skip参数不能小于0.");
        }
        if (size < 0) {
            size = RowBounds.NO_ROW_LIMIT;
        }
        if (wheres == null) {
            wheres = new QueryParams();
        }
        wheres.setStart(skip);
        wheres.setSize(size);
        return sqlSession.selectList(statement, wheres);
    }

    /*
     * （非 Javadoc）
     *
     * @see
     * com.funi.core.framework.daos.IDataProvider#queryList(com.funi.core.QueryParams
     * , int, int, boolean)
     */
    @Override
    public PagedList<T> queryListForPaged(QueryParams wheres, int pageIndex, int pageSize, boolean detail) {
        return queryListForPaged(detail ? getIbatisBaseStatement().getQueryHasDetailListStatementId()
                : getIbatisBaseStatement().getQueryNoDetailListStatementId(), getIbatisBaseStatement()
                .getQueryCountStatementId(), wheres, pageIndex, pageSize);
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#queryList(java.lang.String,
     * java.lang.String, com.funi.core.QueryParams, int, int)
     */
    @Override
    public PagedList<T> queryListForPaged(String listStatement, String countStatement, QueryParams wheres,
                                          int pageIndex, int pageSize) {
        if (ObjectUtils.isEmpty(listStatement)) {
            throw new IllegalArgumentException("listStatement参数不能为null或empty.");
        }
        if (ObjectUtils.isEmpty(countStatement)) {
            throw new IllegalArgumentException("countStatement参数不能为null或empty.");
        }
        if (pageIndex < 0) {
            throw new IllegalArgumentException("pageIndex参数不能小于0.");
        }
        if (pageSize < 0) {
            throw new IllegalArgumentException("pageSize参数不能小于0.");
        }
        List<T> list = queryList(listStatement, wheres, pageIndex * pageSize, pageSize);
        PagedList<T> result = new PagedList<>();
        result.getList().addAll(list);
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        int total = queryCount(countStatement, wheres);
        result.setTotalSize(total);
        if (total % pageSize == 0) {
            result.setPageCount(total / pageSize);
        } else {
            result.setPageCount(total / pageSize + 1);
        }
        return result;
    }

    /*
     * （非 Javadoc）
     *
     * @see com.funi.core.framework.daos.IDataProvider#getSqlSession()
     */
    @Override
    public SqlSession getSqlSession() {
        return sqlSession;
    }

}
