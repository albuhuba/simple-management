package com.huba.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
	private ObjectMapper objectMapper;

	public void createMessage(String jsonMessage) {
		checkNotNull(jsonMessage, MESSAGE_CANNOT_BE_NULL);

		Message message = new Message();
		message.setMessage(jsonMessage);

		messageDao.save(message);
	}

	public List<MessageRO> getMessages() {
		return messageDao.getMessages().stream().map(c -> {
			try {
				return objectMapper.readValue(c.getMessage(), MessageRO.class);
			} catch (IOException e) {
				//
			}
			return null;
		}).collect(Collectors.toList());
	}

}
