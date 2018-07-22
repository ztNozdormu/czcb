/**
 * 2018/2/1 10:27:46 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IAdminDao;
import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.model.Admin;
import cn.com.czcb.service.IAdminService;
/**
 *  Service 实现
 * Created by AJiong on 2018/02/01.
 */
@Service
public class AdminService extends ModelService<Admin> implements IAdminService {

    @Autowired
	private IAdminDao adminDao;

	@Override
	public IIbatisDataProvider<Admin, String> getModelDao() {
		return this.adminDao;
	}

}
