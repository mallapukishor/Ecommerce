package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.exceptions.ApiException;
import com.kishor.Ecommerce.project.exceptions.RescourceNotFoundException;
import com.kishor.Ecommerce.project.model.Cart;
import com.kishor.Ecommerce.project.model.CartItem;
import com.kishor.Ecommerce.project.model.Product;
import com.kishor.Ecommerce.project.payload.CartDTO;
import com.kishor.Ecommerce.project.payload.ProductDTO;
import com.kishor.Ecommerce.project.repository.CartItemRepository;
import com.kishor.Ecommerce.project.repository.CartRepository;
import com.kishor.Ecommerce.project.repository.ProductRepository;
import com.kishor.Ecommerce.project.utils.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CartServiceImp implements CartService{
    @Autowired
  private   CartRepository cartRepository;
    @Autowired
  private   ProductRepository productRepository;
    @Autowired
   private AuthUtil authUtil;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
  private   CartItemRepository cartItemRepository;
    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        //Find exsting cart or create one
        Cart cart=createCart();
        //Retrieve Product Details;
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new RescourceNotFoundException("product","productId",productId));
        //perform validations
     CartItem cartItem= cartItemRepository.findCartItemByProductIdAndCartId(
                 cart.getCartId(),
                 productId
         );
     if(cartItem !=null){
         throw new ApiException("product"+product.getProductName()+"already exsts in the cart");
     }
     if(product.getQuantity()==0){
         throw new ApiException(product.getProductName()+"is not available");
     }
     if(product.getQuantity()<quantity){
         throw new ApiException("please make an order of the"+product.getProducts()+
                 "less thana or equal to the quantity"+product.getQuantity());
     }
        //create cart item
        CartItem newCartItem=new CartItem();

     newCartItem.setProduct(product);
     newCartItem.setCart(cart);
     newCartItem.setQuantity(quantity);
     newCartItem.setDiscount(product.getDiscount());
     newCartItem.setProductPrice(product.getSpecialprice());

        //save cart item
        cartItemRepository.save(newCartItem);
        product.setQuantity(product.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialprice()*quantity));
        cartRepository.save(cart);
        //Return update cart
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        List<CartItem>cartItems=cart.getCartItems();
        Stream<ProductDTO>productDTOStream=cartItems.stream().map(item->{
            ProductDTO map=modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
      List<Cart>carts=cartRepository.findAll();
      if(carts.size()==0){
          throw new ApiException("no cart exist");
      }
      List<CartDTO>cartDTOS=carts.stream().map(cart ->{
          CartDTO cartDTO=modelMapper.map(cart, CartDTO.class);
          List<ProductDTO>products=cart.getCartItems().stream()
                  .map(p->modelMapper.map(p.getProduct(), ProductDTO.class))
                  .collect(Collectors.toList());
          cartDTO.setProducts(products);
          return cartDTO;
      }).collect(Collectors.toList());
        return cartDTOS;
    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart=cartRepository.findCartByEmailAndCartId(emailId,cartId);
        if(cart==null){
            throw new RescourceNotFoundException("cart","cartId",cartId);
        }
        CartDTO cartDTO=modelMapper.map(cart,CartDTO.class);
        cart.getCartItems().forEach(c->c.getProduct().setQuantity(c.getQuantity()));
        List<ProductDTO>products=cart.getCartItems().stream()
                .map(p->modelMapper.map(p.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());
        cartDTO.setProducts(products);
        return cartDTO;
    }

    private Cart createCart(){
        Cart useCart= cartRepository.findCartByEmail(authUtil.loggedInEmail());
        if(useCart !=null){
            return useCart;
        }
        Cart cart =new Cart();
        cart.setTotalPrice(0.00);
        cart.setUser(authUtil.loggedInUser());
        Cart newCart=cartRepository.save(cart);
        return newCart;
    }
}
