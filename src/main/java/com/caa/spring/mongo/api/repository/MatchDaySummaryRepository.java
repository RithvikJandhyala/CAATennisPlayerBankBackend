package com.caa.spring.mongo.api.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.caa.spring.mongo.api.model.Match;
import com.caa.spring.mongo.api.model.MatchDaySummary;
public interface MatchDaySummaryRepository extends MongoRepository<MatchDaySummary, Integer>{

	
}
