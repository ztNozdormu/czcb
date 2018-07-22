/**
 * 2018/1/25 17:05:18 AJiong created.
 */

package cn.com.czcb.dao;

import cn.com.czcb.model.SmsCode;

/**
 * 短信验证码 Ibatis Dao 接口
 * Created by AJiong on 2018/01/25.
 */
public interface ISmsCodeDao extends IIbatisDataProvider<SmsCode, String> {
    void updateSmsCodeList(QueryParams wheres);
}
