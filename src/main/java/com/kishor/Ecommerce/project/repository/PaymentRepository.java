package com.kishor.Ecommerce.project.repository;

import com.kishor.Ecommerce.project.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
