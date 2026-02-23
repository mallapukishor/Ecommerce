package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import com.kishor.Ecommerce.project.repository.ProductReposistory;


public interface ProductService {

    ProductDTO addProduct(Long caterogyID, Product product);

    ProductResponse getAllProduct();
}
