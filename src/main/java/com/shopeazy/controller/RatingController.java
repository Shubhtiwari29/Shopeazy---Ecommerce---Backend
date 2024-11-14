package com.shopeazy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopeazy.config.JwtProvider;
import com.shopeazy.exception.ProductException;
import com.shopeazy.exception.UserException;
import com.shopeazy.model.Rating;
import com.shopeazy.model.User;
import com.shopeazy.request.RatingRequest;
import com.shopeazy.service.RatingService;
import com.shopeazy.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	@Autowired
	private UserService userService;
	@Autowired
	private RatingService ratingService;
	@Autowired
    private JwtProvider jwtProvider;
	
	@PostMapping("/create")
	public ResponseEntity<Rating>createRating(@RequestBody RatingRequest request,
			@RequestHeader("Authorization")String jwt)
			throws UserException,ProductException{
			
		User user = userService.findUserProfileByJwt(jwt);
		Rating rating = ratingService.createRating(request, user);
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>>getProductRating(@PathVariable Long productId,
			@RequestHeader("Authorization")String jwt)throws UserException,ProductException{
		
		if (!jwtProvider.validateToken(jwt)) {
            throw new UserException("Invalid JWT Token");
        }
        userService.findUserProfileByJwt(jwt);
        List<Rating> ratings = ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }	
		
	/*
	 * User user = userService.findUserProfileByJwt(jwt);
	 * 
	 * List<Rating>ratings = ratingService.getProductRating(productId);
	 * 
	 * return new ResponseEntity<>(ratings,HttpStatus.OK);
	 * 
	 * }
	 */
}
