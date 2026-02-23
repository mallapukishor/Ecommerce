package com.kishor.Ecommerce.project.controller;

import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import com.kishor.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
   private ProductService productService;
    @PostMapping("/admin/categories/{caterogyID}/product")
    public ResponseEntity< ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long caterogyID){
        ProductDTO productDTO=productService.addProduct(caterogyID,product);
        return new ResponseEntity<>( productDTO, HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse>getAllProduct(){
        ProductResponse productResponse=productService.getAllProduct();
        return new ResponseEntity<>( productResponse,HttpStatus.OK);
    }
}
