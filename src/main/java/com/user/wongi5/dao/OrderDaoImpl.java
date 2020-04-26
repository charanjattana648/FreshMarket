package com.user.wongi5.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;

@Component
public class OrderDaoImpl implements OrderDao {

NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	private SqlParameterSource getSqlParameterByPurchase_History(Purchase_History ph)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(ph!=null)
		{
			parameterSource.addValue("email", ph.getEmail());
			parameterSource.addValue("status", ph.getStatus());
			parameterSource.addValue("order_time", ph.getOrder_time());			
			parameterSource.addValue("totalPrice", ph.getTotal_Price());
		}
		return parameterSource;
	}
	
	private SqlParameterSource getSqlParameterByOrder_details(Order_details od)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(od!=null)
		{
			parameterSource.addValue("id", od.getId());
			parameterSource.addValue("itemId", od.getItemId());
			parameterSource.addValue("itemName", od.getItemName());
			parameterSource.addValue("itemQty", od.getItemQty());
			parameterSource.addValue("itemPrice", od.getItemPrice());
			parameterSource.addValue("orderId", od.getOrderId());
			parameterSource.addValue("item_total_Price", od.getItem_total_Price());
		}
		return parameterSource;
	}

	@Override
	public List<Purchase_History> getPurchase_HistoryByEmail(String email) {
		String sql="SELECT * FROM PURCHASE_HISTORY WHERE EMAIL=:email";
		List<Purchase_History> phList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("email", email);
		System.out.println("Fetching items......!!!");
		phList=jdbcTemplate.query(sql,params,new Purchase_HistoryMapper()); 		
		return phList;
	}

	@Override
	public List<Purchase_History> getPurchase_HistoryByStatus(String status) {
		String sql="SELECT * FROM PURCHASE_HISTORY WHERE STATUS = :status";
		List<Purchase_History> phList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		System.out.println("Fetching items......!!! "+status);
		phList=jdbcTemplate.query(sql,params,new Purchase_HistoryMapper()); 		
		return phList;
	}

	@Override
	public List<Order_details> getOrder_details() {
		String sql="SELECT * FROM ORDER_DETAILS";
		List<Order_details> odList=null;
		System.out.println("Fetching items......!!!");
		odList=jdbcTemplate.query(sql,new Order_detailsMapper()); 		
		return odList;
	}

	
	
	private static final class Purchase_HistoryMapper implements RowMapper{

		public Purchase_History mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Purchase_History ph=new Purchase_History();			
			ph.setOrderId(rs.getInt("orderId"));
			ph.setEmail(rs.getString("email"));
			ph.setOrder_time(rs.getString("order_time"));
			ph.setStatus(rs.getString("status"));
			ph.setTotal_Price(rs.getDouble("total_Price"));
			return ph;
		}
		
	}
	
	private static final class Order_detailsMapper implements RowMapper{

		public Order_details mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Order_details od=new Order_details();
			od.setItemId(rs.getInt("itemId"));
			od.setItemName(rs.getString("itemName"));
			od.setItemQty(rs.getInt("itemQty"));
			od.setItemPrice(rs.getDouble("itemPrice"));
			od.setItem_total_Price(rs.getDouble("item_total_Price"));
			return od;
		}
	}
	 

}
