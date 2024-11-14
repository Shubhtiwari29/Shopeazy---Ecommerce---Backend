package com.shopeazy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Product;
import com.shopeazy.model.Review;
import com.shopeazy.model.User;
import com.shopeazy.repository.ProductRepository;
import com.shopeazy.repository.ReviewRepository;
import com.shopeazy.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductRepository prodcutRepository;
	@Autowired
	private ProductService productService;

	@Override
	public Review createReview(ReviewRequest request, User user) throws ProductException {
		
		Product product = productService.findProductById(request.getProductId());
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setReview(request.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview(Long productId) {
		
		return reviewRepository.getAllProductsReview(productId);
	}

}
