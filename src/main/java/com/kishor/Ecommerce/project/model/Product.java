package com.kishor.Ecommerce.project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    @NotBlank
    @Size(min = 3,message = "product name is at least 3 character")
    private String productName;
    private String image;
    @NotBlank
    @Size(min = 6,message = "product description is at least 6 characters")
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialprice;

    @ManyToOne
    @JoinColumn(name = " caterogy_id")
    private Caterogy caterogy;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;
}
