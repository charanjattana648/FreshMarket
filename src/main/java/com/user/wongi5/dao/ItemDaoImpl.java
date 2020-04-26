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

import com.user.wongi5.model.Item;
import com.user.wongi5.model.User;

@Component
public class ItemDaoImpl implements ItemDao{
	
NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	private SqlParameterSource getSqlParameterByModel(Item i)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(i!=null)
		{
			parameterSource.addValue("itemId", i.getItemId());
			parameterSource.addValue("itemName", i.getItemName());
			parameterSource.addValue("itemType", i.getItemType());
			parameterSource.addValue("itemCount", i.getItemCount());
			parameterSource.addValue("itemDesc", i.getItemDesc());
			parameterSource.addValue("itemPrice", i.getItemPrice());
			parameterSource.addValue("itemImage", i.getItemImage());
		}
		return parameterSource;
	}
	
	private SqlParameterSource getSqlParameterByModel(int itemId) {
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
			parameterSource.addValue("itemId", itemId);
		return parameterSource;
	}

	public boolean addItem(Item item) {
		boolean res=true;
		try {
		String sql="INSERT INTO ITEM(ITEMNAME, ITEMTYPE, ITEMPRICE, ITEMCOUNT, ITEMIMAGE, ITEMDESC) VALUES(:itemName, :itemType, :itemPrice, :itemCount, :itemImage,:itemDesc)";
		
		jdbcTemplate.update(sql, getSqlParameterByModel(item));
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	public boolean updateItem(Item item) {
		boolean res=true;
		try {
			String sql="UPDATE ITEM set ITEMNAME=:itemName , ITEMTYPE=:itemType , ITEMPRICE=:itemPrice, ITEMCOUNT=:itemCount, ITEMDESC=:itemDesc WHERE ITEMID=:itemId";
			
			if(item.getItemImage()!=null)
			{
				sql="UPDATE ITEM set ITEMNAME=:itemName , ITEMTYPE=:itemType , ITEMPRICE=:itemPrice, ITEMCOUNT=:itemCount, ITEMIMAGE=:itemImage, ITEMDESC=:itemDesc WHERE ITEMID=:itemId";
			}
			jdbcTemplate.update(sql, getSqlParameterByModel(item));

		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	public boolean updateItemQty(int itemId,int itemCount) {
		boolean res=true;
		try {
			Item item=new Item();
			item.setItemCount(itemCount);
			item.setItemId(itemId);
			String sql="UPDATE ITEM set ITEMCOUNT=:itemCount WHERE ITEMID=:itemId";
			jdbcTemplate.update(sql, getSqlParameterByModel(item));			
		
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}
	
	public boolean deleteItem(int itemId) {
		boolean res=true;
		try {
		String sql="DELETE FROM ITEM WHERE ITEMID=:itemId";
		
		jdbcTemplate.update(sql, getSqlParameterByModel(itemId));
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	

	public List<Item> getItemsBy(String filterType,String itemValue) {
		String sql="SELECT * FROM ITEM WHERE ITEMID=:itemId";
		List<Item> itemList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		
		switch(filterType) {
		case "itemId":
			 params.put("itemId", Integer.parseInt(itemValue));
			break;
		case "itemName":
			 sql="SELECT * FROM ITEM WHERE ITEMNAME=:itemName";
			 params.put("itemName", itemValue);
			break;
		case "itemType":
			 sql="SELECT * FROM ITEM WHERE ITEMTYPE=:itemType";
			 params.put("itemType", itemValue);
			break;
		}
		System.out.println("Fetching items......!!!");
		
		itemList=jdbcTemplate.query(sql,params,new ItemMapper());
		if(itemList!=null)
		{
			System.out.println("Details : getItem by ....");
			return itemList;
		}
		return null;
	}

	public List<Item> getItems() {
		String sql="SELECT * FROM ITEM";
		List<Item> itemList=null;
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println("Fetching items......!!!");
		itemList=jdbcTemplate.query(sql,new ItemMapper());
		//System.out.println("item counts : "+itemList.size());
		if(itemList!=null)
		{
			System.out.println("Details : getItems");
			return itemList;
		}
		return null;
	}
	
	private static final class ItemMapper implements RowMapper{

		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			Item i=new Item();
			i.setItemId(rs.getInt("itemId"));
			i.setItemName(rs.getString("itemName"));
			i.setItemCount(rs.getInt("itemCount"));
			i.setItemPrice(rs.getDouble("itemPrice"));
			i.setItemType(rs.getString("itemType"));
			i.setItemDesc(rs.getString("itemDesc"));
			i.setItemImage(rs.getBytes("itemImage"));
			return i;
		}
		
	}
	
	

}
