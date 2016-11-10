package com.huba.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huba.controller.request.MessageBodyRequestDTO;

/**
 * The {@link #messageCreationDelegated} would have passed also without an actual messgae, but the framework makes sure we'll have one and
 * for the logical requirements perspective I've added the simplest Json.
 * 
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

	private static final String EMPTY_JSON_MESSAGE = "{}";

	@Mock
	private SimpleMessageService service;

	@Mock
	private SimpMessagingTemplate template;

	@InjectMocks
	private MessageController controller;

	@Test
	public void messageCreationDelegated() throws JsonProcessingException {
		MessageBodyRequestDTO mock = mock(MessageBodyRequestDTO.class);
		doReturn(EMPTY_JSON_MESSAGE).when(mock).getMessage();
		controller.createMessage(mock);

		verify(service).createMessage(eq((String) null));
		verify(template).convertAndSend(any(String.class), eq((Object) null));
	}

	@Test
	public void getMessages() {
		List<MessageRO> originalMessages = new ArrayList<>();
		MessageRO message = new MessageRO();
		message.setMessage("Message");
		originalMessages.add(message);
		doReturn(originalMessages).when(service).getMessages();

		List<MessageRO> messages = controller.getAllMessages();

		assertEquals(new Integer(1), new Integer(messages.size()));
	}

}
