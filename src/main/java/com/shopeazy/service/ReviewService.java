package com.shopeazy.service;

import java.util.List;

import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Review;
import com.shopeazy.model.User;
import com.shopeazy.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest request, User user) throws ProductException;

	public List<Review> getAllReview(Long productId);
}
