package com.shopeazy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.shopeazy.exception.ProductException;
import com.shopeazy.model.Category;
import com.shopeazy.model.Product;
import com.shopeazy.repository.CategoryRepository;
import com.shopeazy.repository.ProductRepository;
import com.shopeazy.request.CreateProductRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImplementation implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserService userService;

	@Override
	public Product createProduct(CreateProductRequest request) {
		Category topLevelCategory = categoryRepository.findByName(request.getTopLevelCategory());
		if (topLevelCategory == null) {
			topLevelCategory = new Category();
			topLevelCategory.setName(request.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			topLevelCategory = categoryRepository.save(topLevelCategory);
		}
		Category secondLevelCategory = categoryRepository.findByNameAndParent(request.getSecondLevelCategory(),
				topLevelCategory.getName());
		if (secondLevelCategory == null) {
			secondLevelCategory = new Category();
			secondLevelCategory.setName(request.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevelCategory);
			secondLevelCategory.setLevel(2);
			secondLevelCategory = categoryRepository.save(secondLevelCategory);
		}
		Category thirdLevelCategory = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(),
				secondLevelCategory.getName());
		if (thirdLevelCategory == null) {
			thirdLevelCategory = new Category();
			thirdLevelCategory.setName(request.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevelCategory);
			thirdLevelCategory.setLevel(3);
			thirdLevelCategory = categoryRepository.save(thirdLevelCategory);
		}
		Product product = new Product();
		product.setTitle(request.getTitle());
		product.setColor(request.getColor());
		product.setDescription(request.getDescription());
		product.setDiscountedPrice(request.getDiscountedPrice());
		product.setDiscountPercent(request.getDiscountedPercent());
		product.setImageUrl(request.getImageUrl());
		product.setPrice(request.getPrice());
		product.setBrand(request.getBrand());
		product.setSizes(request.getSize());
		product.setQuantity(request.getQuantity());
		product.setCategory(thirdLevelCategory);
		product.setCreatedAt(LocalDateTime.now());
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	@Transactional(rollbackFor = ProductException.class)
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
// Clear related entities or perform necessary cleanup
		product.getSizes().clear(); // Example: Clearing sizes relationship
		product.getRatings().clear();
		product.getReviews().clear();
// Delete product
		productRepository.delete(product);
		return "Product Deleted Successfully..!!";
	}

	@Override
	@Transactional(rollbackFor = ProductException.class)
	public Product updateProduct(Long productId, Product reqProduct) throws ProductException {
		Product product = findProductById(productId);
		if (reqProduct.getQuantity() != 0) {
			product.setQuantity(reqProduct.getQuantity());
		}
		if (reqProduct.getPrice() > 0) {
			product.setPrice(reqProduct.getPrice());
		}
		if (reqProduct.getDiscountPercent() > 0) {
			product.setDiscountPercent(reqProduct.getDiscountPercent());
		}
		if (reqProduct.getDiscountedPrice() > 0) {
			product.setDiscountedPrice(reqProduct.getDiscountedPrice());
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		Optional<Product> optional = productRepository.findById(productId);
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new ProductException("Product Not Found With Product ID : " + productId);
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findProductsByCategory(String categoryName) {
		return productRepository.findByCategoryNameIgnoreCase(categoryName);
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
// Fetch products based on filters
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
// Apply additional filters if necessary
		if (colors != null && !colors.isEmpty()) {
			products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
// Handle stock filtering
		if (stock != null) {
			if (stock.equals("in_stock")) {
				products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
			} else if (stock.equals("out_of_stock")) {
				products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
			}
		}
// Ensure proper pagination considering edge cases
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), products.size());
		List<Product> pageContent = new ArrayList<>();
		if (start <= end) {
			pageContent = products.subList(start, end);
		}
		return new PageImpl<>(pageContent, pageable, products.size());
	}
}
/*
 * HERE'S MY ORIGINAL CODE
 * 
 * @Override public Product createProduct(CreateProductRequest request) {
 * 
 * Category topLevelCategory =
 * categoryRepository.findByName(request.gettopLevelCategoryCategory());
 * if(topLevelCategory==null) { Category topLevelCategoryCategory = new
 * Category();
 * topLevelCategoryCategory.setName(request.gettopLevelCategoryCategory());
 * topLevelCategoryCategory.setLevel(1);
 * 
 * topLevelCategory = categoryRepository.save(topLevelCategoryCategory); }
 * Category secondLevelCategory = categoryRepository.
 * findByNameAndParent(request.getsecondLevelCategoryCategory(),topLevelCategory
 * .getName()); if(secondLevelCategory==null) { Category
 * secondLevelCategoryCategory = new Category();
 * secondLevelCategoryCategory.setName(request.getsecondLevelCategoryCategory())
 * ; secondLevelCategoryCategory.setParentCategory(topLevelCategory);
 * secondLevelCategoryCategory.setLevel(2);
 * 
 * secondLevelCategory = categoryRepository.save(secondLevelCategoryCategory); }
 * Category thirdLevelCategory = categoryRepository.
 * findByNameAndParent(request.getthirdLevelCategoryCategory(),
 * secondLevelCategory.getName()); if(thirdLevelCategory==null) { Category
 * thirdLevelCategoryCategory = new Category();
 * thirdLevelCategoryCategory.setName(request.getthirdLevelCategoryCategory());
 * thirdLevelCategoryCategory.setParentCategory(secondLevelCategory);
 * thirdLevelCategoryCategory.setLevel(3);
 * 
 * secondLevelCategory = categoryRepository.save(thirdLevelCategoryCategory); }
 * Product product = new Product(); product.setTitle(request.getTitle());
 * product.setColor(request.getColor());
 * product.setDescription(request.getDescription());
 * product.setDiscountedPrice(request.getDiscountedPrice());
 * product.setDiscountPercent(request.getDiscountedPercent());
 * product.setImageUrl(request.getImageUrl());
 * product.setPrice(request.getPrice()); product.setBrand(request.getBrand());
 * product.setSizes(request.getSize());
 * product.setQuantity(request.getQuantity());
 * product.setCategory(thirdLevelCategory);
 * product.setCreatedAt(LocalDateTime.now());
 * 
 * Product savedProduct = productRepository.save(product);
 * 
 * return savedProduct; }
 */

/*
 * HERE'S MY ORIGINAL CODE->
 * 
 * @Override public Product updateProduct(Long productId, Product reqProduct)
 * throws ProductException { Product product = findProductById(productId);
 * if(reqProduct.getQuantity()!=0) {
 * product.setQuantity(reqProduct.getQuantity()); } return
 * productRepository.save(product); }
 */

/*
 * HERE'S MY ORIGINAL CODE-> @Override public Page<Product> getAllProduct(String
 * category, List<String> colors, List<String> sizes, Integer minPrice, Integer
 * maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber,
 * Integer pageSize) {
 * 
 * Pageable pageable = PageRequest.of(pageNumber,pageSize); List<Product>
 * products = productRepository.filterProducts(category, minPrice, maxPrice,
 * minDiscount, sort); if(!colors.isEmpty()) {
 * products=products.stream().filter(p->colors.stream().anyMatch(c->c.
 * equalsIgnoreCase(p.getColor()))) .collect(Collectors.toList()); }
 * if(stock!=null) { if(stock.equals("in_stock")){
 * products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.
 * toList()); } else if(stock.equals("out_of_stock")) {
 * products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.
 * toList()); } } int startIndex = (int) pageable.getOffset(); int endIndex =
 * Math.min(startIndex + pageable.getPageSize(),products.size());
 * 
 * List<Product>pageContent = products.subList(startIndex, endIndex);
 * 
 * Page<Product>filteredProducts = new
 * PageImpl<>(pageContent,pageable,products.size()); return filteredProducts; }
 */

/*
 * @Override public Product createProduct(CreateProductRequest request) { //
 * Find top level category Category topLevelCategory =
 * categoryRepository.findByName(request.getTopLevelCategory()); if
 * (topLevelCategory == null) { topLevelCategory = new Category();
 * topLevelCategory.setName(request.getTopLevelCategory());
 * topLevelCategory.setLevel(1); topLevelCategory =
 * categoryRepository.save(topLevelCategory); }
 * 
 * // Find second level category under the top level Category
 * secondLevelCategory =
 * categoryRepository.findByNameAndParent(request.getSecondLevelCategory(),
 * topLevelCategory.getName()); if (secondLevelCategory == null) {
 * secondLevelCategory = new Category();
 * secondLevelCategory.setName(request.getSecondLevelCategory());
 * secondLevelCategory.setParentCategory(topLevelCategory);
 * secondLevelCategory.setLevel(2); secondLevelCategory =
 * categoryRepository.save(secondLevelCategory); }
 * 
 * // Find third level category under the second level Category
 * thirdLevelCategory =
 * categoryRepository.findByNameAndParent(request.getThirdLevelCategory(),
 * secondLevelCategory.getName()); if (thirdLevelCategory == null) {
 * thirdLevelCategory = new Category();
 * thirdLevelCategory.setName(request.getThirdLevelCategory());
 * thirdLevelCategory.setParentCategory(secondLevelCategory);
 * thirdLevelCategory.setLevel(3); thirdLevelCategory =
 * categoryRepository.save(thirdLevelCategory); }
 * 
 * // Create the product Product product = new Product();
 * product.setTitle(request.getTitle()); product.setColor(request.getColor());
 * product.setDescription(request.getDescription());
 * product.setDiscountedPrice(request.getDiscountedPrice());
 * product.setDiscountPercent(request.getDiscountedPercent());
 * product.setImageUrl(request.getImageUrl());
 * product.setPrice(request.getPrice()); product.setBrand(request.getBrand());
 * product.setSizes(request.getSize());
 * product.setQuantity(request.getQuantity());
 * product.setCategory(thirdLevelCategory);
 * product.setCreatedAt(LocalDateTime.now());
 * 
 * // Save the product Product savedProduct = productRepository.save(product);
 * 
 * return savedProduct; }
 */