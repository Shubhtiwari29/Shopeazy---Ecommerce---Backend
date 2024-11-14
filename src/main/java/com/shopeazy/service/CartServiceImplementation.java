package com.shopeazy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Cart;
import com.shopeazy.model.CartItem;
import com.shopeazy.model.Product;
import com.shopeazy.model.User;
import com.shopeazy.repository.CartRepository;
import com.shopeazy.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	private ProductService productService;

	@Override
	public Cart createCart(User user) {
		Cart newCart = new Cart();
		newCart.setUser(user);
		cartRepository.save(newCart);
		return newCart;
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest request) throws ProductException {

		Cart cart = cartRepository.findUserCart(userId);
		Product product = productService.findProductById(request.getProductId());

		CartItem isPresent = cartItemService.isCartItemExisist(cart, product, request.getSize(), userId);

		if (isPresent == null) {
			CartItem newCartItem = new CartItem();
			newCartItem.setProduct(product);
			newCartItem.setCart(cart);
			newCartItem.setQuantity(request.getQuantity());
			newCartItem.setUserId(userId);

			int newPrice = request.getQuantity() * product.getDiscountedPrice();
			newCartItem.setPrice(newPrice);
			newCartItem.setSize(request.getSize());

			CartItem createdCartItem = cartItemService.createCartItem(newCartItem);
			cart.getCartItems().add(createdCartItem);
		}

		return "Item Added to the Cart..!!";
	}

	@Override
	public Cart findUserCart(Long userId) {

		Cart cart = cartRepository.findUserCart(userId);

		int totalPrice = 0;
		int totalDiscountedPrice = 0;
		int totalItem = 0;

		for (CartItem cartItem : cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem + cartItem.getQuantity();
		}

		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice - totalDiscountedPrice);

		return cartRepository.save(cart);
	}

}
