package com.huba.controller.db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

	@Autowired
	private SessionFactory sessionFactory;

	public void save(Message message) {
		currentSession().save(message);
	}

	private Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessages() {
		return currentSession().createCriteria(Message.class).list();
	}
}
