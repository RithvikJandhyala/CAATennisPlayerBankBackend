package com.caa.spring.mongo.api.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.caa.spring.mongo.api.model.Match;

public interface MatchRepository extends MongoRepository<Match, Long>{
	List<Match> findMatchByDivision(String division);	
	
}
