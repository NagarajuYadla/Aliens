package spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.json.simple.JSONObject;

import lombok.Data;

@Entity
@Table(name="candidate_details_json_Format")
@Data
public class CandidateJSONDetails {
	@Id
	@GeneratedValue(generator="candidate_seq",strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name="candidate_seq",sequenceName="candidate_sequence",initialValue=1,allocationSize=1)
	int id;
	String details;
}
