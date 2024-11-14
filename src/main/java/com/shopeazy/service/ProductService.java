package com.shopeazy.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Product;
import com.shopeazy.request.CreateProductRequest;

public interface ProductService {
	
	public Product createProduct(CreateProductRequest request);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId, Product reqProduct) throws ProductException;
	
	public Product findProductById(Long productId) throws ProductException;

	List<Product> findProductsByCategory(String categoryName);
	
	public List<Product> findAllProducts();
	
	public Page<Product> getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice
			,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
}
