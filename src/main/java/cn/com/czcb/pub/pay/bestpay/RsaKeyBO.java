package cn.com.czcb.pub.pay.bestpay;

import java.io.Serializable;

/**
 * function:   <br/>
 * author:wangjie <br/>
 * date:2016/5/12 <br/>
 */
public class RsaKeyBO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9135508865464047312L;

	/** ������ */
    private String keyIndex;

    /** ��Կ */
    private String pubKey;

    /** ˽Կ */
    private String privKey;

    public String getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(String keyIndex) {
        this.keyIndex = keyIndex;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPrivKey() {
        return privKey;
    }

    public void setPrivKey(String privKey) {
        this.privKey = privKey;
    }

    @Override
    public String toString() {
        return "RsaKeyBO{" +
                "keyIndex='" + keyIndex + '\'' +
                ", pubKey='" + pubKey + '\'' +
                ", privKey='" + privKey + '\'' +
                '}';
    }
}
