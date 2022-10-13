package spring.service.serviceImpl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.criterion.CriteriaQuery;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Business.PDFReading;
import spring.model.CandidateJSONDetails;
import spring.repositary.Candidate_Details_Repo;
import spring.service.Candidate_Details_Service;

@Service
public class Candidate_Details_ServiceImplementataion implements Candidate_Details_Service {
//	@Autowired
//	private Candidate_Details_Repo candidate_repo;

	@Autowired
	private Candidate_Details_Repo candidate_repo;
	

	@Override
	public void parseAndMatch() {
		PDFReading pdr = new PDFReading();
		List<String> content = pdr.readPDF();
		System.out.println("reading done");
		JSONObject details = pdr.fieldExtraction(content.toArray());
		System.out.println("matching done");
		CandidateJSONDetails jsonDetails = new CandidateJSONDetails();
		jsonDetails.setDetails(details.toString());
		//jsonDetails.setId(id);
		System.out.println(jsonDetails);
		candidate_repo.save(jsonDetails);
	}


	
	@Override
	public List<CandidateJSONDetails> searchDetails(String query) {
		// TODO Auto-generated method stub
		List<CandidateJSONDetails> detailsList= candidate_repo.searchDetails(query);
		return detailsList;
	}

}
