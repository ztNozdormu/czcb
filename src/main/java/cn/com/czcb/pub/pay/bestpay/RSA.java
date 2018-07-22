package cn.com.czcb.pub.pay.bestpay;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * function:   <br/>
 * author:wangjie <br/>
 * date:2016/5/12 <br/>
 */
public class RSA {

    /** RSA:�����㷨 */
    public static final String  KEY_ALGORITHM =             "RSA";

    public static final String  KEY_ALGORITHM_RSA_NONE =             "RSA/ECB/PKCS1Padding";

    /** SHA1WithRSA:��SHA�㷨����ǩ������RSA�㷨���м��� */
    public static final String  SIGN_ALGORITHMS =           "SHAWithRSA";

    /** ��ԿKEY*/
    public static final String  PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCx2zk90WzGIzGjl7opxIFdoinxyp+pjvN1wC0OTrGk6o/c0RyrmQstu690IJPXu/6urLmB7/T2Iy/UUvSkqwzL7oX6D7llTjyR4MQjwvPVy7JZR2WYu1dvPgQn++/DVBuFDtfYW6pRlIi27iPxXyQ3ozAfHo5biR5nNelhu0lnVQIDAQAB";

    /** ����ܵĴ�С���ǲ�Ҫ̫�󣬷���Ч�ʻ�� */
    private static final int    KEY_SIZE =                  1024;

    /** ��ȡ��Կ��key */
    private static final String PUBLIC_KEY =                "RSAPublicKey";

    /** ��ȡ˽Կ��key */
    private static final String PRIVATE_KEY =               "RSAPrivateKey";

    /** RSA���������Ĵ�С */
    private static final int    MAX_ENCRYPT_BLOCK =         117;

    /** RSA���������Ĵ�С */
    private static final int    MAX_DECRYPT_BLOCK =         128;


    /**
     * * ������Կ�� *
     *
     * @return KeyPair *
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    /**
     * * ���ɹ�Կ *
     *
     * @param keyPair *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static String generateRSAPublicKey(KeyPair keyPair) throws Exception {

        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        return Base64.encode(publicKey.getEncoded());
    }

    /**
     * * ����˽Կ *
     *
     * @param keyPair *
     * @return RSAPublicKey *
     * @throws Exception
     */
    public static String generateRSAPrivateKey(KeyPair keyPair) throws Exception {

        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        return Base64.encode(privateKey.getEncoded());
    }

    /**
     * * ���ɹ�˽Կ�� *
     *
     * @return Map *
     * @throws Exception
     */
    public static Map<String, Object> generateRSAKey() throws Exception {
        KeyPair keyPair = generateKeyPair();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, generateRSAPublicKey(keyPair));
        keyMap.put(PRIVATE_KEY, generateRSAPrivateKey(keyPair));
        return keyMap;
    }

    /**
     * ��˽Կ����Ϣ��������ǩ��
     *
     * @param content                ��ǩ������
     * @param privateKey             ˽Կ(BASE64����)
     * @param input_charset          �����ʽ
     * @return                       ǩ��ֵ
     */
    public static String sign(String content, String privateKey, String input_charset) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) );
        KeyFactory keyf 				= KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(input_charset));
        byte[] signed = signature.sign();
        return Base64.encode(signed);
    }

    /**
     * �ù�Կ����ϢRSA��ǩ�����
     *
     * @param content                ��ǩ������
     * @param sign                   ǩ��ֵ
     * @param yzf_public_key         ��Կ
     * @param input_charset          �����ʽ
     * @return                       ����ֵ
     */
    public static boolean verify(String content, String sign, String yzf_public_key, String input_charset) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        byte[] encodedKey = Base64.decode(yzf_public_key);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

        signature.initVerify(pubKey);
        signature.update( content.getBytes(input_charset) );

        return signature.verify( Base64.decode(sign) );
    }

    /**
     * ʹ�ù�ԿRSA����
     * @param content               ����
     * @param yzf_public_key        ��Կ
     * @param input_charset         �����ʽ
     * @return                      ���ܺ���ַ���
     */
    public static String encrypt(String content, String yzf_public_key, String input_charset) throws Exception {

        String str = null;
        ByteArrayOutputStream writer = null;
        try {
            PublicKey pubKey = getPublicKey(yzf_public_key);

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA_NONE);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            // ���ñ����ʽ
            InputStream ins = new ByteArrayInputStream(content.getBytes(input_charset));
            writer = new ByteArrayOutputStream();

            byte[] buf = new byte[MAX_DECRYPT_BLOCK];
            int bufl;

            while ((bufl = ins.read(buf)) != -1) {
                byte[] block = null;

                if (buf.length == bufl) {
                    block = buf;
                } else {
                    block = new byte[bufl];
                    for (int i = 0; i < bufl; i++) {
                        block[i] = buf[i];
                    }
                }
                writer.write(cipher.doFinal(block));
            }
            str = new String(Base64.encode(writer.toByteArray()));
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writer = null;
            }
        }
        return str;
    }

    /**
     * ʹ��˽ԿRSA����
     * @param content ����
     * @param private_key �̻�˽Կ
     * @param input_charset �����ʽ
     * @return ���ܺ���ַ���
     */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa���ܵ��ֽڴ�С�����128������Ҫ���ܵ����ݣ���128λ�𿪽���
        byte[] buf = new byte[MAX_DECRYPT_BLOCK];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    /**
     * �õ�˽Կ
     *
     * @param key ��Կ�ַ���������base64���룩
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes = Base64.decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    /**
     * �õ���Կ
     *
     * @param key �����ַ���������base64���룩
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = Base64.decode(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        return publicKey;
    }

    public static void main(String args[]) {
        try {
//            RSAKeyInfo rsaKeyInfo = RSA.generateRSAKey();
//            String pubKey = rsaKeyInfo.getPubKey();         // ��Կ
//            String privKey = rsaKeyInfo.getPrivKey();       // ˽Կ
//
//            System.out.print(pubKey + "\n" + privKey);
//
//            String content = "�ҽ�������";
//
//            String encryStr = RSA.encrypt(content, pubKey, SysConstants.SYS_CHARSET);
//
//            System.out.println("���ܽ����" + encryStr);
//
//            String decryptStr = RSA.decrypt(encryStr, privKey, SysConstants.SYS_CHARSET);
//
//            System.out.println("���ܽ����" + decryptStr);

            String encryStr = "VxhK8Ku73TltoujAl0mE9J8O2tgsFvDQc3CuSJWv/L9MtLBom3XgPd8KngJMlaV7wKafK5JsR9g0zSYCjKOpi26FaBEGPI8yfXvR7s+MDZ0dEvdcuvqYcSccS7jwLm1H5ZFZT1f/Vytxw4/cjGafcZm+BAV6BzeJz6sskMRT2S4=";

            String privKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALHbOT3RbMYjMaOXuinEgV2iKfHKn6mO83XALQ5OsaTqj9zRHKuZCy27r3Qgk9e7/q6suYHv9PYjL9RS9KSrDMvuhfoPuWVOPJHgxCPC89XLsllHZZi7V28+BCf778NUG4UO19hbqlGUiLbuI/FfJDejMB8ejluJHmc16WG7SWdVAgMBAAECgYEAmfYICzdrTenRYqhJgzaUNhXW8XRR2lng7yG43xXIOdbDSofKpdEKGEDMlV5OHQakZVkoDQ9Honq7QLW/CXz4yW71iAK8SFn22LGKxzdSgPZkGmVZ+ZKDPjmKg9QyzI8VbHWKuIHCD0iJgaN7SF5B1CtsGRs66f+aNTLyLLr2QAECQQDWtXfjJi0keQ13mLpAYfc7e7dX0zna5zqkUbA2q1JYPG8j9GIJ/j3NrUUJbhFiBEJUX5BYtP7zXOXA8/QNqvtVAkEA1A9vRllNQNEiAG3ZlvVSh3RXWPsVDQkerzWKBlMvQZIa+eQ/Q9o+b0tOvvt1IIvwaSXpqBEcpC09vzfjoNy8AQJBAMNUmFr4Uj1KO6xAL8F+3pMo/CVULuAtWLZA8tTpi6JmaJ4HKGH7AHLrXVE052+KfGWSAxoQn5j7PLILvk3o7XkCQDcG4ksQ9Tjyi64s0x+W/RllGR1f2fCOA0ZX0D8f6s1LCnD5x2jmAvmCQybPvW76oSHH0r/n4NTBYJpz+D9PyAECQG+WZ/QoXpyHiRr1JUdjq2BX7+EA8hsC6+pmBzOnRzbOxWxmlfMNC5gzhhOyAVMdQM8UaRgKBOSsqsWz6artj1Y=";
            String decryptStr = RSA.decrypt(encryStr, privKey, "UTF-8");
//
            System.out.println("���ܽ����" + decryptStr);

//            String public_exponent = "010001";
//            String modulus = "009e1b8381423dbbf9bbfa2d963755f6752928f8793a1d966170a3ffb9bad061d096ec9c2d7129f3639c17af17a2d8ee30de94afec1da9533a4e4d00331c31f67a967b9c3d98545e210615a7ff1c5a02dbdefbfffac0c7507240160f076d21ec78b4e6f2b47918d8a47ca195b223343626dd638f0cc8ebf0d21966e51ead24652f";
//
//            RSAPublicKey rsaPublicKey = RSA.getPublicKey(modulus, public_exponent);


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

