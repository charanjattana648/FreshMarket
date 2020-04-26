package com.user.wongi5.model;

import java.util.Arrays;

public class CartItems {

	private int itemId;
	private String itemName;
	private byte[] itemImage;
	private double itemPrice;
	private int qty;
	private double itemTotalPrice;
	
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public byte[] getItemImage() {
		return itemImage;
	}
	public void setItemImage(byte[] itemImage) {
		this.itemImage = itemImage;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	@Override
	public String toString() {
		return "itemName=" + itemName + "@itemPrice="
				+ itemPrice + "@qty=" + qty + "@itemTotalPrice=" + itemTotalPrice;
	}
	
	

}
