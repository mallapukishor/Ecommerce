package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.payload.CartDTO;

import java.util.List;

public interface CartService {
     CartDTO addProductToCart(Long productId,Integer quantity);

     List<CartDTO> getAllCarts();

     CartDTO getCart(String emailId, Long cartId);
}
