/**
 * 2018/1/25 15:06:08 AJiong created.
 */

package cn.com.czcb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.IUserDao;
import cn.com.czcb.model.User;
import cn.com.czcb.service.IUserService;
/**
 *  Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class UserService extends ModelService<User> implements IUserService {

    @Autowired
	private IUserDao userDao;

	@Override
	public IIbatisDataProvider<User, String> getModelDao() {
		return this.userDao;
	}

}
