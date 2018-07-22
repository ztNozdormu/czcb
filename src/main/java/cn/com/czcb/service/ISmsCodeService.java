/**
 * 2018/1/25 17:05:18 AJiong created.
 */

package cn.com.czcb.service;

import cn.com.czcb.model.SmsCode;

/**
 * 短信验证码 Service 接口
 * Created by AJiong on 2018/01/25.
 */
public interface ISmsCodeService extends IModelService<SmsCode> {
    /**
     * 发送验证码
     * @param phone
     * @param code
     */
    void sendSms(String phone, String code);

    /**
     * 
     * @param phone
     * @param code
     * @return boolean
     */
    Boolean validateSmsCode(String phone, String code);
}
