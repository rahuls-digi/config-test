package com.liverpool.configuration.api.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liverpool.configuration.api.bean.User;
import com.liverpool.configuration.api.bean.Users;
import com.liverpool.configuration.api.repository.UserRepository;
import com.liverpool.configuration.api.service.UserService;

@Service
public class UserServiceIpml implements UserService {

	@Autowired
	private UserRepository userRepo;

	
	@Override
	public Users getUsers() {
		List<User> userList = userRepo.findAll();
		Users u = new Users();
		u.setUsers(userList);
	
		return u;

	}


	@Override
	public String addUser(User user) {
		if(isValidPassword(user.getPassword())) {
			userRepo.insert(user);
			return "sucessfully registered "+ user.getUserName();
			
		}
		else
		{
			return "invalid password";
		}
		
	}


	@Override
	public User login(String userName, String password) {
	
		if(isValidPassword(password)) {
			return userRepo.findByUserNameAndPassword(userName, password);
		}
		else
		{
			return null;
		}
	}

	private boolean isValidPassword(String password) {
		String regex ="^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		Pattern pattern= Pattern.compile(regex);
		Matcher matcher= pattern.matcher(password);
	
		return matcher.matches();
		
	}
}
