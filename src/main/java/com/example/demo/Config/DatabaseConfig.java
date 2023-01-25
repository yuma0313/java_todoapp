package com.example.demo.Config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {
  private final DataSource dataSource;

  @Bean
  public UserDetailsManager userDetailsManager() {
    return new JdbcUserDetailsManager(dataSource);
  }

//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//  }
}
