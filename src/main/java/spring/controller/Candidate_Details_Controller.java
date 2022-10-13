package spring.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.model.CandidateJSONDetails;
import spring.model.User_login;
import spring.service.Candidate_Details_Service;

@RestController
public class Candidate_Details_Controller {
	@Autowired
	private Candidate_Details_Service candidate_service;
	
	@GetMapping("parseAndMatch")
	public String parseAndMatch() {
		candidate_service.parseAndMatch();
		return "done";
	}
	
	
	//API  :  http://localhost:8080/app/parseAndMatch
	
	@GetMapping("/search")
	public ResponseEntity<List<CandidateJSONDetails>> searchDetalis(@RequestParam("query") String query)
	{
		return ResponseEntity.ok(candidate_service.searchDetails(query));
		
	}
	
	
	//API  :  http://localhost:8080/app/search?query=skills
	

}
