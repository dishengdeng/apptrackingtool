package portal;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




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
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/bootstrap/**",
                    		"/dist/**",
                    		"/datatables/**",
                    		"/metisMenu/**",
                    		"/jquery/**",
                    		"/font-awesome/**",
                    		"/datatables-plugins/**",
                    		"/datatables-responsive/**",
                    		"/favicon_a.ico",
                    		"/registration").permitAll()
                    .antMatchers("/users").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/updateUser").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/addUser").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/deleteUser").hasAnyRole("ADMIN","SYSADMIN")
                    .antMatchers("/roles").hasRole("SYSADMIN")
                    .antMatchers("/updateRole").hasRole("SYSADMIN")
                    .antMatchers("/addRole").hasRole("SYSADMIN")
                    .antMatchers("/deleteRole").hasRole("SYSADMIN")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
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
