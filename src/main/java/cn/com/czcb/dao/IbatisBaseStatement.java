package cn.com.czcb.dao;



/**
 * Ibatis映射文件的基本语句定义类
 * @author AJiong
 * @version $Id: IbatisBaseStatement.java, v 0.1 2017年11月7日 下午3:53:40 Ajiong Exp $
 */
@SuppressWarnings("javadoc")
public class IbatisBaseStatement {

    private String insertStatementId;
    private String updateStatementId;
    private String deleteStatementId;
    private String getHasDetailStatementId;
    private String getNoDetailStatementId;
    private String whereDeleteStatementId;
    private String queryCountStatementId;
    private String queryHasDetailListStatementId;
    private String queryNoDetailListStatementId;

    /**
     * 获取插入语句ID
     *
     * @return insertStatementId 插入语句ID
     */
    public String getInsertStatementId() {
        return insertStatementId;
    }

    /**
     * 设置插入语句ID
     *
     * @param insertStatementId 要设置的插入语句ID
     */
    public void setInsertStatementId(String insertStatementId) {
        this.insertStatementId = insertStatementId;
    }

    /**
     * 获取根据主键更新的语句ID
     *
     * @return updateStatementId 根据主键更新的语句ID
     */
    public String getUpdateStatementId() {
        return updateStatementId;
    }

    /**
     * 设置根据主键更新的语句ID
     *
     * @param updateStatementId 要设置的根据主键更新的语句ID
     */
    public void setUpdateStatementId(String updateStatementId) {
        this.updateStatementId = updateStatementId;
    }

    /**
     * 获取根据主键删除的语句ID
     *
     * @return deleteStatementId 根据主键删除的语句ID
     */
    public String getDeleteStatementId() {
        return deleteStatementId;
    }

    /**
     * 设置根据主键删除的语句ID
     *
     * @param deleteStatementId 要设置的根据主键删除的语句ID
     */
    public void setDeleteStatementId(String deleteStatementId) {
        this.deleteStatementId = deleteStatementId;
    }

    /**
     * 获取根据主键查询的语句ID（返回值包含明细数据）
     *
     * @return getHasDetailStatementId 根据主键查询的语句ID（返回值包含明细数据）
     */
    public String getGetHasDetailStatementId() {
        return getHasDetailStatementId;
    }

    /**
     * 设置根据主键查询的语句ID（返回值包含明细数据）
     *
     * @param getHasDetailStatementId 要设置的根据主键查询的语句ID（返回值包含明细数据）
     */
    public void setGetHasDetailStatementId(String getHasDetailStatementId) {
        this.getHasDetailStatementId = getHasDetailStatementId;
    }

    /**
     * 获取根据主键查询的语句ID（返回值不包含明细数据）
     *
     * @return getNoDetailStatementId 根据主键查询的语句ID（返回值不包含明细数据）
     */
    public String getGetNoDetailStatementId() {
        return getNoDetailStatementId;
    }

    /**
     * 设置根据主键查询的语句ID（返回值不包含明细数据）
     *
     * @param getNoDetailStatementId 要设置的根据主键查询的语句ID（返回值不包含明细数据）
     */
    public void setGetNoDetailStatementId(String getNoDetailStatementId) {
        this.getNoDetailStatementId = getNoDetailStatementId;
    }

    /**
     * 获取根据条件删除的语句ID
     *
     * @return whereDeleteStatementId 根据条件删除的语句ID
     */
    public String getWhereDeleteStatementId() {
        return whereDeleteStatementId;
    }

    /**
     * 设置根据条件删除的语句ID
     *
     * @param whereDeleteStatementId 要设置的根据条件删除的语句ID
     */
    public void setWhereDeleteStatementId(String whereDeleteStatementId) {
        this.whereDeleteStatementId = whereDeleteStatementId;
    }

    /**
     * 获取根据条件查询数目的语句ID
     *
     * @return queryCountStatementId 根据条件查询数目的语句ID
     */
    public String getQueryCountStatementId() {
        return queryCountStatementId;
    }

    /**
     * 设置根据条件查询数目的语句ID
     *
     * @param queryCountStatementId 要设置的根据条件查询数目的语句ID
     */
    public void setQueryCountStatementId(String queryCountStatementId) {
        this.queryCountStatementId = queryCountStatementId;
    }

    /**
     * 获取根据条件查询列表的语句ID（返回值包含明细数据）
     *
     * @return queryHasDetailListStatementId 根据条件查询列表的语句ID（返回值包含明细数据）
     */
    public String getQueryHasDetailListStatementId() {
        return queryHasDetailListStatementId;
    }

    /**
     * 设置根据条件查询列表的语句ID（返回值包含明细数据）
     *
     * @param queryHasDetailListStatementId 要设置的根据条件查询列表的语句ID（返回值包含明细数据）
     */
    public void setQueryHasDetailListStatementId(String queryHasDetailListStatementId) {
        this.queryHasDetailListStatementId = queryHasDetailListStatementId;
    }

    /**
     * 获取根据条件查询列表的语句ID（返回值不包含明细数据）
     *
     * @return queryNoDetailListStatementId 根据条件查询列表的语句ID（返回值不包含明细数据）
     */
    public String getQueryNoDetailListStatementId() {
        return queryNoDetailListStatementId;
    }

    /**
     * 设置根据条件查询列表的语句ID（返回值不包含明细数据）
     *
     * @param queryNoDetailListStatementId 要设置的根据条件查询列表的语句ID（返回值不包含明细数据）
     */
    public void setQueryNoDetailListStatementId(String queryNoDetailListStatementId) {
        this.queryNoDetailListStatementId = queryNoDetailListStatementId;
    }

}
