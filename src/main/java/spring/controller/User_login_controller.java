package spring.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import spring.model.User_login;
import spring.service.User_login_service;

@RestController
public class User_login_controller {
	@Autowired
	private User_login_service userService;
	
	@PostMapping("/usersave")
	public String saveStudent(@RequestBody User_login user)
	{
		User_login em=new User_login();
		BeanUtils.copyProperties(user, em);
		User_login em1=userService.saveUser(em);
		String message=null;
		if(em1!=null)
		{
			message="User saved sucessfully in Database";
		}
		else
		{
			message="User Not saved sucessfully in Database";
		}
		return message;
		
	}

}
