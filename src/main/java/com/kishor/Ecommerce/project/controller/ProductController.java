package com.kishor.Ecommerce.project.controller;

import com.kishor.Ecommerce.project.config.AppConstant;
import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.payload.ProductResponse;
import com.kishor.Ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
   private ProductService productService;
    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProduct(
           @Valid @RequestBody ProductDTO productDTO,
            @PathVariable Long categoryId) {

        ProductDTO productDto = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse>getAllProduct(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
                                                        @RequestParam(name = "pageSize",defaultValue =AppConstant.PAGE_SIZE,required = false )Integer pageSize,
                                                        @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_PRODUCTS_BY,required = false)String sortBy,
                                                        @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_DIR,required = false)String sortOrder){
        ProductResponse productResponse=productService.getAllProduct(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>( productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/categories/{caterogyID}/products")
    public ResponseEntity<ProductResponse>getProductByCategory(@PathVariable Long caterogyID
            ,@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
                                                               @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
                                                               @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY)String sortBy,
                                                               @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_DIR)String sortOrder
                                                               ){
        ProductResponse productResponse=productService.searchByCategory(caterogyID,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue =AppConstant.SORT_PRODUCTS_BY,required = false)String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstant.SORT_DIR,required = false)String sortOrder
            ) {

        ProductResponse productResponse = productService.searchProductByKeyword(keyword,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>updateProduct(@Valid @RequestBody ProductDTO productDTO,@PathVariable Long productId){
        ProductDTO productdto=productService.updateProduct(productDTO,productId);
        return new ResponseEntity<>(productdto,HttpStatus.OK);
    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>deleteProduct(@PathVariable Long productId){
       ProductDTO productDTO= productService.deleteProduct(productId);
       return new ResponseEntity<>(productDTO,HttpStatus.OK);

    }
    @PutMapping("products/{productId}/image")
    public ResponseEntity<ProductDTO>updateProductImage(@PathVariable Long productId,
                                                        @RequestParam("image")MultipartFile image) throws IOException {
    ProductDTO productDTO=productService.updateProductImage(productId,image);
    return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
}
