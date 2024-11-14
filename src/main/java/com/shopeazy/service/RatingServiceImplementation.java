package com.shopeazy.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Product;
import com.shopeazy.model.Rating;
import com.shopeazy.model.User;
import com.shopeazy.repository.RatingRepository;
import com.shopeazy.request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest request, User user) throws ProductException {
		Product product = productService.findProductById(request.getProductId());

		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(request.getRating());
		rating.setCreatedAt(LocalDateTime.now());

		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {

		return ratingRepository.getAllProductsRating(productId);
	}

}
