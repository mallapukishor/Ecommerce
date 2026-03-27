package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {

    ProductDTO addProduct(Long caterogyID, ProductDTO productDTO);

    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long caterogyID);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;


}
