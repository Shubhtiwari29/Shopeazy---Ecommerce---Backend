package com.shopeazy.request;

import jakarta.validation.constraints.NotNull;

public class AddItemRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    private String size;
	private int quantity;
	private Integer price;
	
	public AddItemRequest() {
		
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
}
/*@NotBlank(message = "Size cannot be blank")
    private String size;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be positive")
    private Integer price;

    // Default constructor
    public AddItemRequest() {}

    // Parameterized constructor
    public AddItemRequest(Long productId, String size, int quantity, Integer price) {
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }*/
/*
 * public class AddItemRequest {
 * 
 * private Long productId; private String size; private int quantity; private
 * Integer price;
 * 
 * public AddItemRequest() { // TODO Auto-generated constructor stub }
 * 
 * public AddItemRequest(Long productId, String size, int quantity, Integer
 * price) { super(); this.productId = productId; this.size = size; this.quantity
 * = quantity; this.price = price; }
 * 
 * public Long getProductId() { return productId; }
 * 
 * public void setProductId(Long productId) { this.productId = productId; }
 * 
 * public String getSize() { return size; }
 * 
 * public void setSize(String size) { this.size = size; }
 * 
 * public int getQuantity() { return quantity; }
 * 
 * public void setQuantity(int quantity) { this.quantity = quantity; }
 * 
 * public Integer getPrice() { return price; }
 * 
 * public void setPrice(Integer price) { this.price = price; }
 * 
 * }
 */
