package com.user.wongi5.controller;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.user.wongi5.dao.CartDao;
import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.model.CartItems;
import com.user.wongi5.model.Item;
import com.user.wongi5.service.CartService;

@Controller
public class CartController {

	NumberFormat formatter = new DecimalFormat("00.00"); 
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	ItemDao itemDao;
	
	/*
	 * @GetMapping("/items") public ModelAndView showCartView(HttpServletRequest
	 * req) { ModelAndView mv=new ModelAndView("/items"); List<CartItems>
	 * itemList=null; itemList=cartDao.showCart(req);
	 * System.out.println("Entering cookies Items page and item count is : "
	 * +itemList.size()); //List<String> imageList =getImageList(itemList);
	 * //mv.addObject("imageList",imageList); mv.addObject("itemList",itemList);
	 * return mv; }
	 */
	
	@GetMapping("/showcart")
	public ModelAndView cartView(HttpServletRequest req) {
		ModelAndView mv=new ModelAndView("user/show-cart");
		List<CartItems> cartList=null;
		CartService cs=new CartService();
		cartList=cartDao.showCart(req);
		if(cartList!=null) {
		System.out.println("Entering User Items page and item count is : "+cartList.size());
		//List<String> imageList =getImageList(itemList);
		//mv.addObject("imageList",imageList);
		}
		//System.out.println("count ------------------- "+cartList.size());
		mv.addObject("cartList",cartList);
		mv.addObject("totalItemPrice",formatter.format(cs.getItemTotalPrice(cartList)));
		mv.addObject("totalTax",formatter.format(cs.getTax(cartList)));
		mv.addObject("totalPrice",formatter.format(cs.getTotalPrice(cartList)));
		return mv;	    
	}
	
	public List<String> getImageList(List<Item> itemList)
	{
		List<String> imageList = new ArrayList();
		for(Item i:itemList)
		{
			byte[] encodeBase64 =   Base64.getEncoder().encode(i.getItemImage());  //encodeBase64(i.getItemImage());
	         String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				 imageList.add(base64Encoded);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}	        
	         System.out.println("entering........il");
		}
		return imageList;
	}
	
	//@RequestParam("itemImage") MultipartFile itemImage,@RequestParam("itemId") int itemId,@RequestParam("itemName") String itemName,@RequestParam("itemQty") int qty,
	//@RequestParam("itemPrice") double itemPrice
	@RequestMapping("/showcart")
	public ModelAndView addItemToCart(HttpServletRequest req,HttpServletResponse res)
	{	

		ModelAndView mv=cartView(req);
		System.out.println("getting carttttt list...................");
		//cartDao.addCart(req, res, getItem(itemId,itemName,qty,itemPrice));
		return mv;
	}
	
	public CartItems getItem(int itemId,String itemName,int qty,double itemPrice)
	{	
		CartItems ci=new CartItems();
		ci.setItemId(itemId);
		ci.setItemName(itemName);
		ci.setItemPrice(itemPrice);
		ci.setQty(qty);
		ci.setItemTotalPrice(qty*itemPrice);		
		return ci;
		
	}
	
	@RequestMapping("/removeFromCart")//@RequestParam("itemImage") MultipartFile itemImage
	public ModelAndView removeFromCart(HttpServletRequest req,HttpServletResponse res,@RequestParam("itemId") int itemId)
	{	
		ModelAndView mv=cartView(req);
		System.out.println("removing item from carttttt...................");
		cartDao.removeCart(res, itemId);
		return mv;
	}

	@RequestMapping("/placeOrder")
	public ModelAndView purchaseItems(HttpSession session,HttpServletRequest req,HttpServletResponse res)
	{
		ModelAndView mv=cartView(req);
		List<CartItems> cartList=null;
		CartService cs=new CartService();
		cartList=cartDao.showCart(req);
		cartDao.orderItems(session,cartList);
		return mv;		
	}

}
