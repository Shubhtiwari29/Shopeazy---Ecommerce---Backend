package com.shopeazy.service;

import com.shopeazy.exception.CartItemException;
import com.shopeazy.exception.UserException;
import com.shopeazy.model.Cart;
import com.shopeazy.model.CartItem;
import com.shopeazy.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userid,Long id, CartItem cartItem) throws  CartItemException,UserException;

	public CartItem isCartItemExisist(Cart cart, Product product,String size,Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
