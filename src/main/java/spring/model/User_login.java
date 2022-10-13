package spring.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_details")


public class User_login {
	@Id
	private String user_name;
	private String password;
	@Override
	public String toString() {
		return "User_login [user_name=" + user_name + ", password=" + password + "]";
	}
	

}
