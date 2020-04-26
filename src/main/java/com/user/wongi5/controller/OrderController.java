package com.user.wongi5.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.user.wongi5.dao.CartDao;
import com.user.wongi5.dao.OrderDao;
import com.user.wongi5.model.Purchase_History;

@Controller
public class OrderController {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	LoginController lc;
	
	@GetMapping("/showOrders")
	public ModelAndView showOrders(HttpSession session,@RequestParam String status)
	{
		ModelAndView mv=lc.login(session);
		System.out.println("OrderController : before entering in showOrders if ststm");
		if(lc.getUserType()!=null && !lc.getUserType().equals(""))
		{
			System.out.println("OrderController : entering in showOrders if ststm");
			if(lc.getUserType().equalsIgnoreCase("admin"))
			{
				//if(status.isEmpty())
					status="Pending";
				mv=showAdminOrders(status);
			}else {
				mv=showCustomerOrders();
			}
		}
		
		return mv;
	}

	
	
	@RequestMapping("/showOrders")
	public ModelAndView show_Orders(HttpSession session,@RequestParam String status)
	{
		ModelAndView mv=lc.login(session);
		System.out.println("OrderController : before entering in showOrders if ststm");
		if(lc.getUserType()!=null && !lc.getUserType().equals(""))
		{
			System.out.println("OrderController : entering in showOrders if ststm");
			if(lc.getUserType().equalsIgnoreCase("admin"))
			{
				//if(status.isEmpty())
					status="Pending";
				mv=showAdminOrders(status);
			}else {
				mv=showCustomerOrders();
			}
		}
		
		return mv;
	}
	
	public ModelAndView showCustomerOrders()
	{
		ModelAndView mv=new ModelAndView("user/show-orders");
		if(lc.getUserEmail()!=null)
		{
		List<Purchase_History> 	purchase_history_list=orderDao.getPurchase_HistoryByEmail(lc.getUserEmail());
		mv.addObject("purchase_history_list",purchase_history_list );
		mv.addObject("order_details", orderDao.getOrder_details());
		}
		return mv;
	}
	
	public ModelAndView showAdminOrders(String status)
	{
		ModelAndView mv=new ModelAndView("admin/show-orders");
		if(lc.getUserEmail()!=null)
		{
		List<Purchase_History> 	purchase_history_list=orderDao.getPurchase_HistoryByStatus("Pending");
		mv.addObject("purchase_history_list",purchase_history_list );
		mv.addObject("order_details", orderDao.getOrder_details());
		}
		return mv;
	}

}
