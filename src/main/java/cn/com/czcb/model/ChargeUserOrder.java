package cn.com.czcb.model;

public class ChargeUserOrder {
	
	private ChargeOrder chargeOrder;
	
	private User user;
	
	private Integer pageSize;

	public ChargeOrder getChargeOrder() {
		return chargeOrder;
	}

	public void setChargeOrder(ChargeOrder chargeOrder) {
		this.chargeOrder = chargeOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
