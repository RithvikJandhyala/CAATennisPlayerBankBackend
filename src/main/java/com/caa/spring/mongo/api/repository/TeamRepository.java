package com.caa.spring.mongo.api.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.caa.spring.mongo.api.model.Team;
public interface TeamRepository extends MongoRepository<Team, String>{
	List<Team> findTeamStandingByDivisionAndName(String division, String name);	
	List<Team> findTeamStandingByDivision(String division);	
}
