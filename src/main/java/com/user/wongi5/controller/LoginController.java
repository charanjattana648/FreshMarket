package com.user.wongi5.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.Session;
import com.user.wongi5.dao.AuthDao;
import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.model.Item;
import com.user.wongi5.model.LoginInfo;
import com.user.wongi5.model.User;

@Controller
@SessionAttributes("user_email")
public class LoginController {
	
	private HttpSession ses=null;
	
	@Autowired
	AuthDao authDao;
	
	@Autowired
	ItemDao itemDao;
	
	
	/**
	 * Create new signUpForm object for empty from
	 * 
	 * @return
	 */
	@ModelAttribute("loginInfo")
	public LoginInfo loginForm() {
		return new LoginInfo();
	}

	/**
	 * Method to show the initial HTML form
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(HttpSession session) {	
	    ModelAndView mv=new ModelAndView("login");	
		try {
		ses=session;
		String user_email=null;
		String user_type=null;
		if(session !=null && session.getAttribute("user_email") !=null)
		{
	    user_email = (String) session.getAttribute("user_email");
	    user_type = (String) session.getAttribute("user_type");
		}
		System.out.println("login page................."+user_email);		
	    if(session !=null && user_email != null && user_type!=null) {
	    	if(user_type.equals("admin"))
	    	{
	    		//return "admin/items";
	    		return itemViewAdmin();
	    	}else {
	    	//return "user/items";}
	    		return itemViewUser();}
	    }
		}catch (Exception e) {
			System.out.println("login ------------- "+e.getMessage());
		}
	    return mv;
	}
	
	public String getUserType()
	{
		if(ses!=null && ses.getAttribute("user_type")!=null)
		{
			return (String) ses.getAttribute("user_type");
		}
		return null;
	}
	
	public String getUserEmail()
	{
		if(ses!=null && ses.getAttribute("user_email")!=null)
		{
			return (String) ses.getAttribute("user_email");
		}
		return null;
	}
	
	public ModelAndView itemViewAdmin() {
		ModelAndView mv=new ModelAndView("/admin/items");
		List<Item> itemList=null;
		itemList=itemDao.getItems();
		System.out.println("Entering admin Items page and item count is : "+itemList.size());
		ItemController ic=new ItemController();
		List<String> imageList =ic.getImageList(itemList);
		mv.addObject("imageList",imageList);
		mv.addObject("itemList",itemList);		
		return mv;	    
	}
	
	public ModelAndView itemViewUser() {
		ModelAndView mv=new ModelAndView("/user/items");
		List<Item> itemList=null;
		itemList=itemDao.getItems();
		System.out.println("Entering User Items page and item count is : "+itemList.size());
		ItemController ic=new ItemController();
		List<String> imageList =ic.getImageList(itemList);
		mv.addObject("imageList",imageList);
		mv.addObject("itemList",itemList);		
		return mv;	    
	}

	@RequestMapping("/login") 
	public ModelAndView login(HttpSession session,@ModelAttribute("loginInfo") LoginInfo loginInfo, Model model){
		ModelAndView mv=new ModelAndView("login");
//		ItemController ic=new ItemController();
		try {
		boolean status=authDao.checkUser(loginInfo.getUserType(), loginInfo.getEmail(), loginInfo.getPassword());
		model.addAttribute("message", "Login Fail");
		System.out.println("status : "+status);
		if(status==true) {
			
			model.addAttribute("user_email", loginInfo.getEmail());
			model.addAttribute("user_type", loginInfo.getUserType());
			model.addAttribute("message", "Login Successful");
			session.setAttribute("user_email", loginInfo.getEmail());
			session.setAttribute("user_type", loginInfo.getUserType());
			if(loginInfo.getUserType().equals("admin"))
			{
			//return "admin/items";
				return itemViewAdmin();
			}
			//return "user/items";
			return itemViewUser();
		}}catch (Exception e) {
		System.out.println("Login Request mapping ----------------- "+e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session)
	{
		ModelAndView mv=new ModelAndView("login");
		try {
		System.out.println("logout --------------" +session.getAttribute("user_email"));
		System.out.println("logout --------------" +session.getAttribute("user_type"));
		
		if(session.getAttribute("user_email") !=null && session.getAttribute("user_type") !=null)
		{
			session.setAttribute("user_email", "");
			session.setAttribute("user_type", "");
		//session.removeAttribute("user_email");
		//session.removeAttribute("user_type");
		ses=null;
		}

		System.out.println("logout --------------" +session.getAttribute("user_email"));
		}catch (Exception e) {
			System.out.println("logout------------------ "+e.getMessage());
		}
		//login(session);
		return mv;		
	}
	
}
