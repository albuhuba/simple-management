package com.huba.controller;

import static com.huba.main.APITags.MESSAGE_CONTROLLER;

import javax.validation.Valid;

import org.atmosphere.cpr.AtmosphereFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	private AtmosphereFramework framework;

	@ApiOperation(tags = MESSAGE_CONTROLLER, value = "Creates a message, saves into the database and sends out the same message to the registered clients.")
	@RequestMapping(value = "/create-message", method = { RequestMethod.POST })
	@Transactional
	public void createMessage(@Valid @RequestBody MessageBodyRequestDTO body) {
		service.createMessage(body.getMessage());
	}

}
