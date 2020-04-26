package com.user.wongi5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.model.User;

@Controller
public class SignUpController {

	@Autowired
	AuthDao authDao;
	
	
	/**
	 * Create new signUpForm object for empty from
	 * 
	 * @return
	 */
	@ModelAttribute("user")
	public User setSignUpForm() {
		return new User();
	}

	/**
	 * Method to show the initial HTML form
	 * 
	 * @return
	 */
	@GetMapping("/showSignUpForm")
	public String showForm() {
		return "signup-form";
	}

	/**
	 * Save User sign up form
	 * 
	 * @param signUpForm
	 * @param model
	 * @return
	 */
	@PostMapping("/saveSignUpForm")
	public String saveUser(@ModelAttribute("user") User user, Model model) {

		// Implement business logic to save user details into a database
		// .....

		System.out.println("EName : " + user.getEmail());
		System.out.println("Name : " + user.getName());
		System.out.println("Password : " + user.getPassword());
		System.out.println("Type : " + user.getUserType());
		
		//List<User> users = userDao.findAll();

		//System.out.println(users);
		
		boolean status=authDao.addUser(user);

		//model.addAttribute("user", users);
			
		if(status==true)
		{
		model.addAttribute("message", "User SignUp successfully.");
		}else {
			model.addAttribute("message", "User SignUp failed.");
		}
//		model.addAttribute("user", user);

		return "signup-success";
	}
}
