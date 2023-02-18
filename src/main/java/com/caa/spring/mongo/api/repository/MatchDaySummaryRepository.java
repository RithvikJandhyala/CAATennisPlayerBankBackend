package com.caa.spring.mongo.api.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.caa.spring.mongo.api.model.MatchDaySummary;

public interface MatchDaySummaryRepository extends MongoRepository<MatchDaySummary, Integer>{
	List<MatchDaySummary> findMatchDaySummaryByDivision(String division);	
	
}
