package com.huba.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.cpr.AnnotationProcessor;
import org.atmosphere.cpr.AtmosphereFramework;
import org.atmosphere.cpr.AtmosphereInterceptor;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.BroadcasterLifeCyclePolicy.ATMOSPHERE_RESOURCE_POLICY;
import org.atmosphere.spring.bean.AtmosphereSpringContext;
import org.atmosphere.util.VoidAnnotationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Huba Albu {ha@viabill.com}
 *
 **/
@Configuration
public class AtmosphereConfiguration {

	@Bean
	public AtmosphereFramework atmosphereFramework() throws ServletException, InstantiationException, IllegalAccessException {
		AtmosphereFramework atmosphereFramework = new AtmosphereFramework(false, false);
		// atmosphereFramework.setBroadcasterCacheClassName(UUIDBroadcasterCache.class.getName());
		atmosphereFramework.addAtmosphereHandler("/messages/*", atmosphereMessageHandler(), interceptors());

		return atmosphereFramework;
	}

	@Bean
	public AtmosphereMessageHandler atmosphereMessageHandler() {
		return new AtmosphereMessageHandler();
	}

	private List<AtmosphereInterceptor> interceptors() {
		List<AtmosphereInterceptor> atmosphereInterceptors = new ArrayList<>();
		// atmosphereInterceptors.add(new TrackMessageSizeInterceptor());
		return atmosphereInterceptors;
	}

	@Bean
	public BroadcasterFactory broadcasterFactory() throws ServletException, InstantiationException, IllegalAccessException {
		return atmosphereFramework().getAtmosphereConfig().getBroadcasterFactory();
	}

	@Bean
	public AtmosphereSpringContext atmosphereSpringContext() {
		AtmosphereSpringContext atmosphereSpringContext = new AtmosphereSpringContext();
		Map<String, String> map = new HashMap<>();
		map.put("org.atmosphere.cpr.broadcasterClass", org.atmosphere.cpr.DefaultBroadcaster.class.getName());
		map.put(AtmosphereInterceptor.class.getName(), TrackMessageSizeInterceptor.class.getName());
		map.put(AnnotationProcessor.class.getName(), VoidAnnotationProcessor.class.getName());
		map.put("org.atmosphere.cpr.broadcasterLifeCyclePolicy", ATMOSPHERE_RESOURCE_POLICY.IDLE_DESTROY.toString());
		atmosphereSpringContext.setConfig(map);
		return atmosphereSpringContext;
	}
}
