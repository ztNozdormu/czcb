package cn.com.czcb.model.tft;

import java.io.Serializable;

/**
 * 接口地址配置
 * @author Administrator
 */
public class AddressMapping implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer Id;
	/**
	 * 测试接口地址
	 */
	private String testUrl;
	/**
	 * 正式接口地址
	 */
	private String url;
	/**
	 * 请求key
	 */
	private String urlKey;
	/**
	 * 备注信息
	 */
	private String remark;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public String getTestUrl() {
		return testUrl;
	}
	public void setTestUrl(String testUrl) {
		this.testUrl = testUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlKey() {
		return urlKey;
	}
	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
