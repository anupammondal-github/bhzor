package ics.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ics.model.User;
@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);

	}
	@Transactional
	public void editUser(User user) {
		sessionFactory.getCurrentSession().update(user);
	}
	@Transactional
	public void deleteUser(Long userId) {
		sessionFactory.getCurrentSession().delete(findUser(userId));
	}
	@Transactional
	public User findUser(Long userId) {
		return (User) sessionFactory.getCurrentSession().get(User.class, userId);
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public User findUserByName(String username) {
		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
			.createQuery("from User where username=?")
			.setParameter(0, username)
			.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().createQuery("from User").list();
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllCustomers() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		List<User> allCustomers = (List<User>) criteria.add(Restrictions.eq("roleName", "Customer")).list();
		return allCustomers;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllDistributors() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		List<User> allDistributors = (List<User>) criteria.add(Restrictions.eq("roleName", "Distributor")).list();
		return allDistributors;
	}
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> getAllAdministrators() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		List<User> allAdministrators = (List<User>) criteria.add(Restrictions.eq("roleName", "Administrator")).list();
		return allAdministrators;
	}
	@SuppressWarnings("unchecked")
	public List<User> getUsersByRole(String roleName) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		List<User> users = (List<User>) criteria.add(Restrictions.eq("roleName", roleName)).list();
		return users;
	}
	
	public Long totalUserNum() {
		return (Long) sessionFactory.getCurrentSession().createCriteria(User.class).
		setProjection(Projections.rowCount())
		.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> list(Integer offset, Integer maxResults) {
		return sessionFactory.getCurrentSession()
			    .createCriteria(User.class)
			    .setFirstResult(offset!=null?offset:0)
			    .setMaxResults(maxResults!=null?maxResults:10)
			    .list();
	}
	
	@SuppressWarnings("unchecked")
	public User findUserByEmail(String email) {
		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
			.createQuery("from User where email=?")
			.setParameter(0, email)
			.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
