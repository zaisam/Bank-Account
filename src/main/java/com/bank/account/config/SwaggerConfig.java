package com.bank.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebSecurityConfigurerAdapter {


	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.httpBasic().disable()
		.formLogin().disable();
		
		http.authorizeRequests().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
				"/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll();
		

	}
}
