package cn.com.czcb.pub.pay.bestpay;



/**
 * function:   <br/>
 * author:wangjie <br/>
 * date:2016/5/12 <br/>
 */
public class Resp<T> extends BaseRespVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2943364457152583207L;
	private boolean success = true;
    private String errorCode="";
    private String errorMsg="";
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Resp{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * ���캯��
     */
    public Resp() {
    }

    /**
     * ���캯��
     * @param response
     */
    public Resp(Response response) {
        this.success = response.isSuccess();
        this.errorCode = response.getErrorCode();
        this.errorMsg = response.getErrorMsg();
    }

    /**
     * ���캯��
     */
    public Resp(boolean success, String errorCode, String errorMsg) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
