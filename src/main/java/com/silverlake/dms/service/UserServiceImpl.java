package com.silverlake.dms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.silverlake.dms.dao.UserDao;
import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findBySso(String sso) {
		return dao.findBySSO(sso);
	}
	
	public List<User> selectAll() {
		return dao.selectAll();
	}
	
	@Override
	public void save(User addUser) throws SQLException {
		dao.save(addUser);
	}

	@Override
	public void update(User addUser) throws SQLException {
		dao.update(addUser);
	}

	@Override
	public void delete(int id) throws SQLException {
		dao.delete(id);
	}

}
