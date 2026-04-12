package com.kishor.Ecommerce.service;

import com.kishor.Ecommerce.project.payload.OrderDTO;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
