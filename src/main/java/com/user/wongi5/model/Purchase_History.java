package com.user.wongi5.model;

import java.sql.Date;

public class Purchase_History {

	private int orderId;
	private String email;
	private double total_Price;
	private String status;
	private String order_time;
	
	
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getTotal_Price() {
		return total_Price;
	}
	public void setTotal_Price(double total_Price) {
		this.total_Price = total_Price;
	}
	@Override
	public String toString() {
		return "Purchase_History [orderId=" + orderId + ", email=" + email + ", total_Price=" + total_Price + "]";
	}
	
	
}
