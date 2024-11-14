package com.shopeazy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopeazy.exception.CartItemException;
import com.shopeazy.exception.UserException;
import com.shopeazy.model.Cart;
import com.shopeazy.model.CartItem;
import com.shopeazy.model.Product;
import com.shopeazy.model.User;
import com.shopeazy.repository.CartItemRepository;
import com.shopeazy.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private CartRepository cartRepository;

	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

		CartItem createdItem = cartItemRepository.save(cartItem);
		return createdItem;
	}

	@Override
	public CartItem updateCartItem(Long userid, Long id, CartItem cartItem) throws CartItemException, UserException {

		CartItem item = findCartItemById(id);
		User user = userService.findUserById(userid);

		if (user.getId().equals(userid)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity() * item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExisist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemRepository.isCartItemExisit(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem = findCartItemById(cartItemId);

		User user = userService.findUserById(cartItem.getUserId());

		User requestUser = userService.findUserById(userId); // HERE requestUser IS BASICALLY A LOGEDIN USER

		if (user.getId().equals(requestUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		} else {
			throw new UserException("You CANNNOT REMOVE ANOTHER USER's CART ITEM...!!");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {

		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("Cart Item not Founf with Cart Item ID : " + cartItemId);
	}

}
