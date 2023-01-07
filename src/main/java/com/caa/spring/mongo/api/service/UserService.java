package com.caa.spring.mongo.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.caa.spring.mongo.api.model.User;
import com.caa.spring.mongo.api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public Optional<User> authenticateUser(User user){
		Optional <User> myUser = repository.findById(user.getUsername());
		if(myUser.isPresent() ){
			
			if(myUser.get().getPassword().equals(user.getPassword())) {
				System.out.println("Success");
				return myUser;
			}
			else {
				System.out.println("Invalid Password");
				return null;
			}
		}
		else {
			System.out.println("user doesn't exist");
			return null;
		}
		
	}
	
	public String saveUser(User user) {
		repository.save(user);
		return "Created User with ID:" + user.getUsername();
	}
	
}
