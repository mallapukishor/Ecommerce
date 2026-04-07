package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
