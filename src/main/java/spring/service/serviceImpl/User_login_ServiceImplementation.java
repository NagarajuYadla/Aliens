package spring.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.model.User_login;
import spring.repositary.User_login_Repo;
import spring.service.User_login_service;
@Service
public class User_login_ServiceImplementation implements User_login_service {
	@Autowired
	private User_login_Repo userRepo;

	@Override
	public User_login saveUser(User_login user) {
		// TODO Auto-generated method stub
		User_login login=userRepo.save(user);
		return login;
	}

}
