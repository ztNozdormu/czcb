/**
 * 2018/1/25 17:05:19 AJiong created.
 */

package cn.com.czcb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.czcb.dao.IIbatisDataProvider;
import cn.com.czcb.dao.ISmsCodeDao;
import cn.com.czcb.dao.QueryParams;
import cn.com.czcb.model.SmsCode;
import cn.com.czcb.pub.SmsUtil;
import cn.com.czcb.service.ISmsCodeService;
/**
 * 短信验证码 Service 实现
 * Created by AJiong on 2018/01/25.
 */
@Service
public class SmsCodeService extends ModelService<SmsCode> implements ISmsCodeService {

    @Autowired
	private ISmsCodeDao smsCodeDao;

	@Override
	public IIbatisDataProvider<SmsCode, String> getModelDao() {
		return this.smsCodeDao;
	}
	/** 
     * @see cn.com.czcb.service.ISmsCodeService#sendSms(java.lang.String, java.lang.String)
     */
    @Override
    public void sendSms(String phone, String code) {
        String realContent = SmsUtil.contentTemplate.replaceFirst("\\$\\{" + "key1" + "\\}", code);
        SmsUtil.sendSms(realContent, phone);
        //查询之前发送的验证码，并改变其状态
        QueryParams params = new QueryParams();
        params.addParameter("phone", phone);
        List<SmsCode> smsCodeList = smsCodeDao.queryList(params, 0, 1000, true);
        List<String> smsCodeIds = new ArrayList<>();
        if (smsCodeList != null && smsCodeList.size() > 0) {
            for (int i = 0; i < smsCodeList.size(); i++) {
                smsCodeIds.add(smsCodeList.get(i).getId()); // 将list中的记录复制到数组中，用于批量更新
            }
            QueryParams updateParams = new QueryParams();
            updateParams.put("smsCodeIds", smsCodeIds);
            smsCodeDao.updateSmsCodeList(updateParams); // 执行更新操作
        }
        SmsCode smsCode = new SmsCode();
        // 4.插入新的短信记录
        smsCode.setId(UUID.randomUUID().toString());
        smsCode.setPhone(phone);
        smsCode.setValidatCode(code);
        smsCode.setValidated(false);
        smsCode.setCreateTime(System.currentTimeMillis());
        smsCodeDao.insertObject(smsCode);
    }

    /** 
     * @see cn.com.czcb.service.ISmsCodeService#validateSmsCode(java.lang.String, java.lang.String)
     */
    @Override
    public Boolean validateSmsCode(String phone, String code) {
        QueryParams params = new QueryParams();
        params.addParameter("phone", phone);
        params.addParameter("validatCode", code);
        params.addParameter("validated", false);
        List<SmsCode> smsCodeList = smsCodeDao.queryList(params, 0, 1000, true);
        //未找到验证码或者验证码已过期
        if (smsCodeList == null || smsCodeList.size() == 0 || System.currentTimeMillis() > (smsCodeList.get(0).getCreateTime() + 600000)) {
            return false;
        }
        SmsCode smsCode = smsCodeList.get(0);
        smsCode.setValidated(true);
        smsCodeDao.updateObject(smsCode);
        return true;
    }
}
