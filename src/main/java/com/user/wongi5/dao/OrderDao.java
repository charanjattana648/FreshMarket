package com.user.wongi5.dao;

import java.util.List;
import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;


public interface OrderDao {
	
	public List<Purchase_History> getPurchase_HistoryByEmail(String email);
	public List<Purchase_History> getPurchase_HistoryByStatus(String status);
	public List<Order_details> getOrder_details();
}
