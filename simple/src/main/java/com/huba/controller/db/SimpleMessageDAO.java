package com.huba.controller.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huba.models.Message;

/**
 * DAO layer for saving and retrieving the data from the database.
 * 
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@Service
public class SimpleMessageDAO {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleMessageDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Message message) {
		currentSession().save(message);
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}
}
