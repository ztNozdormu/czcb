package cn.com.czcb.service;

import java.util.List;

import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;

@SuppressWarnings("javadoc")
public interface IModelService<T> {
	public T getModelById(String id);
	public T doUpdateModel(T model);
	public T doAddModel(T model);
	public boolean doDeleteModel(String id);
	public List<T> queryList(QueryParams params);
	public List<T> queryList(QueryParams params,int start,int size);
	public int queryCount(QueryParams params);
	public PagedList<T> queryPagedList(QueryParams params,int start,int size);
}
