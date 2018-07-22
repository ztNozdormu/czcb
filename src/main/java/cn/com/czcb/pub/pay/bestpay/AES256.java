package cn.com.czcb.pub.pay.bestpay;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

/**
 * function:   <br/>
 * author:wangjie <br/>
 * date:2016/5/12 <br/>
 */
public class AES256 {

    /** �����㷨 */
    static final String KEY_ALGORITHM = "AES";

    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    static final String SYS_CHARSET = "UTF-8";

    public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    /**
     * AES����
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String AES_Encode(String str, String key) throws Exception {

        byte[] textBytes = str.getBytes(SYS_CHARSET);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(SYS_CHARSET), KEY_ALGORITHM);
        Cipher cipher = null;
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return Base64.encode(cipher.doFinal(textBytes));
    }

    /**
     * AES����
     *
     * @param str
     * @param key
     * @return
     * @throws Exception
     */
    public static String AES_Decode(String str, String key)	throws Exception {

        byte[] textBytes = Base64.decode(str);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(key.getBytes(SYS_CHARSET), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), SYS_CHARSET);
    }

    //�������34λ���ֺ���ĸ
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //����length����ʾ���ɼ�λ�����
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //�����ĸ��������
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //����Ǵ�д��ĸ����Сд��ĸ
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    public static void main(String[] args) throws Exception{

        // ����Ϊ34λ
        String key = "abcdefghijklmnopqrstuvwxyz123456";

        String plainText;
        String encodeText;
        String decodeText;
        // Encrypt
//        plainText  = "imcore.net";
        plainText = "{bankCode:1,title:\\\"��ã�����\\\",addtime:\\\"2010-05-03\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\",title:\\\"��ã�����\\\"}";
        encodeText = AES256.AES_Encode(plainText, key);
        System.out.println("AES256_Encode : "+encodeText);

        encodeText = "voKWn5aAxanYvVm/v9vLr70E2JPmxw7YdoUx/HXpm27MpEd+eNuA9Hwn3aX5OjK7 My02YC1g2EgRFYUJEFZAhIEPQnSnP2JHMJnQv4HVG9pTbvZ7KwgMwrUkLalW9Q7U w1fTY+xpWj6n4+ruVdvZo9JohbZa7yjFD1Y+eliMSX9otZK9HmihcnaqBNCoVRbX ZCN2t0A2nXKQwzAm9wAj0QHPkn+gb0GJexBpINz17pURJhTLpfnTPk6rA5CeFJ+V JeDAn0zMUHIMuqWhAQykXsPKKZAUK+xrkqmovV5ln6Gx2EKYoNMA8358tNkp+hDQ STZfYCbB58qmyK3yL98rsMO9QrGzhr2dnrAWq8iu21UoGCs+xzC440I/n0p2B5QJ FpeSbmCz4pNzYPhaczVaSypndmj9Q1pv/vKGxrAbGqHDZH44fIJdA3iywrFf8rIC xuptuVV+sbp2bW8Mc4eBcg==";
        // Decrypt
        decodeText = AES256.AES_Decode(encodeText, key);
        System.out.println("AES256_Decode : "+decodeText);

        // AES256_Encode : kWyuTmUELRiREWIPpLy3ZA==
        // AES256_Decode : imcore.net

    }

}
