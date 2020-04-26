package com.user.wongi5.controller;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.user.wongi5.dao.CartDao;
import com.user.wongi5.dao.ItemDao;
import com.user.wongi5.model.CartItems;
import com.user.wongi5.model.Item;

@Controller
//@SessionAttributes
public class ItemController {
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	CartDao cartDao;
	
	@Autowired
	LoginController lc;
	
	//HttpRequest req
	@GetMapping("/admin/items")
	public ModelAndView itemViewAdmin() {
		ModelAndView mv=new ModelAndView("/admin/items");
		
		List<Item> itemList=null;
		itemList=itemDao.getItems();
		System.out.println("Entering admin Items page and item count is : "+itemList.size());
		List<String> imageList =getImageList(itemList);
		mv.addObject("imageList",imageList);
		mv.addObject("itemList",itemList);		
		return mv;	    
	}
	
	@GetMapping("/user/items")
	public ModelAndView itemViewUser() {
		ModelAndView mv=new ModelAndView("/user/items");
		List<Item> itemList=null;
		itemList=itemDao.getItems();

		System.out.println("Entering User Items page and item count is : "+itemList.size());
		List<String> imageList =getImageList(itemList);
		mv.addObject("imageList",imageList);
		mv.addObject("itemList",itemList);		
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
	
	
	/**
	 * Method to show the initial HTML form
	 * 
	 * @return
	 */
	
	@RequestMapping("/saveItem")
	public ModelAndView addItems(@RequestParam("itemName") String itemName,@RequestParam("itemType") String  itemType,@RequestParam("itemImage") MultipartFile itemImage,@RequestParam("itemCount") int itemCount,
			@RequestParam("itemPrice") double itemPrice,@RequestParam("itemDesc") String itemDesc)
	
	{
		ModelAndView mv=new ModelAndView("/admin/items");
		try {
			Item item=addData(0, itemName,itemType, itemCount, itemPrice, itemImage, itemDesc);	
			boolean status=itemDao.addItem(item);
			System.out.println("Save Item---- Status "+status);
			mv=itemViewAdmin();
			if(status==true) {
				mv.addObject("message","New Item Added..!!");
				}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/updateItem")
	public ModelAndView updateItem(@RequestParam("itemId") int itemId,@RequestParam("itemName") String itemName,@RequestParam("itemType") String  itemType,@RequestParam("itemImage") MultipartFile itemImage,@RequestParam("itemCount") int itemCount,
			@RequestParam("itemPrice") double itemPrice,@RequestParam("itemDesc") String itemDesc)
	{
		ModelAndView mv=new ModelAndView("/admin/items");
		try {
			Item item=addData(itemId, itemName,itemType, itemCount, itemPrice, itemImage, itemDesc);
			System.out.println("Image ------------- : "+item.getItemImage());
			boolean status=itemDao.updateItem(item);
			mv=itemViewAdmin();
			if(status==true) {
			mv.addObject("message","Item Updated..!!");
			}
			System.out.println("Status "+status);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	@RequestMapping("/admin/filterItems")
	public ModelAndView filterItems(@RequestParam("filterType") String filterType,@RequestParam("itemValue") String itemValue)
	{
		ModelAndView mv=new ModelAndView("/admin/items");
		try {
			System.out.println("Filter type is : "+filterType);
			if(itemValue.isEmpty())
			{
				mv=itemViewAdmin();
			}else {
			List itemList=(List) itemDao.getItemsBy(filterType, itemValue);
			if(itemList!=null)
			{
				List<String> imageList =getImageList(itemList);
				mv.addObject("imageList",imageList);
				mv.addObject("itemList",itemList);
			}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/filterItems")
	public ModelAndView filterItemsForUser(HttpSession session,@RequestParam("filterType") String filterType,@RequestParam("itemValue") String itemValue)
	{
		ModelAndView mv=new ModelAndView("/user/items");
		//String user_type = (String) session.getAttribute("user_type");
		String user_type = lc.getUserType();
		System.out.println("user_type ------- "+user_type);
		if(user_type!=null && user_type.equals("admin"))
		{
			System.out.println("Entering admin..................");
			mv=new ModelAndView("/admin/items");
		}
		
		try {
			System.out.println("Filter type is : "+filterType);
			if(itemValue.isEmpty() && user_type.equals("admin"))
			{
				mv=itemViewAdmin();
			}else if(itemValue.isEmpty() && !user_type.equals("admin"))
			{
				mv=itemViewUser();
			}else {
			List itemList=(List) itemDao.getItemsBy(filterType, itemValue);
			if(itemList!=null)
			{
				List<String> imageList =getImageList(itemList);
				mv.addObject("imageList",imageList);
				mv.addObject("itemList",itemList);
			}
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	@RequestMapping("/admin/deleteItem")
	public ModelAndView deleteItems(@RequestParam("itemId") int id)
	{
		ModelAndView mv=new ModelAndView("/admin/items");
		try {
			
			boolean status=itemDao.deleteItem(id);
			System.out.println("Deleted item : Status "+status);
			mv=itemViewAdmin();
			if(status==true) {
				mv.addObject("message","Item Deleted..!!");
				}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	/**
	 * addData method load data and return Item Object.
	 * @param itemId
	 * @param itemName
	 * @param itemType
	 * @param itemCount
	 * @param itemPrice
	 * @param itemImage
	 * @param itemDesc
	 * @return
	 */
	public Item addData(int itemId,String itemName,String itemType, int itemCount,double itemPrice, MultipartFile itemImage, String itemDesc)
	{
		Item item=new Item();
		try {			
		item.setItemId(itemId);
		item.setItemName(itemName);
		item.setItemType(itemType);
		item.setItemCount(itemCount);
		if(itemImage.getSize()==0)
		{

			item.setItemImage(null);
			System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh.............................");
		}else {

			item.setItemImage(itemImage.getBytes());
		}
		item.setItemPrice(itemPrice);
		item.setItemDesc(itemDesc);	
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return item;
	}
	
	@RequestMapping("/addItemToCart")//@RequestParam("itemImage") MultipartFile itemImage
	public ModelAndView addItemToCart(HttpServletRequest req,HttpServletResponse res,@RequestParam("itemId") int itemId,@RequestParam("itemName") String itemName,@RequestParam("itemQty") int qty,
			@RequestParam("itemPrice") double itemPrice)
	{	

		ModelAndView mv=itemViewUser();
		List<Item> itemList=itemDao.getItemsBy("itemId", itemId+"");
		if(itemList!=null && itemList.size()>0)
		{
			if(itemList.get(0).getItemCount()>qty)
			{
			cartDao.addCart(req, res, getItem(itemId,itemName,qty,itemPrice));
			mv.addObject("message","Item added to cart");
			}else {
				mv.addObject("message","The Item Qty you requested is more than we have!!");
			}
		}
		System.out.println("entering carttttt...................");
		
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
	

	/*
	 * @RequestMapping("admin/logout") public ModelAndView logout(HttpSession
	 * session) { ModelAndView mv=new ModelAndView("login"); try {
	 * System.out.println("logout --------------"
	 * +session.getAttribute("user_email"));
	 * System.out.println("logout --------------"
	 * +session.getAttribute("user_type"));
	 * 
	 * if(session.getAttribute("user_email") !=null &&
	 * session.getAttribute("user_type") !=null) {
	 * session.removeAttribute("user_email"); session.removeAttribute("user_type");
	 * ses=null; }
	 * 
	 * System.out.println("logout --------------"
	 * +session.getAttribute("user_email")); }catch (Exception e) {
	 * System.out.println("logout------------------ "+e.getMessage()); }
	 * //login(session); return mv; }
	 */

}
