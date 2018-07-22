package cn.com.czcb.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author AJiong
 * @version $Id: PagedList.java, v 0.1 2017年11月7日 下午3:54:01 Ajiong Exp $
 */
@SuppressWarnings("javadoc")
public class PagedList<T> implements Serializable{
	private static final long serialVersionUID = -4169096025868641406L;
    private List<T> list = new ArrayList<T>();
    private int pageIndex;
    private int pageCount;
    private int pageSize;
    private int totalSize;

    /**
     * 获取当前页列表
     *
     * @return 对象列表
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 设置当前页列表
     *
     * @param list 要设置的当前页列表
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置页码
     *
     * @param pageIndex 要设置的页码
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取页数
     *
     * @return 页页数
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 设置页大小
     *
     * @param pageCount 要设置的页大小
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 获取页大小
     *
     * @return 页大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置页大小
     *
     * @param pageSize 要设置的页大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * 设置总记录数
     *
     * @param totalSize 要设置的总记录数
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

}
