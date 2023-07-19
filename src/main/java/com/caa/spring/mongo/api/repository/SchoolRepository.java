package com.caa.spring.mongo.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.caa.spring.mongo.api.model.School;


public interface SchoolRepository extends MongoRepository<School, Integer> {

	School findByName(String name);

}
