package cn.com.czcb.service.impl;

import java.util.List;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.PagedList;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.pub.ObjectUtils;
import cn.com.czcb.service.IModelService;

@SuppressWarnings("javadoc")
public  abstract class ModelService<T> implements IModelService<T>{

	@Override
	public T getModelById(String id) {
		return getModelDao().getObject(id, false);
	}

	@Override
	public T doUpdateModel(T model) {
		getModelDao().updateObject(model);
		return model;
	}

	@Override
	public T doAddModel(T model) {
		getModelDao().insertObject(model);
		return model;
	}

	@Override
	public boolean doDeleteModel(String id) {
		int res = getModelDao().deleteObject(id);
		return res>0;
	}

	@Override
	public List<T> queryList(QueryParams params) {
		final int defaultSkip = 0;
		int skip = defaultSkip;
		final int defaultSize = -1;
		int size = defaultSize;
		final Object startObj = params.get("__start");
		final Object sizeObj = params.get("__size");
		if(startObj!=null){
			skip = ObjectUtils.parseStringToInt(startObj.toString());
			if(skip<defaultSkip){
				skip = defaultSkip;
			}
		}
		if(sizeObj!=null){
			size = ObjectUtils.parseStringToInt(sizeObj.toString());
			if(size<defaultSize){
				size = defaultSize;
			}
		}
		return getModelDao().queryList(params, skip, size, false);
	}

	@Override
	public List<T> queryList(QueryParams params, int start, int size) {
		return getModelDao().queryList(params, start, size, false);
	}

	@Override
	public int queryCount(QueryParams params) {
		return getModelDao().queryCount(params);
	}

	@Override
	public PagedList<T> queryPagedList(QueryParams params, int start, int size) {
		return getModelDao().queryListForPaged(params, start, size, false);
	}
	
	public abstract IIbatisDataProvider<T,String> getModelDao();
	
}
