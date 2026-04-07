package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
