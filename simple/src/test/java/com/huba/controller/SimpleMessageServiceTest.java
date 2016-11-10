package com.huba.controller;

import static com.huba.controller.SimpleMessageService.MESSAGE_CANNOT_BE_NULL;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huba.controller.db.SimpleMessageDAO;
import com.huba.models.Message;

/**
 *
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@RunWith(MockitoJUnitRunner.class)
public class SimpleMessageServiceTest {

	private static final String EMPTY_JSON_MESSAGE = "{}";

	@Mock
	private SimpleMessageDAO messageDao;

	@InjectMocks
	private SimpleMessageService service;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Before
	public void before() {
		this.service.setObjectMapper(objectMapper);
	}

	@Test
	public void nullMessage() {
		exception.expect(NullPointerException.class);
		exception.expectMessage(MESSAGE_CANNOT_BE_NULL);

		service.createMessage(null);

		verify(messageDao, never()).save(any(Message.class));
	}

	@Test
	public void properMessageBody() {
		service.createMessage(EMPTY_JSON_MESSAGE);

		verify(messageDao).save(any(Message.class));
	}

	@Test
	public void getAllMessagesEmptyResponse() throws Exception {
		List<Message> originalMessages = new ArrayList<>();
		doReturn(originalMessages).when(messageDao).getMessages();

		List<MessageRO> messages = service.getMessages();

		assertTrue(messages.isEmpty());
	}

	@Test
	public void getAllMessagesAtLeastOneElement() throws Exception {
		List<Message> originalMessages = new ArrayList<>();
		Message message = new Message();
		message.setMessage("Message");
		originalMessages.add(message);
		doReturn(originalMessages).when(messageDao).getMessages();

		List<MessageRO> messages = service.getMessages();

		assertTrue(!messages.isEmpty());
	}

}
