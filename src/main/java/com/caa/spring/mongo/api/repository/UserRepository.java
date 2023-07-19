package com.caa.spring.mongo.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.caa.spring.mongo.api.model.User;
public interface UserRepository extends MongoRepository<User, String>{

	List<User> findUserByRole(String role);
	
}
