package com.user.wongi5.service;

import java.util.List;

import com.user.wongi5.model.CartItems;

public class CartService {
	
	public double getItemTotalPrice(List<CartItems> ci_List)
	{
		double total_price=0;
		for(CartItems ci:ci_List)
		{
			total_price+=ci.getQty()*ci.getItemPrice();
		}
		return total_price;
	}
	
	public double getTax(List<CartItems> ci_List)
	{
		double total_price=getItemTotalPrice(ci_List);
		double total_tax=0;
		final double TAX_PERCENT=0.11;
		total_tax=total_price*TAX_PERCENT;
		return total_tax;
	}
	
	public double getTotalPrice(List<CartItems> ci_List)
	{
		double total_price=0;
		double total_item_price=getItemTotalPrice(ci_List);
		double total_tax=getTax(ci_List);
		total_price+=total_item_price+total_tax;
		return total_price;
	}

}
