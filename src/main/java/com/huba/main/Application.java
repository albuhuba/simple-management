package com.huba.main;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
@ComponentScan(basePackages = { "com.huba", "com.huba.controller" })
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class })
public class Application {

	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		LOG.debug("Starting the API application.");
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
		p.setIgnoreResourceNotFound(true);
		return p;
	}

	@Bean
	@CrossOrigin(origins = "*", allowedHeaders = "origin")
	public FilterRegistrationBean crossOriginFilter() {
		FilterRegistrationBean crossOriginFilterBean = new FilterRegistrationBean();
		crossOriginFilterBean.setFilter(new GenericFilterBean() {

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				HttpServletResponse res = (HttpServletResponse) response;
				res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
				chain.doFilter(request, response);
			}
		});
		crossOriginFilterBean.setName("CrossOriginFilter");
		crossOriginFilterBean.setOrder(0);
		return crossOriginFilterBean;
	}

}
