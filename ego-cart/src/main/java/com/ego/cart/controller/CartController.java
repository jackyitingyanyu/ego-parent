package com.ego.cart.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.cart.service.CartService;

@Controller
public class CartController {
	@Resource
	private CartService cartServiceImpl;
	
	@RequestMapping("cart/add/{id}.html")
	public String addCart(@PathVariable long id,int num,HttpServletRequest request) {
		cartServiceImpl.addCart(id, num, request);
		return "cartSuccess";
	}
	
	/**
	 * 显示购物车
	 * @return
	 */
	@RequestMapping("cart/cart.html")
	public String showCart() {
		
		return "cart";
	}
	
	
}
