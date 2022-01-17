package com.huba.controller;

import static com.huba.main.APITags.MESSAGE_CONTROLLER;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huba.controller.request.MessageBodyRequestDTO;

import io.swagger.annotations.ApiOperation;

/**
 *
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@RestController
@RequestMapping("/api-public")
public class MessageController {

	@Autowired
	private SimpleMessageService service;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private ObjectMapper objectMapper;

	@ApiOperation(tags = MESSAGE_CONTROLLER, value = "Creates a message, saves into the database and sends out the same message to the registered clients.")
	@RequestMapping(value = "/create-message", method = { RequestMethod.POST }, consumes = "application/json")
	@Transactional
	public void createMessage(@Valid @RequestBody MessageBodyRequestDTO body) throws JsonProcessingException {
		String message = objectMapper.writeValueAsString(body);
		service.createMessage(message);

		this.template.convertAndSend("/topic/message", message);
	}

	@ApiOperation(tags = MESSAGE_CONTROLLER, value = "Gets all messages from the DB and send back as a Json.")
	@RequestMapping(value = "/get-all-messages", method = { RequestMethod.GET }, produces = "application/json")
	@Transactional(readOnly = true)
	public List<MessageRO> getAllMessages() {
		return service.getMessages();
	}

}
