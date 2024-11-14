package com.shopeazy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingRequest {
	@NotNull(message = "Product ID cannot be null")
	private Long productId;
	@Min(value = 0, message = "Rating must be at least 0")
	@Max(value = 5, message = "Rating must be at most 5")
	private double rating;

	public RatingRequest() {
		// TODO Auto-generated constructor stub
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
