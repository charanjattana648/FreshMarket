package com.user.wongi5.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.user.wongi5.model.CartItems;
import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;

public interface CartDao {
	
	public void addCart(HttpServletRequest req,HttpServletResponse res,CartItems items);
	public void removeCart(HttpServletResponse res,int cartId);
	public List<CartItems> showCart(HttpServletRequest req);
	public Purchase_History orderItems(HttpSession session,List<CartItems> cartList);
	public List<Purchase_History> getPurchase_HistoryByEmail(String email);
	public List<Order_details> getOrder_details(int orderId);
}
