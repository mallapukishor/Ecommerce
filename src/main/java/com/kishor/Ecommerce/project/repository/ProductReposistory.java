package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReposistory extends JpaRepository<Product,Long> {
    List<Product> findByCaterogyOrderByPriceAsc(Caterogy caterogy);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
