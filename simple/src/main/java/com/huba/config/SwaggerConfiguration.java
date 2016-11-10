package com.huba.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Value("${swagger.enabled?: true}")
	private boolean enabled;

	@Bean
	public Docket allGroup() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("all").enable(enabled).apiInfo(apiInfo()).select().paths(allPaths()).build();
	}

	@Bean
	public Docket authGroup() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("common").enable(enabled).apiInfo(apiInfo()).select().paths(commonPaths()).build();
	}

	@Bean
	public Docket commonGroup() {
		return new Docket(DocumentationType.SWAGGER_2)
				.securitySchemes(Lists.newArrayList(apiKey()))
				.groupName("common-api")
				.enable(enabled)
				.apiInfo(apiInfo())
				.select()
				.paths(commonPaths())
				.build();
	}

	/**
	 * It is not really used at this point, but for a simple JWT token based authentication this is very useful.
	 * 
	 * @return
	 */
	private ApiKey apiKey() {
		return new ApiKey("token", "X-AUTH-TOKEN", "header");
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "token", ",");
	}

	private Predicate<String> allPaths() {
		return regex("/(common)/.*");
	}

	private Predicate<String> commonPaths() {
		return regex("/common/.*");
	}

	private ApiInfo apiInfo() {
		// @formatter:off
		return new ApiInfoBuilder().
				title("Huba Public API").
				description("This is documentation for the simple messagin API").
				contact(new Contact("albuhuba@yahoo.com", "", "")).
				version("0.0.1-SNAPSHOT").
				build();
		// @formatter:on
	}

}
