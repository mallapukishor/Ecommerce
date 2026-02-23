package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.exceptions.RescourceNotFoundException;
import com.kishor.Ecommerce.project.model.Caterogy;
import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import com.kishor.Ecommerce.project.repository.CaterogyReposistory;
import com.kishor.Ecommerce.project.repository.ProductReposistory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceimp implements ProductService{
    @Autowired
    private ProductReposistory productReposistory;
    @Autowired
    private CaterogyReposistory caterogyReposistory;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDTO addProduct(Long caterogyID, Product product) {
        Caterogy caterogy=caterogyReposistory.findById(caterogyID)
                .orElseThrow(()-> new RescourceNotFoundException("Caterogy","CaterogyID",caterogyID));
        product.setCaterogy(caterogy);
        product.setImage("product.png");
        double specialPrice=product.getPrice()-((product.getDiscount()*0.01)*product.getPrice());
        product.setSpecialprice(specialPrice);
        Product savedProduct=productReposistory.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProduct() {
        List<Product>products=productReposistory.findAll();
        List<ProductDTO>productDTOS=products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }
}
