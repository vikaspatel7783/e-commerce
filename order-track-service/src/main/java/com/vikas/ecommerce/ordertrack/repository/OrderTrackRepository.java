package com.vikas.ecommerce.ordertrack.repository;

import com.vikas.ecommerce.ordertrack.message.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTrackRepository extends JpaRepository<OrderEvent, Long> {
}
