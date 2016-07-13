package com.silverlake.dms.service;

import java.sql.SQLException;
import java.util.List;

import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;

public interface UserService {

	public User findById(int id);
	
	public User findBySso(String sso);
	
	public List<User> selectAll();
	
	public void save(User addUser) throws SQLException;
	
	public void update(User addUser) throws SQLException;
	
	public void delete(int id) throws SQLException;
}