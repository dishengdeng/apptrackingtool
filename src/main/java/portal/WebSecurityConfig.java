package portal;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import portal.service.Impl.CustomAuthenticationFailureHandler;





@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private UserDetailsService userDetailsService;
    
 

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    // Register HttpSessionEventPublisher
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http	.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/bootstrap/**",
                    		"/dist/**",
                    		"/datatables/**",
                    		"/metisMenu/**",
                    		"/jquery/**",
                    		"/font-awesome/**",
                    		"/datatables-plugins/**",
                    		"/datatables-responsive/**",
                    		"/favicon.png",
                    		"/registration","/websocket/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/deleteInstanceSLA").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceSLA").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceContract").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceContract").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceLicense").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceLicense").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceDesktop").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceDesktop").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceServer").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceServer").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteInstanceSite").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addInstanceSite").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateAppInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addAppInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteAppInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addCluster").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateCluster").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteCluster").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateContract").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addContract").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteContract").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/contractupload").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deletecontractfile").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deletedepartmentfile").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/departmentupload").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateDesktop").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addDesktop").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteDesktop").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addApplicationInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteApplicationInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteApplicationCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addApplicationCompany").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateLicense").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addLicense").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteLicense").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateServer").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteserverfile").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addServer").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteServer").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/serverupload").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateSite").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSite").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSite").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateSLA").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteslafile").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSLA").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSLA").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/slaupload").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSLARole").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateSLARole").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSLARole").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addZone").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/updateZone").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteZone").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteDepartmentInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addDepartmentInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteDepartmentStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addDepartmentStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteLicenseInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addLicenseInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteServerInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addServerInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSupportInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSupportInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteContractInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addContractInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteCompanyApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addCompanyApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteCompanyInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSLAInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addSLAInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteStakeHolderDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addStakeHolderDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteStakeHolderRole").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/addStakeHolderRole").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/deleteSlaroleStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addSlaroleStakeholder").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteDesktopInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addDesktopInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/appinstanceupload").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteappinstancefile").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteapplicationfile").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/applicationupload").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addApplicationDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteApplicationDepartment").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addApplicationSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteApplicationSupport").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteDepartmentapplication").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addDepartmentapplication").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteSiteInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addSiteInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteSiteZone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addSiteZone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteZoneSite").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addZoneSite").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteZoneInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addZoneInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteInstanceZone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addInstanceZone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addproject").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/updateproject").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteproject").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteProjectInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addProjectInstance").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteProjectApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addProjectApplication").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteApplicationProject").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addApplicationProject").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteAppSite").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addAppSite").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteappzone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addAppZone").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteZoneApp").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addZoneApp").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/deleteSiteApp").hasAnyRole("ADMIN","SYSADMIN","USER")
					.antMatchers("/addSiteApp").hasAnyRole("ADMIN","SYSADMIN","USER")
                    .antMatchers("/users").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/loggedusers").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/kickoutuser").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/addUser").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/deleteUser").hasAnyRole("ADMIN","SYSADMIN")                    
                    .antMatchers("/roles").hasRole("SYSADMIN")
                    .antMatchers("/updateRole").hasRole("SYSADMIN")
                    .antMatchers("/addRole").hasRole("SYSADMIN")
                    .antMatchers("/deleteRole").hasRole("SYSADMIN")
//rest api
                    .antMatchers("/api/zones").hasAnyRole("ADMIN","SYSADMIN","INTEGRATION")
                    .antMatchers("/api/zone/create").hasAnyRole("ADMIN","SYSADMIN","INTEGRATION")
                    .antMatchers("/api/zone/update").hasAnyRole("ADMIN","SYSADMIN","INTEGRATION")
                    .antMatchers("/api/alert").hasAnyRole("ADMIN","SYSADMIN","INTEGRATION")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureHandler(customAuthenticationFailureHandler())
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .sessionManagement()
				.maximumSessions(10).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry()).expiredUrl("/login");
        		
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth
	      .inMemoryAuthentication()
	      .withUser("capsys")
	        .password("C@p8y8")
	        .roles("SYSADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }   
}
