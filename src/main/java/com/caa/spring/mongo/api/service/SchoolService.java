package com.caa.spring.mongo.api.service;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caa.spring.mongo.api.model.School;
import com.caa.spring.mongo.api.repository.SchoolRepository;
import com.caa.spring.mongo.api.repository.TeamRepository;


@Service
public class SchoolService {
	@Autowired
	private SchoolRepository repository;
	@Autowired
	private TeamRepository teamRepository;
	public List<School> getSchools(){
		return repository.findAll();
	}
	
	public String deleteSchool(int id) {
		repository.deleteById(id);
		return "School deleted with  ";
	}
	public int addSchool(String name, MultipartFile file) throws IOException { 
		System.out.println("Entered addSchool");
		System.out.println("Name" + name);
		System.out.println("Image File" + file);
		int id;
		List <School> schools = getSchools();
		if(schools.size() == 0) {
			id = 1000;
		}
		else {
			School maxIDSchool = schools.stream()
	                .max(Comparator.comparingInt(School::getID))
	                .get();
			int maxID = maxIDSchool.getID();
			id = maxID +1000;
		}
		
        School school = new School(id,name); 
        school.setImage(file.getBytes());
          //new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        school = repository.insert(school); 
        return school.getID(); 
    }

    public School getSchool(int id) { 
        return repository.findById(id).get(); 
    }
    public String clear() {
		//repository.deleteAll();
		teamRepository.deleteAll();
		return "ALL SCHOOLS CLEARED";
	}
}
