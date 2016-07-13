package com.silverlake.dms.dao;

import java.sql.SQLException;
import java.util.List;

import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;

public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);

	public List<User> selectAll();
	
	public void save(User addUser) throws SQLException;
	
	public void update(User addUser) throws SQLException;
	
	public void delete(int id) throws SQLException;
}
