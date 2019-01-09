package portal;







import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;
@Configuration
@ComponentScan({ "portal" })
public class TemplateConfig extends WebMvcConfigurerAdapter  implements ApplicationContextAware{
		private ApplicationContext applicationContext;
		
	  @Bean
	  public ViewResolver viewResolver() {
	    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
	    resolver.setTemplateEngine(templateEngine());
	    resolver.setCharacterEncoding("UTF-8");
	    return resolver;
	  }

	  @Bean
	  public SpringTemplateEngine templateEngine() {
		  final Set<IDialect> dialects = new HashSet<>();
		 dialects.add(new SpringSecurityDialect());
		  //dialects.add(new LayoutDialect());
	    SpringTemplateEngine engine = new SpringTemplateEngine();

	    //engine.addDialect(new SpringSecurityDialect());
	   
	    engine.setTemplateResolver(templateResolver());
	    //engine.addDialect(new SpringSecurityDialect());
	    engine.addDialect(new LayoutDialect());
	    engine.setAdditionalDialects(dialects);
	    return engine;
	  }
	  @Bean
	  public ITemplateResolver templateResolver() {
	    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
	    resolver.setApplicationContext(applicationContext);
	    resolver.setPrefix("classpath:/templates/");
	    resolver.setSuffix(".html");
	    resolver.setTemplateMode("HTML5");
	    resolver.setCacheable(false);
	    return resolver;
	  }
	  
		@Bean
		public MessageSource messageSource() {
		    ReloadableResourceBundleMessageSource messageSource
		      = new ReloadableResourceBundleMessageSource();
		     
		    messageSource.setBasename("classpath:messages");
		    messageSource.setDefaultEncoding("UTF-8");
		    return messageSource;
		}
		  
		@Bean
		public LocalValidatorFactoryBean getValidator() {
		    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		    bean.setValidationMessageSource(messageSource());
		    return bean;
		}
		
		@Bean
		public LocaleResolver localeResolver() {
		    SessionLocaleResolver slr = new SessionLocaleResolver();
		    slr.setDefaultLocale(Locale.US);

		    return slr;
		}	

		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
		    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		    lci.setParamName("lang");
		    return lci;
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		    registry.addInterceptor(localeChangeInterceptor());
		}	  

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.applicationContext = context;
		
	}	
}
