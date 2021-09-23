package com.websystique.springmvc.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.websystique.springmvc.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
    private SessionFactory sessionFactory;
	@Override
	public void saveEmployee(User u) {
		sessionFactory.openSession().save(u);

	}

}
