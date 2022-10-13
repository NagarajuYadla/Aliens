package spring.service;

import java.util.List;

import spring.model.CandidateJSONDetails;
import spring.model.User_login;

public interface Candidate_Details_Service {
	
	//public Candidate_Details saveCandidate(Candidate_Details candidate);
	//public List<Candidate_Details> getAllCandidates() throws Exception;
	public void parseAndMatch();
	public List<CandidateJSONDetails> searchDetails(String query);
	

}
