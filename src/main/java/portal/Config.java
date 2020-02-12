package portal;





import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


import org.springframework.core.env.Environment;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;






@SuppressWarnings("deprecation")
@Configuration
@EnableTransactionManagement
@ComponentScan({ "portal" })
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("portal.repository")
@EnableAsync
public class Config {
	private static final String PROP_DATABASE_DRIVER = "db.driver";
	private static final String PROP_DATABASE_PASSWORD = "db.password";
	private static final String PROP_DATABASE_URL = "db.url";
	private static final String PROP_DATABASE_USERNAME = "db.username";
	private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";
	private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";	
	private static final String PROP_HIBERNATE_AUTO_QUOTED_KEYWORD = "hibernate.auto_quote_keyword";
	private static final String PROP_MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String PROP_MAIL_SMTP_PORT = "mail.smtp.port";	
	private static final String PROP_MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String PROP_MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String PROP_MAIL_USERNAME = "mail.username";
	private static final String PROP_MAIL_PASSWORD = "mail.password";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	
	@Resource
	private Environment env;


	
	@Bean
	public DataSource dataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));

		entityManagerFactoryBean.setJpaProperties(getHibernateProperties());

		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}	
	

	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
		properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
		properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
		properties.put(PROP_HIBERNATE_AUTO_QUOTED_KEYWORD, env.getRequiredProperty(PROP_HIBERNATE_AUTO_QUOTED_KEYWORD));
		properties.put("hibernate.connection.CharSet", "utf-8");
		properties.put("hibernate.connection.useUnicode", true);
		properties.put("hibernate.connection.characterEncoding", "utf-8");

		return properties;
	}
	
	@Bean	
	public Session mailSession()
	{
		if(env.getRequiredProperty(PROP_MAIL_SMTP_AUTH).toUpperCase()=="TRUE")
		{
			return Session.getDefaultInstance(getEmailProperties(),  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(env.getRequiredProperty(PROP_MAIL_USERNAME), env.getRequiredProperty(PROP_MAIL_PASSWORD));
				}
			  });
		}

		return Session.getDefaultInstance(getEmailProperties(), null);
		
	}

	
	private Properties getEmailProperties()
	{
		Properties properties = new Properties();
		properties.put(PROP_MAIL_SMTP_HOST, env.getRequiredProperty(PROP_MAIL_SMTP_HOST));
		properties.put(PROP_MAIL_SMTP_PORT, env.getRequiredProperty(PROP_MAIL_SMTP_PORT));
		properties.put(PROP_MAIL_SMTP_AUTH, env.getRequiredProperty(PROP_MAIL_SMTP_AUTH));
		properties.put(PROP_MAIL_SMTP_STARTTLS_ENABLE, env.getRequiredProperty(PROP_MAIL_SMTP_STARTTLS_ENABLE));
		
		return properties;
	}
	
    @Bean (name = "importTaskExecutor")
    public Executor importTaskExecutor() {
        LOGGER.debug("Creating Async Import Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("ImportThread-");
        executor.initialize();
        return executor;
    }
	
}
	

