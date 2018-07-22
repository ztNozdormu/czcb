package cn.com.czcb.pub;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncpUtils {
	
	public static final String USER_PWD_ENCP_PREFIX = "OPIJHBNIYTFGAWSDFGYBHUJK";
	
	/**
	 * 用户密码加密
	 * @param beforeEncp
	 * @return
	 */
	public static String encpUserPwd(String beforeEncp){
		if(ObjectUtils.isEmpty(beforeEncp)){
			return beforeEncp;
		}
		return encryptMD5(USER_PWD_ENCP_PREFIX+beforeEncp);
	}
	
	/**
	 * md5加密
	 * @param strInput
	 * @return
	 */
	public static String encryptMD5(String strInput) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strInput.getBytes());
			byte b[] = md.digest();
			buf = new StringBuffer(b.length * 2);
			for (int i = 0; i < b.length; i++) {
				if (((int) b[i] & 0xff) < 0x10) { // & 0xff转换无符号整型
					buf.append("0");
				}
				buf.append(Long.toHexString((int) b[i] & 0xff));
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return buf==null?null:buf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(encryptMD5(USER_PWD_ENCP_PREFIX+"123456"));
	}
	
}
