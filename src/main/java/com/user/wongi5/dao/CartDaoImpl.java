package com.user.wongi5.dao;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.user.wongi5.model.CartItems;
import com.user.wongi5.model.Item;
import com.user.wongi5.model.Order_details;
import com.user.wongi5.model.Purchase_History;
import com.user.wongi5.service.CartService;

@Component
public class CartDaoImpl implements CartDao {
	
	private final String NAME="CartItem";
	
	@Autowired
	ItemDao itemDao;
	
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

	public void addCart(HttpServletRequest req,HttpServletResponse res,CartItems items) {
		

		System.out.println(items.getItemId()+"   -------------------------    "+items.toString());
		Cookie cookie=new Cookie(NAME+"-"+items.getItemId()+"", items.toString());
		cookie.setMaxAge(60*60*24*7);
		res.addCookie(cookie);
	}

	@Override
	public void removeCart(HttpServletResponse res,int cartId) {
				
		Cookie cookie=new Cookie(NAME+"-"+cartId,"");
		cookie.setMaxAge(0);
		res.addCookie(cookie);
		
	}

	@Override
	public List<CartItems> showCart(HttpServletRequest req) {
		//int x=req.getCookies().length;
		List<CartItems> clist=new ArrayList<>();
		for(Cookie c:req.getCookies())
		{
			CartItems cart_item=new CartItems();
			//
			if(c.getName().contains(NAME))
			{
			System.out.println(c.getName()+"  ------  : "+c.getValue());
			String item_id=(c.getName().substring(NAME.length()+1));
			System.out.println("item_id--------------"+item_id);
			cart_item.setItemId(Integer.parseInt(item_id));
			String c_item[]=c.getValue().split("@");
			for(String item:c_item)
			{
			String c_item_value[]=item.split("=");
			switch(c_item_value[0])
			{
			case "itemName":
				cart_item.setItemName(c_item_value[1]);
				break;
			case "itemPrice":
				cart_item.setItemPrice(Double.parseDouble(c_item_value[1]));
				break;
			case "qty":
				cart_item.setQty(Integer.parseInt(c_item_value[1]));
				break;
			case "itemTotalPrice":
				cart_item.setItemTotalPrice(Double.parseDouble(c_item_value[1]));
				break;
			}
			
			}
			clist.add(cart_item);
			}
		}
		for(CartItems ci:clist)
		{
			System.out.println("-------------"+ci.toString());
		}
		return clist;
	}
	
	public Purchase_History orderItems(HttpSession session,List<CartItems> cartList)
	{
		Purchase_History ph=null;
		CartService cs=null;
		Order_details od=null;
		boolean orderStatus=false,orderInserted=false;
		List<Item> itemList=null;
		
		for(CartItems ci:cartList)
		{
			itemList=itemDao.getItemsBy("itemId", ci.getItemId()+"");
			if(ci.getQty()<=itemList.get(0).getItemCount())
			{
				int new_item_count=itemList.get(0).getItemCount()-ci.getQty();
				orderStatus=itemDao.updateItemQty(ci.getItemId(), new_item_count);
			}
			if(orderStatus==true && orderInserted==false)
			{
				ph=new Purchase_History();
				cs=new CartService();						
				String email=(String) session.getAttribute("user_email");
				ph.setEmail(email);
				ph.setTotal_Price(cs.getTotalPrice(cartList));
				ph.setStatus("Pending");
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				ph.setOrder_time(df.format(dateobj));
				addPurchase_History(ph);
				orderInserted=true;
			}
			od=new Order_details();
			int oId=getLatestPurchase_History().getOrderId();
			System.out.println("ci name..............>>>>>>>>>>>> "+ci.getItemName());
			od.setItemId(ci.getItemId());
			od.setItemName(ci.getItemName());
			od.setItemPrice(ci.getItemPrice());
			od.setItemQty(ci.getQty());
			od.setOrderId(oId);
			od.setItem_total_Price(ci.getItemTotalPrice());
			addOrder_details(od);
		}
		return ph;
	}
	
	/*
	 * public int getLatestOrderId() { String sql="SELECT * FROM ITEM"; }
	 */
	
	public Purchase_History getLatestPurchase_History() {
		String sql="SELECT * FROM PURCHASE_HISTORY ORDER BY orderId LIMIT 1";
		List<Purchase_History> phList=null;
		//Map<String, Object> params = new HashMap<String, Object>();
		System.out.println("Fetching items......!!!");

		phList=jdbcTemplate.query(sql,new Purchase_HistoryMapper()); 	
		if(phList!=null && phList.size()>0)
		{
			return phList.get(0);
		}
		return null;
	}
	
	public List<Purchase_History> getPurchase_HistoryByEmail(String email) {
		String sql="SELECT * FROM PURCHASE_HISTORY WHERE EMAIL=:email";
		List<Purchase_History> phList=null;
		System.out.println("Fetching items......!!!");
		phList=jdbcTemplate.query(sql,new Purchase_HistoryMapper()); 		
		return phList;
	}
	
	public List<Order_details> getOrder_details(int orderId) {
		String sql="SELECT * FROM ORDER_DETAILS WHERE ORDERID=:orderId";
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
	 
	
	public boolean addPurchase_History(Purchase_History ph) {
		boolean res=true;
		try {
		String sql="INSERT INTO PURCHASE_HISTORY(EMAIL,STATUS,TOTAL_PRICE,ORDER_TIME) VALUES (:email ,:status,:totalPrice,:order_time)";
		int x=jdbcTemplate.update(sql, getSqlParameterByPurchase_History(ph));
		
		System.out.println("oooooooo................."+x);
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public boolean addOrder_details(Order_details od) {
		boolean res=true;
		try {			
		String sql="INSERT INTO ORDER_DETAILS (ITEMID,ITEMNAME,ORDERID, ITEMQTY, ITEMPRICE, ITEM_TOTAL_PRICE) VALUES (:itemId,:itemName ,:orderId,:itemQty,:itemPrice,:item_total_Price)";
		jdbcTemplate.update(sql, getSqlParameterByOrder_details(od));
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

}
