package cn.com.czcb.pub;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class Md5Utils {
	
	/**
	 * md5加密
	 * @param str
	 * @return
	 */
	public static String md5(String str) { 
        try {  
            // 得到一个信息摘要器  
            MessageDigest digest = MessageDigest.getInstance("md5");  
            byte[] result = digest.digest(str.getBytes());  
            StringBuffer buffer = new StringBuffer();  
            // 把没一个byte 做一个与运算 0xff;  
            for (byte b : result) {  
                // 与运算  
                int number = b & 0xff;// 加盐  
                String str1 = Integer.toHexString(number);  
                if (str1.length() == 1) {  
                    buffer.append("0");  
                }  
                buffer.append(str1);  
            }  
            // 标准的md5加密后的结果  
            return buffer.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return "";  
        }  
  
    }
	/**
	 * 卡莱博尔提供的MD5加密算法
	 * @param inStr
	 * @return
	 */
	public static String encrypt(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }
	@Test
	public void test() {
		//String appId = "";
		String token = "G/B3NNkE39EvQ1FpRzOl/EpoVmGUYL9ZimdvDTkOPUgvAZiuDPpLMyNrInAeng8I";
		String randomStr = "2017-09-26";
		String salt = "bsgxUhealth";
		System.out.println(md5(token + randomStr + salt));
	}
}
