package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposistory extends JpaRepository<Product,Long> {
}
