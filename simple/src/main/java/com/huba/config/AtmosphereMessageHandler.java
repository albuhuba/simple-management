package com.huba.config;

import java.io.IOException;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResponse;

/**
 *
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@AtmosphereHandlerService(path = "/messages")
public class AtmosphereMessageHandler implements AtmosphereHandler {

	private static final String POST = "POST";
	private static final String GET = "GET";

	@Override
	public void onRequest(AtmosphereResource resource) throws IOException {
		AtmosphereRequest request = resource.getRequest();
		if (request.getMethod().equalsIgnoreCase(GET)) {
			resource.suspend();
		} else if (request.getMethod().equalsIgnoreCase(POST)) {
			resource.getBroadcaster().broadcast(request.getReader().readLine().trim());
		}
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent event) throws IOException {
		AtmosphereResource resource = event.getResource();
		AtmosphereResponse response = resource.getResponse();

		String body = event.getMessage().toString();
		if (resource.isSuspended()) {
			response.getWriter().write(body);
			switch (resource.transport()) {
			case JSONP:
			case LONG_POLLING:
				event.getResource().resume();
				break;
			case STREAMING:
				response.getWriter().flush();
				break;
			case WEBSOCKET:
			default:
				break;
			}
		} else if (!event.isResuming()) {
			event.broadcaster().broadcast(body);
		}
	}

	@Override
	public void destroy() {
		/**
		 * NOOP
		 */
	}

}
