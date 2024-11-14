Shopeazy is a comprehensive E-commerce platform developed using Spring Boot and React, implementing a full-stack architecture. 
The application offers robust functionality for managing products, orders, and user authentication, providing a seamless shopping experience.

# Features
1) Full CRUD Operations: Manage product and order data with full Create, Read, Update, and Delete functionality.
2) User Authentication: Secure login and registration, role-based access control using JWT and Spring Security.
3) Admin Panel: Admin can manage products, view orders, and handle payment statuses.
4) Razorpay Integration: Simulates real-world payment scenarios through a dummy Razorpay payment gateway integration.
5) MySQL Database: Utilizes MySQL for persistent data storage with Hibernate ORM for efficient data management.

# Technologies Used
1) Backend: Java, Spring Boot, Spring Security, Hibernate, JWT
2) Frontend: React, Tailwind CSS, Redux
3) Database: MySQL
4) Payment Gateway: Razorpay (dummy transactions)
5) Testing: Postman for API testing

# API Endpoints
1) POST /api/auth/register - Register a new user
2) POST /api/auth/login - User login (returns JWT token)
4) GET /api/products - Get all products
5) POST /api/products - Create a new product (Admin)
6) PUT /api/products/{id} - Update product details (Admin)
7) DELETE /api/products/{id} - Delete a product (Admin)
8)GET /api/orders - Get all orders (Admin)
9) POST /api/orders - Create an order
10) POST /api/payment - Simulate a payment (Razorpay Integration)   

# Endpoints and response (example)

# Update Cart Item
URL: /api/cart_items/{cartItemId}
Method: PUT
Tags: cart-item-controller
Request Parameters:
cartItemId (integer, int64, required)
Request Headers:
Authorization (string, required)
Request Body:
{
  "$ref": "#/components/schemas/CartItem"
}
Responses:
200 OK
{
  "$ref": "#/components/schemas/CartItem"
}

# Delete Cart Item
URL: /api/cart_items/{cartItemId}
Method: DELETE
Tags: cart-item-controller
Request Parameters:
cartItemId (integer, int64, required)
Request Headers:
Authorization (string, required)
Responses:
200 OK
{
  "$ref": "#/components/schemas/ApiResponse"
}

# Add Item to Cart
URL: /api/cart/add
Method: PUT
Tags: cart-controller
Request Headers:
Authorization (string, required)
Request Body:
{
  "$ref": "#/components/schemas/AddItemRequest"
}
Responses:
200 OK
{
  "$ref": "#/components/schemas/ApiResponse"
}

# Product Data:

json
Copy code
{
  "id": 1,
  "name": "Sample Product",
  "price": 499.99,
  "description": "A sample product for testing",
  "stock": 100
}
# Order Data:

json
Copy code
{
  "id": 101,
  "userId": 1,
  "productId": 1,
  "quantity": 2,
  "totalPrice": 999.98,
  "orderStatus": "Pending"
}
# Future Enhancements
1) Payment Gateway Integration: Real payment gateway like Stripe or Razorpay for live transactions.
2) Order Tracking: Real-time order tracking with shipment status.
3) Enhanced Admin Panel: Add more admin features like sales reports, customer management, etc.
4) Testing: Add unit and integration tests using JUnit and Mockito for backend API testing.
5) Dockerization: Containerize the application with Docker for easier deployment.
