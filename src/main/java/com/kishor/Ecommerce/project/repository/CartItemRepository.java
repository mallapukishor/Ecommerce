package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id=?1 AND ci.product.id=?2")
    CartItem findCartItemByProductIdAndCartId(Long cartId, Long productId);
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id=?1 AND ci.id=?2")
    void deleteCartItemByProductIdAndCartId(Long cartId, Long productId);
}
