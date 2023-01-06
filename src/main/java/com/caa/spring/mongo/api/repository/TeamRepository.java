package com.caa.spring.mongo.api.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.MatchDaySummary;
import com.caa.spring.mongo.api.model.Team;
public interface TeamRepository extends MongoRepository<Team, String>{

	
}
