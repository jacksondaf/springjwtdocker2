package com.jip.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Map<String, User> userMap = userWithRoles();
		User user = userMap.get(username);
		System.out.println("User >>>>>>>>"+user.getUsername()+"  "+user.getAuthorities());
		
		return user;
	}

	
	@PostConstruct
	public Map<String, User> userWithRoles(){
		
		Map<String, User> userRolesMap = new HashMap<>();
		
		User user1 = new User("test", "test", new ArrayList(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		User user2 = new User("admin", "admin", new ArrayList(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"))));
		
		userRolesMap.put("test", user1);
		userRolesMap.put("admin", user2);
		
		return userRolesMap;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
