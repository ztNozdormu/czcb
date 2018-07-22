package cn.com.czcb.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;


/**
 * Ibatis dao 数据访问接口，所有Ibatis dao都必须实现此接口
 * @author AJiong
 * @version $Id: IIbatisDataProvider.java, v 0.1 2017年11月7日 下午3:51:56 Ajiong Exp $
 */
@SuppressWarnings("javadoc")
public interface IIbatisDataProvider<T, PK> extends IDataProvider<T, PK> {
    /**
     * 插入对象
     *
     * @param statement 语句ID
     * @param object    对象
     * @return 影响的行数
     */
    int insertObject(String statement, T object);

    /**
     * 根据主键更新对象
     *
     * @param statement 语句ID
     * @param object    对象
     * @return 影响的行数
     */
    int updateObject(String statement, T object);

    /**
     * 删除对象
     *
     * @param statement 语句ID
     * @param key       主键
     * @return 影响的行数
     */
    int deleteObject(String statement, PK key);

    /**
     * 获取对象
     *
     * @param statement 语句ID
     * @param key       主键
     * @return 对象
     */
    T getObject(String statement, PK key);

    /**
     * 插入一组对象
     *
     * @param statement 语句ID
     * @param objects   对象数组
     * @return 影响的行数
     */
    int insertList(String statement, T[] objects);

    /**
     * 更新一组对象
     *
     * @param statement 语句ID
     * @param objects   对象数组
     * @return 影响的行数
     */
    int updateList(String statement, T[] objects);

    /**
     * 删除一组对象
     *
     * @param statement 语句ID
     * @param keies     主键数组
     * @return 影响的行数
     */
    int deleteList(String statement, PK[] keies);

    /**
     * 获取一组对象
     *
     * @param statement 语句ID
     * @param keies     主键数组
     * @return 对象数组
     */
    T[] getObjectList(String statement, PK[] keies);

    /**
     * 根据条件删除对象
     *
     * @param statement 语句ID
     * @param wheres    条件
     * @return 影响的行数
     */
    int deleteObjectByWhere(String statement, QueryParams wheres);

    /**
     * 查询对象个数
     *
     * @param statement 语句ID
     * @param wheres    条件
     * @return 对象个数
     */
    int queryCount(String statement, QueryParams wheres);

    /**
     * 查询对象列表
     *
     * @param statement 语句ID
     * @param wheres    条件
     * @param skip      跳过的个数
     * @param size      返回的最大数目,小于0则返回所有记录
     * @return 对象列表
     */
    List<T> queryList(String statement, QueryParams wheres, int skip, int size);

    /**
     * 分布查询对象
     *
     * @param listStatement  列表语句ID
     * @param countStatement 数目语句ID
     * @param wheres         条件
     * @param pageIndex      当前页码
     * @param pageSize       页大小
     * @return 分页列表
     */
    PagedList<T> queryListForPaged(String listStatement, String countStatement, QueryParams wheres, int pageIndex,
                                   int pageSize);

    /**
     * 获取 Sql会话
     *
     * @return Sql会话
     */
    SqlSession getSqlSession();

}

