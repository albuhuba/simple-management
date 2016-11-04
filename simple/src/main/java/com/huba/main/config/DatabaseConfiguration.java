package com.huba.main.config;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.util.jdbc.DriverDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Value("${flyway.driver}")
	private String flywayDriver;

	@Value("${flyway.user}")
	private String flywayUser;

	@Value("${flyway.password}")
	private String flywayPassword;

	@Value("${database.url}")
	private String jdbcUrl;

	@Value("${database.user}")
	private String user;

	@Value("${database.password}")
	private String password;

	public DatabaseConfiguration() {
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setPackagesToScan("com.huba");

		return sessionFactory;
	}

	public Properties hibernateProperties() {
		Properties properties = new Properties();

		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.order_inserts", TRUE);
		properties.put("hibernate.order_updates", TRUE);
		properties.put("hibernate.show_sql", FALSE);
		properties.put("hibernate.format_sql", TRUE);
		properties.put("hibernate.hbm2ddl.auto", "validate");

		return properties;
	}

	@DependsOn("flyway")
	@Bean
	public DataSource dataSource() {
		final HikariDataSource source = new HikariDataSource();
		source.setJdbcUrl(this.jdbcUrl);

		source.addDataSourceProperty("url", this.jdbcUrl);
		source.addDataSourceProperty("user", this.user);
		source.addDataSourceProperty("password", this.password);
		source.addDataSourceProperty("cachePrepStmts", TRUE);
		source.addDataSourceProperty("prepStmtCacheSize", 250);
		source.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
		source.addDataSourceProperty("useServerPrepStmts", TRUE);

		return source;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory().getObject());
		return txManager;
	}

	@Bean(name = "flyway")
	public Flyway flyway() {
		Flyway bean = new Flyway();
		bean.setDataSource(new DriverDataSource(getClass().getClassLoader(), this.flywayDriver, this.jdbcUrl, this.flywayUser, this.flywayPassword,
				"select 1;"));
		bean.setOutOfOrder(true);
		bean.setPlaceholderPrefix("${{");
		bean.setPlaceholderSuffix("}}");
		bean.setLocations("classpath:/db.migrations");

		bean.migrate();

		return bean;
	}
}
