package com.shopeazy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopeazy.exception.ProductException;
import com.shopeazy.exception.UserException;
import com.shopeazy.model.Cart;
import com.shopeazy.model.User;
import com.shopeazy.request.AddItemRequest;
import com.shopeazy.response.ApiResponse;
import com.shopeazy.service.CartService;
import com.shopeazy.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

	
	@RestController
	@RequestMapping("/api/cart")
	@Tag(name = "Cart Management", description = "find user cart,add item to the cart")
	public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	@Operation(description = "find cart by user id")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
	}

	@PutMapping("/add")
	@Operation(description = "add to the cart")
	public ResponseEntity<ApiResponse> addItemtoCart(@Valid @RequestBody AddItemRequest request,
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
		User user = userService.findUserProfileByJwt(jwt);
		cartService.addCartItem(user.getId(), request);
		ApiResponse response = new ApiResponse();
		response.setMessage("Item Added to the cart");
		response.setStatus(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
