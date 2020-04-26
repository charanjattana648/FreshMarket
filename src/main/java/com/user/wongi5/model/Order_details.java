package com.user.wongi5.model;

public class Order_details {
	private int id;
	private int itemId;
	private String itemName;
	private int orderId;
	private int itemQty;
	private double itemPrice;
	private double item_total_Price;
	
	
	public int getId() {
		return id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public double getItem_total_Price() {
		return item_total_Price;
	}
	public void setItem_total_Price(double item_total_Price) {
		this.item_total_Price = item_total_Price;
	}
	@Override
	public String toString() {
		return "Order_details [itemId=" + itemId + ", orderId=" + orderId + ", itemQty=" + itemQty + ", itemPrice="
				+ itemPrice + ", item_total_Price=" + item_total_Price + "]";
	}
	
	
	
	
	
	
	
}
