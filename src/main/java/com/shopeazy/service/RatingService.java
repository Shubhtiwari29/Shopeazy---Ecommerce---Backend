package com.shopeazy.service;

import java.util.List;

import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Rating;
import com.shopeazy.model.User;
import com.shopeazy.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest request, User user) throws ProductException;

	public List<Rating> getProductRating(Long productId);
}
