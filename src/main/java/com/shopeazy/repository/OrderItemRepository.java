package com.shopeazy.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.shopeazy.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
