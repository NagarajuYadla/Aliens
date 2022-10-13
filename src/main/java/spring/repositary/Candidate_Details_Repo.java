package spring.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import spring.model.CandidateJSONDetails;
@Repository
public interface Candidate_Details_Repo extends JpaRepository<CandidateJSONDetails, Integer>{
	
	
	@Query(value="SELECT * FROM public.candidate_details_json_format WHERE details ILIKE (%?1%)",nativeQuery = true)
	List<CandidateJSONDetails> searchDetails(String query);
	
	
}
