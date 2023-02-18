package com.caa.spring.mongo.api.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.caa.spring.mongo.api.model.Player;
public interface PlayerRepository extends MongoRepository<Player, Integer>{

	List<Player> findBySchool(String school, Sort sort);
	List<Player> findByDivision(String division);
	List<Player> findBySchoolAndDivisionAndPlayerType(String school,String division, String playerType);
}
