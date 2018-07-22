package cn.com.czcb.pub;

public class AppException extends RuntimeException{
	private static final long serialVersionUID = -2706359768834538589L;
	
	public static final int NEED_LOGIN = -1;
	
	private int exceptionCode;
	private String exceptionMsg;
	
	protected AppException() {
	}
	
	public AppException(int code){
		this.exceptionCode = code;
	}

	public AppException(String msg){
		super(msg);
		this.exceptionCode = 500;
		this.exceptionMsg = msg;
	}

	public AppException(String msg,int code){
		super(msg);
		this.exceptionCode = code;
		this.exceptionMsg = msg;
	}
	
	public int getExceptionCode() {
		return exceptionCode;
	}
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	

}
