package com.caa.spring.mongo.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
		System.out.println(user);
		return "Created User with Username: " + user.getUsername();
	}
	public List<User> getUsers(){
		//return repository.findAll(Sort.by(Sort.Direction.DESC, "wins"));
		return repository.findUserByRole("Coach");
	}
	public String deleteUser(String username) {
		repository.deleteById(username);
		System.out.print("delete " + username);
		return "Player deleted with  " + username;
	}
	public String clear() {
		repository.deleteAll();
		return "ALL USERS CLEARED";
	}
	
}
