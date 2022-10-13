package spring.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.model.User_login;
@Repository
public interface User_login_Repo extends JpaRepository<User_login,Integer> {

}
