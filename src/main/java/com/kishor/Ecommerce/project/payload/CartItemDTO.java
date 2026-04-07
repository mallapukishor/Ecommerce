package com.kishor.Ecommerce.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long CartItemId;
    private CartDTO cart;
    private ProductDTO productDTO;
    private Integer quantity;
    private double discount;
    private double productPrice;
}
