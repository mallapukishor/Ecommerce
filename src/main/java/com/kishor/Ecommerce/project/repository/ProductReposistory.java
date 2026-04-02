package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReposistory extends JpaRepository<Product,Long> {
    Page<Product> findByCaterogyOrderByPriceAsc(Caterogy caterogy, Pageable pageDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
