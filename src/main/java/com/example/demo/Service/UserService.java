package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserDao;

@Service
public class UserService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserDao dao;
	
	public List<User> selectMany() {
		return dao.selectMany();
	}
	
	public void create(User user) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        jdbcTemplate.update("insert into user (email,username,password)" + "values(?,?,?)",
        	user.getEmail(),
        	user.getUsername(),
        	encodedPassword);
	}
}
