package com.shopeazy.service;

import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Cart;
import com.shopeazy.model.User;
import com.shopeazy.request.AddItemRequest;

public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userid,AddItemRequest request) throws ProductException;
	
	public Cart findUserCart(Long userId);
	
}
