package com.huba.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.huba.controller.db.SimpleMessageDAO;
import com.huba.models.Message;

/**
 *
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@Service
public class SimpleMessageService {

	@VisibleForTesting
	static final String MESSAGE_CANNOT_BE_NULL = "Message cannot be null";

	@Autowired
	private SimpleMessageDAO messageDao;

	@Autowired
	private AtmosphereFramework framework;

	public void createMessage(String jsonMessage) {
		checkNotNull(jsonMessage, MESSAGE_CANNOT_BE_NULL);

		Message message = new Message();
		message.setMessage(jsonMessage);

		messageDao.save(message);
		framework.getBroadcasterFactory();
		BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, "/messages").broadcast(message);
	}

}
