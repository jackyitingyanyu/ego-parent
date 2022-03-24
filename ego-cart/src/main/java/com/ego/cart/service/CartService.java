package com.ego.cart.service;

import javax.servlet.http.HttpServletRequest;

public interface CartService {
	
	void addCart(long id,int num,HttpServletRequest request);
	
}
