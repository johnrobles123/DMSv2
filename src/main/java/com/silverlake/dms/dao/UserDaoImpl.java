package com.silverlake.dms.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.silverlake.dms.model.User;
import com.silverlake.dms.model.UserProfile;
import com.silverlake.dms.model.UserProfileType;
import com.silverlake.dms.service.UserService;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserService userService;
	
	private static final String SELECT_QUERY = "select id, username, password from login";
	
	public User findById(int id) {
		return getByKey(id);
	}

	public User findBySSO(String sso) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("ssoId", sso));
		return (User) crit.uniqueResult();
	}

	public List<User> selectAll() {
		return findAll();
	}
	
	@Override
	public void save(User addUser) throws SQLException {
		addUser.setState("Active");
		
		UserProfile profile = new UserProfile();
		profile.setId(1);
		profile.setType(UserProfileType.USER.getUserProfileType());
		
		Set<UserProfile> profiles = addUser.getUserProfiles();
		profiles.add(profile);
		
		addUser.setUserProfiles(profiles);
		
		saveOrUpdate(addUser);
		//persist(addUser);

	}

	@Override
	public void update(User addUser) throws SQLException {
		StringBuilder stmt = new StringBuilder(); 
		User userObj = userService.findBySso(addUser.getSsoId());
		
//		for (UserProfile u : userObj.getUserProfiles()) {
//			if (u.getType().equalsIgnoreCase(UserProfileType.ADMIN.getUserProfileType().toString())) {
//				addUser.addUserProfiles(u);
//				break;
//			}
//		} 
//		
		//stmt.append("SELECT a.seq_no, a.device_serial_no, b.device_name, a.username, a.reserve_date, a.time_from, a.time_to, a.location, a.add_info FROM device_journal a, device_list b WHERE a.device_serial_no = b.serial_no ");
		stmt.append("update app_user "
				+ "set password = :password, first_name = :firstName, last_name = :lastName , email = :email, state = :state "
				+ "where id = :id");
		    //.append("AND TIMESTAMP(a.reserve_date, a.time_to) >= CURRENT_TIMESTAMP()");
		
		Session session = getSession();
		Query query = session.createSQLQuery(stmt.toString())
				.setParameter("password", addUser.getPassword())
				.setParameter("firstName", addUser.getFirstName())
				.setParameter("lastName", addUser.getLastName())
				.setParameter("email", addUser.getEmail())
				.setParameter("state", addUser.getState())
				.setParameter("id", addUser.getId());
		query.executeUpdate();
		//session.merge(addUser);
//		session.saveOrUpdate(addUser);
		
	}

	@Override
	public void delete(int id) throws SQLException {
		User user = new User();
		
		user = findById(id);
		delete(user);
		
	}
}
