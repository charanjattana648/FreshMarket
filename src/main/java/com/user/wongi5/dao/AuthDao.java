package com.user.wongi5.dao;

import com.user.wongi5.model.User;

public interface AuthDao {

	boolean addUser(User customer);
	boolean checkUser(String userType,String email,String password);
}
