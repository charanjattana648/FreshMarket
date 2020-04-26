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

import com.user.wongi5.model.User;

@Component
public class AuthDaoImpl implements AuthDao {

	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate )
	{
		this.jdbcTemplate=jdbcTemplate;
	}
	
	private SqlParameterSource getSqlParameterByModel(User c)
	{
		MapSqlParameterSource parameterSource=new MapSqlParameterSource();
		if(c!=null)
		{
			parameterSource.addValue("email", c.getEmail());
			parameterSource.addValue("name", c.getName());
			parameterSource.addValue("password", c.getPassword());
			parameterSource.addValue("userType", c.getUserType());
		}
		return parameterSource;
	}
	
	public boolean addUser(User customer) {
		boolean res=true;
		try {
		String sql="INSERT INTO USER(email,name,password,userType) VALUES(:email,:name,:password,:userType)";
		jdbcTemplate.update(sql, getSqlParameterByModel(customer));
		}catch (Exception e) {
			res=false;
			System.out.println(e.getMessage());
		}
		return res;
	}

	public boolean checkUser(String userType, String email, String password) {
		String sql="SELECT * FROM USER WHERE email=:email AND password=:password AND userType=:userType";
		List<User> userList=null;
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("email", email);
        params.put("password", password);
        params.put("userType", userType);
        System.out.println("email  : "+email);
        System.out.println("userType  : "+userType);
        userList=jdbcTemplate.query(sql, params,new UserMapper());
		if(userList.size()==1)
		{
			System.out.println("Name : "+userList.get(0).getName());
			return true;
		}
		
//		List<String> strLst  = getJdbcTemplate().query(sql,new RowMapper {
//
//			  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//			        return rs.getString(1);
//			  }
//
//			});
//
//			if ( strLst.isEmpty() ){
//			  return null;
		
		
		return false;
	}
	
	private static final class UserMapper implements RowMapper{

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
			User c=new User();
			c.setEmail(rs.getString("email"));
			c.setName(rs.getString("name"));
			c.setPassword(rs.getString("password"));
			c.setUserType(rs.getString("userType"));
			return c;
		}
		
	}

	
}
