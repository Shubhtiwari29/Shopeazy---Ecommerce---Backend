package com.shopeazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopeazy.model.Cart;
import com.shopeazy.model.CartItem;
import com.shopeazy.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci From CartItem ci WHere ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExisit(@Param("cart") Cart cart, @Param("product") Product product,
			@Param("size") String size,@Param("userId")Long userId);
}
