package com.jip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jip.model.AuthenticationRequest;
import com.jip.service.MyUserDetailsService;
import com.jip.util.JwtUtil;

@RestController
public class HelloController {

	@Autowired
	Environment env;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	MyUserDetailsService myUserDetailsSerice;
	
	@Autowired
	AuthenticationManager auth;
	
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello "+env.getProperty("welcome.message");
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/alluser")
	public String alluser() {
		return "All User!!!!!";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/alladmin")
	public String alladmin() {
		return "All Admin !!!";
	}
	
	@PostMapping("/authenticate")
	public String authenticate(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			auth.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid Credentials !!");
		}
		
		UserDetails userDetails = myUserDetailsSerice.loadUserByUsername(request.getUsername());
		
		String jwt = jwtUtil.generateToken(userDetails);
		
		return jwt;
	}
}
