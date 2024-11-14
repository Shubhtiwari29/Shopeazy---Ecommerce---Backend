package com.shopeazy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Product;
import com.shopeazy.request.CreateProductRequest;
import com.shopeazy.response.ApiResponse;
import com.shopeazy.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request) {
		Product product = productService.createProduct(request);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}

	@DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProductByProductId(@PathVariable Long productId) throws ProductException {
		productService.deleteProduct(productId);
		ApiResponse response = new ApiResponse();
		response.setMessage("Product With Product ID : " + productId + "Has Been DELETED!!");
		response.setStatus(true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProducts() {
		List<Product> products = productService.findAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PutMapping("/{productId}/update")
	public ResponseEntity<Product> updateProduct(@RequestBody Product productRequest, @PathVariable Long productId)
			throws ProductException {
		Product updateProduct = productService.updateProduct(productId, productRequest);
		return new ResponseEntity<Product>(updateProduct, HttpStatus.CREATED);
	}

	@PostMapping("/creates")
	public ResponseEntity<ApiResponse> createMultipleProducts(@RequestBody CreateProductRequest[] request) {
		for (CreateProductRequest product : request) {
			productService.createProduct(product);
		}
		ApiResponse response = new ApiResponse();
		response.setMessage("Product CREATED!!");
		response.setStatus(true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
